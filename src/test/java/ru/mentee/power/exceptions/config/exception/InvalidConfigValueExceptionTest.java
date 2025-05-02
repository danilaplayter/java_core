package ru.mentee.power.exceptions.config.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidConfigValueExceptionTest {

  @Test
  @DisplayName("Должен создать исключение с сообщением, ключом и значением")
  void shouldCreateExceptionWithMessageKeyAndValue() {
    // Arrange
    String expectedKey = "test.key";
    String expectedValue = "invalid_value";
    String expectedMessage = "Invalid config value for key: " + expectedKey;

    // Act
    InvalidConfigValueException exception =
        new InvalidConfigValueException(expectedKey, expectedValue);

    // Assert
    assertThat(exception)
        .isInstanceOf(ConfigException.class)
        .hasMessage(expectedMessage);
    assertThat(exception.getKey()).isEqualTo(expectedKey);
    assertThat(exception.getValue()).isEqualTo(expectedValue);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  @DisplayName("Должен создать исключение с сообщением, ключом, значением и причиной")
  void shouldCreateExceptionWithMessageKeyValueAndCause() {
    // Arrange
    String expectedKey = "another.key";
    String expectedValue = "bad_value";
    Throwable expectedCause = new NumberFormatException("Not a number");
    String expectedMessage = String.format(
        "Значение ключа '%s' недопустимо: '%s'",
        expectedKey, expectedValue
    );

    // Act
    InvalidConfigValueException exception =
        new InvalidConfigValueException(expectedKey, expectedValue, expectedCause);

    // Assert
    assertThat(exception)
        .isInstanceOf(ConfigException.class)
        .hasMessage(expectedMessage)
        .hasCause(expectedCause);
    assertThat(exception.getKey()).isEqualTo(expectedKey);
    assertThat(exception.getValue()).isEqualTo(expectedValue);
  }

  @Test
  @DisplayName("Должен вернуть ключ, для которого значение некорректно")
  void shouldReturnKey() {
    // Arrange
    String expectedKey = "config.key";
    String expectedValue = "wrong_value";

    // Act
    InvalidConfigValueException exception =
        new InvalidConfigValueException(expectedKey, expectedValue);

    // Assert
    assertThat(exception.getKey()).isEqualTo(expectedKey);
  }

  @Test
  @DisplayName("Должен вернуть некорректное значение")
  void shouldReturnInvalidValue() {
    // Arrange
    String expectedKey = "timeout.key";
    String expectedValue = "not_a_number";

    // Act
    InvalidConfigValueException exception =
        new InvalidConfigValueException(expectedKey, expectedValue);

    // Assert
    assertThat(exception.getValue()).isEqualTo(expectedValue);
  }
}