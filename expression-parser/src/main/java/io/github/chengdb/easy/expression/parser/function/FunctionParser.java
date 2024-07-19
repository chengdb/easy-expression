package io.github.chengdb.easy.expression.parser.function;


import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionParseException;
import io.github.chengdb.easy.expression.parser.function.factory.FunctionClassInfoFactory;

/**
 * 函数解析器
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/7
 */
public abstract class FunctionParser {
    protected final FunctionClassInfoFactory functionClassInfoFactory;

    protected FunctionParser(FunctionClassInfoFactory functionClassInfoFactory) {
        this.functionClassInfoFactory = functionClassInfoFactory;
    }

    /**
     * 解析函数调用字符串
     *
     * @param functionStr 函数调用字符串，格式必须是func(....)且没有无效空字符
     * @return 函数实体
     * @throws FunctionParseException 解析异常
     */
    public abstract Function<?> parse(String functionStr) throws FunctionParseException, FunctionClassException;

}
