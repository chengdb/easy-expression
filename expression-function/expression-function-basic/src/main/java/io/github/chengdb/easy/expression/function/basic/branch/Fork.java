package io.github.chengdb.easy.expression.function.basic.branch;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * 分叉
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "fork",
        argNum = 3,
        argsType = {Boolean.class, Object.class, Object.class},
        describe = "分叉(是否选择岔路1:Boolean, 岔路1:Object, 岔路2:Object):(结果:Object)"
)
public class Fork extends ArgFunction<Object> {
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        Boolean select1 = functionArgs.getArgResultNotNull(context, Boolean.class);
        if (select1) {
            return functionArgs.getArgResult(1, context, Object.class);
        }
        return functionArgs.getArgResult(2, context, Object.class);

    }
}
