package ru.mentee.power.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class BookCatalog {

  private List<Book> books;

  public BookCatalog() {
    this.books = new ArrayList<>();
  }

  public void addBook(Book book) {
    if (book != null) {
      books.add(book);
    }
  }

  public List<Book> getAllBooks() {
    if (books == null || books.isEmpty()) {
      return Collections.emptyList();
    }
    return books;
  }

  public List<Book> sortBooks(Comparator<Book> comparator) {
    if (books == null || books.isEmpty()) {
      return null;
    }
    List<Book> sortedBooks = new ArrayList<>(books);
    sortedBooks.sort(comparator);
    return sortedBooks;
  }

  public List<Book> filterBooks(Predicate<Book> predicate) {
    if (books == null || books.isEmpty()) {
      return Collections.emptyList();
    }
    List<Book> filteredBooks = new ArrayList<>();
    for (Book book : books) {
      if (predicate.test(book)) {
        filteredBooks.add(book);
      }
    }
    return filteredBooks;
  }

  public static Comparator<Book> byTitle() {
    return Comparator.comparing(Book::getTitle);
  }

  public static Comparator<Book> byAuthor() {
    return Comparator.comparing(Book::getAuthor);
  }

  public static Comparator<Book> byYearPublished() {
    return Comparator.comparing(Book::getYearPublished);
  }

  public static Comparator<Book> byPageCount() {
    return Comparator.comparingInt(Book::getPageCount);
  }

  public static Comparator<Book> multipleComparators(List<Comparator<Book>> comparators) {
    if (comparators == null || comparators.isEmpty()) {
      return (b1, b2) -> 0; // возвращаем нейтральный компаратор, если список пуст
    }

    Comparator<Book> result = comparators.getFirst();
    for (int i = 1; i < comparators.size(); i++) {
      result = result.thenComparing(comparators.get(i));
    }

    return result;
  }
}