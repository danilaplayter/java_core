package ru.mentee.power.collections.library;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LibraryDemo {

  public static void main(String[] args) {
    LibraryManager library = new LibraryManager();

    Book book1 = new Book("978-3-16-148410-0", "The Great Gatsby", 1925, Book.Genre.FANTASY,
        List.of("F. Scott Fitzgerald"), true);
    Book book2 = new Book("978-0-06-112008-4", "To Kill a Mockingbird", 1960, Book.Genre.DETECTIVE,
        List.of("Harper Lee"), false);
    Book book3 = new Book("978-0-545-01022-1", "Harry Potter and the Philosopher's Stone", 1997,
        Book.Genre.FANTASY, List.of("J.K. Rowling"), true);
    Book book4 = new Book("978-0-395-19395-7", "The Hobbit", 1937, Book.Genre.FANTASY,
        List.of("J.R.R. Tolkien"), false);
    Book book5 = new Book("978-1-4028-9462-6", "Clean Code", 2008, Book.Genre.TECHNICAL,
        List.of("Robert Martin"), true);
    Book book6 = new Book("978-0-13-235088-4", "Design Patterns", 1994, Book.Genre.TECHNICAL,
        List.of("Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"), true);

    library.addBook(book1);
    library.addBook(book2);
    library.addBook(book3);
    library.addBook(book4);
    library.addBook(book5);
    library.addBook(book6);

    Reader reader1 = new Reader("R001", "John Smith", "john.smith@example.com",
        Reader.ReaderCategory.STUDENT);
    Reader reader2 = new Reader("R002", "Alice Johnson", "alice.johnson@example.com",
        Reader.ReaderCategory.STUDENT);
    Reader reader3 = new Reader("R003", "Bob Wilson", "bob.wilson@example.com",
        Reader.ReaderCategory.STUDENT);

    library.addReader(reader1);
    library.addReader(reader2);
    library.addReader(reader3);

    library.borrowBook("978-3-16-148410-0", "R001", 14); // John borrows The Great Gatsby
    library.borrowBook("978-0-06-112008-4", "R001", 14); // John borrows To Kill a Mockingbird
    library.borrowBook("978-0-545-01022-1", "R002", 21); // Alice borrows Harry Potter
    library.borrowBook("978-0-395-19395-7", "R003", 7);  // Bob borrows The Hobbit

    library.returnBook("978-3-16-148410-0", "R001"); // John returns The Great Gatsby
    library.returnBook("978-0-545-01022-1", "R002"); // Alice returns Harry Potter

    System.out.println("=== Books by Genre (FANTASY) ===");
    List<Book> fantasyBooks = library.getBooksByGenre(Book.Genre.FANTASY);
    fantasyBooks.forEach(book -> System.out.println(book.getTitle()));

    System.out.println("\n=== Books by Author (J.R.R. Tolkien) ===");
    List<Book> tolkienBooks = library.getBooksByAuthor("J.R.R. Tolkien");
    tolkienBooks.forEach(book -> System.out.println(book.getTitle()));

    System.out.println("\n=== Books with 'the' in title ===");
    List<Book> booksWithThe = library.searchBooksByTitle("the");
    booksWithThe.forEach(book -> System.out.println(book.getTitle()));

    System.out.println("\n=== Available Books ===");
    List<Book> availableBooks = library.getAvailableBooks();
    availableBooks.forEach(book -> System.out.println(book.getTitle()));

    // Demonstrate sorting
    System.out.println("\n=== Books Sorted by Title ===");
    List<Book> allBooks = library.getAllBooks();
    List<Book> sortedByTitle = library.sortBooksByTitle(allBooks);
    sortedByTitle.forEach(book -> System.out.println(book.getTitle()));

    System.out.println("\n=== Books Sorted by Publication Year ===");
    List<Book> sortedByYear = library.sortBooksByPublicationYear(allBooks);
    sortedByYear.forEach(
        book -> System.out.println(book.getTitle() + " (" + book.getPublicationYear() + ")"));

    System.out.println("\n=== Books Sorted by Availability ===");
    List<Book> sortedByAvailability = library.sortBooksByAvailability(allBooks);
    sortedByAvailability.forEach(
        book -> System.out.println(book.getTitle() + " - Available: " + book.isAvailable()));

    System.out.println("\n=== Books with Multiple Authors (Iterator) ===");
    Iterator<Book> multiAuthorIterator = library.getBooksWithMultipleAuthorsIterator(2);
    while (multiAuthorIterator.hasNext()) {
      Book book = multiAuthorIterator.next();
      System.out.println(book.getTitle() + " (" + book.getAuthors().size() + " authors)");
    }

    System.out.println("\n=== Fantasy Books from 1997 (Iterator) ===");
    Iterator<Book> genreYearIterator = library.getBooksByGenreAndYearIterator(Book.Genre.FANTASY,
        1997);
    while (genreYearIterator.hasNext()) {
      Book book = genreYearIterator.next();
      System.out.println(book.getTitle());
    }

    System.out.println("\n=== Genre Statistics ===");
    Map<Book.Genre, Integer> genreStats = library.getGenreStatistics();
  }
}