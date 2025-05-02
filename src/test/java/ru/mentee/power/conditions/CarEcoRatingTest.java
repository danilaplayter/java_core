package ru.mentee.power.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarEcoRatingTest {

  private CarEcoRating ratingCalculator;
  private static final int ERROR = -1;
  private static final int MIN_RATING = 1;
  private static final int MAX_RATING = 100;

  @BeforeEach
  void setUp() {
    ratingCalculator = new CarEcoRating();
  }

  @Test
  @DisplayName("Расчет рейтинга для современного электромобиля")
  void calculateRatingForModernElectricCar() {
    String fuelType = "Электро";
    double engineVolume = 0.0;
    double fuelConsumption = 15.0;
    int yearOfManufacture = 2023;
    boolean isEuroCompliant = false;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    int expectedRating = 90;
    assertThat(rating).isEqualTo(expectedRating);
  }

  @Test
  @DisplayName("Расчет рейтинга для эффективного гибрида Евро-6")
  void calculateRatingForEfficientEuro6Hybrid() {

    String fuelType = "Гибрид";
    double engineVolume = 1.5;
    double fuelConsumption = 4.0;
    int yearOfManufacture = 2021;
    boolean isEuroCompliant = true;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    int expectedRating = 80;
    assertThat(rating).isEqualTo(expectedRating);
  }

  @Test
  @DisplayName("Расчет рейтинга для старого дизельного автомобиля не Евро-6")
  void calculateRatingForOldDieselCarNotEuro6() {

    String fuelType = "Дизель";
    double engineVolume = 2.5;
    double fuelConsumption = 8.0;
    int yearOfManufacture = 2015;
    boolean isEuroCompliant = false;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    assertThat(rating).isEqualTo(5);
  }

  @Test
  @DisplayName("Верхняя граница рейтинга (максимум 100)")
  void ensureMaximumRatingIs100() {

    String fuelType = "Электро";
    double engineVolume = 0.0;
    double fuelConsumption = 0.1;
    int yearOfManufacture = Year.now().getValue();
    boolean isEuroCompliant = true;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    assertThat(rating).isLessThanOrEqualTo(MAX_RATING);
  }

  @Test
  @DisplayName("Нижняя граница рейтинга (минимум 1)")
  void ensureMinimumRatingIs1() {

    String fuelType = "Бензин";
    double engineVolume = 7.0;
    double fuelConsumption = 25.0;
    int yearOfManufacture = 1980;
    boolean isEuroCompliant = false;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    assertThat(rating).isEqualTo(MIN_RATING);
  }

  @Test
  @DisplayName("Обработка неизвестного типа топлива")
  void handleUnknownFuelType() {
    // Arrange
    String fuelType = "Водород"; // Неизвестный тип топлива
    double engineVolume = 0.0;
    double fuelConsumption = 10.0;
    int yearOfManufacture = 2020;
    boolean isEuroCompliant = false;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    assertThat(rating).isEqualTo(ERROR);
  }

  @Test
  @DisplayName("Обработка отрицательного объема двигателя")
  void handleNegativeEngineVolume() {
    String fuelType = "Бензин";
    double engineVolume = -2.0;
    double fuelConsumption = 10.0;
    int yearOfManufacture = 2020;
    boolean isEuroCompliant = false;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    assertThat(rating).isEqualTo(ERROR);
  }

  @Test
  @DisplayName("Обработка отрицательного расхода топлива")
  void handleNegativeFuelConsumption() {
    // Arrange
    String fuelType = "Дизель";
    double engineVolume = 2.0;
    double fuelConsumption = -5.0; // Отрицательный расход
    int yearOfManufacture = 2018;
    boolean isEuroCompliant = true;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);
    assertThat(rating).isEqualTo(ERROR);
  }

  @Test
  @DisplayName("Обработка года выпуска в будущем")
  void handleFutureYearOfManufacture() {
    String fuelType = "Гибрид";
    double engineVolume = 1.6;
    double fuelConsumption = 5.5;
    int yearOfManufacture = Year.now().getValue() + 5;
    boolean isEuroCompliant = true;

    int rating = ratingCalculator.calculateEcoRating(
        fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

    assertThat(rating).isEqualTo(ERROR);
  }
}