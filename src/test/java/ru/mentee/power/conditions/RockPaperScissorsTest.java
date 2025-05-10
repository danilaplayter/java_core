package ru.mentee.power.conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RockPaperScissorsTest {

  private RockPaperScissors game;

  private static final String ROCK = "камень";
  private static final String PAPER = "бумага";
  private static final String SCISSORS = "ножницы";
  private static final String WIN = "Победа";
  private static final String DRAW = "Ничья";
  private static final String ERROR = "Ошибка";

  @BeforeEach
  void setUp() {
    game = new RockPaperScissors();
  }

  @Test
  @DisplayName("Камень бьет ножницы")
  void rockBeatsScissors() {
    String result = game.determineWinner(ROCK, SCISSORS);
    assertThat(result).isEqualTo(WIN);
  }

  @Test
  @DisplayName("Ножницы бьют бумагу")
  void scissorsBeatsPaper() {
    String result = game.determineWinner(SCISSORS, PAPER);
    assertThat(result).isEqualTo(WIN);
  }

  @Test
  @DisplayName("Бумага бьет камень")
  void paperBeatsRock() {
    String result = game.determineWinner(PAPER, ROCK);
    assertThat(result).isEqualTo(WIN);
  }

  @Test
  @DisplayName("Ничья при одинаковых ходах (камень)")
  void drawWhenSameChoiceRock() {
    String result = game.determineWinner(ROCK, ROCK);
    assertThat(result).isEqualTo(DRAW);
  }

  @Test
  @DisplayName("Ничья при одинаковых ходах (бумага)")
  void drawWhenSameChoicePaper() {
    String result = game.determineWinner(PAPER, PAPER);
    assertThat(result).isEqualTo(DRAW);
  }

  @Test
  @DisplayName("Ничья при одинаковых ходах (ножницы)")
  void drawWhenSameChoiceScissors() {
    String result = game.determineWinner(SCISSORS, SCISSORS);
    assertThat(result).isEqualTo(DRAW);
  }

  @Test
  @DisplayName("Ошибка при неверном выборе игрока")
  void handleInvalidPlayerChoice() {
    String result = game.determineWinner("колодец", ROCK);
    assertThat(result).isEqualTo(ERROR);
  }

  @Test
  @DisplayName("Ошибка при неверном выборе компьютера")
  void handleInvalidComputerChoice() {
    String result = game.determineWinner(ROCK, "огонь");
    assertThat(result).isEqualTo(ERROR);
  }

  @Test
  @DisplayName("Ошибка при неверных ходах обоих")
  void handleInvalidBothChoices() {
    String result = game.determineWinner("вода", "воздух");
    assertThat(result).isEqualTo(ERROR);
  }

  @RepeatedTest(10)
  @DisplayName("Генерация допустимых ходов компьютера")
  void generateComputerChoiceReturnsValidOption() {
    String choice = game.generateComputerMove();
    assertThat(choice).isIn(ROCK, PAPER, SCISSORS);
  }

  @ParameterizedTest
  @CsvSource({
      "камень, ножницы, Победа",
      "ножницы, бумага, Победа",
      "бумага, камень, Победа",
      "камень, бумага, Поражение",
      "бумага, ножницы, Поражение",
      "ножницы, камень, Поражение",
      "камень, камень, Ничья",
      "бумага, бумага, Ничья",
      "ножницы, ножницы, Ничья"
  })
  @DisplayName("Проверка различных комбинаций")
  void testVariousCombinations(String player, String computer, String expected) {
    String result = game.determineWinner(player, computer);
    assertThat(result).isEqualTo(expected);
  }

}