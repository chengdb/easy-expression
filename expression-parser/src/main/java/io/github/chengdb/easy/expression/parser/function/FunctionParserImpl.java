package io.github.chengdb.easy.expression.parser.function;


import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.FunctionArgs;
import io.github.chengdb.easy.expression.core.function.basic.ConstantFunction;
import io.github.chengdb.easy.expression.core.function.basic.NumberConstantFunction;
import io.github.chengdb.easy.expression.core.function.basic.StringConstantFunction;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionParseException;
import io.github.chengdb.easy.expression.parser.function.factory.FunctionClassInfo;
import io.github.chengdb.easy.expression.parser.function.factory.FunctionClassInfoFactory;
import io.github.chengdb.easy.expression.parser.util.CharCheckUtils;
import io.github.chengdb.easy.expression.parser.util.StringCheckUtil;

import java.util.*;

/**
 * 函数解析器实现
 * <p>
 *     使用栈。字符逐个处理。
 * </p>
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/6
 */
public class FunctionParserImpl extends FunctionParser {



    public FunctionParserImpl(FunctionClassInfoFactory functionClassInfoFactory) {
        super(functionClassInfoFactory);
    }


    /**
     * 解析函数调用字符串
     *
     * @param functionStr 函数调用字符串，格式必须是func(....)
     * @return 函数实体
     * @throws FunctionParseException 解析异常
     */
    @Override
    public Function<?> parse(String functionStr) throws FunctionParseException, FunctionClassException {
        ParseSource parseSource = new ParseSource(functionStr);
        while (parseSource.hasNext()) {
            char c = parseSource.getNextChar();
            try {
                switch (c) {
                    case '(' -> processLeftBracket(parseSource);
                    case ')' -> processRightBracket(parseSource);
                    case '\'' -> processQuotationMarks(parseSource);
                    case ',' -> processCommaMarks(parseSource);
                    default -> {}
                }
            } catch (FunctionParseException functionParseException) {
                // 向异常中添加错误位置
                functionParseException.setErrorCharIndex(parseSource.index);
                throw functionParseException;
            }
            if (parseSource.addToWord && (!Character.isWhitespace(c) || parseSource.inStringScope)) {
                parseSource.wordBuilder.append(c);
            } else {
                parseSource.addToWord = true;
            }
        }
        // 字符串还没闭合就结束了
        if (parseSource.inStringScope) {
            throw new FunctionParseException("未正确闭合 “'” ", parseSource.index);
        }
        // 函数缓存栈内还有函数缓存，说明没有正确闭合
        if (!parseSource.functionCacheStack.isEmpty()) {
            throw new FunctionParseException("未正确闭合 “)” ", parseSource.index);
        }
        return parseSource.currentFunctionCache.buildFunction();
    }

    /**
     * 处理左括号
     *
     * @param parseSource 解析的资源
     * @throws FunctionParseException 解析异常
     */
    private void processLeftBracket(ParseSource parseSource) throws FunctionParseException, FunctionClassException {
        // 如果是在字符串范围内，不做处理
        if (parseSource.inStringScope) {
            return;
        }
        // 创建一个非常量函数缓存
        FunctionCache notConstantFunctionCache = createNotConstantFunctionCache(parseSource.wordBuilder);
        // 这个函数缓存肯定是当前函数缓存的一个参数
        parseSource.currentFunctionCache.addArgFunctionCache(notConstantFunctionCache);
        // 切换当前函数缓存
        parseSource.functionCacheStack.push(parseSource.currentFunctionCache);
        parseSource.currentFunctionCache = notConstantFunctionCache;
        parseSource.addToWord = false;
    }

    /**
     * 处理右括号
     *
     * @param parseSource 解析的资源
     * @throws FunctionParseException 解析异常
     */
    private void processRightBracket(ParseSource parseSource) throws FunctionParseException {
        // 如果是在字符串范围内，不做处理
        if (parseSource.inStringScope) {
            return;
        }
        char beforeChar = parseSource.getBeforeChar();
        // 如果前一个字符是左括号，则说明是一个无参函数调用，就跳过。此时parseSource.wordBuilder为空
        if (beforeChar != '(') {
            if (parseSource.functionCacheStack.isEmpty()) {
                throw new FunctionParseException("不能匹配的‘)’");
            }
            // 同是：一个数字的结束和一个函数调用的结束
            if (beforeChar != ')' && beforeChar != '\'') {
                // 数字结束
                parseSource.currentFunctionCache.addArgFunctionCache(new ConstantFunctionCache(word(parseSource.wordBuilder)));
            }
        }
        parseSource.currentFunctionCache = parseSource.functionCacheStack.pop();
        parseSource.addToWord = false;
    }

