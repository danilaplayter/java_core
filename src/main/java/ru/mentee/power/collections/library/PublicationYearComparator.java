package ru.mentee.power.collections.library;

import java.util.Comparator;

public class PublicationYearComparator implements Comparator<Book> {

  @Override
  public int compare(Book b1, Book b2) {
    return Integer.compare(b1.getPublicationYear(), b2.getPublicationYear());
  }
}