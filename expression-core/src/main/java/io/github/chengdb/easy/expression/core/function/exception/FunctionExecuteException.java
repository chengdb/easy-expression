package io.github.chengdb.easy.expression.core.function.exception;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class FunctionExecuteException extends Exception {

    private final LinkedList<String> errorPath = new LinkedList<>();

    public FunctionExecuteException(String msg) {
        super(msg);
    }
    public FunctionExecuteException(String msg, Throwable e) {
        super(msg, e);
    }
    public FunctionExecuteException(Throwable e) {
        super(e);
    }



    public void appendPath(String functionName) {
        errorPath.addFirst(functionName);
    }


    public String getErrorPath() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = errorPath.iterator();
        stringBuilder.append(iterator.next());
        while (iterator.hasNext()) {
            stringBuilder.append(" > ").append(iterator.next());
        }
        return stringBuilder.toString();
    }
}
