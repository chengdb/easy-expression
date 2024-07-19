package io.github.chengdb.easy.expression.core.function.value.holder;

import io.github.chengdb.easy.expression.core.function.exception.InjectStaticValueException;
import io.github.chengdb.easy.expression.core.function.value.FuncStaticValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class UpdatableFuncStaticValueHolder extends FuncStaticValueHolder {
    private final Map<Class<?>, List<Field>> injectMap = new HashMap<>();


    @Override
    public void injectStaticValue(List<Field> fields) throws InjectStaticValueException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(FuncStaticValue.class)) {
                Class<?> type = field.getType();
                Object o = getStaticValue(type);
                if (o == null) {
                    throw new InjectStaticValueException(field.getName(), field.getType().getTypeName());
                }
                try {
                    field.setAccessible(true);
                    field.set(null, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                List<Field> fields2 = injectMap.computeIfAbsent(type, k -> new LinkedList<>());
                fields2.add(field);
            }
        }
    }


    public void updateStaticValue(Object value) {
        if (value == null) {
            return;
        }
        List<Field> fields = injectMap.get(value.getClass());
        if (fields != null) {
            for (Field field : fields) {
                try {
                    field.set(null, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
