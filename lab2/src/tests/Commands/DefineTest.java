package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Define;
import source.ContextExecute;
import source.exceptions.ArgumentIsNotValid;
import source.exceptions.CommandException;
import source.exceptions.InvalidCountOfArguments;

public class DefineTest {
    Define _def;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _def = new Define(new String[]{"", "a", "1"});

        _def.Execute(_contextExecute);

        Assertions.assertEquals("Defined a as 1.", _def.GetInfo());
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();
        _def = new Define(new String[]{"", "a"});

        Assertions.assertThrows(InvalidCountOfArguments.class, () -> _def.Execute(_contextExecute));
    }

    @Test
    void SecondArgumentIsNotNumber(){
        _contextExecute.Clear();
        _def = new Define(new String[]{"", "a", "b"});

        Assertions.assertThrows(ArgumentIsNotValid.class, () -> _def.Execute(_contextExecute));
    }
}