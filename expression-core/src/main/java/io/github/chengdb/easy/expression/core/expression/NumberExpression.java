package io.github.chengdb.easy.expression.core.expression;


import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.util.NumberUtils;

/**
 * 数字常量表达式
 * @author chengdabaiss
 * @since 1.0.0
 */
public class NumberExpression extends Expression{
    private final Number number;

    public NumberExpression(String numberStr) {
        this.number = NumberUtils.parseNum(numberStr);
    }


    @Override
    public Number execute0(Context context) {
        return number;
    }

}
