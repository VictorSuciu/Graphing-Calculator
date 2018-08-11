# Graphing-Calculator
This is a graphing calculator application I made as a project in grade 12. It can be used to visualize functions of many different types and complexities.

</br>

</br>

![alt text](https://github.com/VictorSuciu/Graphing-Calculator/blob/master/Images/GC_Example.png)

</br>

## Supported Elements

| Category | Elements |
| :---: | --- |
| Operators | <ul><li>Addition **+**</li><li>Subtraction **-** (also used as a negative symbol)</li><li>Multiplication **\***</li><li>Division **/**</li><li>Exponent **^**</li></ul> |
| Trig Functions | <ul><li>**sin()**</li><li>**cos()**</li><li>**tan()**</li><li>**arcsin()**</li><li>**arccos()**</li><li>**arctan()**</li></ul> |
| Other Functions | <ul><li>**log()** (base 10 only)</li><li>**ln()**</li><li>**abs()** (absolute value)</li><li>**sqrt()** (squre root)</li></ul> |
| Constants | <ul><li>**pi**</li><li>**e**</li></ul> |

</br>

## Key Features

### Order of Operations

All PEMDAS rules, including parentheses, are taken into account. There is no limit to how many nested parentheses and functions can exist in a user's equation. 

### Navigation Controls

The graph plane can be moved in any direction using the circular navigation pad, and can be zoomed in and out using the +/- buttons. The dimentions of the plane can also be set manually using the max/min input boxes at the top right.

### Memory

Previously entered functions can be recalled using the back/forward arrow buttons to the right of the equation input box.

### Error Reporting

This graphing calculator will notify the user of errors in the function they are trying to graph. There are three categories of errors.

1. **Unbalanced parentheses.** When there are not the same number of open and closed parentheses.
```
Unclosed parentheses
"sin(2 - x"

Two extra close parentheses
"3(x + 5)(log(x^3) -4)))"
```

</br>

2. **Unrecognized element.** When a user enters a letter, symbol, or any equation element that the calculator doesn't know.
```
Unrecongized element starting with "L"
"LOL i dont wanna graff"

Unrecongized element starting with "c"
"log(5x) * csc(x)"
```

</br>

3. **Invalid use of an operator.** When the user tries to use an operator incorectly or in the wrong context.
```
Incorrect use of + operator
"(5x + )"

Incorrect use of / operator
"3 // x"
```

</br>

4. **Invalid x/y parameters set.** When the user sets the X/Y Minimum value to be equal to or greater than the X/Y Maximum value.
```
Graph plane cannot have width of 0
X Min = 2
X Max = 2

Graph plane cannot have negative/reversed height
Y min = 5
Y Max = -1
```

### Implicit Multiplication

Multiplication can be entered as a "\*" multiplication symbol `2 * x`, or without any symbol `2x`.
