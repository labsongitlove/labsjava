import java.io.*;
import java.util.*;
//import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Использование: java WordFrequencyAnalyzer <имя_файла>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = "output.csv";

        Map<String, Integer> wordCounts = new HashMap<>();
        int totalWords = 0;
        //test

        // Читаем файл и подсчитываем частоту слов
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)))) {
            StringBuilder wordBuilder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                char character = (char) c;
                if (Character.isLetterOrDigit(character)) {
                    wordBuilder.append(character);
                } else if (!wordBuilder.isEmpty()) {
                    String word = wordBuilder.toString().toLowerCase();
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                    totalWords++;
                    wordBuilder.setLength(0);
                }
            }
            // Обрабатываем последнее слово, если оно есть
            if (!wordBuilder.isEmpty()) {
                String word = wordBuilder.toString().toLowerCase();
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                totalWords++;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        // Сортируем слова по убыванию частоты
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCounts.entrySet());
        sortedWords.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Записываем результат в CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName, false))) {
            writer.println("Слово,Частота,Частота (%)");
            for (Map.Entry<String, Integer> entry : sortedWords) {
                double frequencyPercentage = (entry.getValue() * 100.0) / totalWords;
                writer.printf("%s,%d,%.2f%%%n", entry.getKey(), entry.getValue(), frequencyPercentage);
            }
            System.out.println("Результат записан в " + outputFileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}