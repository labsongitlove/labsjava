package source.Commands;

import source.ContextExecute;
import source.exceptions.ArgumentIsNotValid;
import source.exceptions.CommandException;
import source.exceptions.NotEnoughArgumentsOnStackException;

public class SquareRoot implements ICommand {
    private String _varName = "";
    private Double _varValue;

    public SquareRoot(String[] data) {
    }


    public void Execute(ContextExecute contextExecute) throws CommandException{
        TryInitialization(contextExecute);
        _varValue = Math.sqrt(_varValue);

        contextExecute.UpdateVar(_varName, _varValue);
        contextExecute.Push(_varName);
    }

    public String GetInfo(){
        if (IsValid()){
            return "Sqrt of the variable " + _varName + " is " + _varValue + ".";
        }
        return "Sqrt can't execute.";
    }

    private void TryInitialization(ContextExecute contextExecute) throws CommandException {
        _varName = contextExecute.Pop();
        if (_varName == null) {
            throw new NotEnoughArgumentsOnStackException("Sqrt can't execute because in stack not enough vars.");
        }
        _varValue = contextExecute.GetVarValue(_varName);
        if (_varValue < 0){
            throw new ArgumentIsNotValid("Sqrt can't execute because value < 0.");
        }
    }

    private boolean IsValid(){
        return !(_varName == null);
            //throw new RuntimeException("Sqrt can't execute because stack is empty.");
    }
}