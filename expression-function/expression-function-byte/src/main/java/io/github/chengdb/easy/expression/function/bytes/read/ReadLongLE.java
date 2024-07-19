package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;

import java.math.BigDecimal;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readLongLE",
        returnType = Number.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "小端读取长整型(字节数组:byte[], 起始下标:Integer):(长整型:Long)"
)
public class ReadLongLE extends Read<Number>{
    public ReadLongLE() {
        super(Long.BYTES);
    }

    @Override
    protected BigDecimal execute(Context context) {
        long result = 0;
        for (int i = index + readLength - 1; i >= index; i--) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return BigDecimal.valueOf(result);
    }
}
