package source.Commands;

import source.ContextExecute;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.CommandExceptions.NotEnoughArgumentsOnStackException;

public class Multiplication implements ICommand {
    private String _varName1 = "";
    private String _varName2 = "";
    private Double _varValue1;
    private Double _varValue2;

    public Multiplication(String[] data) {
    }

    public void Execute(ContextExecute contextExecute) throws CommandException {
        TryInitialization(contextExecute);
        _varValue1 *= _varValue2;

        contextExecute.UpdateVar(_varName1, _varValue1);
        contextExecute.Push(_varName1);
    }

    public String GetInfo(){
        if (IsValid()){
            return "Mult of the variables " + _varName1 + " and " + _varName2 + " is " + _varValue1 + ".";
        }
        return "Mult can't execute because in stack not enough vars.";
    }

    private void TryInitialization(ContextExecute contextExecute) throws CommandException{
        _varName1 = contextExecute.Pop();
        if (_varName1 == null){
            throw new NotEnoughArgumentsOnStackException("Mult can't execute because in stack not enough vars.");
        }
        _varName2 = contextExecute.Pop();
        if (_varName2 == null){
            contextExecute.Push(_varName1);
            throw new NotEnoughArgumentsOnStackException("Mult can't execute because in stack not enough vars.");
        }
        _varValue1 = contextExecute.GetVarValue(_varName1);
        _varValue2 = contextExecute.GetVarValue(_varName2);
    }
    private boolean IsValid(){
        return !(_varName1 == null || _varName2 == null);
    }
}
