package io.github.chengdb.easy.expression.core.function.exception;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class InjectStaticValueException extends Exception {
    public final String fieldName;
    public final String type;

    public InjectStaticValueException(String fieldName, String type) {
        this.fieldName = fieldName;
        this.type = type;
    }
}
