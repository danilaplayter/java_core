package ru.mentee.power.io.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class LibraryManager implements Serializable {

  @Serial
  private static final long serialVersionUID = 100L;

  private Map<String, Book> booksMap;
  private Map<String, Reader> readersMap;
  private List<Borrowing> borrowingsList;

  private transient Map<Book.Genre, Set<Book>> booksByGenre;
  private transient Map<String, List<Book>> booksByAuthor;

  public LibraryManager() {
    this.booksMap = new HashMap<>();
    this.readersMap = new HashMap<>();
    this.borrowingsList = new LinkedList<>();
    rebuildIndexes();
  }

  // --- Методы Состояния ---
  public void saveLibraryState(String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream(filename)))) {
      out.writeObject(this);
    } catch (IOException e) {
      System.err.println("Ошибка при сохранении состояния библиотеки: " + e.getMessage());
      throw new RuntimeException("Проваленное сохранение. Данные не записаны в файл.", e);
    }
  }

  public static LibraryManager loadLibraryState(String filename) {

    Path filePath = Path.of(filename);
    if (!Files.exists(filePath)) {
      System.out.println("Файл " + filename + " не найден. Будет создана новая библиотека.");
      return null;
    }

    // Проверяем, что это файл, а не директория
    if (!Files.isRegularFile(filePath)) {
      System.err.println("Ошибка: " + filename + " не является файлом");
      return null;
    }

    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(new FileInputStream(filename)))) {
      LibraryManager manager = (LibraryManager) in.readObject();
      manager.rebuildIndexes();
      return manager;
    } catch (FileNotFoundException e) {
      System.err.println("Файл не найден: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Ошибка ввода-вывода: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.err.println("Класс не найден: " + e.getMessage());
    }
    return null;
  }

  private void rebuildIndexes() {
    booksByGenre = new EnumMap<>(Book.Genre.class);
    booksByAuthor = new HashMap<>();

    if (booksMap != null) {
      for (Book book : booksMap.values()) {
        booksByGenre.computeIfAbsent(book.getGenre(), k -> new HashSet<>()).add(book);

        for (String author : book.getAuthors()) {
          booksByAuthor.computeIfAbsent(author, k -> new ArrayList<>()).add(book);
        }
      }
    }
  }

  // --- Методы CSV ---
  static final String BOOK_CSV_HEADER = "ISBN;Title;Authors;Genre;Year;Pages;Available";

  public void exportBooksToCsv(String filename, String delimiter) {
    try (BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
      writer.write(BOOK_CSV_HEADER);
      writer.newLine();

      for (Book book : booksMap.values()) {
        String authors = String.join(",", book.getAuthors());
        String line = String.join(delimiter, book.getIsbn(), book.getTitle(), authors,
            book.getGenre().name(), String.valueOf(book.getPublicationYear()),
            String.valueOf(book.getPageCount()), String.valueOf(book.isAvailable()));
        writer.write(line);
        writer.newLine();
      }

    } catch (IOException e) {
      System.err.println("Ошибка при экспорте в CSV: " + e.getMessage());
      throw new RuntimeException("Не удалось экспортировать данные в CSV", e);
    }
  }

  public int importBooksFromCsv(String filename, String delimiter, boolean append) {
    int importedCount = 0;

    if (!append) {
      booksMap.clear();
      rebuildIndexes();
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {

      reader.readLine();

      String line;
      while ((line = reader.readLine()) != null) {
        try {
          String[] parts = line.split(delimiter);
          if (parts.length != 7) {
            System.err.println("Некорректное количество полей в строке: " + line);
            continue;
          }

          Book book = new Book(parts[0], // ISBN
              parts[1], // Title
              Integer.parseInt(parts[4]), // Year
              Book.Genre.valueOf(parts[3])); // Genre

          for (String author : parts[2].split(",")) {
            book.addAuthor(author.trim());
          }

          book.setPageCount(Integer.parseInt(parts[5]));
          book.setAvailable(Boolean.parseBoolean(parts[6]));

          if (addBook(book)) {
            importedCount++;
          }
        } catch (IllegalArgumentException e) {
          System.err.println("Ошибка парсинга строки: " + line + " - " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.err.println("Ошибка при импорте из CSV: " + e.getMessage());
      throw new RuntimeException("Не удалось импортировать данные из CSV", e);
    }

    return importedCount;
  }

  public boolean addBook(Book book) {
    if (book == null || booksMap.containsKey(book.getIsbn())) {
      return false;
    }

    booksMap.put(book.getIsbn(), book);

    // Обновляем индексы
    booksByGenre.computeIfAbsent(book.getGenre(), k -> new HashSet<>()).add(book);
    for (String author : book.getAuthors()) {
      booksByAuthor.computeIfAbsent(author, k -> new ArrayList<>()).add(book);
    }

    return true;
  }

  public boolean removeBook(String isbn) {
    Book book = booksMap.remove(isbn);
    if (book == null) {
      return false;
    }

    // Обновляем индексы
    booksByGenre.get(book.getGenre()).remove(book);
    for (String author : book.getAuthors()) {
      booksByAuthor.get(author).remove(book);
    }

    return true;
  }

  public Book getBookByIsbn(String isbn) {
    return booksMap.get(isbn);
  }

  public List<Book> getAllBooks() {
    return new ArrayList<>(booksMap.values());
  }

  public boolean addReader(Reader reader) {
    if (reader == null || readersMap.containsKey(reader.getId())) {
      return false;
    }
    readersMap.put(reader.getId(), reader);
    return true;
  }

  public boolean removeReader(String readerId) {
    return readersMap.remove(readerId) != null;
  }

  public Reader getReaderById(String readerId) {
    return readersMap.get(readerId);
  }

  public List<Reader> getAllReaders() {
    return new ArrayList<>(readersMap.values());
  }

  public boolean borrowBook(String isbn, String readerId, int days) {
    Book book = getBookByIsbn(isbn);
    Reader reader = getReaderById(readerId);

    if (book == null || reader == null || !book.isAvailable()) {
      return false;
    }

    book.setAvailable(false);
    borrowingsList.add(new Borrowing(isbn, readerId, days));
    return true;
  }

  public boolean returnBook(String isbn) {
    Book book = getBookByIsbn(isbn);
    if (book == null || book.isAvailable()) {
      return false;
    }

    book.setAvailable(true);
    return true;
  }

  public List<Borrowing> getAllBorrowings() {
    return new ArrayList<>(borrowingsList);
  }

  public List<Book> getAvailableBooks() {
    return booksMap.values().stream().filter(Book::isAvailable).collect(Collectors.toList());
  }

  public List<Borrowing> getOverdueBorrowings() {
    return borrowingsList.stream().filter(b -> !b.isReturned() && b.isOverdue())
        .collect(Collectors.toList());
  }

  // --- Методы для работы с индексами ---
  public Set<Book> getBooksByGenre(Book.Genre genre) {
    return Collections.unmodifiableSet(booksByGenre.getOrDefault(genre, new HashSet<>()));
  }

  public List<Book> getBooksByAuthor(String author) {
    return Collections.unmodifiableList(booksByAuthor.getOrDefault(author, new ArrayList<>()));
  }

}