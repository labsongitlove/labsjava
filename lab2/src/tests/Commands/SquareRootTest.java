package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.SquareRoot;
import source.ContextExecute;
import source.exceptions.CommandException;
import source.exceptions.NotEnoughArgumentsOnStackException;

public class SquareRootTest {
    SquareRoot _sqrt;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _sqrt = new SquareRoot(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 4.0);
        _contextExecute.Push("a");

        _sqrt.Execute(_contextExecute);

        Double value = _contextExecute.GetVarValue("a");
        Assertions.assertEquals(2, value);
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();

        Assertions.assertThrows(NotEnoughArgumentsOnStackException.class, () -> _sqrt.Execute(_contextExecute));
    }
}
