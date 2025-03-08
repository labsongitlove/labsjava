package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Plus;
import source.ContextExecute;
import source.exceptions.CommandException;
import source.exceptions.NotEnoughArgumentsOnStackException;

public class PlusTest {
    Plus _plus;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _plus = new Plus(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");
        _contextExecute.UpdateVar("b", 1.0);
        _contextExecute.Push("b");

        _plus.Execute(_contextExecute);

        Double value = _contextExecute.GetVarValue("b");
        Assertions.assertEquals(2, value);
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");

        Assertions.assertThrows(NotEnoughArgumentsOnStackException.class, () -> _plus.Execute(_contextExecute));
    }
}