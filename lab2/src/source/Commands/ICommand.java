package source.Commands;

import source.ContextExecute;

public interface ICommand {
    void Execute(ContextExecute contextExecute);
    String GetInfo();
}
