package ru.mentee.power.exceptions.config.exception;

public class MissingConfigKeyException extends ConfigException {

  private final String key;

  public MissingConfigKeyException(String key) {
    super("Обязательный ключ конфигурации отсутствует: " + key);
    this.key = key;
  }


  public MissingConfigKeyException(String key, Throwable cause) {
    super("Обязательный ключ конфигурации отсутствует: " + key, cause);
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}