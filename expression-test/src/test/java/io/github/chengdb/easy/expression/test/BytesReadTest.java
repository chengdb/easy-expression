package io.github.chengdb.easy.expression.test;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class BytesReadTest {


    ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

    @Test
    public void intTest() throws ExpressionParseException, ExpressionExecuteException {
        long num = -2342343847928374928L;
        byte[] bytes = new byte[Long.BYTES];

        ByteBuffer.wrap(bytes).putLong(num);

        byte[] bytes2 = new byte[Long.BYTES];

        for (int i = 0; i < bytes.length; i++) {
            bytes2[Long.BYTES - i - 1] = bytes[i];
        }
        bytes = bytes2;


        String expressionStr = "readLongLE($RF(), 0)";



        Expression expression = expressionParser.parse(expressionStr);

        Context context = Context.newSingleResourceContext("bytes", bytes);

        Object execute = expression.execute(context);
        System.out.println(execute);

    }

    @Test
    public void readAllTest() throws ExpressionParseException, ExpressionExecuteException {

        byte[] bytes = new byte[]{-1, -1, 85, 43, 113, 109, -82, 57};

        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);

        Context context = Context.newSingleResourceContext("bytes", bytes);
        Object resultExpression;
        Object resultNetty;

        String expressionStr = "readLongLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getLongLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readLong($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getLong(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readInt($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getInt(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readIntLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getIntLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readFloat($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getFloat(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readFloatLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getFloatLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readDouble($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getDouble(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readDoubleLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getDoubleLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readByte($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getByte(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readBoolean($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getBoolean(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readShort($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getShort(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readShortLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getShortLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedByte($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedByte(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedInt($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedInt(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedIntLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedIntLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedMedium($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedMedium(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedMediumLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedMediumLE(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedShort($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedShort(0))+ "----" + resultNetty.getClass().getName());

        expressionStr = "readUnsignedShortLE($RF(), 0)";
        System.out.println("==========" + expressionStr);
        System.out.println("expression:" + (resultExpression = expressionParser.parse(expressionStr).execute(context)) + "----" + resultExpression.getClass().getName());
        System.out.println("     netty:" + (resultNetty = byteBuf.getUnsignedShortLE(0))+ "----" + resultNetty.getClass().getName());
    }


}
