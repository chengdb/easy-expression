package io.github.chengdb.easy.expression.function.bytes.read;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class ReadIntegerTest {
    @Test
    public void test() {
        int num = -234234;
        byte[] bytes = new byte[Integer.BYTES];
        ByteBuffer.wrap(bytes).putInt(num);

        int index = 0;
        int result = 0;
        for (int i = 0; i < Integer.BYTES; i++) {
            result = (result << 8) | (bytes[index + i] & 0xFF);
        }
        assert result == num;
    }


}
