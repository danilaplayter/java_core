package ru.mentee.power.io.model; // Пример пакета

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String isbn;
    private String title;
    private Set<String> authors;
    private Genre genre;
    private int publicationYear;
    private int pageCount;
    private boolean available;

    public enum Genre implements Serializable {
        FICTION, NON_FICTION, SCIENCE, HISTORY, FANTASY,
        DETECTIVE, ROMANCE, BIOGRAPHY, CHILDREN, TECHNICAL, ART
    }

    public Book(String isbn, String title, int publicationYear, Genre genre,
                List<String> authors, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.authors = new HashSet<>(authors);
        this.available = available;
    }

    public Book(String isbn, String title, int publicationYear, Genre genre) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.authors = new HashSet<>();
        this.available = isAvailable();
    }

    public void addAuthor(String author) {
       this.authors.add(author);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }


    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genre=" + genre +
                ", publicationYear=" + publicationYear +
                ", pageCount=" + pageCount +
                ", available=" + available +
                '}';
    }
}