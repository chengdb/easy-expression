package io.github.chengdb.easy.expression.test;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import org.junit.Test;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class ArrayForEachTest {

    @Test
    public void test1() throws ExpressionParseException, ExpressionExecuteException {
        String expressionStr = "arrayForEach($RF(), 'item', segment(print($GV('item')), equal($GV('item'), '2')), 0)";

        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

        Expression expression = expressionParser.parse(expressionStr);

        Context context = Context.newSingleResourceContext("array", new String[]{"1", "2", "3"});

        Object execute = expression.execute(context);

        System.out.println(execute);
    }


}
