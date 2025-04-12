package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class QuickSortAlgorithmsTest {

    @Test
    void testQuickSort() {
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        int[] expected = {1, 2, 3, 5, 5, 5, 6, 7, 8, 9};

        assertThat(QuickSortAlgorithms.quickSort(testData)).isEqualTo(expected);
    }

    @Test
    void testEmptyArray() {
        int[] emptyArray = {};

        assertThat(QuickSortAlgorithms.quickSort(emptyArray)).isEmpty();
    }

    @Test
    void testSingleElementArray() {
        int[] singleElementArray = {42};

        assertThat(QuickSortAlgorithms.quickSort(singleElementArray)).containsExactly(42);
    }

    @Test
    void testArrayWithNegativeValues() {
        int[] arrayWithNegatives = {-5, 7, -2, 9, -3, 5, -1, 8, 5, -6};
        int[] expected = {-6, -5, -3, -2, -1, 5, 5, 7, 8, 9};
        assertThat(QuickSortAlgorithms.quickSort(arrayWithNegatives)).isEqualTo(expected);
    }

    @Test
    void testAlreadySortedArray() {
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertThat(QuickSortAlgorithms.quickSort(sortedArray)).isEqualTo(sortedArray);
    }

    @Test
    void testReverseSortedArray() {
        int[] reverseSortedArray = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        assertThat(QuickSortAlgorithms.quickSort(reverseSortedArray)).isEqualTo(expected);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] arrayWithDuplicates = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};

        assertThat(QuickSortAlgorithms.quickSort(arrayWithDuplicates)).isEqualTo(expected);
    }

    @Test
    void testGenerateRandomArray() {
        int size = 100;
        int maxValue = 1000;

        int[] randomArray = QuickSortAlgorithms.generateRandomArray(size, maxValue);
        assertThat(randomArray).hasSize(size);

        for (int value : randomArray) {
            assertThat(value).isLessThanOrEqualTo(maxValue);
            assertThat(value).isGreaterThanOrEqualTo(0);
        }
    }
}