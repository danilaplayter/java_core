package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

  public static final String ROCK = "камень";
  public static final String PAPER = "бумага";
  public static final String SCISSORS = "ножницы";

  public static final String WIN = "Победа";
  public static final String LOSE = "Поражение";
  public static final String DRAW = "Ничья";
  public static final String ERROR = "Ошибка";

  private static final List<String> VALID_MOVES = Arrays.asList(ROCK, PAPER, SCISSORS);

  private Random random = new Random();
  private Scanner scanner;

  public String determineWinner(String playerMove, String computerMove) {
    if (!validateMove(playerMove) || !validateMove(computerMove)) {
      return ERROR;
    }
    if (playerMove.equals(computerMove)) {
      return DRAW;
    }
    if ((playerMove.equals(ROCK) && computerMove.equals(SCISSORS)) ||
        (playerMove.equals(SCISSORS) && computerMove.equals(PAPER)) ||
        (playerMove.equals(PAPER) && computerMove.equals(ROCK))) {
      return WIN;
    } else {
      return LOSE;
    }
  }

  private boolean validateMove(String move) {
    return VALID_MOVES.contains(move);
  }

  public String generateComputerMove() {
    int index = random.nextInt(VALID_MOVES.size());
    return VALID_MOVES.get(index);
  }

  public void playOneGame() {
    System.out.print("Введите ваш ход (камень, ножницы, бумага): ");
    String playerMove = scanner.nextLine().trim();
    String computerMove = generateComputerMove();
    System.out.println("Ход компьютера: " + computerMove);
    String result = determineWinner(playerMove, computerMove);
    System.out.println("Результат: " + result);
  }

  public void startGameLoop() {
    scanner = new Scanner(System.in);
    System.out.println("Добро пожаловать в игру Камень-Ножницы-Бумага!");
    System.out.println("Правила: камень бьет ножницы, ножницы бьют бумагу, бумага бьет камень.");

    boolean playAgain = true;
    while (playAgain) {
      playOneGame();
      System.out.print("Хотите сыграть еще? (да/нет): ");
      String answer = scanner.nextLine().trim().toLowerCase();
      if (!answer.equals("да")) {
        playAgain = false;
      }
    }
    scanner.close();
    System.out.println("Спасибо за игру!");
  }

  public static void main(String[] args) {
    RockPaperScissors game = new RockPaperScissors();
    game.startGameLoop();
  }
}