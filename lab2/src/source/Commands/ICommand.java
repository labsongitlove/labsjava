package source.Commands;

//import java.util.ArrayList;
import java.util.HashMap;

public interface ICommand { //TODO usable
    //String _type;
    //ICommand(String type, String); Или нет, тип будет определяться выше?
    HashMap<String, Double> Execute(HashMap<String, Double> vars);
    String GetInfo();
    //private void ParseArg();
    //private boolean IsCorrectTest();
}
