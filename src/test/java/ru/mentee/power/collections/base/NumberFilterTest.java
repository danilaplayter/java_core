package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NumberFilterTest {

    @Test
    void shouldReturnOnlyEvenNumbersFromMixedList() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Act
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);

        // Assert
        assertThat(result)
                .hasSize(3)
                .containsExactly(2, 4, 6);
    }

    @Test
    void shouldReturnEmptyListWhenSourceContainsOnlyOddNumbers() {
        List<Integer> numbers = Arrays.asList(1, 3, 5, 7);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllNumbersWhenSourceContainsOnlyEvenNumbers() {
        List<Integer> numbers = Arrays.asList(2, 6, 8);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEqualTo(numbers);
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsEmpty() {
        List<Integer> numbers = new ArrayList<>();
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsNull() {
        List<Integer> numbers = null;
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();

    }

    @Test
    void shouldIgnoreNullElementsWhenFilteringList() {
        List<Integer> numbers = Arrays.asList(null, null, null);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleCustomScenarioForFilterEvenNumbers() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, null, 5, null, 6);

        // Act
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);

        // Assert
        assertThat(result)
                .hasSize(3)
                .containsExactly(2, 4, 6);

    }
}