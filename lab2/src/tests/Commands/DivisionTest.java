package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Division;
import source.ContextExecute;
import source.exceptions.CommandExceptions.ArgumentIsNotValid;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.NotEnoughArgumentsOnStackException;

public class DivisionTest {
    Division _div;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _div = new Division(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");
        _contextExecute.UpdateVar("b", 1.0);
        _contextExecute.Push("b");

        _div.Execute(_contextExecute);

        Double value = _contextExecute.GetVarValue("b");
        Assertions.assertEquals(1, value);
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");

        Assertions.assertThrows(NotEnoughArgumentsOnStackException.class, () -> _div.Execute(_contextExecute));
    }

    @Test
    void DivisionByZero(){
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 0.0);
        _contextExecute.Push("a");
        _contextExecute.UpdateVar("b", 1.0);
        _contextExecute.Push("b");

        Assertions.assertThrows(ArgumentIsNotValid.class, () -> _div.Execute(_contextExecute));
    }
}
