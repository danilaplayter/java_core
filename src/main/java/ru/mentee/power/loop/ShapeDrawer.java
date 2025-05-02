package ru.mentee.power.loop;

public class ShapeDrawer {

  private final String ERROR = "Ошибка";

  public String drawSquare(int size) {
    if (size <= 0) {
      return ERROR;
    }
    StringBuilder squere = new StringBuilder();

    for (int i = 0; i < size; i++) {
      squere.append("*".repeat(size));
      squere.append('\n');
    }
    return squere.toString();
  }

  public String drawEmptySquare(int size) {
    if (size <= 0) {
      return ERROR;
    }
    StringBuilder square = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
          square.append("*");
        } else {
          square.append(" ");
        }
      }
      square.append("\n");
    }
    return square.toString();
  }

  public String drawTriangle(int height) {
    if (height <= 0) {
      return ERROR;
    }
    StringBuilder triangle = new StringBuilder();
    for (int i = 0; i < height; i++) {
      triangle.append("*".repeat(i + 1)).append("\n");
    }
    return triangle.toString();
  }

  public String drawRhombus(int size) {
    if (size <= 0) {
      return ERROR;
    }
    if (size % 2 == 0) {
      size++;
    }

    StringBuilder rhombus = new StringBuilder();
    int half = size / 2;

    for (int i = 0; i <= half; i++) {
      rhombus.append(" ".repeat(half - i));
      rhombus.append("*".repeat(2 * i + 1));
      rhombus.append("\n");
    }

    for (int i = half - 1; i >= 0; i--) {
      rhombus.append(" ".repeat(half - i));
      rhombus.append("*".repeat(2 * i + 1));
      rhombus.append("\n");
    }

    return rhombus.toString();
  }

  public void printShape(String shape) {
    System.out.println(shape);
  }

  public static void main(String[] args) {
    ShapeDrawer drawer = new ShapeDrawer();

    System.out.println("Квадрат 5x5:");
    drawer.printShape(drawer.drawSquare(5));

    System.out.println("\nПустой квадрат 5x5:");
    drawer.printShape(drawer.drawEmptySquare(5));

    System.out.println("\nТреугольник высотой 5:");
    drawer.printShape(drawer.drawTriangle(5));

    System.out.println("\nРомб размером 5:");
    drawer.printShape(drawer.drawRhombus(2));
  }
}