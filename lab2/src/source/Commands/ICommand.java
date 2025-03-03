package source.Commands;

//import java.util.ArrayList;
import source.ContextExecute;

import java.util.HashMap;

public interface ICommand { //TODO usable
    //String _type;
    //ICommand(String type, String); Или нет, тип будет определяться выше?
    void Execute(ContextExecute contextExecute);
    String GetInfo();
    //private void ParseArg();
    //private boolean IsCorrectTest();
}
