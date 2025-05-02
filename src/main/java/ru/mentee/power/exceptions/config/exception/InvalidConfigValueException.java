package ru.mentee.power.exceptions.config.exception;

/**
 * Исключение, выбрасываемое при недопустимом формате значения в конфигурации.
 */
public class InvalidConfigValueException extends ConfigException {

  private final String key;
  private final String value;

  public InvalidConfigValueException(String key, String value) {
    super("Invalid config value for key: " + key);
    this.key = key;
    this.value = value;
  }

  public InvalidConfigValueException(String key, String value, Throwable cause) {
    super(String.format("Значение ключа '%s' недопустимо: '%s'", key, value), cause);
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}