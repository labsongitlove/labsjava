package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Push;
import source.ContextExecute;
import source.exceptions.CommandExceptions.ArgumentIsNotValid;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.InvalidCountOfArguments;

public class PushTest {
    Push _push;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _push = new Push(new String[]{"", "a"});

        _push.Execute(_contextExecute);

        Assertions.assertEquals("a = 1.0", _contextExecute.PeekInfo());
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();
        _push = new Push(new String[]{""});

        Assertions.assertThrows(InvalidCountOfArguments.class, () -> _push.Execute(_contextExecute));
    }

    @Test
    void ArgumentNotBeingContext(){
        _contextExecute.Clear();
        _push = new Push(new String[]{"", "a"});

        Assertions.assertThrows(ArgumentIsNotValid.class, () -> _push.Execute(_contextExecute));
    }
}
