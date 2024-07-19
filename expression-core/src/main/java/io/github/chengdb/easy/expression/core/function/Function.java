package io.github.chengdb.easy.expression.core.function;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteError;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.io.Serializable;

/**
 * 函数
 *
 * @param <T> 函数返回值
 *
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/10/31
 */
public abstract class Function<T> implements Serializable {

    public static final Long serialVersionUID = 1L;

    /**
     * 执行函数
     * @param context 上下文
     * @return 执行结果
     * @throws FunctionExecuteException 函数执行异常
     */
    protected abstract T execute(Context context) throws FunctionExecuteException;


    /**
     * 执行函数并处理异常。
     * @param context 上下文
     * @return 执行结果
     * @throws FunctionExecuteException 函数执行异常
     */
    public final T execute0(Context context) throws FunctionExecuteException {
        try {
            beforeExecute(context);
            T executeResult = execute(context);
            postExecute(context);
            return executeResult;
        } catch (FunctionExecuteException fee) {
            // 追加路径
            fee.appendPath(this.getFunctionName());
            throw fee;
        }
        catch (FunctionExecuteError e) {
            throw e;
        }
        catch (Exception e) {
            // 未预料到的异常，创建FunctionExecuteException，并追加路径
            FunctionExecuteException functionExecuteException = new FunctionExecuteException("未预料到的异常", e);
            functionExecuteException.appendPath(getFunctionName());
            throw functionExecuteException;
        }
    }

    protected String getFunctionName() {
        return this.getClass().getAnnotation(Func.class).name();
    }

    /**
     * 执行前
     * @param context 上下文
     * @throws FunctionExecuteException 函数执行异常
     */
    protected void beforeExecute(Context context) throws FunctionExecuteException {}

    /**
     * 执行后
     * @param context 上下文
     * @throws FunctionExecuteException 函数执行异常
     */
    protected void postExecute(Context context) throws FunctionExecuteException {}
}
