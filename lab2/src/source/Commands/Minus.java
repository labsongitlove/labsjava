package source.Commands;

import source.ContextExecute;

public class Minus implements ICommand {
    private String _varName1 = "";
    private String _varName2 = "";
    private Double _varValue1;
    private Double _varValue2;

    public Minus(String[] data) {
    }


    public void Execute(ContextExecute contextExecute){
        _varName1 = contextExecute.Pop();
        _varName2 = contextExecute.Pop();

        ValidTest();

        _varValue1 = contextExecute.GetVarValue(_varName1);
        _varValue2 = contextExecute.GetVarValue(_varName2);
        _varValue1 -= _varValue2;

        contextExecute.UpdateVar(_varName1, _varValue1);
        contextExecute.Push(_varName1);
    }

    public String GetInfo(){
        ValidTest();
        return "Minus of the variables " + _varName1 + " and " + _varName2 + " is " + _varValue1 + ".";
    }

    private void ValidTest(){
        if (_varName1 == null || _varName2 == null){
            throw new RuntimeException("Minus can't execute because in stack not enough vars.");
        }
    }
}