    /**
     * 处理单引号
     *
     * @param parseSource 解析的资源
     * @throws FunctionParseException 解析异常
     */
    private void processQuotationMarks(ParseSource parseSource) throws FunctionParseException {
        // 一个字符串开始
        if (!parseSource.inStringScope) {
            parseSource.inStringScope = true;
        }
        // 一个字符串结束
        else if (parseSource.getBeforeChar() != '\\') {
            nextCharCheckSeparator(parseSource);
            parseSource.inStringScope = false;
            parseSource.wordBuilder.append('\'');
            parseSource.currentFunctionCache.addArgFunctionCache(new ConstantFunctionCache(word(parseSource.wordBuilder)));
            parseSource.addToWord = false;
        }
    }

    /**
     * 处理逗号
     *
     * @param parseSource 解析的资源
     * @throws FunctionParseException 解析异常
     */
    private void processCommaMarks(ParseSource parseSource) throws FunctionParseException {
        // 如果是在字符串范围内，不做处理
        if (parseSource.inStringScope) {
            return;
        }
        Character readNextValidChar = parseSource.readNextValidChar();
        // ','后面应该跟的是一个表达式（数字或字符串或函数调用）
        if (readNextValidChar == null || !CharCheckUtils.isExpressionStart(readNextValidChar)) {
            parseSource.toNextValidChar();
            throw new FunctionParseException();
        }
        char beforeChar = parseSource.getBeforeChar();
        // 一个数字的结束
        if (beforeChar != ')' && beforeChar != '\'') {
            // 数字结束
            parseSource.currentFunctionCache.addArgFunctionCache(new ConstantFunctionCache(word(parseSource.wordBuilder)));
        }
        parseSource.addToWord = false;
    }

    /**
     * 创建不是常量函数缓存的函数缓存
     *
     * @param wordBuilder 字
     * @return 函数缓存
     * @throws FunctionParseException 解析异常
     */
    private FunctionCache createNotConstantFunctionCache(StringBuilder wordBuilder) throws FunctionParseException, FunctionClassException {
        String word = word(wordBuilder);

        FunctionClassInfo functionClassInfo = functionClassInfoFactory.getFunctionClassInfo(word);

        if (functionClassInfo == null) {
            throw new FunctionParseException("未知的函数名称：" + word);
        }

        if (functionClassInfo.isArgFunction) {
            return new ArgFunctionCache(functionClassInfo);
        } else {
            return new NotArgFunctionCache(functionClassInfo);
        }
    }

    /**
     * 读取缓存的字，并将缓存清空
     *
     * @param wordBuilder 字构建器
     * @return  读取的字
     */
    private String word(StringBuilder wordBuilder) {
        String word = wordBuilder.toString();
        wordBuilder.delete(0, wordBuilder.length());
        return word;
    }

    /**
     * 检查下一个有效字符，必须是隔断字符。
     * <p>隔断字符：','、')'</p>
     *
     * @param parseSource 解析资源
     * @throws FunctionParseException 检查失败
     */
    private void nextCharCheckSeparator(ParseSource parseSource) throws FunctionParseException {
        Character nextChar = parseSource.readNextValidChar();
        if (nextChar == null || !(nextChar == ',' || nextChar == ')')) {
            parseSource.toNextValidChar();
            throw new FunctionParseException();
        }
    }

    /**
     * 解析时使用的资源
     */
    private static class ParseSource {
        // 函数实体栈
        private final Deque<FunctionCache> functionCacheStack = new LinkedList<>();
        // 字栈
        private final StringBuilder wordBuilder = new StringBuilder();
        // 是否在字符串域内
        private boolean inStringScope = false;
        // 当前的函数构造缓存
        private FunctionCache currentFunctionCache = new HeaderFunctionCache();
        // 函数字符串的chars
        private final char[] functionStrChars;
        // 是否将当前字符加入到字
        private boolean addToWord = true;
        // 当前字符下标
        private int index = -1;
        // 最大下标
        private final int maxIndex;

