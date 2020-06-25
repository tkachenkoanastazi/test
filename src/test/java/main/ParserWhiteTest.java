package main;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserWhiteTest extends Parser{

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public  void dot_end() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="a.";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            char[][] result=parser.exprParser(str,(int)length);
            char[][] actual=new char[2][2];
            actual[0]="a.".toCharArray();
            actual[1][0]=2;
            actual[1][1]=2;
            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public  void lonelyDot_notEnd() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="a.aa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            char[][] result=parser.exprParser(str,(int)length);
            char[][] actual=new char[2][4];
            actual[0]="a.aa".toCharArray();
            actual[1][0]=2;
            actual[1][1]=2;
            actual[1][2]=2;
            actual[1][3]=2;

            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public  void oneMoreDot_notEnd() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="a.+aa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);

            char[][] result=parser.exprParser(str,(int)length);

            char[][] actual=new char[2][4];
            actual[0]="a.aa".toCharArray();
            actual[1][0]=2;
            actual[1][1]=1;
            actual[1][2]=2;
            actual[1][3]=2;

            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public void zeroMoreDot_notEnd() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="a.*aa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            char[][] result=parser.exprParser(str,(int)length);
            char[][] actual=new char[2][4];
            actual[0]="a.aa".toCharArray();
            actual[1][0]=2;
            actual[1][1]=0;
            actual[1][2]=2;
            actual[1][3]=2;
            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public void plusOrStar() {/////////////////////////////////////////////////////////////
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="*";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);

            thrown.expect(UnsupportedOperationException.class);
            char[][] result=parser.exprParser(str,(int)length);
            thrown.expectMessage("Неверно построено регулярное выражение");
            // assertEquals("Неверно построено регулярное выражение", ,););
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public void symbol_end() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="aaaa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            char[][] result=parser.exprParser(str,(int)length);
            char[][] actual=new char[2][4];
            actual[0]="aaaa".toCharArray();
            actual[1][0]=2;
            actual[1][1]=2;
            actual[1][2]=2;
            actual[1][3]=2;
            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public void lonelySymbol_notEnd() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="aaaa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            char[][] result=parser.exprParser(str,(int)length);
            char[][] actual=new char[2][4];
            actual[0]="aaaa".toCharArray();
            actual[1][0]=2;
            actual[1][1]=2;
            actual[1][2]=2;
            actual[1][3]=2;
            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public void oneMoreSymbol_notEnd() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="a+aa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            char[][] result=parser.exprParser(str,(int)length);
            char[][] actual=new char[2][3];
            actual[0]="aaa".toCharArray();
            actual[1][0]=1;
            actual[1][1]=2;
            actual[1][2]=2;
            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

    @Test
    public void zeroMoreSymbol_notEnd() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String str="a*aa";
            Parser parser=new Parser();
            long length=parser.lengthOfSuperExpression(str);
            System.out.println(length);
            char[][] result=parser.exprParser(str,(int)length);
            System.out.println(result[0].length);
            for(int i=0;i<result.length;i++){
                System.out.println(result[0][i]);
                System.out.println(result[1][i]);
            }
            char[][] actual=new char[2][3];
            actual[0]="aaa".toCharArray();
            actual[1][0]=0;
            actual[1][1]=2;
            actual[1][2]=2;
            Assert.assertEquals(result, actual);
        }, () -> "Тест выполняется больше 1000 ms");
    }

}
