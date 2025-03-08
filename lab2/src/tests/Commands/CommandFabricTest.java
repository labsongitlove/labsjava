package tests.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.Commands.CommandFabric;
import source.Commands.ICommand;
import source.ContextExecute;
import source.exceptions.ArgumentIsNotValid;
import source.exceptions.InvalidCountOfArguments;

import javax.naming.InvalidNameException;
import java.io.IOException;

public class CommandFabricTest {
    CommandFabric _fab;
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest() throws Exception{
        _contextExecute.Clear();
        _fab = new CommandFabric();

        ICommand command = _fab.GetCommand("DEFINE a 1");

        command.Execute(_contextExecute);

        Assertions.assertEquals("Defined a as 1.", command.GetInfo());
    }

    @Test
    void LetterCaseAndExistCommandTest() throws Exception{
        _contextExecute.Clear();
        _fab = new CommandFabric();

        Assertions.assertThrows(ArgumentIsNotValid.class, () -> _fab.GetCommand("DEFine a 1"));
    }

    @Test
    void ConfigDoNotExistTest(){
        _contextExecute.Clear();
        Assertions.assertThrows(IOException.class, () -> _fab = new CommandFabric("src\\tests\\Commands\\doNotExist.txt"));
    }

    @Test
    void ConfigIsEmptyTest() throws Exception{
        _contextExecute.Clear();
        _fab = new CommandFabric("src\\tests\\Commands\\empty.txt");

        Assertions.assertThrows(ArgumentIsNotValid.class, () -> _fab.GetCommand("DEFine a 1"));
    }

    @Test
    void ConfigContainsNotExistentClassTest(){
        _contextExecute.Clear();
        Assertions.assertThrows(InvalidNameException.class, () -> _fab = new CommandFabric("src\\tests\\Commands\\notExistentClass.txt"));
    }

    @Test
    void ConfigContainsInvalidCountOfArgumentTest(){
        _contextExecute.Clear();
        Assertions.assertThrows(InvalidCountOfArguments.class, () -> _fab = new CommandFabric("src\\tests\\Commands\\invalidCount.txt"));
    }
}
