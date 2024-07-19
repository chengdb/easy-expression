package io.github.chengdb.easy.expression.core.function.exception;

/**
 * 函数运行错误，与函数定义相关，出现此异常请检查响应函数的定义
 * @author chengdabai
 * @since 1.0.0
 */
public class FunctionExecuteError extends RuntimeException {

    public FunctionExecuteError(String message) {
        super(message);
    }

}
