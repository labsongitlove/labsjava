package source.Commands;

import source.exceptions.FabricExceptions.CommandDoesNotExist;
import source.exceptions.FabricExceptions.FabricException;
import source.exceptions.FabricExceptions.IncorrectDataException;
import source.exceptions.FabricExceptions.ParseConfigError;

import java.io.*;
import java.util.HashMap;

public class CommandFabric {
    HashMap<String, Class<?>> _commandsMap;
    String _filename = "src\\source\\Commands\\config.txt";

    public CommandFabric() throws FabricException{
        _commandsMap = ParceConfig();
    }

    public CommandFabric(String filename) throws FabricException {
        _filename = filename;
        _commandsMap = ParceConfig();
    }

    public ICommand GetCommand(String line) throws FabricException {
        String[] data = line.split(" ");

        if (data.length == 0){
            throw new IncorrectDataException("Line is empty.");
        }

        if (_commandsMap != null){
            if (_commandsMap.get(data[0]) != null){
                ICommand command = null;
                try{
                    command = (ICommand) _commandsMap.get(data[0]).getDeclaredConstructor(String[].class).newInstance((Object) data);
                }
                catch (Exception ignore){}
                return command;
            }
            throw new CommandDoesNotExist("The command could not be processed: " + data[0] + " command does not exist.");
        }
        throw new ParseConfigError("There is no information about commands(Parse config error).");
    }
    private HashMap<String, Class<?>> ParceConfig() throws FabricException{

        HashMap<String, Class<?>> commandsMap = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(_filename))){
            String line;

            while((line = br.readLine()) != null){
                String[] temp = line.split(" ");
                if (temp.length != 2){
                    throw new ParseConfigError("Parse config error: invalid count words in line.");
                }
                try {
                    Class<?> className = Class.forName("source.Commands." + temp[0]);
                    commandsMap.put(temp[1], className);
                }
                catch (ClassNotFoundException e){
                    throw new ParseConfigError("Parse config error: invalid name of command's class.");
                }
            }
        }
        catch (IOException e){
            throw new ParseConfigError("Parse config error: config file does not exist.");
        }
        return commandsMap;
    }

}
