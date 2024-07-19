package io.github.chengdb.easy.expression.test;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.core.function.value.holder.UpdatableFuncStaticValueHolder;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import io.github.chengdb.easy.expression.test.function.OutputManager;
import org.junit.Test;

import java.util.Collections;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class StaticValueInjectTest {

    @Test
    public void NoUpdatableTest() throws ExpressionParseException, ExpressionExecuteException {
        String expressionStr = "output($RF())";
        UpdatableFuncStaticValueHolder funcStaticValueHolder = new UpdatableFuncStaticValueHolder();
        funcStaticValueHolder.setStaticValue(new OutputManager("111"));
        BasedOnFileFunctionGetter functionClassGetter = new BasedOnFileFunctionGetter(Collections.singletonList("output:com.lj.easy.expression.test.function.OutputTestFunction"));
        ExpressionParser expressionParser = ExpressionParser.newInstance(functionClassGetter, funcStaticValueHolder);
        Expression expression = expressionParser.parse(expressionStr);
        Context context = Context.newSingleResourceContext("str", "test-staticValueInject");
        expression.execute(context);
        funcStaticValueHolder.updateStaticValue(new OutputManager("222"));
        expression.execute(context);
    }







}
