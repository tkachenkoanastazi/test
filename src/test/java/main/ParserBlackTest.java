package main;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ParserBlackTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    Parser parser = new Parser();

    @Test
    public void simpleString()
    {
        String expression="mama";
        String str = "papa mama ya";
        int result = parser.parse(expression,str);
        assertEquals(6,result);
    }
    @Test
    public void dotString()
    {
        String expression = "m.";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(6,result);
    }
    @Test
    public void plusString()
    {
        String expression = "m+ama";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(6,result);
    }
    @Test
    public void starString()
    {
        String expression ="m*ma";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(5,result);
    }
    @Test
    public void dotstarString()
    {
        String expression ="m.*ma";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(6,result);
    }
    @Test
    public void dotplusString() {
        String expression ="m.+ma";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(6,result);
    }
    @Test
    public void hardLettersParse() {
        String expression ="a.a";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(2,result);
    }
    @Test
    public void noMatchLettersParse()
    {
        String expression ="k.*a";
        String str = "papa mama ya";
        int result= parser.parse(expression,str);
        assertEquals(-1,result);
    }
    @Test
    public void symbolsParse() {
        String expression="ma*ma";
        String str="?papa& ma*ma)+ ya";
        int result = parser.parse(expression,str);
        assertEquals(8, result);
    }
    @org.junit.Test
    public void quoteParse() {
        String expression="ma*ma\"";
        String str="?pa\"pa& ma*ma\")+ ya";
        int result = parser.parse(expression,str);
        assertEquals(9, result);
    }
    @Test
    public void russianParse() {
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            String expression="м*м";
            String str="мама папа я";
            int result = parser.parse(expression,str);
            assertEquals(0, result);
        }, () -> "Тест выполняется больше 1000 ms");
    }
    @Test
    public void zeroTextParse() {
        String expression=" ";
        String str="ма";
        int result = parser.parse(expression,str);
        assertEquals(-1, result);
    }
    @Test
    public void zeroTextAndStringParse() {
        String expression="";
        String str="";
        int result = parser.parse(expression,str);
        assertEquals(-1, result);
    }

}