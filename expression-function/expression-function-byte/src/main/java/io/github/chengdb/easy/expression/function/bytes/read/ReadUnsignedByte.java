package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readUnsignedByte",
        returnType = Short.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取无符号字节(字节数组:byte[], 起始下标:Integer):(无符号字节:Short)"
)
public class ReadUnsignedByte extends Read<Short> {
    public ReadUnsignedByte() {
        super(Byte.BYTES);
    }

    @Override
    protected Short execute(Context context) throws FunctionExecuteException {
        return (short) (bytes[index] & 0xFF);
    }
}
