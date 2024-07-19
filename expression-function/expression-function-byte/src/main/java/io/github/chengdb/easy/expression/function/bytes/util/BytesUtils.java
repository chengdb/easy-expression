package io.github.chengdb.easy.expression.function.bytes.util;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class BytesUtils {


    public static short readShort(byte[] bytes, int index) {
        short result = 0;
        for (int i = 0; i < Short.BYTES; i++) {
            result = (short) ((result << 8) | (bytes[index + i] & 0xFF));
        }
        return result;
    }

    public static short readShortLE(byte[] bytes, int index) {
        short result = 0;
        for (int i = index + Short.BYTES; i >= index; i--) {
            result = (short) ((result << 8) | (bytes[index + i] & 0xFF));
        }
        return result;
    }

    public static int readInt(byte[] bytes, int index) {
        int result = 0;
        for (int i = 0; i < Integer.BYTES; i++) {
            result = (result << 8) | (bytes[index + i] & 0xFF);
        }
        return result;
    }

    public static int readIntLE(byte[] bytes, int index) {
        int result = 0;
        for (int i = index + Integer.BYTES - 1; i >= index; i--) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return result;
    }


    public static int readMediumLE(byte[] bytes, int index) {
        int result = 0;
        for (int i = index + 2; i >= index; i--) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return result;
    }

    public static int readMedium(byte[] bytes, int index) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            result = (result << 8) | (bytes[index + i] & 0xFF);
        }
        return result;
    }

}
