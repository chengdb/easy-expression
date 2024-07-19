package io.github.chengdb.easy.expression.parser.expression;

import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.value.holder.FuncStaticValueHolder;
import io.github.chengdb.easy.expression.parser.expression.exception.DevelopLevelExpressionParseException;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import io.github.chengdb.easy.expression.parser.expression.exception.UserLevelExpressionParseException;
import io.github.chengdb.easy.expression.parser.function.FunctionParser;
import io.github.chengdb.easy.expression.parser.function.FunctionParserImpl;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionParseException;
import io.github.chengdb.easy.expression.parser.function.factory.FunctionClassGetter;
import io.github.chengdb.easy.expression.parser.function.factory.FunctionClassInfoFactory;
import io.github.chengdb.easy.expression.parser.util.CharCheckUtils;
import io.github.chengdb.easy.expression.parser.util.StringCheckUtil;
import io.github.chengdb.easy.expression.core.expression.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 表达式解析器。
 * <p>文法如下：</p>
 * <ul>
 *     <li>表达式 ::= 函数表达式 | 常量表达式 | 复合表达式</li>
 *     <li>函数表达式 ::= 函数调用</li>
 *     <li>常量表达式 ::= 常量</li>
 *     <li>复合表达式 ::= 表达式; 表达式</li>
 *     <li>常量 ::= 字符串 | 数字</li>
 *     <li>函数调用 ::= 标识符( 参数列表 )</li>
 *     <li>参数列表 ::= 参数 | 参数, 参数列表 | 空</li>
 *     <li>参数 ::= 常量 | 函数调用</li>
 *     <li>数字 ::= 小数和整数</li>
 *     <li>字符串 ::= '字符集合'</li>
 *     <li>标识符 ::= java标识符</li>
 * </ul>
 * <p>比如：</p>
 * <ul>
 *     <li>func1()</li>
 *     <li>func1(); func2()</li>
 *     <li>func1(123, '321'); func2(func3(), 123)</li>
 *     <li>func1(123, '321'); func2(func3(func4()), 123)</li>
 * </ul>
 *
 *
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/10/31
 */
public class ExpressionParser {

    /**
     * 用newInstance()方法创建
     */
    private ExpressionParser(FunctionParser functionParser){
        this.functionParser = functionParser;
    }

    /**
     * 函数解析器
     */
    private  final FunctionParser functionParser;

    public static ExpressionParser newInstance() {
        return newInstance(new FunctionClassGetter() {
            @Override
            public Class<? extends Function<?>> functionClass(String functionName) {
                return null;
            }
        });
    }


    public static ExpressionParser newInstance(FunctionClassGetter functionClassGetter) {
        FunctionParser functionParser;
        FunctionClassInfoFactory functionClassInfoFactory;
        functionClassInfoFactory = new FunctionClassInfoFactory(functionClassGetter);
        functionParser = new FunctionParserImpl(functionClassInfoFactory);
        return new ExpressionParser(functionParser);
    }

    public static ExpressionParser newInstance(FunctionClassGetter functionClassGetter, FuncStaticValueHolder funcStaticValueHolder) {
        FunctionParser functionParser;
        FunctionClassInfoFactory functionClassInfoFactory;
        functionClassInfoFactory = new FunctionClassInfoFactory(funcStaticValueHolder, functionClassGetter);
        functionParser = new FunctionParserImpl(functionClassInfoFactory);
        return new ExpressionParser(functionParser);
    }

    public static ExpressionParser newInstance( FuncStaticValueHolder funcStaticValueHolder) {
        FunctionParser functionParser;
        FunctionClassInfoFactory functionClassInfoFactory;
        functionClassInfoFactory = new FunctionClassInfoFactory(funcStaticValueHolder, new FunctionClassGetter() {
            @Override
            public Class<? extends Function<?>> functionClass(String functionName) {
                return null;
            }
        });
        functionParser = new FunctionParserImpl(functionClassInfoFactory);
        return new ExpressionParser(functionParser);
    }

    /**
     * 解析表达式字符串，构造表达式实体
     * @param expressionStr 表达式字符串
     * @return 表达式实体
     * @throws ExpressionParseException 解析异常
     */
    public Expression parse(String expressionStr) throws ExpressionParseException {
        String expressionTrim = expressionStr.trim();
        char firstChar = expressionTrim.charAt(0);
        // 是字符串表达式
        if (firstChar == '\'') {
            return parseStringExpression(expressionTrim);
        }
        // 数字表达式
        else if (CharCheckUtils.isNumStart(firstChar)) {
            return parseNumberExpression(expressionTrim);
        }
        // 函数表达式或复合表达式
        else if (CharCheckUtils.isFuncStart(firstChar)) {
            try {
                return parseFunctionExpressionOrCompositionExpression(expressionStr);
            } catch (FunctionClassException fde) {
                throw new DevelopLevelExpressionParseException(fde.getMessage());
            } catch (FunctionParseException fpe) {
                int errorCharIndex = fpe.getErrorCharIndex();
                throw new UserLevelExpressionParseException(errorCharIndex, "出错位置：" + errorCharIndex + "。"
                        + fpe.getMessage() + "。");
            }
        }
        // 无法识别
        else {
            throw new UserLevelExpressionParseException(1, "无法识别的表达式");
        }
    }

