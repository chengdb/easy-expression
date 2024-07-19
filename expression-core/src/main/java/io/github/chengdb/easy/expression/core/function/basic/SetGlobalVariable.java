package io.github.chengdb.easy.expression.core.function.basic;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = SetGlobalVariable.FUNCTION_NAME,
        argNum = 2,
        argsType = {String.class, Object.class},
        describe = "设置全局变量(变量名:String, 变量:Object):(原变量:Object)"
)
public class SetGlobalVariable extends ArgFunction<Object> {
    public static final String FUNCTION_NAME = "$SGV";
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        String name = functionArgs.getArgResult(context, String.class);
        Object value = functionArgs.getArgResult(1, context, Object.class);
        return Context.setGlobalVariable(name, value);
    }
}
