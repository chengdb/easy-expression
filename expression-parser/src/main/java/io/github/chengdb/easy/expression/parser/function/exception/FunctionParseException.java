package io.github.chengdb.easy.expression.parser.function.exception;

/**
 * 函数解析错误
 * @author chengdabai
 * @since 1.0.0
 * @since 2023/11/1
 */
public class FunctionParseException extends Exception {

    private int errorCharIndex;



    public FunctionParseException(String msg) {
        super(msg);
    }

    public FunctionParseException() {
        super("无法解析的字段");
    }

    public FunctionParseException(String msg, int errorCharIndex) {
        super(msg);
        this.errorCharIndex = errorCharIndex;
    }


    public void setErrorCharIndex(int errorCharIndex) {
        this.errorCharIndex = errorCharIndex;
    }

    public int getErrorCharIndex() {
        return errorCharIndex;
    }
}
