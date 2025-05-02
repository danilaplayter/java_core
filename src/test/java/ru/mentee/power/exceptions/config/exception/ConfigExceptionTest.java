package ru.mentee.power.exceptions.config.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConfigExceptionTest {

  @Test
  @DisplayName("Должен создать исключение с сообщением")
  void shouldCreateExceptionWithMessage() {

    String expectedMessage = "Текст для исключения";

    ConfigException exception = new ConfigException(expectedMessage);

    assertThat(exception)
        .isInstanceOf(Exception.class)
        .hasMessage(expectedMessage);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  @DisplayName("Должен создать исключение с сообщением и причиной")
  void shouldCreateExceptionWithMessageAndCause() {

    String expectedMessage = "Тестовое исключение с указанием причины";
    Throwable expectedCause = new RuntimeException("Root cause");

    ConfigException exception = new ConfigException(expectedMessage, expectedCause);

    assertThat(exception)
        .isInstanceOf(Exception.class)
        .hasMessage(expectedMessage)
        .hasCause(expectedCause);
  }
}