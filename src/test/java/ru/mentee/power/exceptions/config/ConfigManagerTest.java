package ru.mentee.power.exceptions.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;

class ConfigManagerTest {

  private ConfigManager configManager;
  private Map<String, String> testConfig;

  @BeforeEach
  void setUp() {
    testConfig = new HashMap<>();
    testConfig.put("string.key", "testValue");
    testConfig.put("int.key", "42");
    testConfig.put("boolean.true.key", "true");
    testConfig.put("boolean.false.key", "false");

    configManager = new ConfigManager(testConfig);
  }

  @Test
  @DisplayName("Должен успешно получить строковое значение по существующему ключу")
  void shouldGetStringValueWhenKeyExists() throws MissingConfigKeyException {
    String value = configManager.getRequiredValue("string.key");
    assertThat(value).isEqualTo("testValue");
  }

  @Test
  @DisplayName("Должен выбросить MissingConfigKeyException при запросе несуществующего ключа")
  void shouldThrowMissingConfigKeyExceptionWhenKeyDoesNotExist() {
    assertThatThrownBy(() -> configManager.getRequiredValue("nonexistent.key"))
        .isInstanceOf(MissingConfigKeyException.class)
        .hasMessageContaining("nonexistent.key");
  }

  @Test
  @DisplayName("Должен успешно получить целочисленное значение по существующему ключу")
  void shouldGetIntValueWhenKeyExists()
      throws MissingConfigKeyException, InvalidConfigValueException {
    int value = configManager.getRequiredIntValue("int.key");
    assertThat(value).isEqualTo(42);
  }

  @Test
  @DisplayName("Должен успешно получить булево значение по существующему ключу")
  void shouldGetBooleanValueWhenKeyExists()
      throws MissingConfigKeyException, InvalidConfigValueException {
    boolean trueValue = configManager.getRequiredBooleanValue("boolean.true.key");
    assertThat(trueValue).isTrue();

    boolean falseValue = configManager.getRequiredBooleanValue("boolean.false.key");
    assertThat(falseValue).isFalse();
  }

  @Test
  @DisplayName("Должен выбросить InvalidConfigValueException при запросе некорректного булева значения")
  void shouldThrowInvalidConfigValueExceptionWhenBooleanValueIsInvalid() {
    testConfig.put("invalid.boolean.key", "yes");
    ConfigManager manager = new ConfigManager(testConfig);

    assertThatThrownBy(() -> manager.getRequiredBooleanValue("invalid.boolean.key"))
        .isInstanceOf(InvalidConfigValueException.class);
  }

  @Test
  @DisplayName("Должен успешно добавить новое значение в конфигурацию")
  void shouldAddNewValueToConfig() {
    String newKey = "new.key";
    String newValue = "newValue";

    configManager.setValue(newKey, newValue);

    assertThat(configManager.getConfig()).containsEntry(newKey, newValue);
  }

  @Test
  @DisplayName("Должен выбросить InvalidConfigValueException при запросе некорректного целочисленного значения")
  void shouldThrowInvalidConfigValueExceptionWhenIntValueIsInvalid() {
    testConfig.put("invalid.int.key", "not_a_number");
    ConfigManager manager = new ConfigManager(testConfig);

    assertThatThrownBy(() -> manager.getRequiredIntValue("invalid.int.key"))
        .isInstanceOf(InvalidConfigValueException.class);
  }

  @Test
  @DisplayName("Должен успешно обновить существующее значение в конфигурации")
  void shouldUpdateExistingValueInConfig() {
    String existingKey = "string.key";
    String newValue = "updatedValue";

    configManager.setValue(existingKey, newValue);

    assertThat(configManager.getConfig()).containsEntry(existingKey, newValue);
  }
}