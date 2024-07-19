package io.github.chengdb.easy.expression.function.basic.collection;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.FunctionArgs;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.util.List;


/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "listPut",
        argNum = -2,
        argsType = {List.class, Object.class},
        describe = "向列表中放元素(列表:List<Object>, ...元素:Object):(存的最后一个:Object)"
)
public class ListPut extends ArgFunction<List<Object>> {
    @Override
    protected List<Object> execute(Context context) throws FunctionExecuteException {
        FunctionArgs.ArgsIterator iterator = functionArgs.iterator();
        @SuppressWarnings("unchecked")
        List<Object> list = iterator.nextNotNull(context, List.class);
        while (iterator.hasNext()) {
            list.add(iterator.next(context, Object.class));
        }
        return list;
    }
}
