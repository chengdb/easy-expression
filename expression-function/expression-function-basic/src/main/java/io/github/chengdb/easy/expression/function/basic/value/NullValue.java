package io.github.chengdb.easy.expression.function.basic.value;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.NonArgFunction;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "_null",
        describe = "null值():(null:Object)"
)
public class NullValue extends NonArgFunction<Object> {
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        return null;
    }
}
