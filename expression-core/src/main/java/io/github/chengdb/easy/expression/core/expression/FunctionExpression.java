package io.github.chengdb.easy.expression.core.expression;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * 函数表达式
 * @author chengdabai
 * @since 1.0.0
 */
public class FunctionExpression extends Expression {

    /**
     * 函数实体
     */
    private final Function<?> function;

    public FunctionExpression(Function<?> function) {
        this.function = function;
    }

    @Override
    public Object execute0(Context context) throws FunctionExecuteException {
        return function.execute0(context);
    }
}
