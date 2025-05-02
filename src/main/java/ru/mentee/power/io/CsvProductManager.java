package ru.mentee.power.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Product {

  private int id;
  private String name;
  private double price;
  private int quantity;

  public Product(int id, String name, double price, int quantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  @Override
  public String toString() { // Пример
    return "Product{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", quantity="
        + quantity + '}';
  }
}

public class CsvProductManager {

  protected static final String CSV_HEADER = "ID;Name;Price;Quantity";
  private static final int EXPECTED_FIELDS = 4;

  public static void saveProductsToCsv(List<Product> products, String filename, String delimiter) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      writer.write(CSV_HEADER);
      writer.newLine();

      for (Product product : products) {
        String line = String.join(delimiter, String.valueOf(product.getId()), product.getName(),
            String.valueOf(product.getPrice()), String.valueOf(product.getQuantity()));
        writer.write(line);
        writer.newLine();
      }
      System.out.println("Продукты сохранены в " + filename);
    } catch (IOException e) {
      System.err.println("Ошибка при сохранении в CSV: " + e.getMessage());
    }
  }

  public static List<Product> loadProductsFromCsv(String filename, String delimiter) {
    List<Product> products = new ArrayList<>();
    Path filePath = Paths.get(filename);

    if (!Files.exists(filePath)) { // Используем Files.exists для проверки
      System.err.println("Файл не найден: " + filename);
      return products;
    }

    try {
      String fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
      products = loadProductsFromString(fileContent, delimiter);
    } catch (IOException e) {
      System.err.println("Ошибка при чтении файла CSV: " + filename + " -> " + e.getMessage());
    }
    return products;
  }

  public static List<Product> loadProductsFromString(String csvData, String delimiter) {
    List<Product> products = new ArrayList<>();
    if (csvData == null || csvData.isEmpty()) {
      return products;
    }

    String[] lines = csvData.split("\\R");
    if (lines.length <= 1) {
      System.err.println("Ошибка: CSV данные не содержат строк данных после заголовка.");
      return products;
    }

    for (int i = 1; i < lines.length; i++) {
      Product product = parseProductFromCsvLine(lines[i], delimiter, i + 1);
      if (product != null) {
        products.add(product);
      }
    }
    System.out.println("Продукты загружены из строки.");
    return products;
  }

  public static Product parseProductFromCsvLine(String line, String delimiter, int lineNumber) {
    if (line == null || line.trim().isEmpty()) {
      return null;
    }

    String[] parts = line.split(delimiter);
    if (parts.length != EXPECTED_FIELDS) {
      System.err.println(
          "Ошибка в строке " + lineNumber + ": ожидалось " + EXPECTED_FIELDS + " полей, получено "
              + parts.length);
      return null;
    }

    try {
      int id = Integer.parseInt(parts[0].trim());
      String name = parts[1].trim();
      double price = Double.parseDouble(parts[2].trim());
      int quantity = Integer.parseInt(parts[3].trim());
      return new Product(id, name, price, quantity);
    } catch (NumberFormatException e) {
      System.err.println("Ошибка парсинга числа в строке " + lineNumber + ": " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Ошибка обработки строки " + lineNumber + ": " + e.getMessage());
    }
    return null;
  }


  public static void main(String[] args) {
    String filename = "products.csv";
    String delimiter = ";";

    // 1. Создать тестовые данные
    List<Product> initialProducts = new ArrayList<>();
    initialProducts.add(new Product(101, "Ноутбук Альфа", 75000.50, 15));
    initialProducts.add(new Product(102, "Мышь Гамма", 1200.00, 55));
    initialProducts.add(new Product(103, "Клавиатура Омега", 3500.75, 30));

    // 2. Сохранить в CSV
    System.out.println("Сохранение продуктов...");
    saveProductsToCsv(initialProducts, filename, delimiter);

    // 3. Загрузить из CSV (из файла)
    System.out.println("\nЗагрузка продуктов из файла...");
    List<Product> loadedFromFile = loadProductsFromCsv(filename, delimiter);
    System.out.println("Загружено из файла:");
    loadedFromFile.forEach(System.out::println);

    // 4. Загрузить из строки (демонстрация)
    System.out.println("\nЗагрузка продуктов из строки...");
    String csvDataString = CSV_HEADER + "\n" + "201;Монитор;25000;5\n" + "202;Вебкамера;4500;25";
    List<Product> loadedFromString = loadProductsFromString(csvDataString, delimiter);
    System.out.println("Загружено из строки:");
    loadedFromString.forEach(System.out::println);

    // 5. Пример с ошибками
    System.out.println("\nЗагрузка продуктов из строки с ошибками...");
    String csvDataWithError =
        CSV_HEADER + "\n" + "301;Correct;10.0;1\n" + "invalid line\n" + "303;ErrorPrice;abc;5\n"
            + "304;CorrectToo;20.5;10";
    List<Product> loadedWithError = loadProductsFromString(csvDataWithError, delimiter);
    System.out.println("Загружено из строки с ошибками (только корректные): ");
    loadedWithError.forEach(System.out::println);

    new File(filename).delete();
  }
}