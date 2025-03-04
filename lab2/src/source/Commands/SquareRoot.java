package source.Commands;

import source.ContextExecute;

public class SquareRoot implements ICommand {
    private String _varName = "";
    private Double _varValue;


    public void Execute(ContextExecute contextExecute){
        _varName = contextExecute.Pop();

        ValidTest();

        _varValue = contextExecute.GetVarValue(_varName);
        _varValue = Math.sqrt(_varValue);

        contextExecute.UpdateVar(_varName, _varValue);
        contextExecute.Push(_varName);
    }

    public String GetInfo(){
        ValidTest();

        return "Sqrt of the variable " + _varName + " is " + _varValue + ".";
    }

    private void ValidTest(){
        if (_varName == null){
            throw new RuntimeException("Sqrt can't execute because stack is empty.");
        }
    }
}