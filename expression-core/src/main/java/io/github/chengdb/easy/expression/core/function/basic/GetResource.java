package io.github.chengdb.easy.expression.core.function.basic;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = GetResource.FUNCTION_NAME,
        argNum = 1,
        argsType = {String.class},
        describe = "获取资源(资源名称:String):(资源:Object)"
)
public class GetResource extends ArgFunction<Object> {
    public static final String FUNCTION_NAME = "$R";

    @Override
    public Object execute(Context context) throws FunctionExecuteException {
        String name = functionArgs.getArgResult(context, String.class);
        if (name == null) {
            throw new FunctionExecuteException("资源名称为空");
        }
        return context.getResource(name);
    }
}
