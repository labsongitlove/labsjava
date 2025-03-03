package source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Logs {
    public static void Update(String message, int numString){
        String filename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true)))
        {
            String text = numString + ": " + message + "\n";
            bw.append(text);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.err.println();
    }

    public static void Update(String message){
        String filename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true)))
        {
            String text = message + "\n";
            bw.append(text);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.err.println();
    }

    public static void WriteStartInfo() {
        String filename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true)))
        {
            String text = String.format("\n\n\n\nlog date: %s\ninput:\n", LocalDateTime.now());
            bw.append(text);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void WriteEndInfo(ContextExecute contextExecute) {
        String filename = "F:\\projects\\labsjava\\lab2\\src\\source\\log.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true)))
        {
            var varsInfo = contextExecute.GetVars();
            String text = String.format("Vars info: %s\nExecute finished at %s.", varsInfo, LocalTime.now());
            bw.append(text);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
