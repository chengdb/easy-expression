import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import org.junit.Test;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class ParserTest {

    @Test
    public void test1() throws ExpressionParseException, ExpressionExecuteException {
        ExpressionParser expressionParser = ExpressionParser.newInstance();
        Expression expression = expressionParser.parse("$SV('var', $R('theResource')); $GV('var')");
//        Context context = Context.newSingleResourceContext("theResource", "你好，表达式");
        Context context = Context.newNonResourceContext();
        Object o = expression.execute(context);
        System.out.println(o);
    }
}
