package ru.mentee.power.exceptions.config.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MissingConfigKeyExceptionTest {

  @Test
  @DisplayName("Должен создать исключение с сообщением и ключом")
  void shouldCreateExceptionWithMessageAndKey() {
    // Arrange
    String testKey = "test.config.key";
    String expectedMessage = "Обязательный ключ конфигурации отсутствует: " + testKey;

    // Act
    MissingConfigKeyException exception = new MissingConfigKeyException(testKey);

    // Assert
    assertThat(exception)
        .isInstanceOf(ConfigException.class)
        .hasMessage(expectedMessage);
    assertThat(exception.getKey()).isEqualTo(testKey);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  @DisplayName("Должен создать исключение с сообщением, ключом и причиной")
  void shouldCreateExceptionWithMessageKeyAndCause() {
    // Arrange
    String testKey = "db.connection.url";
    Throwable testCause = new RuntimeException("Config file not readable");
    String expectedMessage = "Обязательный ключ конфигурации отсутствует: " + testKey;

    // Act
    MissingConfigKeyException exception = new MissingConfigKeyException(testKey, testCause);

    // Assert
    assertThat(exception)
        .isInstanceOf(ConfigException.class)
        .hasMessage(expectedMessage)
        .hasCause(testCause);
    assertThat(exception.getKey()).isEqualTo(testKey);
  }

  @Test
  @DisplayName("Должен вернуть ключ, который отсутствует в конфигурации")
  void shouldReturnMissingKey() {
    // Arrange
    String testKey = "security.api.key";

    // Act
    MissingConfigKeyException exception = new MissingConfigKeyException(testKey);

    // Assert
    assertThat(exception.getKey())
        .isEqualTo(testKey)
        .isNotBlank();
  }
}