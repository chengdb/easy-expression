package io.github.chengdb.easy.expression.core.function.basic;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.util.NumberUtils;

/**
 * 数字常量函数
 * @author chengdabai
 * @since 1.0.0
 */
public class NumberConstantFunction extends ConstantFunction<Number> {

    public NumberConstantFunction(String number) {
        super(number);
        setValue(NumberUtils.parseNum(number));
    }

    @Override
    public Number execute(Context context) {
        return value;
    }

    @Override
    public Class<?> valueType() {
        return value.getClass();
    }
}
