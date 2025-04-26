/*
package ru.mentee.power.collections.library.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComparatorsTest {

    @Test
    @DisplayName("TitleComparator должен сортировать книги по названию в алфавитном порядке")
    void titleComparatorShouldSortBooksAlphabetically() {

        Book book1 = new Book("1", "Java", 2020, Book.Genre.TECHNICAL, List.of("Author1"), true);
        Book book2 = new Book("2", "Algorithms", 2019, Book.Genre.TECHNICAL, List.of("Author2"), true);
        Book book3 = new Book("3", "Design Patterns", 2021, Book.Genre.TECHNICAL, List.of("Author3"), true);
        Book book4 = new Book("4", "Algorithms", 2018, Book.Genre.TECHNICAL, List.of("Author4"), true);

        TitleComparator comparator = new TitleComparator();

        assertThat(comparator.compare(book2, book1)).isNegative();
        assertThat(comparator.compare(book1, book3)).isPositive();
        assertThat(comparator.compare(book4, book2)).isZero();

    }

    @Test
    @DisplayName("TitleComparator должен корректно обрабатывать null-значения")
    void titleComparatorShouldHandleNullValues() {
        Book book1 = new Book("1", null, 2020, Book.Genre.TECHNICAL, List.of("Author1"), true);
        Book book2 = new Book("2", "Java", 2019, Book.Genre.TECHNICAL, List.of("Author2"), true);

        TitleComparator comparator = new TitleComparator();

        assertThatThrownBy(() -> comparator.compare(book1, book2))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> comparator.compare(book2, book1))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("PublicationYearComparator должен сортировать книги от новых к старым")
    void publicationYearComparatorShouldSortBooksFromNewToOld() {
        Book book1 = new Book("1", "Book1", 2020, Book.Genre.FICTION, List.of("Author1"), true);
        Book book2 = new Book("2", "Book2", 2019, Book.Genre.FICTION, List.of("Author2"), true);
        Book book3 = new Book("3", "Book3", 2021, Book.Genre.FICTION, List.of("Author3"), true);

        PublicationYearComparator comparator = new PublicationYearComparator();

        assertThat(comparator.compare(book3, book1)).isPositive();
        assertThat(comparator.compare(book1, book2)).isPositive();
        assertThat(comparator.compare(book2, book2)).isZero();
    }

    @Test
    @DisplayName("AvailabilityComparator должен сортировать сначала доступные, потом недоступные книги")
    void availabilityComparatorShouldSortAvailableFirst() {
        Book book1 = new Book("1", "Book1", 2020, Book.Genre.FICTION, List.of("Author1"), false);
        Book book2 = new Book("2", "Book2", 2019, Book.Genre.FICTION, List.of("Author2"), true);
        Book book3 = new Book("3", "Book3", 2021, Book.Genre.FICTION, List.of("Author3"), true);

        AvailabilityComparator comparator = new AvailabilityComparator();

        assertThat(comparator.compare(book2, book1)).isPositive();
        assertThat(comparator.compare(book3, book1)).isPositive();
    }

    @Test
    @DisplayName("GenreAndTitleComparator должен сортировать сначала по жанру, потом по названию")
    void genreAndTitleComparatorShouldSortByGenreThenByTitle() {
        Book book1 = new Book("1", "Java", 2020, Book.Genre.TECHNICAL, List.of("Author1"), true);
        Book book2 = new Book("2", "Algorithms", 2019, Book.Genre.TECHNICAL, List.of("Author2"), true);
        Book book3 = new Book("3", "Novel", 2021, Book.Genre.FICTION, List.of("Author3"), true);
        Book book4 = new Book("4", "Drama", 2018, Book.Genre.FICTION, List.of("Author4"), true);

        GenreAndTitleComparator comparator = new GenreAndTitleComparator();

        assertThat(comparator.compare(book3, book1)).isNegative();
        assertThat(comparator.compare(book1, book3)).isPositive();

        assertThat(comparator.compare(book2, book1)).isNegative();
        assertThat(comparator.compare(book4, book3)).isNegative();
    }
}*/
