package ru.mentee.power.collections.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

class LibraryManagerTest {

    private LibraryManager libraryManager;
    private Book book1, book2, book3;
    private Reader reader1, reader2;

    @BeforeEach
    void setUp() {
        libraryManager = new LibraryManager();

        // Создаем тестовые книги
        book1 = new Book("111", "Java Basics", 2020, Book.Genre.CHILDREN,
                List.of("John Doe", "Mike Smith"), true);
        book2 = new Book("222", "Advanced Java", 2022, Book.Genre.TECHNICAL,
                List.of("John Doe"), true);
        book3 = new Book("333", "History of Art", 2019, Book.Genre.ART,
                List.of("Anna Brown", "Emma Wilson"), true);

        // Создаем тестовых читателей
        reader1 = new Reader("r1", "Alice Johnson", "alice@example.com", Reader.ReaderCategory.STUDENT);
        reader2 = new Reader("r2", "Bob Smith", "bob@example.com", Reader.ReaderCategory.REGULAR);

        // Добавляем книги и читателей в библиотеку
        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);

        libraryManager.addReader(reader1);
        libraryManager.addReader(reader2);
    }

    @Nested
    @DisplayName("Тесты CRUD операций с книгами")
    class BookCrudTests {
        @Test
        @DisplayName("Должен корректно добавлять книгу в библиотеку")
        void shouldAddBookCorrectly() {
            Book newBook = new Book("444", "New Book", 2023, Book.Genre.FICTION,
                    List.of("New Author"), true);

            boolean result = libraryManager.addBook(newBook);

            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn("444")).isEqualTo(newBook);
        }

        @Test
        @DisplayName("Не должен добавлять дубликат книги")
        void shouldNotAddDuplicateBook() {
            Book duplicate = new Book("111", "Duplicate", 2023, Book.Genre.FICTION,
                    List.of("Author"), true);

            boolean result = libraryManager.addBook(duplicate);

            assertThat(result).isFalse();
            assertThat(libraryManager.getBookByIsbn("111").getTitle()).isEqualTo("Java Basics");
        }

        @Test
        @DisplayName("Должен возвращать книгу по ISBN")
        void shouldReturnBookByIsbn() {
            Book found = libraryManager.getBookByIsbn("222");

            assertThat(found).isNotNull();
            assertThat(found.getTitle()).isEqualTo("Advanced Java");
            assertThat(found.getAuthors()).containsExactly("John Doe");
        }

        @Test
        @DisplayName("Должен возвращать null при поиске книги по несуществующему ISBN")
        void shouldReturnNullForNonExistingIsbn() {
            Book found = libraryManager.getBookByIsbn("999");

            assertThat(found).isNull();
        }

        @Test
        @DisplayName("Должен корректно удалять книгу из библиотеки")
        void shouldRemoveBookCorrectly() {
            boolean result = libraryManager.removeBook("333");

            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn("333")).isNull();
        }

        @Test
        @DisplayName("Должен возвращать false при попытке удалить несуществующую книгу")
        void shouldReturnFalseWhenRemovingNonExistingBook() {
            boolean result = libraryManager.removeBook("999");

            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Тесты поиска и фильтрации книг")
    class BookSearchAndFilterTests {
        @Test
        @DisplayName("Должен возвращать список всех книг")
        void shouldReturnAllBooks() {
            List<Book> allBooks = libraryManager.getAllBooks();

            assertThat(allBooks).hasSize(3)
                    .extracting(Book::getIsbn)
                    .containsExactlyInAnyOrder("111", "222", "333");
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного жанра")
        void shouldReturnBooksByGenre() {
            List<Book> techBooks = libraryManager.getBooksByGenre(Book.Genre.TECHNICAL);

            assertThat(techBooks).hasSize(1)
                    .extracting(Book::getIsbn)
                    .containsExactly("222");

            assertThat(techBooks)
                    .extracting(Book::getGenre)
                    .containsOnly(Book.Genre.TECHNICAL);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного автора")
        void shouldReturnBooksByAuthor() {
            List<Book> johnDoeBooks = libraryManager.getBooksByAuthor("John Doe");

            assertThat(johnDoeBooks).hasSize(2)
                    .extracting(Book::getIsbn)
                    .containsExactlyInAnyOrder("111", "222");

            assertThat(johnDoeBooks)
                    .allMatch(book -> book.getAuthors().contains("John Doe"));
        }

        @Test
        @DisplayName("Должен находить книги по части названия")
        void shouldFindBooksByTitlePart() {
            List<Book> javaBooks = libraryManager.searchBooksByTitle("Java");

            assertThat(javaBooks).hasSize(2)
                    .extracting(Book::getTitle)
                    .allMatch(title -> title.contains("Java"));
        }

        @Test
        @DisplayName("Должен возвращать только доступные книги")
        void shouldReturnOnlyAvailableBooks() {
            libraryManager.borrowBook("111", "r1", 14);

            List<Book> availableBooks = libraryManager.getAvailableBooks();

            assertThat(availableBooks).hasSize(2)
                    .extracting(Book::getIsbn)
                    .containsExactlyInAnyOrder("222", "333");

            assertThat(availableBooks)
                    .allMatch(Book::isAvailable);
        }
    }

    @Nested
    @DisplayName("Тесты сортировки книг")
    class BookSortingTests {
        @Test
        @DisplayName("Должен корректно сортировать книги по названию")
        void shouldSortBooksByTitle() {
            List<Book> books = new ArrayList<>(libraryManager.getAllBooks());
            List<Book> sorted = libraryManager.sortBooksByTitle(books);

            assertThat(sorted)
                    .extracting(Book::getTitle)
                    .containsExactly("Advanced Java", "History of Art", "Java Basics");
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по году публикации")
        void shouldSortBooksByPublicationYear() {
            List<Book> books = new ArrayList<>(libraryManager.getAllBooks());
            List<Book> sorted = libraryManager.sortBooksByPublicationYear(books);

            assertThat(sorted)
                    .extracting(Book::getPublicationYear)
                    .containsExactly(2019, 2020, 2022);
        }

        @Test
        @DisplayName("Должен возвращать корректную статистику по жанрам")
        void shouldReturnCorrectGenreStatistics() {
            Map<Book.Genre, Integer> stats = libraryManager.getGenreStatistics();

            assertThat(stats)
                    .containsEntry(Book.Genre.TECHNICAL, 1)
                    .containsEntry(Book.Genre.ART, 1);
        }
    }

    @Nested
    @DisplayName("Тесты CRUD операций с читателями")
    class ReaderCrudTests {
        @Test
        @DisplayName("Должен корректно добавлять читателя")
        void shouldAddReaderCorrectly() {
            Reader newReader = new Reader("r3", "New Reader", "new@example.com",
                    Reader.ReaderCategory.REGULAR);

            boolean result = libraryManager.addReader(newReader);

            assertThat(result).isTrue();
            assertThat(libraryManager.getReaderById("r3")).isEqualTo(newReader);
        }

        @Test
        @DisplayName("Не должен добавлять дубликат читателя")
        void shouldNotAddDuplicateReader() {
            Reader duplicate = new Reader("r1", "Duplicate", "dup@example.com",
                    Reader.ReaderCategory.REGULAR);

            boolean result = libraryManager.addReader(duplicate);

            assertThat(result).isFalse();
            assertThat(libraryManager.getReaderById("r1").getName()).isEqualTo("Alice Johnson");
        }

        @Test
        @DisplayName("Должен возвращать читателя по ID")
        void shouldReturnReaderById() {
            Reader found = libraryManager.getReaderById("r2");

            assertThat(found).isNotNull();
            assertThat(found.getName()).isEqualTo("Bob Smith");
            assertThat(found.getEmail()).isEqualTo("bob@example.com");
        }

        @Test
        @DisplayName("Должен возвращать null при поиске читателя по несуществующему ID")
        void shouldReturnNullForNonExistingReaderId() {
            Reader found = libraryManager.getReaderById("r99");

            assertThat(found).isNull();
        }

        @Test
        @DisplayName("Должен корректно удалять читателя")
        void shouldRemoveReaderCorrectly() {
            boolean result = libraryManager.removeReader("r2");

            assertThat(result).isTrue();
            assertThat(libraryManager.getReaderById("r2")).isNull();
        }
    }

    @Nested
    @DisplayName("Тесты операций с выдачей книг")
    class BorrowingOperationsTests {
        @Test
        @DisplayName("Должен корректно оформлять выдачу книги")
        void shouldBorrowBookCorrectly() {
            boolean result = libraryManager.borrowBook("333", "r2", 14);

            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn("333").isAvailable()).isFalse();

            List<Borrowing> borrowings = libraryManager.getAllBorrowings();
            assertThat(borrowings).anyMatch(b ->
                    b.getIsbn().equals("333") &&
                            b.getReaderId().equals("r2") &&
                            b.getReturnDate() == null);
        }

        @Test
        @DisplayName("Не должен выдавать недоступную книгу")
        void shouldNotBorrowUnavailableBook() {
            libraryManager.borrowBook("111", "r1", 14);
            boolean result = libraryManager.borrowBook("111", "r2", 7);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Должен корректно оформлять возврат книги")
        void shouldReturnBookCorrectly() {
            libraryManager.borrowBook("222", "r1", 14);
            boolean result = libraryManager.returnBook("222", "r1");

            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn("222").isAvailable()).isTrue();

            List<Borrowing> borrowings = libraryManager.getAllBorrowings();
            assertThat(borrowings).anyMatch(b ->
                    b.getIsbn().equals("222") &&
                            b.getReaderId().equals("r1") &&
                            b.getReturnDate() != null);
        }

        @Test
        @DisplayName("Должен возвращать список просроченных выдач")
        void shouldReturnOverdueBorrowings() {
            // Создаем просроченную выдачу
            Borrowing overdue = new Borrowing("111", "r1",
                    LocalDate.now().minusDays(15),
                    LocalDate.now().minusDays(1));
            libraryManager.getAllBorrowings().add(overdue);

            List<Borrowing> overdueBorrowings = libraryManager.getOverdueBorrowings();

            assertThat(overdueBorrowings).hasSize(1)
                    .allMatch(b -> b.getDueDate().isBefore(LocalDate.now()) &&
                            b.getReturnDate() == null);
        }

        @Test
        @DisplayName("Должен корректно продлевать срок выдачи")
        void shouldExtendBorrowingPeriodCorrectly() {
            libraryManager.borrowBook("333", "r2", 14);
            LocalDate originalDueDate = libraryManager.getAllBorrowings().get(0).getDueDate();

            boolean result = libraryManager.extendBorrowingPeriod("333", "r2", 7);

            assertThat(result).isTrue();
            assertThat(libraryManager.getAllBorrowings().get(0).getDueDate())
                    .isEqualTo(originalDueDate.plusDays(7));
        }
    }

    @Nested
    @DisplayName("Тесты статистики и отчетов")
    class StatisticsAndReportsTests {
        @Test
        @DisplayName("Должен возвращать корректную статистику по жанрам")
        void shouldReturnCorrectGenreStatistics() {
            Map<Book.Genre, Integer> stats = libraryManager.getGenreStatistics();

            assertThat(stats)
                    .containsEntry(Book.Genre.CHILDREN, 1)
                    .containsEntry(Book.Genre.ART, 1);
        }

        @Test
        @DisplayName("Должен возвращать список самых популярных книг")
        void shouldReturnMostPopularBooks() {
            libraryManager.borrowBook("111", "r1", 14);
            libraryManager.borrowBook("111", "r2", 14);
            libraryManager.borrowBook("222", "r1", 14);

            Map<Book, Integer> popularBooks = libraryManager.getMostPopularBooks(2);

            assertThat(popularBooks).hasSize(2);
            assertThat(popularBooks.keySet()).extracting(Book::getIsbn)
                    .containsExactly("111", "222");
            assertThat(popularBooks.values()).containsExactly(1, 1);
        }

        @Test
        @DisplayName("Должен возвращать список самых активных читателей")
        void shouldReturnMostActiveReaders() {
            libraryManager.borrowBook("111", "r1", 14);
            libraryManager.borrowBook("222", "r1", 14);
            libraryManager.borrowBook("333", "r2", 14);

            Map<Reader, Integer> activeReaders = libraryManager.getMostActiveReaders(2);

            assertThat(activeReaders).hasSize(2);
            assertThat(activeReaders.keySet()).extracting(Reader::getId)
                    .containsExactly("r1", "r2");
            assertThat(activeReaders.values()).containsExactly(2, 1);
        }

        @Test
        @DisplayName("Должен возвращать список читателей с просроченными книгами")
        void shouldReturnReadersWithOverdueBooks() {
            // Создаем просроченную выдачу
            Borrowing overdue = new Borrowing("111", "r1",
                    LocalDate.now().minusDays(15),
                    LocalDate.now().minusDays(1));
            libraryManager.getAllBorrowings().add(overdue);

            List<Reader> readersWithOverdue = libraryManager.getReadersWithOverdueBooks();

            assertThat(readersWithOverdue).hasSize(1)
                    .extracting(Reader::getId)
                    .containsExactly("r1");
        }
    }

    @Nested
    @DisplayName("Тесты итераторов")
    class IteratorsTests {
        @Test
        @DisplayName("Должен корректно итерироваться по книгам определенного жанра и года")
        void shouldIterateOverBooksByGenreAndYear() {
            Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.TECHNICAL, 2022);

            List<Book> result = new ArrayList<>();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }

            assertThat(result).hasSize(1)
                    .extracting(Book::getIsbn)
                    .containsExactly("222");
        }

        @Test
        @DisplayName("Должен корректно итерироваться по книгам с несколькими авторами")
        void shouldIterateOverBooksWithMultipleAuthors() {
            Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);

            List<Book> result = new ArrayList<>();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }

            assertThat(result).hasSize(2)
                    .extracting(Book::getIsbn)
                    .containsExactlyInAnyOrder("111", "333");
        }

        @Test
        @DisplayName("Должен корректно итерироваться по просроченным выдачам")
        void shouldIterateOverOverdueBorrowings() {
            // Создаем просроченную выдачу
            Borrowing overdue = new Borrowing("111", "r1",
                    LocalDate.now().minusDays(15),
                    LocalDate.now().minusDays(1));
            libraryManager.getAllBorrowings().add(overdue);

            Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();

            List<Borrowing> result = new ArrayList<>();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }

            assertThat(result).hasSize(1)
                    .allMatch(b -> b.getDueDate().isBefore(LocalDate.now()) &&
                            b.getReturnDate() == null);
        }

        @Test
        @DisplayName("Должен выбрасывать NoSuchElementException при отсутствии следующего элемента")
        void shouldThrowNoSuchElementExceptionWhenNoMoreElements() {
            Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FICTION, 2000);

            assertThatThrownBy(iterator::next)
                    .isInstanceOf(NoSuchElementException.class);
        }
    }
}