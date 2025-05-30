package ru.mentee.power.collections.list;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LinkedListAnalyzerTest {

  @Test
  @DisplayName("Метод mergeLists должен корректно объединять списки без дубликатов")
  void shouldMergeListsWithoutDuplicates() {
    LinkedList<Integer> list1 = new LinkedList<>(Arrays.asList(1, 2, 3));
    LinkedList<Integer> list2 = new LinkedList<>(Arrays.asList(3, 4, 5));

    List<Integer> result = LinkedListAnalyzer.mergeListsPreserveOrder(list1, list2);

    assertThat(result)
        .isNotNull()
        .hasSize(5)
        .containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  @DisplayName("Метод mergeLists должен выбросить исключение при null аргументах")
  void shouldThrowExceptionForNullArgumentsInMergeLists() {
    LinkedList<Integer> validList = new LinkedList<>(Arrays.asList(1, 2, 3));

    assertThatThrownBy(() -> LinkedListAnalyzer.mergeListsPreserveOrder(null, validList))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Один из списков list1 или list2 - null");

    assertThatThrownBy(() -> LinkedListAnalyzer.mergeListsPreserveOrder(validList, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Один из списков list1 или list2 - null");

    assertThatThrownBy(() -> LinkedListAnalyzer.mergeListsPreserveOrder(null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Один из списков list1 или list2 - null");
  }

  @Test
  @DisplayName("Метод reverse должен корректно обращать порядок элементов")
  void shouldReverseListCorrectly() {
    LinkedList<Integer> input = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
    List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);

    List<Integer> result = LinkedListAnalyzer.reverse(input);

    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  @DisplayName("Метод reverse должен выбросить исключение при null аргументе")
  void shouldThrowExceptionForNullArgumentInReverse() {
    assertThatThrownBy(() -> LinkedListAnalyzer.reverse(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Список null");
  }

  @Test
  @DisplayName("Метод removeDuplicates должен удалять дубликаты, сохраняя порядок")
  void shouldRemoveDuplicatesCorrectly() {
    LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "a", "c", "b", "d"));
    List<String> expected = Arrays.asList("a", "b", "c", "d");

    List<String> result = LinkedListAnalyzer.removeDuplicates(input);

    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  @DisplayName("Метод removeDuplicates должен выбросить исключение при null аргументе")
  void shouldThrowExceptionForNullArgumentInRemoveDuplicates() {
    assertThatThrownBy(() -> LinkedListAnalyzer.removeDuplicates(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Список null");
  }

  @Test
  @DisplayName("Метод rotate должен корректно сдвигать элементы вправо")
  void shouldRotateElementsToTheRight() {
    LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e"));
    List<String> expected = Arrays.asList("c", "d", "e", "a", "b");

    List<String> result = LinkedListAnalyzer.rotate(input, 3);

    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  @DisplayName("Метод rotate должен корректно сдвигать элементы влево")
  void shouldRotateElementsToTheLeft() {
    LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e"));
    List<String> expected = Arrays.asList("c", "d", "e", "a", "b");

    List<String> result = LinkedListAnalyzer.rotate(input, -2);

    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  @DisplayName("Метод rotate должен выбросить исключение при null аргументе")
  void shouldThrowExceptionForNullArgumentInRotate() {
    assertThatThrownBy(() -> LinkedListAnalyzer.rotate(null, 1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Список null");
  }

  @Test
  @DisplayName("Метод rotate должен корректно обрабатывать сдвиг на размер списка")
  void shouldHandleFullRotation() {
    LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "c"));
    List<String> expected = Arrays.asList("a", "b", "c");

    List<String> result = LinkedListAnalyzer.rotate(input, 3);

    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  @DisplayName("Метод rotate должен корректно обрабатывать пустой список")
  void shouldHandleEmptyListRotation() {
    LinkedList<String> input = new LinkedList<>();

    List<String> result = LinkedListAnalyzer.rotate(input, 5);

    assertThat(result).isEmpty();
  }
}