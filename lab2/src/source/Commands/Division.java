package source.Commands;

import source.ContextExecute;
import source.exceptions.CommandExceptions.ArgumentIsNotValid;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.NotEnoughArgumentsOnStackException;

public class Division implements ICommand {
    private String _varName1 = "";
    private String _varName2 = "";
    private Double _varValue1;
    private Double _varValue2;

    public Division(String[] data) {
    }

    public void Execute(ContextExecute contextExecute) throws CommandException {
        TryInitialization(contextExecute);
        _varValue1 /= _varValue2;

        contextExecute.UpdateVar(_varName1, _varValue1);
        contextExecute.Push(_varName1);
    }

    public String GetInfo(){
        if (IsValid()){
            return "Div of the variables " + _varName1 + " and " + _varName2 + " is " + _varValue1 + ".";
        }
        return "Div can't execute.";
    }

    private void TryInitialization(ContextExecute contextExecute) throws CommandException{
        _varName1 = contextExecute.Pop();
        if (_varName1 == null){
            throw new NotEnoughArgumentsOnStackException("Div can't execute because in stack not enough vars.");
        }
        _varName2 = contextExecute.Pop();
        if (_varName2 == null){
            contextExecute.Push(_varName1);
            throw new NotEnoughArgumentsOnStackException("Div can't execute because in stack not enough vars.");
        }
        _varValue1 = contextExecute.GetVarValue(_varName1);
        _varValue2 = contextExecute.GetVarValue(_varName2);
        if (_varValue2 == 0){
            throw new ArgumentIsNotValid("Div can't execute because second var is zero.");
        }

    }
    private boolean IsValid(){
        return !(_varName1 == null || _varName2 == null || _varValue2 != 0);
    }
}
