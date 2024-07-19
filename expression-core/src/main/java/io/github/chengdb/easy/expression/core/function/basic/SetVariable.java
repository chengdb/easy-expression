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
        name = SetVariable.FUNCTION_NAME,
        argNum = 2,
        argsType = {String.class, Object.class},
        describe = "设置变量(变量名称:String, 变量:Object):(变量:Object)")
public class SetVariable extends ArgFunction<Object> {
    public static final String FUNCTION_NAME = "$SV";

    @Override
    public Object execute(Context context) throws FunctionExecuteException {
        String name = functionArgs.getArgResult(context, String.class);
        if (name == null) {
            throw new FunctionExecuteException("变量名称为空");
        }
        Object variable = functionArgs.getArgResult(1, context, Object.class);
        return context.setVariable(name, variable);
    }
}
