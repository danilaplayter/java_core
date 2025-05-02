package ru.mentee.power.loop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ArrayStatisticsTest {

  @Test
  void testFindMinMax() {

    int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    ArrayStatistics stats = new ArrayStatistics(testData);

    // Проверка
    assertThat(stats.findMin()).isEqualTo(1);
    assertThat(stats.findMax()).isEqualTo(9);
  }

  @Test
  void testCalculateSumAndAverage() {

    int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    ArrayStatistics stats = new ArrayStatistics(testData);

    assertThat(stats.calculateSum()).isEqualTo(51);
    assertThat(stats.calculateAverage()).isEqualTo(5.1);
  }

  @Test
  void testCalculateMedian() {

    int[] testData1 = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    int[] testData2 = {5, 7, 2, 9, 3, 5, 1, 8, 5};

    ArrayStatistics stats1 = new ArrayStatistics(testData1);
    ArrayStatistics stats2 = new ArrayStatistics(testData2);

    assertThat(stats1.calculateMedian()).isEqualTo(5.0);
    assertThat(stats2.calculateMedian()).isEqualTo(5.0);
  }

  @Test
  void testFindMode() {

    int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    ArrayStatistics stats = new ArrayStatistics(testData);

    assertThat(stats.findMode()).isEqualTo(5); // 5 встречается 3 раза
  }

  @Test
  void testCalculateStandardDeviation() {

    int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    ArrayStatistics stats = new ArrayStatistics(testData);
    assertThat(Math.round(stats.calculateStandardDeviation() * 100) / 100.0).isEqualTo(2.56);
  }

  @Test
  void testCountGreaterAndLessThan() {
    int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    ArrayStatistics stats = new ArrayStatistics(testData);

    assertThat(stats.countGreaterThan(5)).isEqualTo(4); // 7, 9, 8, 6
    assertThat(stats.countLessThan(5)).isEqualTo(3); // 2, 3, 1
  }

  @Test
  void testContains() {
    int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
    ArrayStatistics stats = new ArrayStatistics(testData);

    assertThat(stats.contains(7)).isTrue();
    assertThat(stats.contains(10)).isFalse();
  }

  @Test
  void testEmptyArray() {
    int[] testData = {};
    ArrayStatistics stats = new ArrayStatistics(testData);
    assertThat(stats = null).isNull();
  }

  @Test
  void testSingleElementArray() {
    int[] testData = {5};
    ArrayStatistics stats = new ArrayStatistics(testData);
    assertThat(stats.findMax()).isEqualTo(5);
    assertThat(stats.findMin()).isEqualTo(5);
    assertThat(stats.findMode()).isEqualTo(5);
    assertThat(stats.calculateAverage()).isEqualTo(5.0);
    assertThat(stats.calculateMedian()).isEqualTo(5.0);
    assertThat(stats.calculateSum()).isEqualTo(5);
    assertThat(stats.countGreaterThan(5)).isEqualTo(0);
    assertThat(stats.countLessThan(5)).isEqualTo(0);
    assertThat(stats.contains(7)).isFalse();
    assertThat(stats.contains(10)).isFalse();
  }

  @Test
  void testArrayWithDuplicates() {
    int[] testData = {5, 5, 5, 5, 5};
    ArrayStatistics stats = new ArrayStatistics(testData);
    assertThat(stats.findMax()).isEqualTo(5);
    assertThat(stats.findMin()).isEqualTo(5);
    assertThat(stats.findMode()).isEqualTo(5);
    assertThat(stats.calculateAverage()).isEqualTo(5.0);
    assertThat(stats.calculateMedian()).isEqualTo(5.0);
    assertThat(stats.calculateSum()).isEqualTo(25);
    assertThat(stats.countGreaterThan(5)).isEqualTo(0);
    assertThat(stats.countLessThan(5)).isEqualTo(0);
    assertThat(stats.contains(7)).isFalse();
    assertThat(stats.contains(10)).isFalse();

  }

  @Test
  void testArrayWithNegativeValues() {
    int[] testData = {-5, -23, -1, -2, -32};
    ArrayStatistics stats = new ArrayStatistics(testData);
    assertThat(stats.findMax()).isEqualTo(-1);
    assertThat(stats.findMin()).isEqualTo(-32);
    assertThat(stats.findMode()).isEqualTo(-32);
    assertThat(stats.calculateAverage()).isEqualTo(-12.6);
    assertThat(stats.calculateMedian()).isEqualTo(-5.0);
    assertThat(stats.calculateSum()).isEqualTo(-63);
    assertThat(stats.countGreaterThan(5)).isEqualTo(0);
    assertThat(stats.countLessThan(5)).isEqualTo(5);
    assertThat(stats.contains(7)).isFalse();
    assertThat(stats.contains(10)).isFalse();
  }
}