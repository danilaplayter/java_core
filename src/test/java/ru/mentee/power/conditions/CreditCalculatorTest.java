package ru.mentee.power.conditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreditCalculatorTest {

  private CreditCalculator calculator;
  private static final double DELTA = 0.01;

  @BeforeEach
  void setUp() {
    calculator = new CreditCalculator();
  }

  @Test
  @DisplayName("Расчет платежа для ипотеки с отличным кредитным рейтингом")
  void calculateMortgageWithExcellentCreditScore() {

    double loanAmount = 5_000_000;
    int loanTermMonths = 240;
    String creditType = "Ипотека";
    int creditScore = 800;

    double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths,
        creditType, creditScore);

    double monthlyRate = 7.0 / 12 / 100;
    double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
        / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

    assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
  }

  @Test
  @DisplayName("Расчет платежа для потребительского кредита с хорошим кредитным рейтингом")
  void calculateConsumerLoanWithGoodCreditScore() {

    double loanAmount = 500_000;
    int loanTermMonths = 36;
    String creditType = "Потребительский";
    int creditScore = 700;

    double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths,
        creditType, creditScore);

    double monthlyRate = 14.0 / 12 / 100; // В этой строке ошибка!
    double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
        / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

    assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
  }

  @Test
  @DisplayName("Расчет платежа для автокредита со средним кредитным рейтингом")
  void calculateAutoLoanWithAverageCreditScore() {
    // Arrange
    double loanAmount = 800_000;
    int loanTermMonths = 48; // 4 года
    String creditType = "Автокредит";
    int creditScore = 600; // Средний рейтинг

    double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths,
        creditType, creditScore);

    double monthlyRate = 12.0 / 12 / 100;
    double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
        / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

    assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
  }

  @Test
  @DisplayName("Расчет платежа для автокредита с плохим кредитным рейтингом")
  void calculateAutoLoanWithPoorCreditScore() {

    double loanAmount = 1_000_000;
    int loanTermMonths = 60;
    String creditType = "Автокредит";
    int creditScore = 400;

    double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths,
        creditType, creditScore);

    double monthlyRate = 15.0 / 12 / 100;
    double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
        / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

    assertThat(monthlyPayment).isCloseTo(expectedPayment, within(DELTA));
  }

  @Test
  @DisplayName("Обработка слишком маленькой суммы кредита")
  void handleTooSmallLoanAmount() {
    // Arrange
    double loanAmount = 5_000; // Меньше минимальной суммы (10,000)
    int loanTermMonths = 60;
    String creditType = "Автокредит";
    int creditScore = 700;

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);

    assertThat(result).isEqualTo(-1.0);
  }

  @Test
  @DisplayName("Обработка слишком большой суммы кредита")
  void handleTooLargeLoanAmount() {
    // Arrange
    double loanAmount = 11_000_000; // Больше максимальной (10,000,000)
    int loanTermMonths = 120;
    String creditType = "Ипотека";
    int creditScore = 750;

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);

    assertThat(result).isEqualTo(-1.0);
  }

  @Test
  @DisplayName("Обработка слишком короткого срока кредита")
  void handleTooShortLoanTerm() {

    double loanAmount = 1_000_000;
    int loanTermMonths = 0;
    String creditType = "Потребительский";
    int creditScore = 680;

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);
    assertThat(result).isEqualTo(-1); // В этой строке ошибка!
  }

  @Test
  @DisplayName("Обработка слишком длинного срока кредита")
  void handleTooLongLoanTerm() {

    double loanAmount = 2_000_000;
    int loanTermMonths = 400;
    String creditType = "Автокредит";
    int creditScore = 720;

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);

    assertThat(result).isEqualTo(-1.0);
  }

  @Test
  @DisplayName("Обработка неизвестного типа кредита")
  void handleUnknownCreditType() {
    // Arrange
    double loanAmount = 500_000;
    int loanTermMonths = 60;
    String creditType = "Образовательный"; // Неизвестный тип
    int creditScore = 800;

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);

    assertThat(result).isEqualTo(-1.0);
  }

  @Test
  @DisplayName("Обработка слишком низкого кредитного рейтинга")
  void handleTooLowCreditScore() {
    // Arrange
    double loanAmount = 300_000;
    int loanTermMonths = 36;
    String creditType = "Потребительский";
    int creditScore = 250; // Ниже минимального (300)

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);

    assertThat(result).isEqualTo(-1.0);
  }

  @Test
  @DisplayName("Обработка слишком высокого кредитного рейтинга")
  void handleTooHighCreditScore() {
    double loanAmount = 4_000_000;
    int loanTermMonths = 180;
    String creditType = "Ипотека";
    int creditScore = 900;

    double result = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType,
        creditScore);
    assertThat(result).isEqualTo(-1.0);
  }
}