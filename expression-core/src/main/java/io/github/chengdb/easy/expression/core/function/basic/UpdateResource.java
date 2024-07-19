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
        name = UpdateResource.FUNCTION_NAME,
        argNum = 2,
        argsType = {String.class, Object.class},
        describe = "更新资源(资源名称:String, 资源:Object):(原资源:Object)")
public class UpdateResource extends ArgFunction<Object> {
    public static final String FUNCTION_NAME = "$RU";
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        String resourceName = functionArgs.getArgResultNotNull(context, String.class);
        Object newResource = functionArgs.getArgResult(1, context, Object.class);

        Object oldResource = context.getResource(resourceName);

        if (context.updateResource(resourceName, newResource)) {
            return oldResource;
        }
        throw new FunctionExecuteException("没有名为" + resourceName + "的资源，无法更新");
    }
}
