package io.github.chengdb.easy.expression.test.function;

/**
 * @author chengdabai
 * @since 1.0.0
 */
public class OutputManager {
    private final String prefix;

    public OutputManager(String prefix) {
        this.prefix = prefix;
    }

    public void output(String s) {
        System.out.println(prefix + " - output manager : " + s);
    }

}
