package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.function.bytes.util.BytesUtils;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readShortLE",
        returnType = Short.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "小端读取短整数(字节数组:byte[], 起始下标:Integer):(短整数:Integer)"
)
public class ReadShortLE extends Read<Short> {
    public ReadShortLE() {
        super(Short.BYTES);
    }

    @Override
    protected Short execute(Context context) {
        return BytesUtils.readShortLE(bytes, index);
    }
}
