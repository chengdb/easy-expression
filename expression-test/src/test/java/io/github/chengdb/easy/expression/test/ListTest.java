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
public class ListTest {

    @Test
    public void test() throws ExpressionParseException, ExpressionExecuteException {

        String expressionStr = "$SV('list', _emptyList()); " +
                "arrayForEach($RF(), 'item', segment(listPut($GV('list'), $GV('item')), _false()), 0);" +
//                "arrayForEach($RF(), 'item', listPut($GV('list'), $GV('item')), 0);" +
                "$GV('list')";

        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

        Expression expression = expressionParser.parse(expressionStr);

        Context context = Context.newSingleResourceContext("array", new String[]{"1", "2", "3"});

        Object execute = expression.execute(context);
        System.out.println(execute);
    }


}
