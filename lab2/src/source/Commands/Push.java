package source.Commands;

import source.ContextExecute;
import source.exceptions.CommandExceptions.ArgumentIsNotValid;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.InvalidCountOfArguments;

public class Push implements ICommand{
    private final String[] _args;
    private boolean _wasPushed = false;

    public Push(String[] args){
        _args = args;
    }

    public void Execute(ContextExecute contextExecute) throws CommandException {
        if (!IsValid()){
            throw new InvalidCountOfArguments("Invalid count of argument.");
        }
        if (!contextExecute.Push(_args[1])) {
            throw new ArgumentIsNotValid("An attempt to stack an uninitialized variable.");
        }
        _wasPushed = true;
    }

    public String GetInfo(){
        if (IsValid() && _wasPushed){
            return _args[1] + " is pushed.";
        }
        return "Push can't execute because argument is not valid.";
    }

    private boolean IsValid(){
        return (_args.length - 1 == 1);
    }
}
