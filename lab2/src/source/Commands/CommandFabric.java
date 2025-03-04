package source.Commands;

public class CommandFabric {
    public static ICommand GetCommand(String line){ //TODO config file with map<name command, name class>
        String[] data = line.split(" ");

        if (data.length == 0){
            throw new RuntimeException("Command is empty.");
        }

        switch (data[0].toUpperCase()){
            case "DEFINE":
                return new Define(data);
            case "PRINT":
                return new Print();
            case "PUSH":
                return new Push(data);
            case "POP":
                return new Pop();
            case "#":
                return new Comment();
            case "SQRT":
                return new SquareRoot();
            case "PLUS":
                return new Plus();
            case "DIV":
                return new Division();
            case "MULT":
                return new Multiplication();
            case "MINUS":
                return new Minus();
            default:
                throw new RuntimeException("The command could not be processed: " + data[0] + " command does not exist.");
        }
    }


}
