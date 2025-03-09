import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private int _totalWords = 0;
    private final Map<String, Integer> _wordCounts = new HashMap<>();

    public void ParseFile(String inputFileName) throws FileNotFoundException{
        _totalWords = 0;
        _wordCounts.clear();

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
                    _wordCounts.compute(word, (key, count) -> count == null ? 1 : count + 1);
                    _totalWords++;
                    wordBuilder.setLength(0);
                }
            }
            // Обрабатываем последнее слово, если оно есть
            if (!wordBuilder.isEmpty()) {
                String word = wordBuilder.toString().toLowerCase();
                _wordCounts.put(word, _wordCounts.getOrDefault(word, 0) + 1);
                _totalWords++;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            throw new FileNotFoundException(e.getMessage());
        }
        }

    public Integer GetTotalWords(){
        return _totalWords;
    }

    public Map<String, Integer> GetWordCounts(){
        return _wordCounts;
    }

    public List<Map.Entry<String, Integer>> GetWordCountsSorted(){
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(_wordCounts.entrySet());
        sortedWords.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return sortedWords;
    }
}
