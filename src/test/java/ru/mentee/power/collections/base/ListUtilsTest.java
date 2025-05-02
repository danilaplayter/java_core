package ru.mentee.power.collections.base;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListUtilsTest {

  @Test
  void shouldMergeTwoListsAndRemoveDuplicates() {
    // Arrange
    List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");
    List<String> list2 = Arrays.asList("Banana", "Cherry", "Date");

    // Act
    List<String> result = ListUtils.mergeLists(list1, list2);

    // Assert
    assertThat(result).hasSize(4).containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");
  }

  @Test
  void shouldReturnFirstListElementsWhenSecondListIsEmpty() {

    List<String> list1 = new ArrayList<>();
    List<String> list2 = Arrays.asList("Apple", "Banana", "Cherry");

    List<String> result = ListUtils.mergeLists(list1, list2);

    assertThat(result).hasSize(3).containsExactlyInAnyOrder("Apple", "Banana", "Cherry");

  }

  @Test
  void shouldReturnEmptyListWhenBothListsAreEmpty() {

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();

    List<String> result = ListUtils.mergeLists(list1, list2);

    assertThat(result).hasSize(0).isEmpty();

  }

  @Test
  void shouldReturnSecondListWhenFirstListIsNull() {

    List<String> list1 = Arrays.asList("Apple", "Banana", "Cherry");

    List<String> result = ListUtils.mergeLists(list1, null);

    assertThat(result).hasSize(3).containsExactlyInAnyOrder("Apple", "Banana", "Cherry");
  }

  @Test
  void shouldReturnEmptyListWhenBothListsAreNull() {

    assertThat(ListUtils.mergeLists(null, null)).isNull();

  }

  @Test
  void shouldIgnoreNullElementsWhenMergingLists() {

    List<String> list1 = Arrays.asList("Apple", "Banana", null, "Cherry");
    List<String> list2 = Arrays.asList("Banana", "Cherry", null, null, "Date");
    // Act
    List<String> result = ListUtils.mergeLists(list1, list2);

    assertThat(result).hasSize(4).containsExactlyInAnyOrder("Apple", "Banana", "Cherry", "Date");

  }

  @Test
  void shouldHandleCustomScenarioForMergeLists() {
    List<String> identical = Arrays.asList("java", "kotlin");
    assertEquals(identical, ListUtils.mergeLists(identical, identical));
  }
}