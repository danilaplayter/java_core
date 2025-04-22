package ru.mentee.power.collections.library;

import com.sun.source.tree.LambdaExpressionTree;

import javax.swing.plaf.TreeUI;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BooleanSupplier;

public class LibraryManager {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Reader> readers = new HashMap<>();
    private final List<Borrowing> borrowings = new ArrayList<>();
    private final Map<Book.Genre, Set<Book>> booksByGenre = new EnumMap<>(Book.Genre.class);
    private final Map<String, List<Book>> booksByAuthor = new HashMap<>();

    // ============ Методы для работы с книгами ============

    public boolean addBook(Book book) {
        if (book == null) {
            throw new NullPointerException("Значение book не может быть Null");
        }

        String isbn = book.getIsbn();

        if (isbn == null) {
            throw new IllegalArgumentException("ISBN книги не может быть null");
        }

        if (books.containsKey(isbn)) {
            return false;
        }

        books.put(isbn, book);

        booksByGenre.computeIfAbsent(book.getGenre(), k -> new HashSet<>()).add(book);

        for (String author : book.getAuthors()) {
            booksByAuthor.computeIfAbsent(author, k -> new ArrayList<>()).add(book);
        }

        return true;

    }

    public Book getBookByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        return books.get(isbn);
    }

    public boolean removeBook(String isbn) {

        Book book = books.get(isbn);

        if (book == null) {
            return false;
        }

        books.remove(isbn);

        Set<Book> genreBooks = booksByGenre.get(book.getGenre());
        if (genreBooks != null) {
            genreBooks.remove(book);
        }

        // 3. Удаляем из booksByAuthor (для каждого автора)
        for (String author : book.getAuthors()) {
            List<Book> authorBooks = booksByAuthor.get(author);
            if (authorBooks != null) {
                authorBooks.remove(book);
            }
        }

        return true;

    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> getBooksByGenre(Book.Genre genre) {
        Set<Book> genreBooks = booksByGenre.get(genre);

        if (genreBooks == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(genreBooks);

    }

    public List<Book> getBooksByAuthor(String author) {
        if (author == null) {
            throw new IllegalArgumentException("Автор не может быть null");
        }
        List<Book> bookByAuthor = booksByAuthor.get(author);
        if (bookByAuthor == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(bookByAuthor);
    }

    public List<Book> searchBooksByTitle(String titlePart) {
        if (titlePart == null) {
            return Collections.emptyList();
        }
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(titlePart.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getAvailableBooks() {
        List<Book> booksAvailable = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.isAvailable()) {
                booksAvailable.add(book);
            }
        }

        return booksAvailable;
    }

    public List<Book> sortBooksByTitle(List<Book> books) {
        if (books == null) {
            return Collections.emptyList();
        }

        List<Book> sortedList = new ArrayList<>(books);
        sortedList.sort(new TitleComparator());
        return sortedList;

    }

    public List<Book> sortBooksByPublicationYear(List<Book> books) {
        if (books == null) {
            return Collections.emptyList();
        }

        List<Book> sortedList = new ArrayList<>(books);
        sortedList.sort(new PublicationYearComparator());
        return sortedList;

    }

    public List<Book> sortBooksByAvailability(List<Book> books) {
        if (books == null) {
            return Collections.emptyList();
        }

        List<Book> sortedList = new ArrayList<>(books);
        sortedList.sort(new AvailabilityComparator());
        return sortedList;

    }

    // ============ Методы для работы с читателями ============

    public boolean addReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Читатель не может быть null");
        }
        if (readers.containsKey(reader.getId())) {
            return false;
        }

        readers.put(reader.getId(), reader);
        return true;
    }

    public Reader getReaderById(String readerId) {
        if (readerId == null) {
            throw new IllegalArgumentException("ID читателя не может быть null");
        }

        if (!readers.containsKey(readerId)) {
            return null;
        }
        return readers.get(readerId);

    }

    public boolean removeReader(String readerId) {
        if (readerId == null) {
            throw new IllegalArgumentException("ID читателя не может быть null");
        }

        if (!readers.containsKey(readerId)) {
            return false;
        }
        return readers.remove(readerId, readers.get(readerId));

    }

    public List<Reader> getAllReaders() {
        return new ArrayList<>(readers.values());
    }

    // ============ Методы для выдачи и возврата книг ============

    public boolean borrowBook(String isbn, String readerId, int borrowDays) {
        if (isbn == null || readerId == null) {
            throw new IllegalArgumentException("ISBN и ID читателя не могут быть null");
        }


        Book book = books.get(isbn);
        Reader reader = readers.get(readerId);
        if (book == null || reader == null) {
            return false;
        }

        if (!book.isAvailable()) {
            return false;
        }

        book.setAvailable(false);

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(borrowDays);
        Borrowing borrowing = new Borrowing(isbn, readerId, borrowDate, dueDate);
        borrowings.add(borrowing);

        return true;

    }

    public boolean returnBook(String isbn, String readerId) {
        if (isbn == null || readerId == null) {
            throw new IllegalArgumentException("ISBN и ID читателя не могут быть null");
        }

        Book book = books.get(isbn);

        if (book == null) {
            return false;
        }

        Borrowing activeBorrowing = null;
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getIsbn().equals(isbn) && borrowing.getReaderId().equals(readerId)
                    && borrowing.getReturnDate() == null) {
                activeBorrowing = borrowing;
                break;
            }
        }

        if(activeBorrowing == null){
            return false;
        }

        book.setAvailable(true);
        activeBorrowing.setReturnDate(LocalDate.now());
        return true;
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowings;
    }

    public List<Borrowing> getOverdueBorrowings() {
        List<Borrowing> overdueBorrowings = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getReturnDate() == null
                    && borrowing.getDueDate().isBefore(today)) {
                overdueBorrowings.add(borrowing);
            }
        }

        return overdueBorrowings;
    }

    public List<Borrowing> getBorrowingsByReader(String readerId) {
        if (readerId == null) {
            throw new IllegalArgumentException("ID читателя не может быть null");
        }

        List<Borrowing> readerBorrowings = new ArrayList<>();
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getReaderId().equals(readerId)) {
                readerBorrowings.add(borrowing);
            }
        }
        return readerBorrowings;
    }

    public List<Borrowing> getBorrowingsByBook(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN не может быть null");
        }

        List<Borrowing> bookBorrowings = new ArrayList<>();
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getIsbn().equals(isbn)) {
                bookBorrowings.add(borrowing);
            }
        }
        return bookBorrowings;
    }

    public boolean extendBorrowingPeriod(String isbn, String readerId, int additionalDays) {
        if (isbn == null || readerId == null) {
            throw new IllegalArgumentException("ISBN и ID читателя не могут быть null");
        }
        if (additionalDays <= 0) {
            throw new IllegalArgumentException("Дополнительные дни должны быть положительным числом");
        }

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getIsbn().equals(isbn)
                    && borrowing.getReaderId().equals(readerId)
                    && borrowing.getReturnDate() == null) {
                borrowing.setDueDate(borrowing.getDueDate().plusDays(additionalDays));
                return true;
            }
        }
        return false;
    }

    // ============ Методы для статистики и отчетов ============

    public Map<Book.Genre, Integer> getGenreStatistics() {
        Map<Book.Genre, Integer> statistics = new EnumMap<>(Book.Genre.class);
        for (Map.Entry<Book.Genre, Set<Book>> entry : booksByGenre.entrySet()) {
            statistics.put(entry.getKey(), entry.getValue().size());
        }
        return statistics;
    }

    public Map<Book, Integer> getMostPopularBooks(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Лимит должен быть положительным числом");
        }

        Map<Book, Integer> bookPopularity = new HashMap<>();

        for (Borrowing borrowing : borrowings) {
            Book book = books.get(borrowing.getIsbn());
            if (book != null) {
                bookPopularity.put(book, bookPopularity.getOrDefault(book, 0) + 1);
            }
        }

        List<Map.Entry<Book, Integer>> entries = new ArrayList<>(bookPopularity.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<Book, Integer>>() {
            @Override
            public int compare(Map.Entry<Book, Integer> e1, Map.Entry<Book, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        Map<Book, Integer> result = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<Book, Integer> entry : entries) {
            if (count++ >= limit) break;
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public Map<Reader, Integer> getMostActiveReaders(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Лимит должен быть положительным числом");
        }

        Map<Reader, Integer> readerActivity = new HashMap<>();

        for (Borrowing borrowing : borrowings) {
            Reader reader = readers.get(borrowing.getReaderId());
            if (reader != null) {
                readerActivity.put(reader, readerActivity.getOrDefault(reader, 0) + 1);
            }
        }

        List<Map.Entry<Reader, Integer>> entries = new ArrayList<>(readerActivity.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<Reader, Integer>>() {
            @Override
            public int compare(Map.Entry<Reader, Integer> e1, Map.Entry<Reader, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        // 4. Ограничиваем результат
        Map<Reader, Integer> result = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<Reader, Integer> entry : entries) {
            if (count++ >= limit) break;
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public List<Reader> getReadersWithOverdueBooks() {
        List<Borrowing> overdue = getOverdueBorrowings();
        Set<String> readerIds = new HashSet<>();
        List<Reader> result = new ArrayList<>();

        for (Borrowing borrowing : overdue) {
            if (readerIds.add(borrowing.getReaderId())) {
                Reader reader = readers.get(borrowing.getReaderId());
                if (reader != null) {
                    result.add(reader);
                }
            }
        }

        return result;
    }

    // ============ Методы для работы с итераторами ============

    public Iterator<Book> getBooksByGenreAndYearIterator(Book.Genre genre, int year) {
        return books.values().stream()
                .filter(book -> book.getGenre() == genre && book.getPublicationYear() == year)
                .iterator();
    }

    public Iterator<Book> getBooksWithMultipleAuthorsIterator(int minAuthorsCount) {
        if (minAuthorsCount < 2) {
            throw new IllegalArgumentException("Минимальное количество авторов должно быть не менее 2");
        }

        return books.values().stream()
                .filter(book -> book.getAuthors().size() >= minAuthorsCount)
                .iterator();
    }

    public Iterator<Borrowing> getOverdueBorrowingsIterator() {
        return getOverdueBorrowings().iterator();
    }

}