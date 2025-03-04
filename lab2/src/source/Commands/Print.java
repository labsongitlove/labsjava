package source.Commands;

import source.ContextExecute;

public class Print implements ICommand{
    String _printInfo = "";

    public Print(String[] data) {
    }

    public void Execute(ContextExecute contextExecute){
        _printInfo = contextExecute.PeekInfo();
        System.out.println(_printInfo);
    }

    public String GetInfo(){
        return _printInfo + " is printed.";
    }
}
