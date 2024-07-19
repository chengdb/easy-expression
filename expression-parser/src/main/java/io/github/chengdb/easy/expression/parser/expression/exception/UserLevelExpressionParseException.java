package io.github.chengdb.easy.expression.parser.expression.exception;

/**
 * 表达式解析异常 - 用户级别
 * @author chengdabai
 * @since 1.0.0
 */
public class UserLevelExpressionParseException extends ExpressionParseException{

    public final int errorIndex;

    public UserLevelExpressionParseException(int errorIndex, String message) {
        super(message);
        this.errorIndex = errorIndex;
    }


}
