package source.Commands;

import source.ContextExecute;

public class Pop implements ICommand{
    private String _varInfo;

    public void Execute(ContextExecute contextExecute){
        _varInfo = contextExecute.PeekInfo();
        if (contextExecute.Pop() == null) {
            throw new RuntimeException("Null reference.");
        }
    }

    public String GetInfo(){
        return _varInfo + " is pop.";
    }
}
