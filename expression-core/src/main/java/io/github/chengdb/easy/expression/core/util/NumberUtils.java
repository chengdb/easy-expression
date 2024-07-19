package io.github.chengdb.easy.expression.core.util;

import java.math.BigDecimal;

/**
 * 数字工具
 * @author chengdabai
 * @since 1.0.0
 */
public class NumberUtils {

    public static BigDecimal INTEGER_MAX = new BigDecimal(Integer.MAX_VALUE);
    public static BigDecimal INTEGER_MIN = new BigDecimal(Integer.MIN_VALUE);



    public static Number parseNum(String numStr) {
        BigDecimal bigDecimal = new BigDecimal(numStr);

        if (bigDecimal.scale() > 0) {
            return bigDecimal;
        }
        if (bigDecimal.compareTo(INTEGER_MAX) <= 0 && bigDecimal.compareTo(INTEGER_MIN) >= 0) {
            return bigDecimal.intValue();
        }
        return bigDecimal;
    }




    /**
     * 取整数值
     * @param obj 原来的值
     * @return 整数
     */
    public static Integer integerValue(Object obj) throws NumberProcessException {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (!(obj instanceof BigDecimal decimalValue)) {
            throw new NumberProcessException("不是整数");
        }
        if (decimalValue.scale() <= 0) { // 判断是否为整数
            int result = decimalValue.intValue(); // 可以安全地转换为整数
            // 检查转换后的值是否与原始值相等
            if (BigDecimal.valueOf(result).compareTo(decimalValue) != 0) {
                throw new NumberProcessException("超出整数范围");
            }
            return result;
        } else {
            throw new NumberProcessException("是浮点数");
        }
    }

    /**
     * 取长整数值
     * @param obj 原来的值
     * @return 长整数
     */
    public static Long longValue(Object obj) throws NumberProcessException {
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (!(obj instanceof BigDecimal decimalValue)) {
            throw new NumberProcessException("不是长整数");
        }
        if (decimalValue.scale() <= 0) { // 判断是否为整数
            long result = decimalValue.longValue(); // 可以安全地转换为长整数
            // 检查转换后的值是否与原始值相等
            if (BigDecimal.valueOf(result).compareTo(decimalValue) != 0) {
                throw new NumberProcessException("超出长整数范围");
            }
            return result;
        } else {
            throw new NumberProcessException("是浮点数");
        }
    }

    public static class NumberProcessException extends Exception {
        public NumberProcessException(String msg) {
            super(msg);
        }
    }

}
