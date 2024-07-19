package io.github.chengdb.easy.expression.core.expression;

import io.github.chengdb.easy.expression.core.context.Context;
import io.github.chengdb.easy.expression.core.function.Function;
import io.github.chengdb.easy.expression.core.function.exception.FunctionExecuteException;

/**
 * 符合表达式
 * <p>包含多个函数表达式</p>
 * @author chengdabai
 * @since 1.0.0
 */
public class CompositionExpression extends Expression {

    private final Function<?>[] functions;

    public CompositionExpression(Function<?>[] functions) {
        this.functions = functions;
    }

    @Override
    public Object execute0(Context context) throws FunctionExecuteException {
        int forI = functions.length - 1;
        for (int i = 0; i < forI; i++) {
            functions[i].execute0(context);
        }
        return functions[forI].execute0(context);
    }
}
