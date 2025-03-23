package source.Commands;

import source.ContextExecute;
import source.exceptions.CommandExceptions.CommandException;

public interface ICommand {
    void Execute(ContextExecute contextExecute) throws CommandException;
    String GetInfo();
}
