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
        name = "readUnsignedMediumLE",
        returnType = Integer.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "小端读取无符号中整数(字节数组:byte[], 起始下标:Integer):(无符号中整数:Integer)"
)
public class ReadUnsignedMediumLE extends Read<Integer>{
    public ReadUnsignedMediumLE() {
        super(3);
    }

    @Override
    protected Integer execute(Context context) throws FunctionExecuteException {
        return BytesUtils.readMediumLE(bytes, index);
    }
}
