package ru.mentee.power.methods.library;

public class Library {
    private Book[] books;
    private int bookCount;
    private int capacity;

    public Library(int capacity) {
        this.capacity = capacity;
        this.books = new Book[capacity];
        this.bookCount = 0;
    }

    public boolean addBook(Book book) {
        if (book == null || bookCount >= capacity) {
            return false;
        }
        books[bookCount] = book;
        bookCount++;
        return true;
    }

    public Book findBookByTitle(String title) {
        if (title == null) return null;

        for (int i = 0; i < bookCount; i++) {
            if (title.equals(books[i].getTitle())) {
                return books[i];
            }
        }
        return null;
    }

    public boolean checkoutBook(String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }

        Book bookToCheckout = null;
        for (int i = 0; i < bookCount; i++) {
            if (title.equals(books[i].getTitle())) {
                bookToCheckout = books[i];
                break;
            }
        }

        if (bookToCheckout == null || !bookToCheckout.isAvailable()) {
            return false;
        }

        // 4. Выдаем книгу (меняем статус)
        bookToCheckout.setAvailable(false);
        return true;
    }

    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);
        if (book == null || book.isAvailable()) {
            return false;
        }
        book.setAvailable(true);
        return true;
    }

    public Book[] listAvailableBooks() {
        int availableCount = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) {
                availableCount++;
            }
        }

        Book[] availableBooks = new Book[availableCount];
        int index = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable() ) {
                availableBooks[index++] = books[i];
            }
        }
        return availableBooks;
    }

    public Book[] listCheckedOutBooks() {
        int checkedOutCount = 0;
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isAvailable()) {
                checkedOutCount++;
            }
        }

        Book[] checkedOutBooks = new Book[checkedOutCount];
        int index = 0;
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isAvailable()) {
                checkedOutBooks[index++] = books[i];
            }
        }
        return checkedOutBooks;
    }
}