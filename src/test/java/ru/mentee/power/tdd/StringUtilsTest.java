package ru.mentee.power.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты для утилиты работы со строками")
class StringUtilsTest {

  private StringUtils stringUtils; // Тестируемый объект

  @BeforeEach
  void setUp() {
    stringUtils = new StringUtils();
  }

  @Test
  @DisplayName("Переворот обычной строки")
  void shouldReverseNormalString() {
    // Arrange
    String original = "hello";
    String expected = "olleh";

    String actual = stringUtils.reverse(original);

    assertThat(actual).isEqualTo(expected);
  }

  //Проверил основной случай без каких либо исключений - работает

  @Test
  @DisplayName("Переворот пустой строки")
  void shouldReturnEmptyStringWhenInputIsEmpty() {
    String original = "";
    String expected = "";

    String actual = stringUtils.reverse(original);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }

  //Проверил, что если перевернуть пустую строку, работает, возвращает пустую строку


  @Test
  @DisplayName("Возврат null при null на входе")
  void shouldReturnNullWhenInputIsNull() {
    // Arrange
    String original = null;

    String actual = stringUtils.reverse(original);

    assertThat(actual).isNull();
  }

  //Проверил, что если перевернуть null, работает, возвращает null


  @Test
  @DisplayName("Переворот строки с одним символом")
  void shouldReturnSameStringWhenSingleCharacter() {
    // Arrange
    String original = "a";
    String expected = "a";

    String actual = stringUtils.reverse(original);

    assertThat(actual).isEqualTo(expected);
  }

  //Проверил, что если перевернуть один символ, работает, возвращает то же символ


  @Test
  @DisplayName("Переворот строки-палиндрома")
  void shouldReturnSameStringForPalindrome() {
    // Arrange
    String original = "madam";
    String expected = "madam";

    String actual = stringUtils.reverse(original);
    assertThat(actual).isEqualTo(expected);
  }

  //Проверил, что если перевернуть палиндром, работает, возвращает себя же

  @Test
  @DisplayName("Переворот строки с пробелами")
  void shouldReturnReversedStringWithSpaces() {
    String original = "1 234 5 6789";
    String expected = "9876 5 432 1";

    String actual = stringUtils.reverse(original);
    assertThat(actual).isEqualTo(expected);
  }

  //Проверил, что если перевернуть строку с пробелами, работает, возвращает строку с пробелами в нужных местах


}