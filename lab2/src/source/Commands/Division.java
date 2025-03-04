package source.Commands;

import source.ContextExecute;

public class Division implements ICommand {
    private String _varName1 = "";
    private String _varName2 = "";
    private Double _varValue1;
    private Double _varValue2;


    public void Execute(ContextExecute contextExecute){
        _varName1 = contextExecute.Pop();
        _varName2 = contextExecute.Pop();

        ValidTest();

        _varValue1 = contextExecute.GetVarValue(_varName1);
        _varValue2 = contextExecute.GetVarValue(_varName2);
        _varValue1 /= _varValue2;

        contextExecute.UpdateVar(_varName1, _varValue1);
        contextExecute.Push(_varName2);
    }

    public String GetInfo(){
        ValidTest();
        return "Div of the variables " + _varName1 + " and " + _varName2 + " is " + _varValue1 + ".";
    }

    private void ValidTest(){
        if (_varName1 == null || _varName2 == null){
            throw new RuntimeException("Div can't execute because in stack not enough vars.");
        }
        if (_varValue2 == 0){
            throw new RuntimeException("Div can't execute because second var is zero.");
        }
    }
}