    /**
     * 解析为函数表达式或符合表达式实体
     *
     * @param rawExpression 原始表达式字符串
     * @return 表达式实体
     * @throws ExpressionParseException 解析异常
     */
    private Expression parseFunctionExpressionOrCompositionExpression(String rawExpression) throws ExpressionParseException, FunctionClassException, FunctionParseException {
        // 将符合表达式切割并去掉无用空字符
        List<String> functionStrList = splitExpressionToFunctions(rawExpression);

        if (functionStrList.size() == 1) {
            // 唯一的函数字符串
            String onlyFunctionStr = functionStrList.get(0);
            if (StringCheckUtil.functionCallCheck(onlyFunctionStr)) {
                return new FunctionExpression(parseFunction(onlyFunctionStr));
            } else {
                throw new UserLevelExpressionParseException(1, "无法识别的函数调用");
            }
        } else {
            // 尝试解析为复合表达式
            // 检查
            for (String s : functionStrList) {
                if (!StringCheckUtil.functionCallCheck(s)) {
                    throw new UserLevelExpressionParseException(1, "无法识别的函数调用：" + s);
                }
            }
            // 构造复合表达式
            Function<?>[] functions = new Function<?>[functionStrList.size()];
            int i = 0;
            for (String functionStr : functionStrList) {
                try {
                    functions[i++] = parseFunction(functionStr);
                } catch (FunctionParseException e) {
                    int j = i - 2, charCount = 0;
                    for (; j >= 0; j--) {
                        charCount += functionStrList.get(j).length();
                    }
                    e.setErrorCharIndex(charCount + e.getErrorCharIndex() + (i - 1));
                    throw e;
                }
            }
            return new CompositionExpression(functions);
        }
    }

    /**
     * 将符合表达式字符串拆分成函数字符串
     *
     * @param expressionStr 表达式字符串
     * @return 函数字符串列表
     */
    private List<String> splitExpressionToFunctions(String expressionStr) {
        // 用于存放拆开的函数
        List<String> functionStrList = new LinkedList<>();
        boolean notStrScope = true;
        StringBuilder stringBuilder = new StringBuilder();
        char[] charArray = expressionStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == ';' && notStrScope) {
                if (stringBuilder.length() > 0) {
                    functionStrList.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }
            } else if (c == '\'' && (notStrScope || expressionStr.charAt(i-1) != '\\')) {
                notStrScope = !notStrScope;
                stringBuilder.append(c);
            } else {
                stringBuilder.append(c);
            }
        }
        if (stringBuilder.length() > 0) {
            functionStrList.add(stringBuilder.toString());
        }
        return functionStrList;
    }

    /**
     * 解析函数字符串为函数实体
     *
     * @param functionStr 要解析的函数字符串
     * @return 函数实体
     */
    private Function<?> parseFunction(String functionStr) throws FunctionClassException, FunctionParseException {
        return functionParser.parse(functionStr);
    }

    /**
     * 解析为字符串表达式
     *
     * @param expressionStr 处理后的表达式字符串
     * @return 字符串表达式
     * @throws ExpressionParseException 解析异常
     */
    private Expression parseStringExpression(String expressionStr) throws ExpressionParseException {
        // 检查结尾
        if (expressionStr.charAt(expressionStr.length()-1) != '\'') {
            throw new UserLevelExpressionParseException(1, "未闭合的“'”");
        }
        // 检查转义
        int stringEnd = expressionStr.length() - 2;

        int charIdx = 1;
        while (charIdx <= stringEnd) {
            char c = expressionStr.charAt(charIdx);
            if (c == '\\') {
                char nextChar = expressionStr.charAt(++charIdx);
                if (charIdx == stringEnd || (nextChar != '\'' && nextChar != '\\')) {
                    throw new UserLevelExpressionParseException(charIdx + 1, "字符串中的”\\“需转义为”\\\\'“");
                }
            } else if (c == '\'') {
                throw new UserLevelExpressionParseException(charIdx + 1, "字符串中的”'“需转义为”\\'“");
            }
            charIdx++;
        }
        return new StringExpression(expressionStr);
    }


    /**
     * 解析为数字表达式
     *
     * @param expressionStr 处理后的表达式字符串
     * @return 数字表达式
     * @throws ExpressionParseException 解析异常
     */
    private Expression parseNumberExpression(String expressionStr) throws ExpressionParseException {
        if (StringCheckUtil.numberCheck(expressionStr)) {
            return new NumberExpression(expressionStr);
        }
        throw new UserLevelExpressionParseException(1, "错误的数字：" + expressionStr);
    }

}
