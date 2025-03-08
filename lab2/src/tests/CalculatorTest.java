package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import source.Calculator;

import java.io.FileNotFoundException;

public class CalculatorTest {

    @Test
    void ValidTest() throws Exception{
        Calculator _calc = new Calculator("src\\tests\\testinput.txt");
        Assertions.assertEquals("Defined a as 4.", _calc.ExecuteCommand());
        Assertions.assertEquals("a is pushed.", _calc.ExecuteCommand());
        Assertions.assertEquals("Sqrt of the variable a is 2.0.", _calc.ExecuteCommand());
        Assertions.assertEquals("There are no more executable commands.", _calc.ExecuteCommand());
    }

    @Test
    void InputFileIsNotExist(){
        Assertions.assertThrows(FileNotFoundException.class, () -> new Calculator("src\\tests\\notExist.txt"));
    }
}
