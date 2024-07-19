package io.github.chengdb.easy.expression.function.basic;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.FunctionArgs;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "segment",
        describe = "片段(...要执行的函数:Object):(最后一个函数的结果:Object)"
)
public class Segment extends ArgFunction<Object> {
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        FunctionArgs.ArgsIterator iterator = functionArgs.iterator();
        Object result = null;
        while (iterator.hasNext()) {
            result = iterator.next(context, Object.class);
        }
        return result;
    }
}
