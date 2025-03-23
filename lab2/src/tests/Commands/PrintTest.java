package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.Print;
import source.ContextExecute;
import source.exceptions.CommandExceptions.CommandException;

public class PrintTest {
    Print _print;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _print = new Print(new String[0]);
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws CommandException {
        _contextExecute.Clear();
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");

        _print.Execute(_contextExecute);

        Assertions.assertEquals("a = 1.0 is printed.", _print.GetInfo());
    }
    @Test
    void InvalidCountForExecute(){
        _contextExecute.Clear();

        _print.Execute(_contextExecute);

        Assertions.assertEquals("null is printed.", _print.GetInfo());
    }
}
