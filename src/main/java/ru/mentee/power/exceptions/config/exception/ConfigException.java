package ru.mentee.power.exceptions.config.exception;

public class ConfigException extends Exception {

  public ConfigException() {
  }

  public ConfigException(String message) {
    super(message);
  }

  public ConfigException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigException(Throwable cause) {
    super(cause);
  }

  public ConfigException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}