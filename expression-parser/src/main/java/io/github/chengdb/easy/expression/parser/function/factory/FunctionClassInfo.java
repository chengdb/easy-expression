package io.github.chengdb.easy.expression.parser.function.factory;


import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;

/**
 * 函数类信息
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/9
 */
public abstract class FunctionClassInfo {

    public final Class<? extends Function<?>> functionClass;
    public final boolean isArgFunction;
    public final Func funcAnnotation;

    public FunctionClassInfo(Class<? extends Function<?>> functionClass, boolean isArgFunction, Func funcAnnotation) {
        this.functionClass = functionClass;
        this.isArgFunction = isArgFunction;
        this.funcAnnotation = funcAnnotation;
    }

    /**
     * 获取函数实体
     * @return 函数实体
     */
    public abstract Function<?> getFunction() throws FunctionClassException;

}
