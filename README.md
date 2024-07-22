# easy-expression
表达式解析。
## 1. 快速开始
### 1.1. 使用示例1
简单的数字加法。
- 示例代码：
```java
public class Main {
    // 表达式解析器
    // 关于BasedOnFileFunctionGetter，将在后文讲述
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
## 2. 自定义函数入门
### 2.1. 示例1
在 _1.2. 使用示例2_ 中，使用复合表达式实现了数字数组中的各个元素相加，下面实现自定义一个函数，实现这个功能。
- 定义函数
```java
@Func(
        // 函数名
        name = "arrayNumAdd",
        // 入参个数
        argNum = 1,
        // 入参的类型
        argsType = Number[].class,
        // 函数的返回类型
        returnType = Number.class,
        // 描述，可选的
        describe = "数字数组相加(数字数组:Number[]):相加结果:Number"
)
public class ArrayNumAdd extends ArgFunction<Number> {
    @Override
    protected Number execute(Context context) throws FunctionExecuteException {
        // 获取函数的第一个入参，是Number[]类型
        // 等价于Number[] numbers = functionArgs.getArgResultNotNull(0, context, Number[].class);
        Number[] numbers = functionArgs.getArgResult(context, Number[].class);
        // 遍历数字数组，将各个数字相加。这里使用了BigDecimal以适用更多类型的数字
        BigDecimal result = new BigDecimal(0);
        for (Number number : numbers) {
            if (number instanceof BigDecimal bDNumber) {
                result = result.add(bDNumber);
            } else {
                result = result.add(new BigDecimal(number.toString()));
            }
        }
        return result;
    }
}
```
- 使用
```java
public class Main {
    private static final ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter(Collections.singletonList("arrayNumAdd:io.github.chengdb.easy.expression.test.function.ArrayNumAdd")));
    public static void main(String[] args) {
        Integer[] nums = new Integer[]{1,2,3,4,5,6,7};
        // 使用自定义的函数arrayNumAdd
        String expressionStr = "arrayNumAdd($RF())";
        Expression expression = expressionParser.parse(expressionStr);
        Object result = expression.execute(Context.newSingleResourceContext("array", nums));
        System.out.println(result);
    }
    
    
}
```
控制台输出：
```
28
```
### 2.1. 示例2
字符串拼接函数。将两个字符串拼接成一个字符。
- 定义函数
```java
@Func(
        name = "strConcat",
        argNum = 2,
        argsType = {String.class, String.class},
        returnType = String.class,
        describe = "字符串拼接(字符串1:String, 字符串2:String):(拼接后的字符串:String)"
)
public class StrConcat extends ArgFunction<String> {
    @Override
    protected String execute(Context context) throws FunctionExecuteException {
        String str1 = functionArgs.getArgResult(context, String.class);
        String str2 = functionArgs.getArgResult(1, context, String.class);
        if (str1 == null) {
            return str2;
        } else {
            return str2 == null ? str1 : str1 + str2;
        }
    }
}
```
- 使用
```java
public class Main {
    public static void main(String[] args) {
        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter(Collections.singletonList("strConcat:io.github.chengdb.easy.expression.test.function.StrConcat")));
        String expressionStr = "strConcat('hello ', 'easy-expression')";
        Expression expression = expressionParser.parse(expressionStr);
        System.out.println(expression.executeWithoutContext());
    }
}
```
控制台输出：
```
hello easy-expression
```
### 2.3. 示例3
_2.2. 示例2_ 中，仅支持两个字符串拼接，下面实现多个（两个及以上）字符串拼接。
- 定义函数
```java
@Func(
        name = "strConcatPlus",
        // -2代表最少两个入参。第2之后的入参类型和第2个相同。
        argNum = -2,
        argsType = {String.class, String.class},
        returnType = String.class,
        describe = "字符串拼接(字符串1:String, 字符串2...:String):(拼接结果:String)"
)
public class StrConcatPlus extends ArgFunction<String> {
    @Override
    protected String execute(Context context) throws FunctionExecuteException {
        StringBuilder stringBuilder = new StringBuilder();
        // 使用ArgsIterator方便的遍历入参
        FunctionArgs.ArgsIterator iterator = functionArgs.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next(context, String.class);
            if (str == null) {
                continue;
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
```
- 使用
```java
public class Main {
    public static void main(String[] args) {
        ExpressionParser expressionParser = ExpressionParser.newInstance(new BasedOnFileFunctionGetter(Collections.singletonList("strConcatPlus:io.github.chengdb.easy.expression.test.function.StrConcatPlus")));
        String expressionStr = "strConcatPlus('hello', ' ', 'easy', '-', 'expression')";
        Expression expression = expressionParser.parse(expressionStr);
        System.out.println(expression.executeWithoutContext());
    }
}
```
控制台输出：
```
hello easy-expression
```