        private ParseSource(String functionStr) {
            this.functionStrChars = functionStr.toCharArray();
            this.maxIndex = functionStrChars.length - 1;
        }

        /**
         * 获取下一个字符
         *
         * @return 下一个字符
         */
        private char getNextChar() {
            return functionStrChars[++index];
        }

        /**
         * 定位至下一个有效字符（非字符串内）
         */
        private void  toNextValidChar() {
            while (hasNext()) {
                if (Character.isWhitespace(getNextChar())) {
                    continue;
                }
                break;
            }
        }

        /**
         * 读取下一个有效字符（非字符串内）
         *
         * @return 有效字符，到结尾则返回null
         */
        private Character readNextValidChar() {
            char c;
            for (int i = index + 1; i <= maxIndex; i++) {
                if (Character.isWhitespace(c = functionStrChars[i])) {
                    continue;
                }
                return c;
            }
            return null;
        }

        /**
         * 是否有下一个字符
         *
         * @return 是否
         */
        private boolean hasNext() {
            return index < maxIndex;
        }

        /**
         * 获取前一个有效字符（非字符串内）
         * <p>!!!不能在字符串范围内使用</p>
         */
        private char getBeforeChar() {
            char c = 0;
            for (int i = index - 1; i >= 0; i--) {
                if (Character.isWhitespace(c = functionStrChars[i])) {
                    continue;
                }
                break;
            }
            return c;
        }
    }

    /**
     * 有参数的函数构建缓存
     */
    private static class ArgFunctionCache extends FunctionCache {
        public final FunctionClassInfo functionClassInfo;

        /**
         * 入参缓存列表
         */
        private final List<FunctionCache> argFunctionCaches = new LinkedList<>();


        private ArgFunctionCache(FunctionClassInfo functionClassInfo) {
            super(functionClassInfo.funcAnnotation.returnType());
            this.functionClassInfo = functionClassInfo;
        }

        @Override
        public void addArgFunctionCache(FunctionCache functionCache) throws FunctionParseException {
            if (functionClassInfo.funcAnnotation.argNum() > 0) {
                // 函数入参数量检查
                if (argFunctionCaches.size() == functionClassInfo.funcAnnotation.argNum()) {
                    throw new FunctionParseException(functionClassInfo.funcAnnotation.name() + "函数入参数量应该为"
                            + functionClassInfo.funcAnnotation.argNum());
                }
                // 函数入参类型检查
                int argTypeIndex = argFunctionCaches.size();
                Class<?> type = functionClassInfo.funcAnnotation.argsType()[argTypeIndex];
                checkArgType(functionCache, argTypeIndex, type);
            }
            else if (functionClassInfo.funcAnnotation.argNum() == 0) {
                argFunctionCaches.add(functionCache);
            }
            else {
                int argMinNum = -functionClassInfo.funcAnnotation.argNum();
                int argTypeIndex = argFunctionCaches.size();
                Class<?> type;
                if (argFunctionCaches.size() < argMinNum) {
                    type = functionClassInfo.funcAnnotation.argsType()[argTypeIndex];
                } else {
                    type = functionClassInfo.funcAnnotation.argsType()[argMinNum - 1];
                }
                checkArgType(functionCache, argTypeIndex, type);
            }
        }

        /**
         * 检检查参数类型
         * @param functionCache 函数缓存
         * @param argTypeIndex 参数下标
         * @param type 要求参数类型
         * @throws FunctionParseException 检查失败
         */
        private void checkArgType(FunctionCache functionCache, int argTypeIndex, Class<?> type) throws FunctionParseException {
            if (functionCache.returnType == type || type == Object.class || functionCache.returnType == Object.class) {
                argFunctionCaches.add(functionCache);
            } else {
                throw new FunctionParseException(functionClassInfo.funcAnnotation.name() + "函数的第" + (argTypeIndex + 1) +
                        "个入参类型应为" + type.getName() + "，现提供的类型为" + functionCache.returnType.getName());
            }
        }

