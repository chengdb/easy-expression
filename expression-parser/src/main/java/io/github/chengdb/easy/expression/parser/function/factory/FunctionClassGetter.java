package io.github.chengdb.easy.expression.parser.function.factory;


import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.basic.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Function 定义类获取
 * @author chengdabai
 * @since 1.0.0
 */
public abstract class FunctionClassGetter {
    private final Map<String, Class<? extends Function<?>>> basicFunctionClass = new HashMap<>(16);
    {
        basicFunctionClass.put(GetResource.FUNCTION_NAME, GetResource.class);
        basicFunctionClass.put(GetVariable.FUNCTION_NAME, GetVariable.class);
        basicFunctionClass.put(SetVariable.FUNCTION_NAME, SetVariable.class);
        basicFunctionClass.put(GetResourceFast.FUNCTION_NAME, GetResourceFast.class);
        basicFunctionClass.put(UpdateResource.FUNCTION_NAME, UpdateResource.class);
        basicFunctionClass.put(GetGlobalVariable.FUNCTION_NAME, GetGlobalVariable.class);
        basicFunctionClass.put(SetGlobalVariable.FUNCTION_NAME, SetGlobalVariable.class);
    }

    public Class<? extends Function<?>> functionClass0(String functionName) {
        return basicFunctionClass.computeIfAbsent(functionName, this::functionClass);
    }

    /**
     * 根据函数名获取函数类
     * @param functionName 函数名
     * @return 函数类
     */
    public abstract Class<? extends Function<?>> functionClass(String functionName);
}
