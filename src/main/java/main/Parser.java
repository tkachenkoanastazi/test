package main;

import Implemented.WrongExpressionCheck;

public class Parser {

    public char[][] exprParser(String expr, int length) throws UnsupportedOperationException{
        if(length==0){
            throw new UnsupportedOperationException("Неверно построено регулярное выражение");
        }
        char[][] superExpression=new char[2][length];
        int a=0;//length of superExpression
        for(int i=0; i<expr.length();i++){
            if(expr.charAt(i)=='*'){
                superExpression[0][a]='.';
            }else{
                if(expr.charAt(i)=='*'||expr.charAt(i)=='+'){
                    throw new UnsupportedOperationException("Неверно построено регулярное выражение");
                }else{
                    superExpression[0][a]=expr.charAt(i);
                }
            }
            if(i+1<expr.length()) {
                if (expr.charAt(i+1)=='*' || expr.charAt(i+1)=='+') {
                    if (expr.charAt(i+1)=='*') {
                        superExpression[1][a] = 0;
                        i++;
                    } else {
                        superExpression[1][a] = 1;
                        i++;
                    }
                } else {
                    superExpression[1][a] = 2;
                }

            }else{
                superExpression[1][a] = 2;
            }

            a++;
        }

        return superExpression;
    }

    public int finder(char[][] expr, char [] str){
        boolean isMatch=false;
        boolean canReturn=false;
        String result="";
        int i_expr=0;
        int i_str=0;
        int startOfMatch=-1;
        while(i_expr<expr[0].length){
            if(i_str<str.length){
                if((expr[0][i_expr]=='.')||(expr[0][i_expr]==str[i_str])){
                    switch(expr[1][i_expr]){
                        case 0:{
                            if(i_expr==0){
                                canReturn=false;
                            }
                            else {
                                canReturn = true;
                            }
                            if(startOfMatch==-1)
                                startOfMatch=i_str;
                            break;
                        }
                        case 1:{
                            canReturn=true;
                            if(startOfMatch==-1)
                                startOfMatch=i_str;
                            //i_str++;
                            break;
                        }
                        case 2:{
                            canReturn=false;
                            if(startOfMatch==-1)
                                startOfMatch=i_str;
                            //i_str++;
                            break;
                        }
                    }
                    i_str++;
                    i_expr++;
                    isMatch=true;
                    result+=str[i_str];
                }else {
                    if (canReturn) {
                        if ((expr[0][i_expr-1] == '.') || (expr[0][i_expr-1] == str[i_str])) {
                            if((expr[1][i_expr-1]==1)||(expr[1][i_expr-1]==0)) {
                                if(startOfMatch==-1)
                                    startOfMatch=i_str;
                                i_str++;
                            }
                            isMatch = true;
                            result += str[i_str];
                        }else{
                            isMatch=false;
                            i_str=startOfMatch+1;
                            startOfMatch=-1;
                            canReturn=false;
                            i_expr=0;
                            result="";
                        }
                    }else{
                        isMatch=false;
                        canReturn=false;
                        i_str++;
                        if(!result.isEmpty()){
                            i_str=startOfMatch+1;
                            startOfMatch=-1;
                            i_expr=0;
                            result="";
                        }
                    }
                }
            }else{
                if (!isMatch){
                    result="";
                    startOfMatch=-1;
                }
                break;
            }
        }

        return startOfMatch;

    }

    public long lengthOfSuperExpression(String expression){
        return expression.length()-(expression.chars().filter(ch -> ch =='+').count()+expression.chars().filter(ch -> ch =='*').count());
    }

    public int parse(String expression, String str){
        WrongExpressionCheck isExprWrong=new WrongExpressionCheck();
        if(isExprWrong.check(expression))
            return -1;
        char [][] myExpression;//=new char[2][expression.length()];
        int result;
        char[] charString;
        charString = str.toCharArray();
        myExpression=exprParser(expression, (int)lengthOfSuperExpression(expression));
        result=finder(myExpression,charString);
        return result;
    }


}
