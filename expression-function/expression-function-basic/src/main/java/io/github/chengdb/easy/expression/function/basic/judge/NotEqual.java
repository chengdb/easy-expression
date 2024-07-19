package io.github.chengdb.easy.expression.function.basic.judge;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "notEqual",
        returnType = Boolean.class,
        argNum = 2,
        argsType = {Object.class, Object.class},
        describe = "判不等(值1:Object, 值2:Object):(是否不等:Boolean)"
)
public class NotEqual extends ArgFunction<Boolean> {
    @Override
    protected Boolean execute(Context context) throws FunctionExecuteException {
        return !Equal.equal(functionArgs, context);
    }
}
