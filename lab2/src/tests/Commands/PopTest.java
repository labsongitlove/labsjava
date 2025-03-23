package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Pop;
import source.ContextExecute;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.NotEnoughArgumentsOnStackException;

public class PopTest {
    Pop _pop;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _pop = new Pop(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");
        _contextExecute.UpdateVar("b", 2.0);
        _contextExecute.Push("b");

        _pop.Execute(_contextExecute);

        Assertions.assertEquals("a = 1.0", _contextExecute.PeekInfo());
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();

        Assertions.assertThrows(NotEnoughArgumentsOnStackException.class, () -> _pop.Execute(_contextExecute));
    }
}
