package io.github.chengdb.easy.expression.test;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class FirstTest {

    @Test
    public void functionClassGetterTest() throws ExpressionParseException, ExpressionExecuteException {

        String expressionStr = "$SV('str', 'hello, expression'); notEqual($GV('str'), 'hello, expression1')";

        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

        Expression expression = expressionParser.parse(expressionStr);

        Context context = Context.newNonResourceContext();

        Object result = expression.execute(context);


        System.out.println(result);


    }
    @Test
    public void forkFunctionTest() throws ExpressionParseException, ExpressionExecuteException {

//        String expressionStr = "$SV('fork', fork($GV('condition'), '岔路1', '岔路2'))";
        String expressionStr = "$SV('condition', equal(1, 1));$SV('fork', fork($GV('condition'), '岔路1', '岔路2')); $GV('fork')";

        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

        Expression expression = expressionParser.parse(expressionStr);

        Context context = Context.newNonResourceContext();

        Object result = expression.execute(context);


        System.out.println(result);

        // 将expression序列化
        byte[] bytes = serializeToBytes(expression);


        System.out.println(bytes.length);
        System.out.println(Arrays.toString(bytes));


    }



    // 将对象序列化为字节数组
    private static byte[] serializeToBytes(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
