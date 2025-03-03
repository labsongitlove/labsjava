package source.Commands;

public interface ICommand { //TODO usable
    //String _type;
    //ICommand(String type, String); Или нет, тип будет определяться выше?
    void Do(String arg);
    //private void ParseArg();
    //private boolean IsCorrectTest();
}
