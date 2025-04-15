package ru.mentee.power.iterators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MatrixTest {
    private Matrix<Integer> squareMatrix;
    private Matrix<Integer> rectangularMatrix;
    private Matrix<Integer> emptyMatrix;

    @BeforeEach
    void setUp() {
        // Квадратная матрица 3x3
        Integer[][] squareData = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        squareMatrix = new Matrix<>(squareData);

        // Прямоугольная матрица 2x4
        Integer[][] rectangularData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        };
        rectangularMatrix = new Matrix<>(rectangularData);

        // Пустая матрица
        Integer[][] emptyData = {};
        emptyMatrix = new Matrix<>(emptyData);
    }

    // ... предыдущие тесты ...

    @Test
    @DisplayName("Итератор по столбцам должен корректно работать с пустой матрицей")
    void columnMajorIteratorShouldHandleEmptyMatrix() {
        Iterator<Integer> iterator = emptyMatrix.columnMajorIterator();
        assertFalse(iterator.hasNext());
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Итератор по столбцам должен корректно обходить прямоугольную матрицу")
    void columnMajorIteratorShouldHandleRectangularMatrix() {
        List<Integer> expected = Arrays.asList(1, 5, 2, 6, 3, 7, 4, 8);
        List<Integer> actual = new ArrayList<>();

        Iterator<Integer> iterator = rectangularMatrix.columnMajorIterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Спиральный итератор должен корректно работать с пустой матрицей")
    void spiralIteratorShouldHandleEmptyMatrix() {
        Iterator<Integer> iterator = emptyMatrix.spiralIterator();
        assertFalse(iterator.hasNext());
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Спиральный итератор должен корректно обходить матрицу по спирали")
    void spiralIteratorShouldTraverseSpirally() {
        List<Integer> expected = Arrays.asList(5, 6, 9, 8, 7, 4, 1, 2, 3);
        List<Integer> actual = new ArrayList<>();

        Iterator<Integer> iterator = squareMatrix.spiralIterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Спиральный итератор должен корректно обходить матрицу 1x1")
    void spiralIteratorShouldHandle1x1Matrix() {
        Integer[][] data = {{42}};
        Matrix<Integer> matrix = new Matrix<>(data);

        List<Integer> expected = Collections.singletonList(42);
        List<Integer> actual = new ArrayList<>();

        Iterator<Integer> iterator = matrix.spiralIterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Зигзаг-итератор должен корректно работать с пустой матрицей")
    void zigzagIteratorShouldHandleEmptyMatrix( ) {
        Iterator<Integer> iterator = emptyMatrix.zigzagIterator();
        assertFalse(iterator.hasNext());
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Зигзаг-итератор должен корректно обходить прямоугольную матрицу")
    void zigzagIteratorShouldHandleRectangularMatrix() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 8, 7, 6, 5);
        List<Integer> actual = new ArrayList<>();

        Iterator<Integer> iterator = rectangularMatrix.zigzagIterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Зигзаг-итератор должен корректно обходить матрицу с одной строкой")
    void zigzagIteratorShouldHandleSingleRowMatrix() {
        Integer[][] data = {{1, 2, 3}};
        Matrix<Integer> matrix = new Matrix<>(data);

        List<Integer> expected = Arrays.asList(1, 2, 3);
        List<Integer> actual = new ArrayList<>();

        Iterator<Integer> iterator = matrix.zigzagIterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Зигзаг-итератор должен корректно обходить матрицу с одним столбцом")
    void zigzagIteratorShouldHandleSingleColumnMatrix() {
        Integer[][] data = {{1}, {2}, {3}};
        Matrix<Integer> matrix = new Matrix<>(data);

        List<Integer> expected = Arrays.asList(1, 2, 3);
        List<Integer> actual = new ArrayList<>();

        Iterator<Integer> iterator = matrix.zigzagIterator();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Все итераторы должны быть независимыми")
    void iteratorsShouldBeIndependent() {
        Iterator<Integer> rowIter = squareMatrix.rowMajorIterator();
        Iterator<Integer> colIter = squareMatrix.columnMajorIterator();
        Iterator<Integer> spiralIter = squareMatrix.spiralIterator();
        Iterator<Integer> zigzagIter = squareMatrix.zigzagIterator();

        // Проверяем первые элементы каждого итератора
        assertEquals(Integer.valueOf(1), rowIter.next());
        assertEquals(Integer.valueOf(1), colIter.next());
        assertEquals(Integer.valueOf(5), spiralIter.next());
        assertEquals(Integer.valueOf(1), zigzagIter.next());

        // Проверяем, что остальные итераторы продолжают работать правильно
        assertEquals(Integer.valueOf(2), rowIter.next());
        assertEquals(Integer.valueOf(4), colIter.next());
        assertEquals(Integer.valueOf(6), spiralIter.next());
        assertEquals(Integer.valueOf(2), zigzagIter.next());
    }
}