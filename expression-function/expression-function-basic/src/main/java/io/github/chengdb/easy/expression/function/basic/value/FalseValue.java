package io.github.chengdb.easy.expression.function.basic.value;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.NonArgFunction;

/**
 * True常量
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "_false",
        returnType = Boolean.class,
        describe = "True常量():(True:Boolean)"
)
public class FalseValue extends NonArgFunction<Boolean> {
    @Override
    protected Boolean execute(Context context) {
        return false;
    }
}
