package ru.mentee.power.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Тесты менеджера продуктов CSV (CsvProductManager)")
class CsvProductManagerTest {

  @TempDir
  Path tempDir;

  private Path testFilePath;
  private final String delimiter = ";";
  private List<Product> sampleProducts;

  @BeforeEach
  void setUp() {
    testFilePath = tempDir.resolve("test_products.csv");
    sampleProducts = new ArrayList<>();
    sampleProducts.add(new Product(1, "Laptop", 999.99, 10));
    sampleProducts.add(new Product(2, "Mouse", 25.50, 50));
    sampleProducts.add(new Product(3, "Keyboard", 75.00, 30));
  }

  @Nested
  @DisplayName("Тесты сохранения/загрузки из файла")
  class FileSaveLoadTests {

    @Test
    @DisplayName("Должен сохранять и загружать список продуктов из файла")
    void shouldSaveAndLoadProductsFromFile() throws IOException {
      // When
      CsvProductManager.saveProductsToCsv(sampleProducts, testFilePath.toString(), delimiter);
      List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(),
          delimiter);

      // Then
      List<String> lines = Files.readAllLines(testFilePath);
      assertThat(lines)
          .hasSize(4) // header + 3 products
          .first()
          .isEqualTo("ID;Name;Price;Quantity"); // Проверяем заголовок

      assertThat(loadedProducts)
          .isNotNull()
          .hasSize(3)
          .usingRecursiveComparison()
          .isEqualTo(sampleProducts);
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пустой список при сохранении/загрузке из файла")
    void shouldHandleEmptyListFile() throws IOException {
      // Given
      List<Product> emptyList = new ArrayList<>();

      // When
      CsvProductManager.saveProductsToCsv(emptyList, testFilePath.toString(), delimiter);
      List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(),
          delimiter);

      // Then
      List<String> lines = Files.readAllLines(testFilePath);
      assertThat(lines)
          .hasSize(1) // только заголовок
          .first()
          .isEqualTo("ID;Name;Price;Quantity");

      assertThat(loadedProducts).isEmpty();
    }

    @Test
    @DisplayName("loadProductsFromCsv должен возвращать пустой список, если файл не найден")
    void loadFromFileShouldReturnEmptyListWhenFileNotExists() {
      // Given
      assertThat(testFilePath).doesNotExist();

      // When
      List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(),
          delimiter);

      // Then
      assertThat(loadedProducts).isEmpty();
    }

    @Test
    @DisplayName("loadProductsFromCsv должен пропускать некорректные строки")
    void loadFromFileShouldSkipMalformedLines() throws IOException {
      // Given
      String csvContent = CsvProductManager.CSV_HEADER + "\n" +
          "1;ValidProduct;10.0;5\n" +
          "invalid line data\n" +
          "3;AnotherProduct;twenty;15\n" +
          "4;LastProduct;50.0;2";
      Files.writeString(testFilePath, csvContent);

      // When
      List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(),
          delimiter);

      // Then
      assertThat(loadedProducts)
          .hasSize(2)
          .extracting(Product::getId)
          .containsExactly(1, 4);
    }
  }

  @Nested
  @DisplayName("Тесты загрузки из строки")
  class StringLoadTests {

    @Test
    @DisplayName("loadProductsFromString должен загружать корректные данные")
    void loadFromStringShouldLoadCorrectData() {
      // Given
      String csvData = CsvProductManager.CSV_HEADER + "\n" +
          "10;ProductA;1.1;11\n" +
          "20;ProductB;2.2;22";

      // When
      List<Product> loadedProducts = CsvProductManager.loadProductsFromString(csvData, delimiter);

      // Then
      assertThat(loadedProducts)
          .hasSize(2)
          .extracting(Product::getName)
          .containsExactly("ProductA", "ProductB");
    }

    @Test
    @DisplayName("loadProductsFromString должен пропускать некорректные строки")
    void loadFromStringShouldSkipMalformedLines() {
      // Given
      String csvData = CsvProductManager.CSV_HEADER + "\n" +
          "1;ValidProduct;10.0;5\n" +
          "invalid line data\n" +
          "3;AnotherProduct;twenty;15\n" +
          "4;LastProduct;50.0;2";

      // When
      List<Product> loadedProducts = CsvProductManager.loadProductsFromString(csvData, delimiter);

      // Then
      assertThat(loadedProducts)
          .hasSize(2)
          .extracting(Product::getId)
          .containsExactly(1, 4);
    }

    @Test
    @DisplayName("loadProductsFromString должен возвращать пустой список для пустой строки или строки с одним заголовком")
    void loadFromStringShouldReturnEmptyForEmptyOrHeaderOnly() {
      assertThat(CsvProductManager.loadProductsFromString(null, delimiter)).isEmpty();
      assertThat(CsvProductManager.loadProductsFromString("", delimiter)).isEmpty();
      assertThat(CsvProductManager.loadProductsFromString(CsvProductManager.CSV_HEADER,
          delimiter)).isEmpty();
    }
  }

  @Nested
  @DisplayName("Тесты парсинга одной строки CSV (parseProductFromCsvLine)")
  class LineParsingTests {

    @Test
    @DisplayName("Должен корректно парсить валидную строку")
    void shouldParseValidLine() {
      // Given
      String validLine = "10;Test Product;19.99;5";

      // When
      Product product = CsvProductManager.parseProductFromCsvLine(validLine, delimiter, 1);

      // Then
      assertThat(product)
          .isNotNull()
          .extracting(
              Product::getId,
              Product::getName,
              Product::getPrice,
              Product::getQuantity
          )
          .containsExactly(10, "Test Product", 19.99, 5);
    }

    @Test
    @DisplayName("Должен возвращать null для строки с неверным количеством полей")
    void shouldReturnNullForIncorrectFieldCount() {
      // Given
      String invalidLine = "10;Test Product;19.99";

      // When
      Product product = CsvProductManager.parseProductFromCsvLine(invalidLine, delimiter, 1);

      // Then
      assertThat(product).isNull();
    }

    @Test
    @DisplayName("Должен возвращать null при ошибке парсинга числа")
    void shouldReturnNullOnNumberFormatException() {
      // Given
      String invalidLine = "10;Test Product;nineteen ninety nine;5";

      // When
      Product product = CsvProductManager.parseProductFromCsvLine(invalidLine, delimiter, 1);

      // Then
      assertThat(product).isNull();
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пробелы по краям полей")
    void shouldHandleLeadingTrailingSpaces() {
      // Given
      String lineWithSpaces = " 20 ; Spaced Product ; 15.50 ; 100 ";

      // When
      Product product = CsvProductManager.parseProductFromCsvLine(lineWithSpaces, delimiter, 1);

      // Then
      assertThat(product)
          .isNotNull()
          .extracting(
              Product::getId,
              Product::getName,
              Product::getPrice,
              Product::getQuantity
          )
          .containsExactly(20, "Spaced Product", 15.50, 100);
    }

    @Test
    @DisplayName("Должен возвращать null для пустой или null строки")
    void shouldReturnNullForNullOrEmptyLine() {
      assertThat(CsvProductManager.parseProductFromCsvLine(null, delimiter, 1)).isNull();
      assertThat(CsvProductManager.parseProductFromCsvLine("", delimiter, 1)).isNull();
      assertThat(CsvProductManager.parseProductFromCsvLine("   ", delimiter, 1)).isNull();
    }
  }
}