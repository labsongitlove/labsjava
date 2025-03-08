package source.Commands;

import source.ContextExecute;
import source.exceptions.ArgumentIsNotValid;
import source.exceptions.CommandException;
import source.exceptions.InvalidCountOfArguments;

public class Define implements ICommand {
    private final String[] _args;
    private Double _value = null;
    public Define(String[] args){
        _args = args;
    }

    public void Execute(ContextExecute contextExecute) throws CommandException{
        TryInitialization();
        contextExecute.UpdateVar(_args[1], _value);
    }
    public String GetInfo(){
        if (IsValid()){
            return String.format("Defined %s as %s.", _args[1], _args[2]);
        }
        return "Define can't execute";
    }
    private void TryInitialization() throws CommandException {
        if (_args.length - 1 != 2){
            throw new InvalidCountOfArguments("Invalid count of argument.");
        }
        try {
            _value = Double.parseDouble(_args[2]);
        } catch (NumberFormatException e) {
            throw new ArgumentIsNotValid("Second argument is not number.");
        }
    }
    private boolean IsValid(){
        return !(_value == null || _args.length - 1 != 2);
    }
}
