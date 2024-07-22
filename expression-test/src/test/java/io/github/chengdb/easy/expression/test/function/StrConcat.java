package io.github.chengdb.easy.expression.test.function;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "strConcat",
        argNum = 2,
        argsType = {String.class, String.class},
        returnType = String.class,
        describe = "字符串拼接(字符串1:String, 字符串2:String):(拼接后的字符串:String)"
)
public class StrConcat extends ArgFunction<String> {
    @Override
    protected String execute(Context context) throws FunctionExecuteException {
        String str1 = functionArgs.getArgResult(context, String.class);
        String str2 = functionArgs.getArgResult(1, context, String.class);
        if (str1 == null) {
            return str2;
        } else {
            return str2 == null ? str1 : str1 + str2;
        }
    }
}
