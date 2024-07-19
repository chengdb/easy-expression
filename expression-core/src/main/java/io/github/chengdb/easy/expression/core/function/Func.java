package io.github.chengdb.easy.expression.core.function;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 函数注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Func {
    /**
     * 函数名
     */
    String name();

    /**
     * 函数参数的数量
     * <p>大于0时，表示函数入参数量</p>
     * <p>小于0时，表示函数入参数量最少为此值的绝对值</p>
     * <p>等于0，表示函数入参数量为任意</p>
     */
    int argNum() default 0;

    /**
     * 对函数的描述
     */
    String describe() default "";

    /**
     * 返回值类型
     */
    Class<?> returnType() default Object.class;

    /**
     * 参数类型列表
     */
    Class<?>[] argsType() default {Object.class};
}