        @Override
        public Function<?> buildFunction() throws FunctionParseException, FunctionClassException {
            // 入参数量检查
            if (functionClassInfo.funcAnnotation.argNum() < 0 && argFunctionCaches.size() < -functionClassInfo.funcAnnotation.argNum()) {
                throw new FunctionParseException(functionClassInfo.funcAnnotation.name() + "函数的入参数量应不少于" + -functionClassInfo.funcAnnotation.argNum());
            }
            // 构建函数
            // 构建参数
            Function<?>[] paramFunctions = new Function<?>[argFunctionCaches.size()];
            int i = 0;
            for (FunctionCache functionCache : argFunctionCaches) {
                paramFunctions[i++] = functionCache.buildFunction();
            }
            Function<?> function = functionClassInfo.getFunction();
            ((ArgFunction<?>) function).setFunctionArgs(new FunctionArgs(paramFunctions));
            return function;
        }
    }

    /**
     * 没有参数的函数构建缓存
     */
    private static class NotArgFunctionCache extends FunctionCache{

        public final FunctionClassInfo functionClassInfo;

        private NotArgFunctionCache(FunctionClassInfo functionClassInfo) {
            super(functionClassInfo.funcAnnotation.returnType());
            this.functionClassInfo = functionClassInfo;
        }

        @Override
        public Function<?> buildFunction() throws FunctionClassException {
            return functionClassInfo.getFunction();
        }

        @Override
        public void addArgFunctionCache(FunctionCache functionCache) throws FunctionParseException {
            throw new FunctionParseException(functionClassInfo.funcAnnotation.name() + "函数不应该有入参");
        }
    }

    /**
     * 常量函数构建缓存
     */
    private static class ConstantFunctionCache extends FunctionCache {

        /**
         * 常量池
         */
        private static final Map<String, ConstantFunction<?>> CONSTANT_POOL = new HashMap<>(64);

        private final Function<?> constantFunction;

        private ConstantFunctionCache(String constantStr) throws FunctionParseException {
            ConstantFunction<?> function = getConstantFunction(constantStr);
            setReturnType(function.valueType());
            this.constantFunction = function;
        }

        @Override
        public Function<?> buildFunction() {
            return constantFunction;
        }

        @Override
        public void addArgFunctionCache(FunctionCache functionCache) throws FunctionParseException {
            throw new FunctionParseException("常量函数不能设置入参");
        }

        /**
         * 获取常量函数实体
         *
         * @param constantStr 常量字符串
         * @return 常量函数实体实体
         * @throws FunctionParseException 解析异常
         */
        private static ConstantFunction<?> getConstantFunction(String constantStr) throws FunctionParseException {
            ConstantFunction<?> function = CONSTANT_POOL.get(constantStr);
            if (function == null) {
                function = createConstantFunction(constantStr);
                CONSTANT_POOL.put(constantStr, function);
            }
            return function;
        }

        /**
         * 创建常量函数
         *
         * @param constantStr 常量字符串
         * @return 常量函数
         * @throws FunctionParseException 解析异常
         */
        private static ConstantFunction<?> createConstantFunction(String constantStr) throws FunctionParseException {
            // 检查是否是数字
            if (StringCheckUtil.numberCheck(constantStr)) {
                return new NumberConstantFunction(constantStr);
            }
            // 检查是否是字符串
            else if (StringCheckUtil.stringCheck(constantStr)) {
                return new StringConstantFunction(constantStr);
            }
            throw new FunctionParseException("未能识别的常量：" + constantStr);
        }

    }

    /**
     * 头
     */
    private static class HeaderFunctionCache extends FunctionCache {

        private FunctionCache functionCache;

        private HeaderFunctionCache() {
            super(null);
        }

        @Override
        public Function<?> buildFunction() throws FunctionParseException, FunctionClassException {
            return functionCache.buildFunction();
        }

        @Override
        public void addArgFunctionCache(FunctionCache functionCache) {
            this.functionCache = functionCache;
        }
    }


    /**
     * 函数构建缓存接口
     */
    private abstract static class FunctionCache {
        private Class<?> returnType;

        private FunctionCache(Class<?> returnType) {
            this.returnType = returnType;
        }

        private FunctionCache() {}

        /**
         * 设置函数的返回类型
         * @param returnType 返回类型
         */
        protected void setReturnType(Class<?> returnType) {
            this.returnType = returnType;
        }

        /**
         * 构建函数
         * @return 构建结果
         * @throws FunctionParseException 构建异常
         */
        abstract Function<?> buildFunction() throws FunctionParseException, FunctionClassException;

        /**
         * 添加函数的入参（函数缓存）
         * @param functionCache 函数缓存
         * @throws FunctionParseException 异常
         */
        abstract void addArgFunctionCache(FunctionCache functionCache) throws FunctionParseException;

    }


}
