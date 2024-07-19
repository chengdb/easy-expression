package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;
import io.github.chengdb.easy.expression.function.bytes.util.IndexUtils;

/**
 * @author chengdabai
 * @since 1.0.0
 */
@Func(
        name = "readBytes",
        returnType = byte[].class,
        argNum = 3,
        argsType = {byte[].class, Integer.class, Integer.class},
        describe = "读取字节数组(字节数组:byte[], 起始下标:Integer, 读取长度: Integer):(字节数组:byte[])"
)
public class ReadBytes extends ArgFunction<byte[]> {
    @Override
    protected byte[] execute(Context context) throws FunctionExecuteException {
        byte[] bytes = functionArgs.getArgResultNotNull(context, byte[].class);

        int index = functionArgs.getArgResultNotNull(1, context, Integer.class);

        int length = functionArgs.getArgResultNotNull(2, context, Integer.class);

        IndexUtils.checkIndex(index, bytes.length, length);

        byte[] result = new byte[length];

        System.arraycopy(bytes, index, result, 0, length);
        return result;
    }
}
