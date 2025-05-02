package ru.mentee.power.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LineProcessor {

  public static void main(String[] args) {
    String inputFileName = "input_for_processor.txt";
    String outputFileName = "output_processed.txt";
    Path inputPath = Paths.get(inputFileName);

    try {
      if (Files.notExists(inputPath)) {
        List<String> defaultLines = List.of("Первая строка.", "Second Line with MixEd Case",
            "третья");
        Files.write(inputPath, defaultLines, StandardCharsets.UTF_8);
        System.out.println("Создан файл по умолчанию: " + inputFileName);
      }
    } catch (IOException e) {
      System.err.println("Ошибка при создании файла по умолчанию: " + e.getMessage());
      return;
    }

    try (Reader reader = new FileReader(inputFileName,
        StandardCharsets.UTF_8); BufferedReader bufferedReader = new BufferedReader(
        reader); Writer writer = new FileWriter(outputFileName,
        StandardCharsets.UTF_8); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
      String line;
      System.out.println("Обработка файла " + inputFileName + "...");

      while ((line = bufferedReader.readLine()) != null) {
        String processedLine = line.toUpperCase();
        bufferedWriter.write(processedLine);
        bufferedWriter.newLine();
      }
      System.out.println("Файл успешно обработан. Результат в " + outputFileName);

    } catch (IOException e) {
      System.err.println("Ошибка при обработке файла: " + e.getMessage());
    }
  }
}