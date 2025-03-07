package source.Commands;

import source.ContextExecute;
import source.exceptions.ArgumentIsNotValid;
import source.exceptions.CommandException;

public class Push implements ICommand{
    private final String[] _args;
    private boolean _wasPushed = false;

    public Push(String[] args){
        _args = args;
    }

    public void Execute(ContextExecute contextExecute) throws CommandException {
        if (!contextExecute.Push(_args[1]) || !IsValid()) {
            throw new ArgumentIsNotValid("An attempt to stack an uninitialized variable.");
        }
        _wasPushed = true;
    }

    public String GetInfo(){
        if (IsValid() && _wasPushed){
            return _args[1] + " is pushed.";
        }
        return "Plus can't execute because argument is not valid.";
    }

    private boolean IsValid(){
        return (_args.length - 1 == 1);
    }
}
