package io.github.chengdb.easy.expression.core.function.basic;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.NonArgFunction;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * 快速获取资源
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "$RF",
        describe = "快速获取资源():(资源:Object)"
)
public class GetResourceFast extends NonArgFunction<Object> {
    public static final String FUNCTION_NAME = "$RF";

    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        return context.getResource();
    }
}
