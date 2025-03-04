package source;

import source.Commands.CommandFabric;
import source.Commands.ICommand;

import java.io.*;
import java.util.ArrayDeque;

import static java.lang.System.exit;

public class Calculator {
    private final ArrayDeque<ICommand> _commands = new ArrayDeque<>();
    private ContextExecute _contextExecute = new ContextExecute();
    private CommandFabric _commandFabric = new CommandFabric();

    public Calculator(String filename){
        Logs.WriteStartInfo();
        MakeQueueCommands(filename);
    }

    public Calculator(){
        Logs.WriteStartInfo();
        MakeQueueCommands("src\\source\\default input.txt");
    }

    private void MakeQueueCommands(String filename){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String log = MakeCommand(line);
                Logs.Update(log, _commands.size());
            }
            Logs.Update("Stack creation is complete");
        } catch (IOException e) {
            Logs.Update("Error reading the file: " + e.getMessage(), 0);
            exit(-1);
        }
    }

    private String MakeCommand(String line){
        try{
            _commands.add(_commandFabric.GetCommand(line));
        }
        catch (RuntimeException e){
            return e.getMessage();
        } catch (Exception ignored) {}
        return "Command was correctly identified.";
    }

    public void TryExecuteCommand(){
        if (_commands.isEmpty()){
            Logs.Update("There are no more executable commands.");
        }
        else {
            ICommand command = _commands.remove();
            try{
                command.Execute(_contextExecute);
                Logs.Update(command.GetInfo());
            }
            catch (RuntimeException e){
                Logs.Update(e.getMessage());
            }
        }
    }

    public void ExecuteAllCommands(){
        while (!_commands.isEmpty()){
            TryExecuteCommand();
        }
        Logs.WriteEndInfo(_contextExecute);
    }
}

/*private ArrayList<String> GetCommandArgs(String line){
        ArrayList<String> args = new ArrayList<String>();
        StringBuilder wordBuilder = new StringBuilder();
        char charr;
        int charNum = 0;

        if (line.isEmpty()){
            return args;
        }

        while (line.length() - charNum != 0) {
            charr = line.charAt(charNum);
            if (Character.isSpaceChar(charr)){
                args.add(wordBuilder.toString());
                wordBuilder.setLength(0);
            }
            else {
                wordBuilder.append(charr);
            }

            charNum++;
        }
        args.add(wordBuilder.toString());

        return args;
    }*/