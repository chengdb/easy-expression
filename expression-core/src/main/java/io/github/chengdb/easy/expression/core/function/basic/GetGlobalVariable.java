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
        name = GetGlobalVariable.FUNCTION_NAME,
        argNum = 1,
        argsType = String.class,
        describe = "获取全局变量(全局变量名:String):(变量值:Object)"
)
public class GetGlobalVariable extends ArgFunction<Object> {
    public static final String FUNCTION_NAME = "$GGV";

    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        String name = functionArgs.getArgResultNotNull(context, String.class);
        return Context.getGlobalVariable(name);
    }
}
