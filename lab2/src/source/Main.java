package source;

public class Main {
    public static void main(String[] args) {
        Calculator calc;
        if (args.length != 0){
            calc = new Calculator(args[0]);
        }
        else{
            calc = new Calculator();
        }

        calc.ExecuteAllCommands();
    }
}