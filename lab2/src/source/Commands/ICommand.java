package source.Commands;

//import java.util.ArrayList;
import java.util.Map;

public interface ICommand { //TODO usable
    //String _type;
    //ICommand(String type, String); Или нет, тип будет определяться выше?
    void Execute(Map<String, Double> vars);
    //private void ParseArg();
    //private boolean IsCorrectTest();
}
