package ru.mentee.power.io.model;

import com.sun.nio.sctp.InvalidStreamException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты сохранения/загрузки состояния LibraryManager")
class LibraryManagerPersistenceTest {

    @TempDir
    Path tempDir;

    private Path stateFilePath;
    private LibraryManager managerToSave;

    @BeforeEach
    void setUp() {
        stateFilePath = tempDir.resolve("library_test_state.ser");
        managerToSave = new LibraryManager();

        // Add test data
        Book book1 = new Book("978-5-389-21499-6", "Мастер и Маргарита", 1966, Book.Genre.FICTION);
        book1.addAuthor("Михаил Булгаков");

        Book book2 = new Book("978-5-17-136333-4", "1984", 1949, Book.Genre.FICTION);
        book2.addAuthor("Джордж Оруэлл");

        managerToSave.addBook(book1);
        managerToSave.addBook(book2);

        Reader reader = new Reader("ivanov", "Иван Иванов", "ivanov@example.com", Reader.ReaderCategory.STUDENT);
        managerToSave.addReader(reader);

        managerToSave.borrowBook(book1.getIsbn(), reader.getId(), 14);
    }

    @Test
    @DisplayName("Должен сохранять и загружать непустое состояние")
    void shouldSaveAndLoadNonEmptyState() {
        // When
        managerToSave.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Then
        assertThat(loadedManager).isNotNull();

        // Check collections sizes
        assertThat(loadedManager.getAllBooks()).hasSameSizeAs(managerToSave.getAllBooks());
        assertThat(loadedManager.getAllReaders()).hasSameSizeAs(managerToSave.getAllReaders());
        assertThat(loadedManager.getAllBorrowings()).hasSameSizeAs(managerToSave.getAllBorrowings());

        // Check transient fields
        assertThat(loadedManager.getBooksByGenre(Book.Genre.FICTION)).isNotEmpty();
        assertThat(loadedManager.getBooksByAuthor("Михаил Булгаков")).isNotEmpty();
    }

    @Test
    @DisplayName("Должен сохранять и загружать пустое состояние")
    void shouldSaveAndLoadEmptyState() {
        // Given
        LibraryManager emptyManager = new LibraryManager();

        // When
        emptyManager.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Then
        assertThat(loadedManager).isNotNull();
        assertThat(loadedManager.getAllBooks()).isEmpty();
        assertThat(loadedManager.getAllReaders()).isEmpty();
        assertThat(loadedManager.getAllBorrowings()).isEmpty();
    }

    @Test
    @DisplayName("loadLibraryState должен возвращать null для несуществующего файла")
    void loadShouldReturnNullForNonExistentFile() {
        // Given
        Path nonExistentPath = tempDir.resolve("non_existent_file.ser");

        // When
        LibraryManager loadedManager = LibraryManager.loadLibraryState(nonExistentPath.toString());

        // Then
        assertThat(loadedManager).isNull();
    }


}