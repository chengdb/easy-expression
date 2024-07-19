package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.function.bytes.util.BytesUtils;

/**
 * @author chengdabai
 * @since 1.0.0
funcAnnotation
 */
@Func(
        name = "readInt",
        returnType = Integer.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取整数(字节数组:byte[], 起始下标:Integer):(整数:Integer)"
)
public class ReadInt extends Read<Integer> {
    public ReadInt() {
        super(Integer.BYTES);
    }

    @Override
    protected Integer execute(Context context) {
        return BytesUtils.readInt(bytes, index);
    }
}
