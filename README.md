# easy-expression
表达式解析。
## 1. 快速开始
### 1.1. 使用示例1
简单的数字加法。
- 示例代码：
```java
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;

public class Main {
    // 表达式解析器
    private static final ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());
    
    public static void main(String[] args) throws ExpressionParseException, ExpressionExecuteException {
        // 表达式字符串
        String expressionStr = "numAdd(1, 2, 3, 4, 5, 6, 7)";
        // 解析成表达式实体
        Expression expression = expressionParser.parse(expressionStr);
        // 执行表达式并输出执行结果
        System.out.println(expression.executeWithoutContext());
    }
}
```
- 控制台输出：
```
28
```
### 1.2. 使用示例2
求数组中所有数字之和。
当然也可以定义一个函数来实现这个功能。如何定义函数将在下文中讲述。
- 示例代码：
```java
import io.github.chengdb.easy.expression.core.expression.Expression;
import io.github.chengdb.easy.expression.core.expression.exception.ExpressionExecuteException;
import io.github.chengdb.easy.expression.function.getter.file.BasedOnFileFunctionGetter;
import io.github.chengdb.easy.expression.parser.expression.ExpressionParser;
import io.github.chengdb.easy.expression.parser.expression.exception.ExpressionParseException;

public class Main {
    // 表达式解析器
    private static final ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter());

    public static void main(String[] args) throws ExpressionParseException, ExpressionExecuteException {
        Integer[] nums = new Integer[]{1,2,3,4,5,6,7};
        String expressionStr = "$SV('sum', 0);arrayForEach($RF(), 'num',segment($SV('sum', numAdd($GV('sum'), $GV('num'))), _false()), 0);$GV('sum');";
        Expression expression = expressionParser.parse(expressionStr);
        Object result = expression.execute(Context.newSingleResourceContext("array", nums));
        System.out.println(result);
    }
}
```
- 控制台输出：
```
28
```
- 代码中的expressionStr：
```
$SV('sum', 0);
arrayForEach(
  $RF(),
  'num',
  segment(
    $SV(
      'sum', 
      numAdd(
        $GV('sum'), 
        $GV('num')
      )
    ), 
    _false() 
  ),
  0
);
$GV('sum');
```