package io.github.chengdb.easy.expression.test.function;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.math.BigDecimal;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "arrayNumAdd",
        argNum = 1,
        argsType = Number[].class,
        returnType = Number.class,
        describe = "数字数组相加(数字数组:Number[]):相加结果:Number"
)
public class ArrayNumAdd extends ArgFunction<Number> {
    @Override
    protected Number execute(Context context) throws FunctionExecuteException {
        // Number[] numbers = functionArgs.getArgResultNotNull(1, context, Number[].class);
        Number[] numbers = functionArgs.getArgResult(context, Number[].class);
        BigDecimal result = new BigDecimal(0);
        for (Number number : numbers) {
            if (number instanceof BigDecimal bDNumber) {
                result = result.add(bDNumber);
            } else {
                result = result.add(new BigDecimal(number.toString()));
            }
        }
        return result;
    }
}
