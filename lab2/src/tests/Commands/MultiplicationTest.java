package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Multiplication;
import source.ContextExecute;
import source.exceptions.CommandException;
import source.exceptions.NotEnoughArgumentsOnStackException;

public class MultiplicationTest {
    Multiplication _mult;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _mult = new Multiplication(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");
        _contextExecute.UpdateVar("b", 1.0);
        _contextExecute.Push("b");

        _mult.Execute(_contextExecute);

        Double value = _contextExecute.GetVarValue("b");
        Assertions.assertEquals(1, value);
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");

        Assertions.assertThrows(NotEnoughArgumentsOnStackException.class, () -> _mult.Execute(_contextExecute));
    }
}
