package ru.mentee.power.collections.library;

import java.util.Comparator;

public class GenreAndTitleComparator implements Comparator<Book> {

  @Override
  public int compare(Book b1, Book b2) {
    int genreComparison = b1.getGenre().compareTo(b2.getGenre());

    if (genreComparison != 0) {
      return genreComparison;
    }

    return b1.getTitle().compareTo(b2.getTitle());
  }
}
