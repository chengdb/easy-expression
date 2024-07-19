package io.github.chengdb.easy.expression.test;

import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import org.junit.Test;

/**
 * @author chengdabei
 * @since 1.0.0
 */
public class NumberTest {
    private static final ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

    @Test
    public void addTest() throws ExpressionParseException, ExpressionExecuteException {
        String expressionStr = "numAdd(1, 2, 3, 4, 5, 6, 7)";
        Expression expression = expressionParser.parse(expressionStr);
        System.out.println(expression.executeWithoutContext());
    }



}
