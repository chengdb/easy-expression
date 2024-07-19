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
        name = GetVariable.FUNCTION_NAME,
        argNum = 1,
        argsType = {String.class},
        describe = "获取变量(变量名称:String):(变量:Object)"
)
public class GetVariable extends ArgFunction<Object> {
    public static final String FUNCTION_NAME = "$GV";

    @Override
    public Object execute(Context context) throws FunctionExecuteException {
        String name = functionArgs.getArgResult(context, String.class);
        if (name == null) {
            throw new FunctionExecuteException("资源名称为空");
        }
        return context.getVariable(name);
    }
}
