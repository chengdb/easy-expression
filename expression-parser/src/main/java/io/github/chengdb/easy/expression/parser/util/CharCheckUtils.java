package io.github.chengdb.easy.expression.parser.util;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class CharCheckUtils {

    /**
     * 判断是否是数字的开头字符
     * @param c 要判断的字符
     * @return 是否
     */
    public static boolean isNumStart(char c) {
        return c == '-' || (c >= '0' && c <= '9');
    }

    /**
     * 判断是否是函数的开头字符
     * @param c 要判断的字符
     * @return 是否
     */
    public static boolean isFuncStart(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '$' || c == '_' ;
    }

    /**
     * 判断是否是字符串的开头字符
     * @param c 要判断的字符
     * @return 是否
     */
    public static boolean isStringStart(char c) {
        return c == '\'';
    }
    /**
     * 判断是否是正确表达式的开头字符
     * @param c 要判断的字符
     * @return 是否
     */
    public static boolean isExpressionStart(char c) {
        return isFuncStart(c) || isStringStart(c) || isNumStart(c);
    }
}
