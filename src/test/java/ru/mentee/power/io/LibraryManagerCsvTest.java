package ru.mentee.power.io;

import ru.mentee.power.collections.library.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

class LibraryManagerCsvTest {

    @TempDir
    Path tempDir;
    private LibraryManager manager;
    private Path exportPath;
    private Path importPath;

    @BeforeEach
    void setUp() {
        manager = new LibraryManager();
        exportPath = tempDir.resolve("export.csv");
        importPath = tempDir.resolve("import.csv");

        Book book = new Book("123", "Test Book", Set.of("Author"), Book.Genre.FICTION, 2020, 100, true);
        manager.addBook(book);
    }

    @Test
    @DisplayName("Экспорт книг в CSV")
    void exportBooksToCsv_ShouldCreateFile() throws IOException {
        manager.exportBooksToCsv(exportPath.toString(), ";");

        assertThat(exportPath).exists();
        assertThat(exportPath).content().contains("123", "Test Book", "Author", "FICTION");
    }

    @Test
    @DisplayName("Импорт книг из CSV (append=false)")
    void importBooksFromCsv_ShouldImportAllBooks() throws IOException {
        manager.exportBooksToCsv(exportPath.toString(), ";");

        LibraryManager newManager = new LibraryManager();
        int count = newManager.importBooksFromCsv(exportPath.toString(), ";", false);

        // Then
        assertThat(count).isEqualTo(1);
        assertThat(newManager.getAllBooks()).hasSize(1);
        assertThat(newManager.getBookByIsbn("123")).isNotNull();
    }

    @Test
    @DisplayName("Импорт книг из CSV (append=true)")
    void importBooksFromCsv_ShouldAppendBooks() throws IOException {
        Book book1 = new Book("123", "Book 1", Set.of("Author"),
                Book.Genre.FICTION, 2020, 100, true);
        manager.addBook(book1);

        String csv = "ISBN;Title;Authors;Genre;Year;Pages;Available\n" +
                "456;Book 2;Author;FICTION;2021;150;true";
        Files.write(exportPath, csv.getBytes());

        int count = manager.importBooksFromCsv(exportPath.toString(), ";", true);

        assertThat(count).isEqualTo(1);
        assertThat(manager.getAllBooks()).hasSize(2); // Оригинальная + импортированная
    }

    @Test
    @DisplayName("Импорт из несуществующего файла")
    void importBooksFromCsv_ShouldHandleMissingFile() {
        assertThatCode(() -> {
            int count = manager.importBooksFromCsv("nonexistent.csv", ";", false);
            assertThat(count).isEqualTo(0);
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Импорт с некорректными данными")
    void importBooksFromCsv_ShouldSkipInvalidLines() throws IOException {
        String badCsv = "ISBN;Title;Authors;Genre;Year;Pages;Available\n" +
                "123;Good Book;Author;FICTION;2020;100;true\n" +
                "456;Bad Book;Author;UNKNOWN_GENRE;2020;100;true\n" +
                "789;;Author;FICTION;2020;100;true";

        Files.write(importPath, badCsv.getBytes());

        int count = manager.importBooksFromCsv(importPath.toString(), ";", false);

        assertThat(count).isEqualTo(1); // Только одна валидная строка
        assertThat(manager.getBookByIsbn("123")).isNotNull();
    }
}