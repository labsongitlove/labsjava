package source.Commands;

import java.util.HashMap;

public class Define implements ICommand {
    private final String[] _args;
    public Define(String[] args){
        _args = args;
    }

    public HashMap<String, Double> Execute(HashMap<String, Double> vars){
        ValidTest();
        double value = Double.parseDouble(_args[2]);
        vars.put(_args[1], value);

        return vars;
    }
    public String GetInfo(){
        ValidTest();

        return String.format("Defined %s as %s.", _args[1], _args[2]);
    }
    private void ValidTest(){
        if (_args.length - 1 != 2){
            throw new RuntimeException("Invalid count of argument.");
        }
        try {
            Double.parseDouble(_args[2]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Second argument is not number.");
        }
    }
}
