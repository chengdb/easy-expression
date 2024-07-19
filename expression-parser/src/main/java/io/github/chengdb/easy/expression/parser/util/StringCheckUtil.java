package io.github.chengdb.easy.expression.parser.util;

/**
 * 字符串检查工具
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/7
 */
public class StringCheckUtil {

    private StringCheckUtil() {}
    /**
     * 函数名称正则
     */
    public static final String FUNCTION_NAME_REGULAR = "[a-zA-Z$_][a-zA-Z$_\\d]*";


    /**
     * 函数调用正则表达式
     */
    public static final String FUNCTION_CALL_REGULAR = "^\\s*([a-zA-Z$_][a-zA-Z$_\\d]*)\\([\\s\\S]*(\\))\\s*$";

    /**
     * 数字正则表达式
     */
    public static final String NUMERIC_REGULAR = "^-?\\d+(\\.\\d+)?$";

    /**
     * 字符串正则表达式（以“ ‘ ”开始和结束）
     */
    public static final String STRING_REGULAR = "^'[\\s\\S]*'$";

    /**
     * 函数名检查
     *
     * @param functionName 函数名
     * @return 是否合法
     */
    public static boolean functionNameCheck(String functionName) {
        return functionName.matches(FUNCTION_NAME_REGULAR);
    }

    /**
     * 函数调用检查
     *
     * @param str 表达式字符串
     * @return 结果
     */
    public static boolean functionCallCheck(String str) {
        return str.matches(FUNCTION_CALL_REGULAR);
    }

    /**
     * 数字检查
     *
     * @param str 表达式字符串
     * @return 结果
     */
    public static boolean numberCheck(String str) {
        return str.matches(NUMERIC_REGULAR);
    }

    /**
     * 是否为字符串表达式判断
     * @param str 表达式字符串
     * @return 结果
     */
    public static boolean stringCheck(String str) {
        return str.matches(STRING_REGULAR);
    }

}
