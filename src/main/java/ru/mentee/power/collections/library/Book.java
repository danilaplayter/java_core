package ru.mentee.power.collections.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Book {

  private String isbn;
  private String title;
  private Set<String> authors;
  private Genre genre;
  private int publicationYear;
  private boolean available;

  public enum Genre {
    FICTION, NON_FICTION, SCIENCE, HISTORY, FANTASY, DETECTIVE, ROMANCE, BIOGRAPHY, CHILDREN, TECHNICAL, ART
  }

  // Constructor that matches test usage
  public Book(String isbn, String title, int publicationYear, Genre genre, List<String> authors,
      boolean available) {
    this.isbn = isbn;
    this.title = title;
    this.publicationYear = publicationYear;
    this.genre = genre;
    this.authors = new HashSet<>(authors);
    this.available = available;
  }

  // Existing constructor for backward compatibility
  public Book(String isbn, String title, int publicationYear, Genre genre) {
    this(isbn, title, publicationYear, genre, new ArrayList<>(), true);
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

  public boolean isAvailable() {
    return available;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthors(Set<String> authors) {
    this.authors = authors;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public void addAuthor(String author) {
    this.authors.add(author);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(isbn, book.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn);
  }

  @Override
  public String toString() {
    return "Book" + "isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", authors=" + authors
        + ", genre=" + genre + ", publicationYear=" + publicationYear + ", available=" + available;
  }

}