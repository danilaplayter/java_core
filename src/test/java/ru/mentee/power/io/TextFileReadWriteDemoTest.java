package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.charset.StandardCharsets; // Указываем кодировку для надежности
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты записи и чтения строк (TextFileReadWriteDemo)")

public class TextFileReadWriteDemoTest {

    @TempDir
    Path tempDir;

    Path testFilePath;
    List<String> lines = List.of("Строка номер раз", "Line two", "И третья строка.");

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_lines.txt");
    }

    private void writeLines(Path path, List<String> linesToWrite) throws IOException {
        try (FileWriter writer = new FileWriter(path.toFile(), StandardCharsets.UTF_8)) { // Явно укажем UTF-8
            for (String line : linesToWrite) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }
    }

    private String readChars(Path path) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
            int charCode;
            while ((charCode = reader.read()) != -1) {
                content.append((char) charCode);
            }
        }
        return content.toString();
    }

    @Test
    @DisplayName("Должен корректно записать и прочитать несколько строк")
    void shouldWriteAndReadLinesCorrectly() throws IOException {

        List<String> linesToWrite = List.of("Строка 1", "Line 2", "Третья строка!");

        writeLines(testFilePath, linesToWrite);

        assertThat(testFilePath).exists();
        assertThat(Files.readAllLines(testFilePath, StandardCharsets.UTF_8)).isEqualTo(linesToWrite);


        String readContent = readChars(testFilePath);

        String expectedContent = String.join(System.lineSeparator(), linesToWrite) + System.lineSeparator();
        assertThat(readContent).isEqualTo(expectedContent);

    }

    @Test
    @DisplayName("Должен корректно обработать запись пустого списка строк")
    void shouldHandleEmptyListWrite() throws IOException {
        List<String> emptyList = new ArrayList<>();

        writeLines(testFilePath, emptyList);

        assertThat(testFilePath).exists();
        assertThat(testFilePath).isEmptyFile();

        String readContent = readChars(testFilePath);

        assertThat(readContent).isEmpty();
    }

    @Test
    @DisplayName("Должен корректно записать и прочитать строки с различными символами")
    void shouldWriteAndReadSpecialChars() throws IOException {
        // Given
        List<String> specialLines = List.of(
                "Табуляция:\tсимволы",
                "Новая\\nСтрока", // Экранированный перенос
                "Символы:!@#$%^&*()_+=-`~"
        );

        writeLines(testFilePath, specialLines);

        assertThat(testFilePath).exists();

        assertThat(Files.readAllLines(testFilePath, StandardCharsets.UTF_8)).isEqualTo(specialLines);

        String readContent = readChars(testFilePath);

        String expectedContent = String.join(System.lineSeparator(), specialLines) + System.lineSeparator();
        assertThat(readContent).isEqualTo(expectedContent);
    }

}
