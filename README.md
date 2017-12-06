# Expression

A Content Analytics Studio normaliser plugin that aids in normalising numbers. The normaliser returns a boolean valued string (true or false) that is the result of evaluating the specified expression passed as an argument.
The first argument to Evaluate must be an expression string, subsequent arguments should be the features to substitute into the variables specified in the expression. Up to four variables are supported.
               
**Expression syntax**
Normalizer functions in CA Studio like Evaluate get passed the Arguments as an array of Strings. That means Evaluate doesn't know the names of the features being used as arguments or the data types you want them to be interpreted as. So in expressions you use simple positional arguments to reference the argument you want to use as a variable in the expression with a number referencing the position of the feature value in the Arguments array you want to substitute in that variable. Supported data types are string (%s), integer (%d) and float (%f). In the above Patient example, with age, gender and weight as the additional arguments, referencing age as an integer would be %d1, gender as a string would be %s2, and weight as a float would be %f3. So our desired expression of
```
age > 50 && ((gender=="male" && weight < 75.0) || (gender=="female" && weight < 50.0))
```
is entered in the expression String feature as
```
%d1 > 50 && ((%s2=="male" && %f3 < 75.0) || (%s2=="female" && %f3 < 50.0))
```
**Supported Operators** 
The expressions submitted to Evaluate must only return true or false. Several of the operators and the functions return data types other than boolean, so be careful that the overall expression is a boolean. Something like "length(%s1) > 5" is perfectly valid but "length(%s1)" on its own will return unpredictable results.

- Mathematical:  + - * / %
- Comparison: == != < > <= >=
- Boolean:  && ||
- Controlling precedence:  ( )

Available functions: 
```
length(String str) returns int
substring(String str, int beginIndex) returns String
substring(String str, int beginIndex, int endIndex) returns String
equalsIgnoreCase(String str, String testString) returns boolean
indexOf(String str, String testString) returns int
indexOf(String str, String testString, int fromIndex) returns int
endsWith(String str, String suffix) returns boolean
```
