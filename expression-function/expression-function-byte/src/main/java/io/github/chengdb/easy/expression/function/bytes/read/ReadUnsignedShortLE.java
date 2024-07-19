package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;
import io.github.chengdb.easy.expression.function.bytes.util.BytesUtils;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readUnsignedShortLE",
        returnType = Integer.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "小端读取无符号短整数(字节数组:byte[], 起始下标:Integer):(无符号短整数:Integer)"
)
public class ReadUnsignedShortLE extends Read<Integer>{
    public ReadUnsignedShortLE() {
        super(Short.BYTES);
    }

    @Override
    protected Integer execute(Context context) throws FunctionExecuteException {
        return BytesUtils.readShortLE(bytes, index) & 0xFFFF;
    }
}
