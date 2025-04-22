package ru.mentee.power.collections.library;

import java.util.Comparator;

public class AvailabilityComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return Boolean.compare(b1.isAvailable(), b2.isAvailable());
    }
}