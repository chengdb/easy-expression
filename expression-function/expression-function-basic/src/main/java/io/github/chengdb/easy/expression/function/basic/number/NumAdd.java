package io.github.chengdb.easy.expression.function.basic.number;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.FunctionArgs;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.math.BigDecimal;

/**
 * 数字加法
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "numAdd",
        argNum = -2,
        argsType = {Number.class, Number.class},
        returnType = Number.class,
        describe = "数字加法(加数1:Number, 加数2:Number, ...):(所有加数之和:Number)"
)
public class NumAdd extends ArgFunction<Number> {
    @Override
    protected Number execute(Context context) throws FunctionExecuteException {
        BigDecimal result = new BigDecimal(0);
        FunctionArgs.ArgsIterator iterator = functionArgs.iterator();
        while (iterator.hasNext()) {
            Number number = iterator.next(context, Number.class);
            if (number instanceof BigDecimal) {
                result = result.add((BigDecimal) number);
            } else {
                result = result.add(new BigDecimal(number.toString()));
            }
        }
        return result;
    }
}
