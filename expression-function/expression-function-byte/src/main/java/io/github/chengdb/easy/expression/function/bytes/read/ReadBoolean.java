package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readBoolean",
        returnType = Boolean.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取布尔值(字节数组:byte[], 起始下标:Integer):(布尔值:Boolean)"
)
public class ReadBoolean extends Read<Boolean>{
    public ReadBoolean() {
        super(1);
    }

    @Override
    protected Boolean execute(Context context) throws FunctionExecuteException {
        return bytes[index] != 0;
    }
}
