package io.github.chengdb.easy.expression.parser.function.factory;

import io.github.chengdb.easy.expression.core.function.exception.InjectStaticValueException;
import io.github.chengdb.easy.expression.core.function.value.FuncStaticValue;
import io.github.chengdb.easy.expression.core.function.value.holder.FuncStaticValueHolder;
import io.github.chengdb.easy.expression.parser.function.exception.FunctionClassException;
import io.github.chengdb.easy.expression.parser.util.StringCheckUtil;
import io.github.chengdb.easy.expression.core.function.ArgFunction;
import io.github.chengdb.easy.expression.core.function.Func;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.NonArgFunction;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 函数工厂
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/1
 */
public class FunctionClassInfoFactory {
    /**
     * 存放函数类信息
     */
    private final Map<String, FunctionClassInfo> funcMap = new HashMap<>();

    private final FuncStaticValueHolder funcStaticValueHolder;


    private final FunctionClassGetter functionClassGetter;

    public FunctionClassInfoFactory(FuncStaticValueHolder funcStaticValueHolder, FunctionClassGetter functionClassGetter) {
        this.funcStaticValueHolder = funcStaticValueHolder;
        this.functionClassGetter = functionClassGetter;
    }

    public FunctionClassInfoFactory(FunctionClassGetter functionClassGetter) {
        this.funcStaticValueHolder = null;
        this.functionClassGetter = functionClassGetter;
    }



    /**
     * 根据函数名获取函数类信息
     *
     * @param name 函数名
     * @return 函数类信息
     */
    public FunctionClassInfo getFunctionClassInfo(String name) throws FunctionClassException {
        // 在缓存中找
        FunctionClassInfo functionClassInfo = funcMap.get(name);
        // 没找到就通过functionClassGetter加载
        if (functionClassInfo == null) {
            Class<? extends Function<?>> functionClass = functionClassGetter.functionClass0(name);
            if (functionClass == null) {
                return null;
            }
            Func annotation = functionClass.getAnnotation(Func.class);
            if (annotation == null) {
                throw new FunctionClassException("定义的函数类必须标注" + Func.class.getName() + "注解：" + functionClass.getName());
            }
            if (!name.equals(annotation.name())) {
                throw new FunctionClassException(Func.class.getName() + "注解中的name字段与实际函数名不符：" + functionClass.getName());
            }
            boolean isArgFunction = false;
            if (NonArgFunction.class.isAssignableFrom(functionClass)) {
                if (annotation.argNum() != 0) {
                    throw new FunctionClassException("NonArgFunction类型函数类的参数数量应该为0。解决：1.将" +
                            functionClass.getName() + "函数类的Func注解的paramNum参数设置为0（默认）；2.使" + functionClass.getName() +
                            "函数类继承" + ArgFunction.class.getName());
                }
            } else if (!(isArgFunction = ArgFunction.class.isAssignableFrom(functionClass))) {
                throw new FunctionClassException("自定义函数类：" + functionClass.getName() + "，必须继承" +
                        NonArgFunction.class.getName() + "或" + ArgFunction.class.getName());
            }

            String funcName = annotation.name();
            // 函数名称检查
            if (!StringCheckUtil.functionNameCheck(funcName)) {
                throw new FunctionClassException(functionClass.getName() + "定义的函数名称非法：" + funcName);
            }
            if (funcMap.containsKey(funcName)) {
                throw new FunctionClassException(functionClass.getName() + "定义的函数名称重复：" + funcName);
            }
            if (isArgFunction) {
                functionClassInfo = new ArgFunctionClassInfo(functionClass, annotation);
            } else {
                functionClassInfo = new NonArgFunctionClassInfo(functionClass, annotation);
            }

            List<Field> needInjectFields = needInjectFields(functionClass);
            if (needInjectFields.size() > 0) {
                if (funcStaticValueHolder == null) {
                    throw new FunctionClassException(functionClass.getName() + "需要注入静态字段，" +
                            "而FunctionClassInfoFactory没有FuncStaticValueHolder");
                } else {
                    try {
                        funcStaticValueHolder.injectStaticValue(needInjectFields);
                    } catch (InjectStaticValueException e) {
                        throw new FunctionClassException(functionClass.getName() + "静态字段: " + e.fieldName +
                                "无法注入。因为FuncStaticValueHolder中没有" + e.type + "类型的值");
                    }
                }
            }

            funcMap.put(funcName, functionClassInfo);
        }
        return functionClassInfo;
    }


    private List<Field> needInjectFields(Class<?> functionClass) {
        LinkedList<Field> fields = new LinkedList<>();
        Field[] declaredFields = functionClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(FuncStaticValue.class)) {
                fields.add(declaredField);
            }
        }
        return fields;
    }



}
