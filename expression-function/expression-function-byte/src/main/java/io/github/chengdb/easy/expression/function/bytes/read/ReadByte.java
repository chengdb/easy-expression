package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readByte",
        returnType = Byte.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取字节(字节数组:byte[], 起始下标:Integer):(字节:Byte)"
)
public class ReadByte extends Read<Byte>{
    public ReadByte() {
        super(Byte.BYTES);
    }

    @Override
    protected Byte execute(Context context) throws FunctionExecuteException {
        return bytes[index];
    }
}
