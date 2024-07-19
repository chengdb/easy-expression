package io.github.chengdb.easy.expression.function.basic.value;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.NonArgFunction;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "_emptyList",
        describe = "空列表():(列表: List)"
)
public class EmptyList extends NonArgFunction<List<Object>> {
    @Override
    protected List<Object> execute(Context context) throws FunctionExecuteException {
        return new LinkedList<>();
    }
}
