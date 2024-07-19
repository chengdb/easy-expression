package io.github.chengdb.easy.expression.function.basic.iteration;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "arrayForEach",
        argNum = 4,
        argsType = {Object[].class, String.class, Boolean.class, Integer.class},
        describe = "数组遍历(数组:Object[], 元素变量名:String, 元素操作:Boolean, 起始下标:Integer)"
)
public class ArrayForEach extends ArgFunction<Object> {
    @Override
    protected Object execute(Context context) throws FunctionExecuteException {
        Object[] array = functionArgs.getArgResultNotNull(context, Object[].class);
        int startIndex = functionArgs.getArgResultNotNull(3, context, Integer.class);
        if (startIndex < 0 || startIndex >= array.length) {
            throw new FunctionExecuteException("遍历数组起始下标越界，数组长度：" + array.length);
        }
        String itemVarName = functionArgs.getArgResultNotNull(1, context, String.class);
        int i;
        for (i = startIndex; i < array.length; i++) {
            Object o = array[i];
            context.setVariable(itemVarName, o);
            Boolean isBreak = functionArgs.getArgResultNotNull(2, context, Boolean.class);
            if (isBreak) {
                break;
            }
        }
        return i;
    }
}
