package io.github.chengdb.easy.expression.core.function.basic;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.util.StringUtils;

/**
 * 字符串常量
 * @author chengdabai
 * @since 1.0.0
 */
public class StringConstantFunction extends ConstantFunction<String> {

    public StringConstantFunction(String str) {
        super(str);
        setValue(StringUtils.stringAntiEscape(str));
    }

    @Override
    public String execute(Context context) {
        return value;
    }

    @Override
    public Class<?> valueType() {
        return String.class;
    }
}
