package ru.mentee.power.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Matrix<T> implements Iterable<T> {

  private final T[][] data;
  private final int rows;
  private final int columns;

  @SuppressWarnings("unchecked")
  public Matrix(T[][] data) {
    if (data == null) {
      throw new IllegalArgumentException("Массив данных не может быть null");
    }

    this.rows = data.length;
    if (rows == 0) {
      this.columns = 0;
    } else {
      this.columns = data[0].length;
      for (T[] row : data) {
        if (row.length != columns) {
          throw new IllegalArgumentException("Все строки матрицы должны иметь одинаковую длину");
        }
      }
    }

    this.data = (T[][]) new Object[rows][];
    for (int i = 0; i < rows; i++) {
      this.data[i] = (T[]) new Object[columns];
      System.arraycopy(data[i], 0, this.data[i], 0, columns);
    }
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public T get(int row, int column) {
    if (row < 0 || row >= rows || column < 0 || column >= columns) {
      throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
    }
    return data[row][column];
  }

  public void set(int row, int column, T value) {
    if (row < 0 || row >= rows || column < 0 || column >= columns) {
      throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
    }
    data[row][column] = value;
  }

  @Override
  public Iterator<T> iterator() {
    return new RowMajorIterator();
  }

  public Iterator<T> rowMajorIterator() {
    return new RowMajorIterator();
  }

  public Iterator<T> columnMajorIterator() {
    return new ColumnMajorIterator();
  }

  public Iterator<T> spiralIterator() {
    return new SpiralIterator();
  }

  public Iterator<T> zigzagIterator() {
    return new ZigzagIterator();
  }

  private class RowMajorIterator implements Iterator<T> {

    private int currentRow = 0;
    private int currentColumn = 0;

    @Override
    public boolean hasNext() {
      return currentRow < rows && currentColumn < columns;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      T element = data[currentRow][currentColumn];

      currentColumn++;
      if (currentColumn >= columns) {
        currentRow++;
        currentColumn = 0;
      }

      return element;
    }
  }

  private class ColumnMajorIterator implements Iterator<T> {

    private int currentRow = 0;
    private int currentColumn = 0;

    @Override
    public boolean hasNext() {
      return currentColumn < columns && currentRow < rows;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      T element = data[currentRow][currentColumn];

      currentRow++;
      if (currentRow >= rows) {
        currentColumn++;
        currentRow = 0;
      }

      return element;
    }
  }

  private class SpiralIterator implements Iterator<T> {

    private int currentRow;
    private int currentCol;
    private int direction = 0; // 0=right, 1=down, 2=left, 3=up
    private int stepsToTake = 1;
    private int stepsTaken = 0;
    private int stepChangeCounter = 0;

    public SpiralIterator() {
      currentRow = rows / 2;
      currentCol = columns / 2;
    }

    @Override
    public boolean hasNext() {
      return currentRow >= 0 && currentRow < rows && currentCol >= 0 && currentCol < columns;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      T element = data[currentRow][currentCol];

      // Двигаемся в текущем направлении
      switch (direction) {
        case 0:
          currentCol++;
          break;
        case 1:
          currentRow++;
          break;
        case 2:
          currentCol--;
          break;
        case 3:
          currentRow--;
          break;
      }
      stepsTaken++;

      if (stepsTaken >= stepsToTake) {
        direction = (direction + 1) % 4;
        stepsTaken = 0;
        stepChangeCounter++;

        if (stepChangeCounter % 2 == 0) {
          stepsToTake++;
        }
      }

      return element;
    }
  }

  private class ZigzagIterator implements Iterator<T> {

    private int row = 0;
    private int col = 0;
    private boolean movingRight = true;

    @Override
    public boolean hasNext() {
      return row < rows && col < columns;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      T element = data[row][col];

      if (movingRight) {
        if (col < columns - 1) {
          col++;
        } else {
          row++;
          if (row < rows) {
            movingRight = false;
          }
        }
      } else {
        if (col > 0) {
          col--;
        } else {
          row++;
          if (row < rows) {
            movingRight = true;
          }
        }
      }

      return element;
    }
  }
}