package io.github.chengdb.easy.expression.core.util;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class StringUtils {
    /**
     * 字符串表达式字符串反转义
     * @param str 字符串表达式字符串
     * @return 结果
     */
    public static String stringAntiEscape(String str) {
        str = str.substring(1, str.length() - 1);
        // 反转义
        str = str.replace("\\'", "'").replace("\\\\", "\\");
        return str;
    }


}
