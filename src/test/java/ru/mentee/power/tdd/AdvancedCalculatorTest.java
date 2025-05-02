package ru.mentee.power.tdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты для AdvancedCalculator")
class AdvancedCalculatorTest {

  private AdvancedCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new AdvancedCalculator();
  }

  @Test
  @DisplayName("Суммирование обычных чисел")
  void shouldSumNormalNumbers() {
    // Arrange
    List<Integer> numbers = List.of(1, 2, 3);
    int expectedSum = 6;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum); // Этот тест должен проходить
  }

  @Test
  @DisplayName("Игнорирование чисел > 1000")
  void shouldIgnoreNumbersGreaterThan1000() {
    // Arrange
    List<Integer> numbers = List.of(2, 1001, 5, 2000);
    int expectedSum = 7;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum); // Этот тест тоже должен проходить
  }

  @Test
  @DisplayName("Обработка пустого списка")
  void shouldReturnZeroForEmptyList() {
    // Arrange
    List<Integer> numbers = Collections.emptyList(); // Используем Collections.emptyList()
    int expectedSum = 0;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum); // И этот
  }

  // --- Тесты с ошибками и граничные случаи --- //

  @Test
  @DisplayName("Граничный случай: Обработка null списка")
//В AdvancedCalculator есть проверка на пустую коллекцию
  void shouldReturnZeroForNullList() {
    // Arrange
    List<Integer> numbers = null;
    int expectedSum = 0;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Число ровно 1000")
  void shouldIncludeNumberExactly1000() {
    // Arrange
    List<Integer> numbers = List.of(5, 1000, 10);
    int expectedSum = 1015; // Ожидаем, что 1000 будет включено
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Список содержит null элементы")
//смотреть коммент в AdvancedCalculator
  void shouldHandleNullElementsInList() {
    // Arrange
    List<Integer> numbers = new ArrayList<>(); // Нужен изменяемый список
    numbers.add(10);
    numbers.add(null); // Добавляем null
    numbers.add(20);
    int expectedSum = 30; // Ожидаем, что null будет проигнорирован

    // Act & Assert
    assertThatCode(() -> calculator.sumIgnoringOver1000(numbers))
        .doesNotThrowAnyException(); // Проверяем, что не падает NPE

    int actualSum = calculator.sumIgnoringOver1000(numbers);
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Тест с отрицательными числами")
  void shouldHandleNegativeNumbers() {
    // Arrange
    List<Integer> numbers = List.of(-5, 10, -2, 1005);
    int expectedSum = 3; // -5 + 10 - 2 = 3 (1005 игнорируется)
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Все числа больше 1000")
  void shouldReturnZeroWhenAllNumbersOver1000() {
    // Arrange
    List<Integer> numbers = List.of(1001, 2000, 3000);
    int expectedSum = 0;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Список содержит только null элементы")
  void shouldHandleAllNullElements() {
    // Arrange
    List<Integer> numbers = new ArrayList<>();
    numbers.add(null);
    numbers.add(null);
    int expectedSum = 0;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Сумма ровно 1000 (должна учитываться)")
  void shouldIncludeExactly1000() {
    // Arrange
    List<Integer> numbers = List.of(1000);
    int expectedSum = 1000;
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Сумма чуть больше 1000 (1001, должна игнорироваться)")
  void shouldIgnoreJustOver1000() {
    // Arrange
    List<Integer> numbers = List.of(1000, 1, 1001);
    int expectedSum = 1001; // 1000 + 1 (1001 игнорируется)
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Большая сумма (5000, должна игнорироваться)")
  void shouldIgnoreLargeAmount5000() {
    // Arrange
    List<Integer> numbers = List.of(5000, 100, 200);
    int expectedSum = 300; // 100 + 200 (5000 игнорируется)
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Сумма чуть больше 5000 (5001, должна игнорироваться)")
  void shouldIgnoreJustOver5000() {
    // Arrange
    List<Integer> numbers = List.of(5001, 500, 501);
    int expectedSum = 1001; // 500 + 501 (5001 игнорируется)
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Несколько чисел на границе 1000")
  void shouldHandleMultipleBorderlineCases() {
    // Arrange
    List<Integer> numbers = List.of(999, 1000, 1001, 998, 1002);
    int expectedSum = 999 + 1000 + 998; // 2997
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }

  @Test
  @DisplayName("Граничный случай: Комбинация разных граничных значений")
  void shouldHandleCombinationOfBorderlineValues() {
    // Arrange
    List<Integer> numbers = List.of(1000, 1001, 5000, 5001, 999, 1);
    int expectedSum = 1000 + 999 + 1; // 2000
    // Act
    int actualSum = calculator.sumIgnoringOver1000(numbers);
    // Assert
    assertThat(actualSum).isEqualTo(expectedSum);
  }
}