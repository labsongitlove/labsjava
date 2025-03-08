package source.Commands;

import source.exceptions.ArgumentIsNotValid;
import source.exceptions.InvalidCountOfArguments;

import javax.naming.InvalidNameException;
import java.io.*;
import java.util.HashMap;

public class CommandFabric {
    HashMap<String, Class<?>> _commandsMap;
    String _filename = "src\\source\\Commands\\config.txt";

    public CommandFabric() throws Exception{
        _commandsMap = ParceConfig();
    }

    public CommandFabric(String filename) throws Exception{
        _filename = filename;
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
            throw new ArgumentIsNotValid("The command could not be processed: " + data[0] + " command does not exist.");
        }
        throw new NullPointerException("There is no information about commands(Parse config error).");
    }
    private HashMap<String, Class<?>> ParceConfig() throws Exception{

        BufferedReader br = new BufferedReader(new FileReader(_filename));
        String line;
        HashMap<String, Class<?>> commandsMap = new HashMap<>();
        while((line = br.readLine()) != null){
            String[] temp = line.split(" ");
            if (temp.length != 2){
                throw new InvalidCountOfArguments("Parse config error: invalid count words in line.");
            }
            try {
                Class<?> className = Class.forName("source.Commands." + temp[0]);
                commandsMap.put(temp[1], className);
            }
            catch (ClassNotFoundException e){
                throw new InvalidNameException("Parse config error: invalid name of command's class.");
            }
        }
        return commandsMap;
    }

}
