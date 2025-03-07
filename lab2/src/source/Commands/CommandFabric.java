package source.Commands;

import java.io.*;
import java.util.HashMap;

public class CommandFabric {
    HashMap<String, Class<?>> _commandsMap;

    public CommandFabric(){
        _commandsMap = ParceConfig();
    }

    public ICommand GetCommand(String line) throws Exception {
        String[] data = line.split(" ");

        if (data.length == 0){
            throw new RuntimeException("Line is empty.");
        }

        if (_commandsMap != null){
            if (_commandsMap.get(data[0]) != null){
                return (ICommand) _commandsMap.get(data[0]).getDeclaredConstructor(String[].class).newInstance((Object) data);
            }
            throw new RuntimeException("The command could not be processed: " + data[0] + " command does not exist.");
        }
        throw new RuntimeException("There is no information about commands(Parse config file error).");
    }
    private HashMap<String, Class<?>> ParceConfig(){
        String filename = "src\\source\\Commands\\config.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            HashMap<String, Class<?>> commandsMap = new HashMap<>();
            while((line = br.readLine()) != null){
                String[] temp = line.split(" ");
                if (temp.length != 2){
                    throw new RuntimeException("Parse config error: invalid count words in line.");
                }
                try {
                    Class<?> className = Class.forName("source.Commands." + temp[0]);
                    if (!ICommand.class.isAssignableFrom(className) || className.isInterface()){
                        throw new RuntimeException("Parse config error: class don't implement ICommand");
                    }
                    commandsMap.put(temp[1], className);
                }
                catch (ClassNotFoundException e){
                    throw new RuntimeException("Parse config error: invalid name of command's class.");
                }
            }
            return commandsMap;
        }
        catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
