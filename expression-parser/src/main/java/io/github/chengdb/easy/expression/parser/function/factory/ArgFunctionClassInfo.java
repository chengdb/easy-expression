package io.github.chengdb.easy.expression.parser.function.factory;

import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;

import java.lang.reflect.InvocationTargetException;

/**
 * 有参函数信息
 * @author chengdabai
 * @since 1.0.0
 */
public class ArgFunctionClassInfo extends FunctionClassInfo {
    public ArgFunctionClassInfo(Class<? extends Function<?>> functionClass, Func funcAnnotation) {
        super(functionClass, true, funcAnnotation);
    }

    @Override
    public Function<?> getFunction() throws FunctionClassException {
        try {
            return functionClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new FunctionClassException(functionClass.getName() + "需有无参构造函数");
        }
    }
}
