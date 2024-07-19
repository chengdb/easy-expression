package io.github.chengdb.easy.expression.core.function.value.holder;

import io.github.chengdb.easy.expression.core.function.exception.InjectStaticValueException;
import io.github.chengdb.easy.expression.core.function.value.FuncStaticValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class FuncStaticValueHolder {
    /**
     * 静态字段值
     */
    private final Map<Class<?>, Object> staticValues = new HashMap<>();

    public void setStaticValue(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        staticValues.put(value.getClass(), value);
    }

    public Object getStaticValue(Class<?> clazz) {
        return staticValues.get(clazz);
    }
    public void injectStaticValue(List<Field> fields) throws InjectStaticValueException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(FuncStaticValue.class)) {
                Class<?> type = field.getType();
                Object o = staticValues.get(type);
                if (o == null) {
                    throw new InjectStaticValueException(field.getName(), field.getType().getTypeName());
                }
                try {
                    field.setAccessible(true);
                    field.set(null, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
