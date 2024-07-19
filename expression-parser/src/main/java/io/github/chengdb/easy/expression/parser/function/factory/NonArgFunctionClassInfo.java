package io.github.chengdb.easy.expression.parser.function.factory;

import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.NonArgFunction;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;

import java.lang.reflect.InvocationTargetException;

/**
 * 无参函数类信息
 * @author chengdabai
 * @since 1.0.0
 */
public class NonArgFunctionClassInfo extends FunctionClassInfo{

    private NonArgFunction<?> function;
    /**
     * 函数类信息
     *
     * @param functionClass   函数类的Class
     * @param funcAnnotation  函数注解
     * @author chengdabai
 * @since 1.0.0
     * @since 2023/11/9
     */
    public NonArgFunctionClassInfo(Class<? extends Function<?>> functionClass, Func funcAnnotation) {
        super(functionClass, false, funcAnnotation);
    }

    @Override
    public Function<?> getFunction() throws FunctionClassException {
        // 无参函数能复用，所以这里只实例化一次
        try {
            return function == null ? (function = (NonArgFunction<?>) functionClass.getConstructor().newInstance()) : function;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new FunctionClassException(functionClass.getName() + "需有无参构造函数");
        }
    }
}
