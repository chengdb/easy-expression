package io.github.chengdb.easy.expression.test;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import org.junit.Test;

import java.util.Collections;

/**
 * @author chengdabai
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


    @Test
    public void addTest2() throws ExpressionParseException, ExpressionExecuteException {
        Integer[] nums = new Integer[]{1,2,3,4,5,6,7};
        String expressionStr = "$SV('sum', 0);arrayForEach($RF(), 'num',segment($SV('sum', numAdd($GV('sum'), $GV('num'))), _false()), 0);$GV('sum');";
        Expression expression = expressionParser.parse(expressionStr);
        Object result = expression.execute(Context.newSingleResourceContext("array", nums));
        System.out.println(result);
    }

    @Test
    public void addTest3() throws ExpressionParseException, ExpressionExecuteException {
        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter(Collections.singletonList("arrayNumAdd:io.github.chengdb.easy.expression.test.function.ArrayNumAdd")));
        Integer[] nums = new Integer[]{1,2,3,4,5,6,7};
        String expressionStr = "arrayNumAdd($RF())";
        Expression expression = expressionParser.parse(expressionStr);
        Object result = expression.execute(Context.newSingleResourceContext("array", nums));
        System.out.println(result);

    }






}
