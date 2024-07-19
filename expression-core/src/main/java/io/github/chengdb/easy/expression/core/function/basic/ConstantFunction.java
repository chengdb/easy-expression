package io.github.chengdb.easy.expression.core.function.basic;

import io.github.chengdb.easy.expression.core.function.Function;

/**
 * 常量函数
 * @author chengdabai
 * @since 1.0.0
 */
public abstract class ConstantFunction<T> extends Function<T> {
    /**
     * 源标识
     */
    private final String raw;

    protected T value;

    protected ConstantFunction(String raw) {
        this.raw = raw;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    public abstract Class<?> valueType();

    @Override
    public String getFunctionName() {
        return raw;
    }
}
