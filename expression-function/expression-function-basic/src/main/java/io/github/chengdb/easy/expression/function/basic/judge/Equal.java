package io.github.chengdb.easy.expression.function.basic.judge;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.FunctionArgs;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * 判等
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "equal",
        returnType = Boolean.class,
        argNum = 2,
        argsType = {Object.class, Object.class},
        describe = "判等(值1:Object, 值2:Object):(是否相等:Boolean)"
)
public class Equal extends ArgFunction<Boolean> {
    @Override
    protected Boolean execute(Context context) throws FunctionExecuteException {
        return equal(functionArgs, context);
    }

    public static boolean equal(FunctionArgs functionArgs, Context context) throws FunctionExecuteException {
        Object value1 = functionArgs.getArgResult(context, Object.class);
        Object value2 = functionArgs.getArgResult(1, context, Object.class);
        if (value1 == null) {
            return value2 == null;
        }
        return value1.equals(value2);
    }


}
