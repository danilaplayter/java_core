package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.Objects;

public class Borrowing {
    private String isbn;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Borrowing(String isbn, String readerId, LocalDate borrowDate, LocalDate dueDate) {
        this.isbn = isbn;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void returnBook(LocalDate returnDate) {
       this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrowing that = (Borrowing) o;

        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(readerId, that.readerId) &&
                Objects.equals(borrowDate, that.borrowDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, readerId, borrowDate);
    }

    @Override
    public String toString() {
        return "BookRecord" +
                "isbn='" + isbn + '\'' +
                ", readerId='" + readerId + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate;
    }
}