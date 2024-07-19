package io.github.chengdb.easy.expression.core.expression;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.util.StringUtils;

/**
 * 字符串常量表达式
 * @author chengdabai
 * @since 1.0.0
 */
public class StringExpression extends Expression {

    private final String str;

    public StringExpression(String strExpression) {
        this.str = StringUtils.stringAntiEscape(strExpression);
    }

    @Override
    public String execute0(Context context) {
        return str;
    }
}
