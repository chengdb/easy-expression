package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;
import io.github.chengdb.easy.expression.function.bytes.util.BytesUtils;

import java.math.BigDecimal;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readUnsignedIntLE",
        returnType = Number.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取无符号短整数(字节数组:byte[], 起始下标:Integer):(无符号短整数:Integer)"
)
public class ReadUnsignedIntLE extends Read<Number>{
    public ReadUnsignedIntLE() {
        super(Short.BYTES);
    }

    @Override
    protected BigDecimal execute(Context context) throws FunctionExecuteException {
        return BigDecimal.valueOf(BytesUtils.readIntLE(bytes, index) & 0xFFFFFFFFL);
    }
}
