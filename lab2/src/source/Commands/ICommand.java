package source.Commands;

import source.ContextExecute;
import source.exceptions.CommandException;

public interface ICommand {
    void Execute(ContextExecute contextExecute) throws CommandException;
    String GetInfo();
}
