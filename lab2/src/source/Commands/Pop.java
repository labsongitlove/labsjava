package source.Commands;

import source.ContextExecute;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.NotEnoughArgumentsOnStackException;

public class Pop implements ICommand{
    private String _varInfo;

    public Pop(String[] data) {
    }

    public void Execute(ContextExecute contextExecute) throws CommandException {
        _varInfo = contextExecute.PeekInfo();
        if (contextExecute.Pop() == null) {
            throw new NotEnoughArgumentsOnStackException("Null reference.");
        }
    }

    public String GetInfo(){
        return _varInfo + " is pop.";
    }
}
