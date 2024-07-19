package io.github.chengdb.easy.expression.core.expression;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteError;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.io.Serializable;

/**
 * 表达式
 * @author chengdabai
 * @since 1.0.0
 */
public abstract class Expression implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 执行表达式
     * @param context 上下文
     * @return 执行后的结果
     */
    protected abstract Object execute0(Context context) throws Exception;

    /**
     * 执行表达式
     * @param context 上下文
     * @return 执行后的结果
     */
    public Object execute(Context context) throws ExpressionExecuteException {
        try {
            return execute0(context);
        }
        catch (FunctionExecuteException fee) {
            throw new ExpressionExecuteException("函数执行错误，函数路径：" + fee.getErrorPath() + "，" + fee.getMessage());
        }
        catch (ExpressionExecuteException | FunctionExecuteError eee) {
            throw eee;
        }
        catch (Exception e) {
            throw new ExpressionExecuteException("未预料的错误", e);
        }
    }

    /**
     * 执行表达式并指定结果类型
     * @param context 上下文
     * @param targetClass 结果类型的class
     * @return 结果
     * @param <T> 结果类型
     */
    public <T> T execute(Context context, Class<T> targetClass) throws ExpressionExecuteException {
        Object executeResult = execute(context);

        if (targetClass.isInstance(executeResult)) {
            return targetClass.cast(executeResult);
        }
        throw new ExpressionExecuteException("不能将" + executeResult.getClass().getName() + "类型的表达式执行结果转为"
                + targetClass.getName() + "类型");
    }

}
