package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Minus;
import source.ContextExecute;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.NotEnoughArgumentsOnStackException;

public class MinusTest {
    Minus _minus;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _minus = new Minus(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");
        _contextExecute.UpdateVar("b", 2.0);
        _contextExecute.Push("b");

        _minus.Execute(_contextExecute);

        Double value = _contextExecute.GetVarValue("b");
        Assertions.assertEquals(1, value);
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");

        Assertions.assertThrows(NotEnoughArgumentsOnStackException.class, () -> _minus.Execute(_contextExecute));
    }
}
