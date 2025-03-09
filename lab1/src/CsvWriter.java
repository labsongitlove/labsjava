import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class CsvWriter {
    public static void Write(String outputFileName, int totalWords, List<Map.Entry<String, Integer>> sortedWords) throws FileNotFoundException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName, false))) {
            writer.println("Слово,Частота,Частота (%)");
            for (Map.Entry<String, Integer> entry : sortedWords) {
                double frequencyPercentage = (entry.getValue() * 100.0) / totalWords;
                writer.printf("%s,%d,%.2f%%%n", entry.getKey(), entry.getValue(), frequencyPercentage);
            }
            System.out.println("Результат записан в " + outputFileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
