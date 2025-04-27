package ru.mentee.power.io.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import ru.mentee.power.io.model.Book;
import ru.mentee.power.io.model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты CSV экспорта/импорта LibraryManager")
class LibraryManagerCsvTest {

    @TempDir
    Path tempDir;

    private LibraryManager libraryManager;
    private Path csvFilePath;
    private final String delimiter = ";";

    @BeforeEach
    void setUp() {
        libraryManager = new LibraryManager();
        csvFilePath = tempDir.resolve("books_test.csv");

        // Добавляем тестовые книги
        Book book1 = new Book("978-5-389-21499-6", "Мастер и Маргарита", 1966, Book.Genre.FICTION);
        book1.addAuthor("Михаил Булгаков");
        libraryManager.addBook(book1);

        Book book2 = new Book("978-5-17-136333-4", "1984", 1949, Book.Genre.FICTION);
        book2.addAuthor("Джордж Оруэлл");
        libraryManager.addBook(book2);
    }

    @Test
    @DisplayName("exportBooksToCsv должен создавать корректный CSV файл")
    void exportShouldCreateCorrectCsv() throws IOException {
        // When
        libraryManager.exportBooksToCsv(csvFilePath.toString(), delimiter);

        // Then
        assertThat(Files.exists(csvFilePath)).isTrue();

        List<String> lines = Files.readAllLines(csvFilePath, StandardCharsets.UTF_8);
        assertThat(lines).isNotEmpty();

        // Проверяем заголовок
        assertThat(lines.get(0)).isEqualTo(LibraryManager.BOOK_CSV_HEADER);

        // Проверяем количество строк данных
        assertThat(lines.size() - 1).isEqualTo(libraryManager.getAllBooks().size());

        // Проверяем формат строк
        String firstDataLine = lines.get(1);
        String[] parts = firstDataLine.split(delimiter);
        assertThat(parts.length).isEqualTo(7);
        assertThat(parts[0]).isEqualTo("978-5-17-136333-4");
        assertThat(parts[1]).isEqualTo("1984");
    }

    @Test
    @DisplayName("importBooksFromCsv должен добавлять книги (append=true)")
    void importShouldAddBooksWhenAppendIsTrue() throws IOException {
        // Given
        int initialBookCount = libraryManager.getAllBooks().size();
        String csvContent = LibraryManager.BOOK_CSV_HEADER + "\n" +
                "ISBN-TEST-1;Новая Книга 1;Автор Тест;FICTION;2024;100;true\n" +
                "ISBN-TEST-2;Новая Книга 2;Автор Тест;SCIENCE;2023;200;false";
        Files.writeString(csvFilePath, csvContent, StandardCharsets.UTF_8);

        // When
        int importedCount = libraryManager.importBooksFromCsv(csvFilePath.toString(), delimiter, true);

        // Then
        assertThat(importedCount).isEqualTo(2);
        assertThat(libraryManager.getAllBooks().size()).isEqualTo(initialBookCount + importedCount);
        assertThat(libraryManager.getBookByIsbn("ISBN-TEST-1")).isNotNull();
        assertThat(libraryManager.getBookByIsbn("ISBN-TEST-2")).isNotNull();
    }

    @Test
    @DisplayName("importBooksFromCsv должен заменять книги (append=false)")
    void importShouldReplaceBooksWhenAppendIsFalse() throws IOException {
        // Given
        String csvContent = LibraryManager.BOOK_CSV_HEADER + "\n" +
                "ISBN-REPLACE-1;Заменяющая Книга;Автор Иной;HISTORY;2022;300;true";
        Files.writeString(csvFilePath, csvContent, StandardCharsets.UTF_8);
        int importBookCount = 1;

        // When
        int importedCount = libraryManager.importBooksFromCsv(csvFilePath.toString(), delimiter, false);

        // Then
        assertThat(importedCount).isEqualTo(importBookCount);
        assertThat(libraryManager.getAllBooks().size()).isEqualTo(importBookCount);
        assertThat(libraryManager.getBookByIsbn("ISBN-REPLACE-1")).isNotNull();
        assertThat(libraryManager.getBookByIsbn("978-5-389-21499-6")).isNull();
    }

    @Test
    @DisplayName("importBooksFromCsv должен пропускать некорректные строки")
    void importShouldSkipMalformedLines() throws IOException {
        // Given
        int initialBookCount = libraryManager.getAllBooks().size();
        String csvContent = LibraryManager.BOOK_CSV_HEADER + "\n" +
                "ISBN-GOOD-1;Хорошая Книга;Автор;FANTASY;2020;150;true\n" +
                "bad-line-format\n" +
                "ISBN-BAD-YEAR;Плохой Год;Автор;SCIENCE;не_число;200;true";
        Files.writeString(csvFilePath, csvContent, StandardCharsets.UTF_8);

        // When
        int importedCount = libraryManager.importBooksFromCsv(csvFilePath.toString(), delimiter, true);

        // Then
        assertThat(importedCount).isEqualTo(1);
        assertThat(libraryManager.getAllBooks().size()).isEqualTo(initialBookCount + 1);
        assertThat(libraryManager.getBookByIsbn("ISBN-GOOD-1")).isNotNull();
        assertThat(libraryManager.getBookByIsbn("ISBN-BAD-YEAR")).isNull();
    }
}