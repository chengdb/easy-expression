package io.github.chengdb.easy.expression.test.function;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.FunctionArgs;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "strConcatPlus",
        argNum = -2,
        argsType = {String.class, String.class},
        returnType = String.class,
        describe = "字符串拼接(字符串1:String, 字符串2...:String):(拼接结果:String)"
)
public class StrConcatPlus extends ArgFunction<String> {
    @Override
    protected String execute(Context context) throws FunctionExecuteException {

        StringBuilder stringBuilder = new StringBuilder();
        FunctionArgs.ArgsIterator iterator = functionArgs.iterator();

        while (iterator.hasNext()) {
            String str = iterator.next(context, String.class);
            if (str == null) {
                continue;
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
