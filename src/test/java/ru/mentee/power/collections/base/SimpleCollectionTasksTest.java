package ru.mentee.power.collections.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SimpleCollectionTasksTest {

  @Test
  void shouldReturnCorrectCountForStringsStartingWithGivenLetter() {
    List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Alex");
    char letter = 'A';
    int result = SimpleCollectionTasks.countStringsStartingWith(names, letter);
    assertThat(result).isEqualTo(3);
  }

  @Test
  void shouldReturnZeroForEmptyList() {
    List<String> emptyList = new ArrayList<>();
    char letter = 'A';
    int result = SimpleCollectionTasks.countStringsStartingWith(emptyList, letter);
    assertThat(result).isEqualTo(0);
  }

  @Test
  void shouldReturnZeroForNullList() {
    List<String> nullList = null;
    char letter = 'A';
    int result = SimpleCollectionTasks.countStringsStartingWith(nullList, letter);
    assertThat(result).isEqualTo(0);
  }

  @Test
  void shouldIgnoreNullAndEmptyStringsInList() {
    List<String> listWithNullsAndEmpties = Arrays.asList("Apple", null, "", "banana", "   ",
        "avocado");
    char letter = 'a';
    int result = SimpleCollectionTasks.countStringsStartingWith(listWithNullsAndEmpties, letter);
    assertThat(result).isEqualTo(2); // "apple" and "avocado" (case-insensitive)
  }

  @Test
  void shouldBeCaseInsensitiveWhenComparingLetters() {
    List<String> names = Arrays.asList("alice", "BOB", "anna", "aLEx");
    char lowerCaseLetter = 'a';
    int resultLowerCase = SimpleCollectionTasks.countStringsStartingWith(names, lowerCaseLetter);
    assertThat(resultLowerCase).isEqualTo(3);

    char upperCaseLetter = 'A';
    int resultUpperCase = SimpleCollectionTasks.countStringsStartingWith(names, upperCaseLetter);
    assertThat(resultUpperCase).isEqualTo(3);
  }

  @Test
  void shouldHandleCustomScenario() {
    // Test with special characters and numbers
    List<String> mixedList = Arrays.asList("123", "@test", "Java", "java", null, "", "python",
        "Python");
    char letter = 'j';
    int result = SimpleCollectionTasks.countStringsStartingWith(mixedList, letter);
    assertThat(result).isEqualTo(2); // "Java" and "java"
  }
}