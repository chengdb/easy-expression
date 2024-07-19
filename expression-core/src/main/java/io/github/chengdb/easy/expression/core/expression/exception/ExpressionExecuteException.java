package io.github.chengdb.easy.expression.core.expression.exception;

/**
 * 表达式执行异常
 * @author chengdabai
 * @since 1.0.0
 */
public class ExpressionExecuteException extends Exception {
    public ExpressionExecuteException(String message) {
        super(message);
    }
    public ExpressionExecuteException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
