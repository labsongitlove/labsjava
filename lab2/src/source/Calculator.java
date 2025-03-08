package source;

import source.Commands.CommandFabric;
import source.Commands.ICommand;
import source.exceptions.CommandException;

import java.io.*;
import java.util.ArrayDeque;

public class Calculator {
    private final ArrayDeque<ICommand> _commands = new ArrayDeque<>();
    private final ContextExecute _contextExecute = new ContextExecute();
    private final CommandFabric _commandFabric;

    public Calculator(String filename) throws Exception{
        _commandFabric = new CommandFabric();
        Logs.WriteStartInfo();
        MakeQueueCommands(filename);
    }

    public Calculator() throws Exception{
        _commandFabric = new CommandFabric();
        Logs.WriteStartInfo();
        MakeQueueCommands("src\\source\\default input.txt");
    }

    private void MakeQueueCommands(String filename) throws FileNotFoundException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String log = MakeCommand(line);
                Logs.Update(log, _commands.size());
            }
            Logs.Update("Stack creation is complete");
        } catch (IOException e) {
            Logs.Update("Error reading the file: " + e.getMessage(), 0);
            throw new FileNotFoundException();
        }
    }

    private String MakeCommand(String line){
        try{
            _commands.add(_commandFabric.GetCommand(line));
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "Command was correctly identified.";
    }

    public String ExecuteCommand(){
        if (_commands.isEmpty()){
            Logs.Update("There are no more executable commands.");
            return "There are no more executable commands.";
        }
        else {
            ICommand command = _commands.remove();
            try{
                command.Execute(_contextExecute);
                Logs.Update(command.GetInfo());
                return command.GetInfo();
            }
            catch (CommandException e){
                Logs.Update(e.getMessage());
                return e.getMessage();
            }
        }
    }

    public void ExecuteAllCommands(){
        while (!_commands.isEmpty()){
            ExecuteCommand();
        }
        Logs.WriteEndInfo(_contextExecute);
    }
}