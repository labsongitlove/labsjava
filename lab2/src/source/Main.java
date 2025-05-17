package source;

import source.exceptions.CalculatorException;
import source.exceptions.CommandExceptions.CommandException;
import source.exceptions.FabricExceptions.FabricException;

public class Main {
    public static void main(String[] args) {
        Calculator calc;
        if (args.length != 0){
            try{
                calc = new Calculator(args[0]);
                calc.ExecuteAllCommands();
            }
            catch (FabricException ignore){}
        }
        else{
            try{
                calc = new Calculator();
                calc.ExecuteAllCommands();
            }
            catch (CalculatorException ignore){}
        }
    }
}