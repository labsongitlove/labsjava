package source.Commands;

import source.ContextExecute;

public interface ICommand { //TODO usable
    void Execute(ContextExecute contextExecute);
    String GetInfo();
}
