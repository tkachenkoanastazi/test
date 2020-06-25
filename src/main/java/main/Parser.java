package main;

import org.jetbrains.annotations.NotNull;

public class Parser {
    protected char[][]exprParser(@NotNull String expr)
    {
        char[][] superExpression=new char[2][expr.length()];
        int a =0;
        for(int i=0; i<expr.length();i++)
        {
            if(expr.charAt(i)=='*')
            {
                superExpression[0][a]='.';
            } else {
                if (expr.charAt(i)=='*' || expr.charAt(i)=='+') {
                    throw new UnsupportedOperationException("Неверное построенное регулярное выражение.");
                } else{
                    superExpression[0][a] = expr.charAt(i);
                }
            }
            if(i+1<expr.length()){
                if (expr.charAt(i+1) == '*' || expr.charAt(i+1)=='+')
                {
                    if(expr.charAt(i+1)=='*'){
                        superExpression[1][a]=0;
                        i++;
                    } else{
                        superExpression[1][a]=1;
                        i++;
                    }
                } else {
                    superExpression[1][a]=2;
                }
            }
            a++;
        }

        return superExpression;
    }

    protected int finder (char[][] expr, char[] str){
        boolean isMatch=false;
        boolean canReturn=false;
        String result="";
        int i_expr=0;
        int i_str=0;
        int startOfMathc=-1;
        while (i_expr<expr.length) {
            if (i_str < str.length) {
                if ((expr[0][i_expr] == '.') || (expr[0][i_expr] == str[i_str])) {
                    switch (expr[1][i_expr]) {
                        case 0: {
                            if (i_expr == 0) {
                                canReturn = false;
                            } else
                                canReturn = true;
                            startOfMathc = i_str;
                            break;
                        }
                        case 1: {
                            canReturn = true;
                            startOfMathc = i_str;
                            i_str++;
                            break;
                        }
                        case 2: {
                            canReturn = false;
                            startOfMathc = i_str;
                            i_str++;
                            break;
                        }
                    }
                    i_expr++;
                    isMatch = true;
                    result += str[i_str];
                } else {
                    if (canReturn) {
                        if ((expr[0][i_expr - 1] == '.') || (expr[0][i_expr - 1] == str[i_str])) {
                            if ((expr[1][i_expr - 1] == 1) || (expr[1][i_expr - 1] == 0)) {
                                startOfMathc = i_str;
                                i_str++;
                            }
                            isMatch = true;
                            result += str[i_str];
                        }
                    }else {
                        isMatch = false;
                        i_str++;
                        if (!result.isEmpty()) {
                            i_str = startOfMathc;
                            i_expr = 0;
                        }
                    }
                }
            } else {
                if(!isMatch){
                    result="";
                    startOfMathc=-1;
                }
                break;
            }
        }
        return startOfMathc;
    }
    int parse(String expression, String str){
        char[][] myExpression;
        int result;
        char[] charString;
        charString=str.toCharArray();
        myExpression=exprParser(expression);
        result=finder(myExpression,charString);
        return result;
    }
}