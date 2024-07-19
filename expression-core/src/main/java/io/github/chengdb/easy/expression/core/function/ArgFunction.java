package io.github.chengdb.easy.expression.core.function;

/**
 * 有参数的函数
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/1
 */
public abstract class ArgFunction<T> extends Function<T> {
    /**
     * 函数的参数列表
     */
    protected FunctionArgs functionArgs;

    public final void setFunctionArgs(FunctionArgs functionArgs) {
        this.functionArgs = functionArgs;
    }

}
