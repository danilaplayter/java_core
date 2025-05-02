package ru.mentee.power.collections.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfigParserTest {

  // ... существующие тесты ...

  @Test
  @DisplayName("Метод removeKey должен возвращать false, если ключ не существовал")
  void shouldReturnFalseIfKeyDidNotExist() {
    ConfigParser parser = new ConfigParser();
    assertFalse(parser.removeKey("non-existent"));
    assertEquals(0, parser.size());
  }

  @Test
  @DisplayName("Метод containsKey должен корректно проверять наличие ключа")
  void shouldCheckIfKeyExists() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("host", "localhost");

    assertTrue(parser.containsKey("host"));
    assertFalse(parser.containsKey("non-existent"));

    assertThatThrownBy(() -> parser.containsKey(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Метод getKeys должен возвращать все ключи")
  void shouldReturnAllKeys() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("host", "localhost");
    parser.setValue("port", "8080");

    Set<String> keys = parser.getKeys();

    assertThat(keys)
        .hasSize(2)
        .containsExactlyInAnyOrder("host", "port");
  }

  @Test
  @DisplayName("Метод getAll должен возвращать копию всех пар ключ-значение")
  void shouldReturnCopyOfAllEntries() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("host", "localhost");
    parser.setValue("port", "8080");

    Map<String, String> allEntries = parser.getAll();

    assertThat(allEntries)
        .hasSize(2)
        .containsEntry("host", "localhost")
        .containsEntry("port", "8080");

    // Проверяем, что возвращается копия
    allEntries.put("new-key", "value");
    assertThat(parser.getAll()).hasSize(2);
  }

  @Test
  @DisplayName("Метод getIntValue должен корректно парсить целые числа")
  void shouldParseIntegerValues() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("port", "8080");
    parser.setValue("timeout", "30");

    assertThat(parser.getIntValue("port")).isEqualTo(8080);
    assertThat(parser.getIntValue("timeout")).isEqualTo(30);
  }

  @Test
  @DisplayName("Метод getIntValue должен выбрасывать исключение, если значение не является числом")
  void shouldThrowExceptionIfValueIsNotANumber() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("invalid", "not-a-number");

    assertThatThrownBy(() -> parser.getIntValue("invalid"))
        .isInstanceOf(NumberFormatException.class);

    assertThatThrownBy(() -> parser.getIntValue("non-existent"))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @DisplayName("Метод getBooleanValue должен корректно распознавать логические значения")
  void shouldParseBooleanValues() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("true1", "true");
    parser.setValue("true2", "yes");
    parser.setValue("true3", "1");
    parser.setValue("false1", "false");
    parser.setValue("false2", "no");
    parser.setValue("false3", "0");
    parser.setValue("false4", "anything");

    assertThat(parser.getBooleanValue("true1")).isTrue();
    assertThat(parser.getBooleanValue("true2")).isTrue();
    assertThat(parser.getBooleanValue("true3")).isTrue();
    assertThat(parser.getBooleanValue("false1")).isFalse();
    assertThat(parser.getBooleanValue("false2")).isFalse();
    assertThat(parser.getBooleanValue("false3")).isFalse();
    assertThat(parser.getBooleanValue("false4")).isFalse();

    assertThatThrownBy(() -> parser.getBooleanValue("non-existent"))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @DisplayName("Метод getListValue должен возвращать пустой список для несуществующего ключа")
  void shouldReturnEmptyListForNonExistentKey() {
    ConfigParser parser = new ConfigParser();
    List<String> result = parser.getListValue("non-existent");

    assertThat(result)
        .isEmpty();
  }

  @Test
  @DisplayName("Метод parseConfig должен выбрасывать исключение при null аргументе")
  void shouldThrowExceptionWhenConfigStringIsNull() {
    ConfigParser parser = new ConfigParser();

    assertThatThrownBy(() -> parser.parseConfig(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Строка конфигурации - null");
  }

  @Test
  @DisplayName("Метод toConfigString должен корректно преобразовывать конфигурацию в строку")
  void shouldConvertConfigToString() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("host", "localhost");
    parser.setValue("port", "8080");

    String result = parser.toConfigString();

    assertThat(result)
        .contains("host=localhost")
        .contains("port=8080")
        .doesNotContain("#");

    // Проверяем формат строки
    String[] lines = result.split("\n");
    assertThat(lines).hasSize(2);
    assertThat(lines[0]).matches("^\\w+=.+$");
  }

  @Test
  @DisplayName("Метод clear должен удалять все пары ключ-значение")
  void shouldClearAllEntries() {
    ConfigParser parser = new ConfigParser();
    parser.setValue("host", "localhost");
    parser.setValue("port", "8080");

    assertEquals(2, parser.size());
    parser.clear();

    assertEquals(0, parser.size());
    assertThat(parser.getKeys()).isEmpty();
  }

  @Test
  @DisplayName("Метод size должен возвращать правильное количество пар")
  void shouldReturnCorrectSize() {
    ConfigParser parser = new ConfigParser();
    assertEquals(0, parser.size());

    parser.setValue("host", "localhost");
    assertEquals(1, parser.size());

    parser.setValue("port", "8080");
    assertEquals(2, parser.size());

    parser.removeKey("host");
    assertEquals(1, parser.size());

    parser.clear();
    assertEquals(0, parser.size());
  }
}