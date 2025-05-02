package ru.mentee.power.collections.library.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;
import ru.mentee.power.collections.library.Borrowing;
import ru.mentee.power.collections.library.LibraryManager;
import ru.mentee.power.collections.library.Reader;

class IteratorsTest {

  private LibraryManager libraryManager;
  private Book book1, book2, book3, book4;
  private Reader reader1, reader2;

  @BeforeEach
  void setUp() {
    libraryManager = new LibraryManager();

    book1 = new Book("111", "Java Basics", 2020, Book.Genre.TECHNICAL,
        List.of("John Doe", "Mike Smith"), true);
    book2 = new Book("222", "Advanced Java", 2022, Book.Genre.TECHNICAL,
        List.of("John Doe"), true);
    book3 = new Book("333", "History of Art", 2019, Book.Genre.ART,
        List.of("Anna Brown", "Emma Wilson", "Sarah Connor"), true);
    book4 = new Book("444", "Children Stories", 2021, Book.Genre.CHILDREN,
        List.of("Alice Wonder"), true);

    libraryManager.addBook(book1);
    libraryManager.addBook(book2);
    libraryManager.addBook(book3);
    libraryManager.addBook(book4);

    reader1 = new Reader("r1", "Alice Johnson", "alice@example.com", Reader.ReaderCategory.REGULAR);
    reader2 = new Reader("r2", "Bob Smith", "bob@example.com", Reader.ReaderCategory.TEACHER);
    libraryManager.addReader(reader1);
    libraryManager.addReader(reader2);

    libraryManager.borrowBook("111", "r1", 7); // Активная выдача
    libraryManager.borrowBook("222", "r2", 14); // Активная выдача

    Borrowing overdue = new Borrowing("333", "r1",
        LocalDate.now().minusDays(15),
        LocalDate.now().minusDays(1));
    libraryManager.getAllBorrowings().add(overdue);

    Borrowing returned = new Borrowing("444", "r2",
        LocalDate.now().minusDays(10),
        LocalDate.now().minusDays(5));
    returned.setReturnDate(LocalDate.now().minusDays(3));
    libraryManager.getAllBorrowings().add(returned);
  }

  @Test
  @DisplayName("Итератор по жанру и году должен возвращать только книги указанного жанра и года")
  void genreAndYearIteratorShouldReturnOnlyMatchingBooks() {
    Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.TECHNICAL,
        2020);

    assertThat(iterator.hasNext()).isTrue();
    Book book = iterator.next();
    assertThat(book).isEqualTo(book1);
    assertThat(book.getGenre()).isEqualTo(Book.Genre.TECHNICAL);
    assertThat(book.getPublicationYear()).isEqualTo(2020);

    assertThat(iterator.hasNext()).isFalse();
  }

  @Test
  @DisplayName("Итератор по жанру и году должен вернуть пустой результат, если нет подходящих книг")
  void genreAndYearIteratorShouldReturnEmptyIteratorWhenNoMatches() {
    Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FICTION,
        2000);

    assertThat(iterator.hasNext()).isFalse();
    assertThatThrownBy(iterator::next)
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @DisplayName("Итератор для книг с несколькими авторами должен возвращать только книги с указанным минимальным количеством авторов")
  void multipleAuthorsIteratorShouldReturnOnlyBooksWithMultipleAuthors() {
    Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);

    List<Book> booksWithMultipleAuthors = new ArrayList<>();
    while (iterator.hasNext()) {
      Book book = iterator.next();
      assertThat(book.getAuthors().size()).isGreaterThanOrEqualTo(2);
      booksWithMultipleAuthors.add(book);
    }

    assertThat(booksWithMultipleAuthors)
        .containsExactlyInAnyOrder(book1, book3);
  }

  @Test
  @DisplayName("Итератор для книг с несколькими авторами должен вернуть пустой результат, если нет подходящих книг")
  void multipleAuthorsIteratorShouldReturnEmptyIteratorWhenNoMatches() {
    Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(4);

    assertThat(iterator.hasNext()).isFalse();
  }

  @Test
  @DisplayName("Итератор для просроченных выдач должен возвращать только просроченные и не возвращенные выдачи")
  void overdueBorrowingsIteratorShouldReturnOnlyOverdueBorrowings() {
    Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();

    assertThat(iterator.hasNext()).isTrue();
    Borrowing borrowing = iterator.next();
    assertThat(borrowing.getDueDate()).isBefore(LocalDate.now());
    assertThat(borrowing.getReturnDate()).isNull();

    assertThat(iterator.hasNext()).isFalse();
  }

  @Test
  @DisplayName("Итератор для просроченных выдач должен вернуть пустой результат, если нет просроченных выдач")
  void overdueBorrowingsIteratorShouldReturnEmptyIteratorWhenNoOverdues() {
    LibraryManager newManager = new LibraryManager();
    Iterator<Borrowing> iterator = newManager.getOverdueBorrowingsIterator();

    assertThat(iterator.hasNext()).isFalse();
  }

  @Test
  @DisplayName("Все итераторы должны выбрасывать NoSuchElementException при вызове next() на пустом итераторе")
  void allIteratorsShouldThrowNoSuchElementExceptionWhenEmpty() {
    Iterator<Book> genreYearIterator = libraryManager.getBooksByGenreAndYearIterator(
        Book.Genre.FICTION, 2000);
    Iterator<Book> authorsIterator = libraryManager.getBooksWithMultipleAuthorsIterator(4);
    Iterator<Borrowing> overdueIterator = new LibraryManager().getOverdueBorrowingsIterator();

    assertThatThrownBy(genreYearIterator::next)
        .isInstanceOf(NoSuchElementException.class);
    assertThatThrownBy(authorsIterator::next)
        .isInstanceOf(NoSuchElementException.class);
    assertThatThrownBy(overdueIterator::next)
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @DisplayName("Метод remove() итератора должен выбрасывать UnsupportedOperationException")
  void removeMethodShouldThrowUnsupportedOperationException() {
    Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.TECHNICAL,
        2020);
    iterator.next();

    assertThatThrownBy(iterator::remove)
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("remove");
  }
}