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
        name = "readFloat",
        returnType = Number.class,
        argNum = 2,
        argsType = {byte[].class, Integer.class},
        describe = "读取浮点型(字节数组:byte[], 起始下标:Integer):(浮点型:Float)"
)
public class ReadFloat extends Read<Number>{
    public ReadFloat() {
        super(Float.BYTES);
    }

    @Override
    protected BigDecimal execute(Context context) throws FunctionExecuteException {
        int result = BytesUtils.readInt(bytes, index);
        float val = Float.intBitsToFloat(result);
        if (Float.isNaN(val)) {
            throw new FunctionExecuteException("不能读取为浮点型数字");
        }
        return BigDecimal.valueOf(val);
    }
}