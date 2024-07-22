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
public class StringTest {

    @Test
    public void StrConcatTest() throws ExpressionParseException, ExpressionExecuteException {
        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter(Collections.singletonList("strConcat:io.github.chengdb.easy.expression.test.function.StrConcat")));
        String expressionStr = "strConcat('hello ', 'easy-expression')";
        Expression expression = expressionParser.parse(expressionStr);
        System.out.println(expression.executeWithoutContext());
    }


    @Test
    public void StrConcatPlusTest() throws ExpressionParseException, ExpressionExecuteException {
        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter(Collections.singletonList("strConcatPlus:io.github.chengdb.easy.expression.test.function.StrConcatPlus")));
        String expressionStr = "strConcatPlus('hello', ' ', 'easy', '-', 'expression')";
        Expression expression = expressionParser.parse(expressionStr);
        System.out.println(expression.executeWithoutContext());
    }



}
