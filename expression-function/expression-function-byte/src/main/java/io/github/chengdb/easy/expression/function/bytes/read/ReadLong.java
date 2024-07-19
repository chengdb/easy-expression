package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;

import java.math.BigDecimal;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readLong",
        returnType = Number.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取长整型(字节数组:byte[], 起始下标:Integer):(长整型:Long)"
)
public class ReadLong extends Read<Number>{
    public ReadLong() {
        super(Long.BYTES);
    }

    @Override
    protected BigDecimal execute(Context context) {
        long result = 0;
        for (int i = 0; i < readLength; i++) {
            result = (result << 8) | (bytes[index + i] & 0xFF);
        }
        return BigDecimal.valueOf(result);
    }
}
