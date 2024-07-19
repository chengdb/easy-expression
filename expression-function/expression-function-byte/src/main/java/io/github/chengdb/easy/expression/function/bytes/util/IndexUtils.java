package io.github.chengdb.easy.expression.function.bytes.util;

import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class IndexUtils {

    public static void checkNonNegative(int index) throws FunctionExecuteException {
        if (index < 0) {
            throw new FunctionExecuteException("读取下标为负数：" + index);
        }
    }

    public static void checkRange(int index, int arrayLength, int readeLength) throws FunctionExecuteException {
        if (index + readeLength > arrayLength) {
            throw new FunctionExecuteException("数组长度：" + arrayLength + "，从" + index + "开始不足以读取" + readeLength + "位");
        }
    }

    public static void checkIndex(int index, int arrayLength, int readeLength) throws FunctionExecuteException {
        checkNonNegative(index);
        checkRange(index, arrayLength, readeLength);
    }

}
