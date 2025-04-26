package ru.mentee.power.io;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import ru.mentee.power.collections.library.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

        // Добавляем тестовые данные
        Book book1 = new Book("123", "Book 1", Set.of("Author 1"), Book.Genre.FICTION, 2020, 100, true);
        Book book2 = new Book("456", "Book 2", Set.of("Author 2"), Book.Genre.NON_FICTION, 2021, 200, false);
        managerToSave.addBook(book1);
        managerToSave.addBook(book2);

        Reader reader = new Reader("1001", "reader1", "John Doe", Reader.ReaderCategory.TEACHER);
        managerToSave.addReader(reader);

        managerToSave.borrowBook("456", "reader1", 14);
    }

    @Test
    @DisplayName("Должен сохранять и загружать непустое состояние")
    void shouldSaveAndLoadNonEmptyState() {
        // When
        managerToSave.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Then
        assertThat(loadedManager).isNotNull();

        // Проверяем основные коллекции
        assertThat(loadedManager.getAllBooks()).hasSize(managerToSave.getAllBooks().size());
        assertThat(loadedManager.getAllReaders()).hasSize(managerToSave.getAllReaders().size());
        assertThat(loadedManager.getAllBorrowings()).hasSize(managerToSave.getAllBorrowings().size());

        // Проверяем содержимое книг
        Book loadedBook = loadedManager.getBookByIsbn("123");
        assertThat(loadedBook).isNotNull();
        assertThat(loadedBook.getTitle()).isEqualTo("Book 1");
        assertThat(loadedBook.getAuthors()).contains("Author 1");

        // Проверяем transient поля (должны быть перестроены после загрузки)
        assertThat(loadedManager.getBooksByGenre(Book.Genre.FICTION)).hasSize(1);
        assertThat(loadedManager.getBooksByAuthor("Author 2")).hasSize(1);
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
        // Given: файл не существует
        Path nonExistentPath = tempDir.resolve("non_existent.ser");

        // When
        LibraryManager loadedManager = LibraryManager.loadLibraryState(nonExistentPath.toString());

        // Then
        assertThat(loadedManager).isNull();
    }

    @Test
    @DisplayName("Должен корректно восстанавливать связи при загрузке")
    void shouldRestoreRelationshipsAfterLoad() {
        // Используем новые уникальные данные
        Book book = new Book("999", "Unique Book", Set.of("Author"), Book.Genre.FICTION, 2020, 100, true);
        Reader reader = new Reader("999", "Unique Reader", "test@example.com", Reader.ReaderCategory.REGULAR);

        managerToSave.addBook(book);
        managerToSave.addReader(reader);
        managerToSave.borrowBook("999", "999", 14);

        managerToSave.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Проверяем новую выдачу
        assertThat(loadedManager.getAllBorrowings()).hasSize(1);
        assertThat(loadedManager.getBookByIsbn("999").isAvailable()).isTrue();
    }
}