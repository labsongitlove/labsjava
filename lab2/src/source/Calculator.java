package source;

import source.Commands.Define;
import source.Commands.ICommand;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import static java.lang.System.exit;

public class Calculator {

    private final Stack<ICommand> _commands = new Stack<>();
    private Map<String, Double> _vars;

    public Calculator(String filename){
        WriteStartLogInfo();
        MakeStackCommands(filename);
    }

    public Calculator(){
        WriteStartLogInfo();
        MakeStackCommands("F:\\projects\\labsjava\\lab2\\src\\source\\default input.txt");
    }

    private void MakeStackCommands(String filename){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            StringBuilder wordBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String log = MakeCommand(line);
                UpdateLog(log, _commands.size());
            }
            UpdateLog("Stack creation is complete");
        } catch (IOException e) {
            UpdateLog("Error reading the file: " + e.getMessage(), 0);
            exit(-1);
        }
    }

    private String MakeCommand(String line){
        ArrayList<String> args = GetCommandArgs(line);

        if (args.isEmpty()){
            return ("Command is empty.");
        }
        else{
            switch (args.get(0).toUpperCase()){ //TODO like fabric
                case "DEFINE":
                    var argsNew = args.subList(1, args.size() - 1);
                    _commands.push(new Define(argsNew));
                    break;
                default:
                    return ("The command could not be processed: " + args.get(0) + "command does not exist.");
            }
        }
        return "Command was correctly identified.";
    }

    private ArrayList<String> GetCommandArgs(String line){
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
    }

    public void TryExecuteCommand(){
        if (_commands.isEmpty()){
            System.err.println("There are no more executable commands.");
        }
        else {
            _commands.pop().Execute(_vars);
        }
    }

    private void UpdateLog(String message, int numString){
        String logFilename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(logFilename, true)))
        {
            String text = numString + ": " + message + "\n";
            bw.append(text);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.err.println();
    }

    private void UpdateLog(String message){
        String logFilename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(logFilename, true)))
        {
            String text = message + "\n";
            bw.append(text);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.err.println();
    }

    private void WriteStartLogInfo() {
        String logFilename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(logFilename, true)))
        {
            String text = String.format("\n\n\n\nlog date: %s\ninput:\n", LocalDateTime.now());
            bw.append(text);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
