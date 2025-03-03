package source.Commands;

public class CommandFabric {
    public static ICommand GetCommand(String line){
        String[] data = line.split(" ");

        if (data.length == 0){
            throw new RuntimeException("Command is empty.");
        }

        switch (data[0].toUpperCase()){
            case "DEFINE":
                return new Define(data);
            default:
                throw new RuntimeException("The command could not be processed: " + data[0] + " command does not exist.");
        }
    }


}
