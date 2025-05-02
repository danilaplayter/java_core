package ru.mentee.power.io.model;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LibraryPersistenceDemo {

  private static final String STATE_FILE = "library_state.ser";
  private static final String BOOKS_CSV_FILE = "books_export.csv";
  private static final String BOOKS_CSV_IMPORT_FILE = "books_import.csv";
  private static final String CSV_DELIMITER = ";";

  public static void main(String[] args) {
    LibraryManager libraryManager;

    System.out.println("Попытка загрузки состояния из " + STATE_FILE + "...");
    libraryManager = LibraryManager.loadLibraryState(STATE_FILE);

    if (libraryManager == null) {
      libraryManager = new LibraryManager();
      System.out.println("Создана новая пустая библиотека. Добавляем тестовые данные...");
      Book book1 = new Book("978-5-389-21499-6", "Мастер и Маргарита", 1966, Book.Genre.FICTION);
      libraryManager.addBook(book1);
      Book book2 = new Book("978-5-17-136333-4", "1984", 1949, Book.Genre.FICTION);
      book2.addAuthor("Джордж Оруэлл");
      libraryManager.addBook(book2);

      Reader reader = new Reader("ivanov", "Иван Иванов", "ivanov12345@gmai.com",
          Reader.ReaderCategory.STUDENT);
      libraryManager.addReader(reader);

      System.out.println("\n--- Текущее состояние библиотеки --- ");
      displayLibraryStats(libraryManager);

      // 3. Демонстрация операций (по желанию)
      System.out.println("\n--- Демонстрация операций --- ");
      libraryManager.borrowBook("978-5-389-21499-6", "ivanov", 14);
      System.out.println("Книга 'Мастер и Маргарита' выдана читателю Иванову на 14 дней");

      // 4. Экспорт книг в CSV
      System.out.println("\n--- Экспорт книг в CSV --- ");
      libraryManager.exportBooksToCsv(BOOKS_CSV_FILE, CSV_DELIMITER);
      System.out.println("Экспорт завершен в " + BOOKS_CSV_FILE);

      // 5. Импорт книг из CSV
      System.out.println("\n--- Импорт книг из CSV --- ");
      System.out.println("\n--- Импорт книг из CSV --- ");
      if (Files.exists(Paths.get(BOOKS_CSV_IMPORT_FILE))) {
        int importedCount = libraryManager.importBooksFromCsv(BOOKS_CSV_IMPORT_FILE, CSV_DELIMITER,
            true);
        System.out.println("Импортировано книг: " + importedCount);
      } else {
        System.out.println(
            "Файл для импорта " + BOOKS_CSV_IMPORT_FILE + " не найден. Импорт пропущен.");
      }

      System.out.println("\n--- Состояние библиотеки после импорта --- ");
      displayLibraryStats(libraryManager);

      // 6. Сохранение состояния перед выходом
      System.out.println("\n--- Сохранение финального состояния --- ");
      libraryManager.saveLibraryState(STATE_FILE);
      System.out.println("Состояние сохранено в " + STATE_FILE);

      System.out.println("\nРабота программы завершена.");
    }
  }

  private static void displayLibraryStats(LibraryManager manager) {
    System.out.println("Всего книг: " + manager.getAllBooks().size());
    System.out.println("Всего читателей: " + manager.getAllReaders().size());
    System.out.println("Всего записей о выдаче: " + manager.getAllBorrowings().size());
    System.out.println("Доступных книг: " + manager.getAvailableBooks().size());
    System.out.println("Просроченных выдач: " + manager.getOverdueBorrowings().size());
  }
}