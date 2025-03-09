import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Использование: java WordFrequencyAnalyzer <имя_файла>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = "output.csv";

        Parser parser = new Parser();
        try{
            parser.ParseFile(inputFileName);
        } catch (FileNotFoundException e) {
            return;
        }
        int totalWords = parser.GetTotalWords();
        List<Map.Entry<String, Integer>> sortedWords = parser.GetWordCountsSorted();

        try{
            CsvWriter.Write(outputFileName, totalWords, sortedWords);
        }catch (FileNotFoundException ignore){}
    }
}