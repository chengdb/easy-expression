package io.github.chengdb.easy.expression.test.function;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;
import io.github.chengdb.easy.expression.core.function.value.FuncStaticValue;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "output",
        argNum = 1
)
public class OutputTestFunction extends ArgFunction<Object> {

    @FuncStaticValue
    private static OutputManager outputManager;

    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        Object argResult = functionArgs.getArgResult(context, Object.class);
        outputManager.output(argResult.toString());
        return argResult;
    }
}
