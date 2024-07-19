package io.github.chengdb.easy.expression.core.function;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.basic.ConstantFunction;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteError;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

import java.io.Serializable;

/**
 * 函数入参
 * <p>入参也是函数</p>
 *
 * @param args 函数的具体参数项列表
 * @author chengdabai
 * @since 1.0.0
 */
public record FunctionArgs(Function<?>[] args) implements Serializable {
    public static final Long serialVersionUID = 1L;

    /**
     * 获取参数的结果
     *
     * @param idx         参数序号
     * @param context     上下文
     * @param targetClass 结果类
     * @param <T>         结果类型
     * @return 结果
     */
    public <T> T getArgResult(int idx, Context context, Class<T> targetClass) throws FunctionExecuteException {
        if (idx < 0 || idx >= args.length) {
            throw new FunctionExecuteError("超出函数入参数量范围，实际函数入参个数：" + args.length + "，错误索引："
                    + idx + "。由函数定义错误引起，请检查相应的函数定义。");
        }
        Function<?> argFunction = args[idx];
        Object execute = argFunction.execute0(context);
        if (execute == null || targetClass.isInstance(execute)) {
            return targetClass.cast(execute);
        }

        Func argAnno = argFunction.getClass().getAnnotation(Func.class);
        Class<?> rightType = argAnno == null ? ((ConstantFunction<?>) argFunction).valueType():argAnno.returnType();
        if (rightType.equals(targetClass) || rightType.equals(Object.class)) {
            throw new FunctionExecuteException("类型转换错误。第" + (idx + 1) + "个入参的函数" + argFunction.getFunctionName()
                    + "的返回类型是" + execute.getClass().getName() + "，不能转换为" + targetClass.getName());
        }
        throw new FunctionExecuteError("请按照函数定义类的注解的returnType字段注明的类型获取参数值。");
    }

    /**
     * 获取第一个参数的结果
     *
     * @param context 上下文
     * @param tClass  结果类
     * @param <T>     结果类型
     * @return 结果
     */
    public <T> T getArgResult(Context context, Class<T> tClass) throws FunctionExecuteException {
        return getArgResult(0, context, tClass);
    }

    /**
     * 获取参数的结果，结果不为空
     *
     * @param idx         参数序号
     * @param context     上下文
     * @param tClass 结果类
     * @param <T>         结果类型
     * @return 结果
     */
    public <T> T getArgResultNotNull(int idx, Context context, Class<T> tClass) throws FunctionExecuteException {
        T argResult = getArgResult(idx, context, tClass);
        if (argResult == null) {
            throw new FunctionExecuteException("第" + (idx + 1) + "个参数结果为null");
        }
        return argResult;
    }

    /**
     * 获取参数的结果，结果不为空
     *
     * @param context     上下文
     * @param tClass 结果类
     * @param <T>         结果类型
     * @return 结果
     */
    public <T> T getArgResultNotNull(Context context, Class<T> tClass) throws FunctionExecuteException {
        return getArgResultNotNull(0, context, tClass);
    }

    /**
     * 获取参数迭代器
     *
     * @return 迭代器
     */
    public ArgsIterator iterator() {
        return new ArgsIterator(this);
    }

    /**
     * 参数迭代器
     */
    public static class ArgsIterator {
        /**
         * 当前下标
         */
        private int index = 0;

        /**
         * 函数参数实体
         */
        private final FunctionArgs functionArgs;

        private ArgsIterator(FunctionArgs functionArgs) {
            this.functionArgs = functionArgs;
        }

        /**
         * 获取参数的结果
         *
         * @param context 上下文
         * @param tClass  结果类
         * @param <T>     结果类型
         * @return 结果
         */
        public <T> T next(Context context, Class<T> tClass) throws FunctionExecuteException {
            return functionArgs.getArgResult(index++, context, tClass);
        }

        /**
         * 获取参数的结果（结果不空）
         *
         * @param context 上下文
         * @param tClass 结果类型
         * @return 结果
         * @param <T> 结果类型
         * @throws FunctionExecuteException 异常
         */
        public <T> T nextNotNull(Context context, Class<T> tClass) throws FunctionExecuteException {
            return functionArgs.getArgResultNotNull(index++, context, tClass);
        }

        /**
         * 判断是否还有下一个参数项
         *
         * @return 如果还有下一个参数项，则返回true；否则返回false
         */
        public boolean hasNext() {
            return index < functionArgs.args.length;
        }

        /**
         * 取消下一个参数的执行
         */
        public void passNext() {
            index++;
        }

    }

}
