package io.github.chengdb.easy.expression.function.bytes.read;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;
import io.github.chengdb.easy.expression.function.bytes.util.IndexUtils;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public abstract class Read<T> extends ArgFunction<T> {

    protected final int readLength;
    protected byte[] bytes;
    protected int index;

    protected Read(int readLength) {
        this.readLength = readLength;
    }

    @Override
    protected void beforeExecute(Context context) throws FunctionExecuteException {
        bytes = functionArgs.getArgResultNotNull(context, byte[].class);
        index = functionArgs.getArgResultNotNull(1, context, Integer.class);
        IndexUtils.checkIndex(index, bytes.length, readLength);
    }
}
