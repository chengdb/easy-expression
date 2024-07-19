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
        name = "print",
        describe = "打印到控制台(...内容:Object):null"
)
public class Print extends ArgFunction<Object> {
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        FunctionArgs.ArgsIterator iterator = functionArgs.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next(context, Object.class));
        }
        return null;
    }
}
