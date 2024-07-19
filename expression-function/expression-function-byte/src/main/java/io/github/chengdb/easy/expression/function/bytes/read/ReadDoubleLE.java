package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.math.BigDecimal;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readDoubleLE",
        returnType = Number.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "小端读取双精度浮点型(字节数组:byte[], 起始下标:Integer):(双精度浮点型:Double)"
)
public class ReadDoubleLE extends Read<BigDecimal>{
    public ReadDoubleLE() {
        super(Double.BYTES);
    }

    @Override
    protected BigDecimal execute(Context context) throws FunctionExecuteException {
        long result = 0;
        for (int i = index + readLength - 1; i >= index; i--) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return BigDecimal.valueOf(Double.longBitsToDouble(result));
    }
}
