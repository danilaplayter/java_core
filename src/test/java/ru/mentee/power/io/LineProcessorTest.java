package ru.mentee.power.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты обработчика строк (LineProcessor)")
class LineProcessorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("input_for_processor.txt"));
        Files.deleteIfExists(Path.of("output_processed.txt"));

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Должен корректно обработать файл с несколькими строками разного регистра")
    void shouldProcessFileWithMixedCaseLines() throws IOException {
        // Подготовка
        Files.write(Path.of("input_for_processor.txt"),
                List.of("Hello", "WoRlD", "jAvA"),
                StandardCharsets.UTF_8);

        // Действие
        LineProcessor.main(new String[]{});

        // Проверки
        assertThat(Files.readAllLines(Path.of("output_processed.txt"), StandardCharsets.UTF_8))
                .containsExactly("HELLO", "WORLD", "JAVA");
        assertThat(errContent.toString()).isEmpty();
    }

    @Test
    @DisplayName("Должен корректно обработать пустой входной файл")
    void shouldProcessEmptyInputFile() throws IOException {
        // Подготовка
        Files.write(Path.of("input_for_processor.txt"),
                List.of(),
                StandardCharsets.UTF_8);

        // Действие
        LineProcessor.main(new String[]{});

        // Проверки
        assertThat(Files.readAllLines(Path.of("output_processed.txt"), StandardCharsets.UTF_8))
                .isEmpty();
        assertThat(errContent.toString()).isEmpty();
    }

    @Test
    @DisplayName("Должен перезаписать существующий выходной файл")
    void shouldOverwriteExistingOutputFile() throws IOException {
        // Подготовка
        Files.write(Path.of("input_for_processor.txt"),
                List.of("new", "data"),
                StandardCharsets.UTF_8);

        // Создаем существующий выходной файл
        Files.write(Path.of("output_processed.txt"),
                List.of("old", "content"),
                StandardCharsets.UTF_8);

        // Действие
        LineProcessor.main(new String[]{});

        // Проверки
        assertThat(Files.readAllLines(Path.of("output_processed.txt"), StandardCharsets.UTF_8))
                .containsExactly("NEW", "DATA");
        assertThat(errContent.toString()).isEmpty();
    }

    @Test
    @DisplayName("Должен создать входной файл по умолчанию, если он не существует")
    void shouldCreateDefaultInputFileIfNotExists() throws IOException {
        // Убедимся, что файла нет
        Files.deleteIfExists(Path.of("input_for_processor.txt"));

        // Действие
        LineProcessor.main(new String[]{});

        // Проверки
        assertThat(Files.exists(Path.of("input_for_processor.txt"))).isTrue();
        assertThat(outContent.toString()).contains("Создан файл по умолчанию");

        // Проверяем содержимое выходного файла
        assertThat(Files.readAllLines(Path.of("output_processed.txt"), StandardCharsets.UTF_8))
                .containsExactly("ПЕРВАЯ СТРОКА.", "SECOND LINE WITH MIXED CASE", "ТРЕТЬЯ");
    }
}