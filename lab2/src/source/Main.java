package source;

public class Main {
    public static void main(String[] args) {
        Calculator calc;
        if (args.length != 0){
            try{
                calc = new Calculator(args[0]);
                calc.ExecuteAllCommands();
            }
            catch (Exception ignore){}
        }
        else{
            try{
                calc = new Calculator();
                calc.ExecuteAllCommands();
            }
            catch (Exception ignore){}
        }
    }
}