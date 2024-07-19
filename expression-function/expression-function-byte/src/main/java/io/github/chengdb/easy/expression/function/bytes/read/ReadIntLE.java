package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.function.bytes.util.BytesUtils;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readIntLE",
        returnType = Integer.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "小端读取整数(字节数组:byte[], 起始下标:Integer):(整数:Integer)"
)
public class ReadIntLE extends Read<Integer> {
    public ReadIntLE() {
        super(Integer.BYTES);
    }

    @Override
    protected Integer execute(Context context) {
        return BytesUtils.readIntLE(bytes, index);
    }
}
