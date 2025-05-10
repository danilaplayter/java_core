package ru.mentee.power.loop;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

  private final Random random;
  private final Scanner scanner = new Scanner(System.in);

  private int gamesPlayed = 0;
  private int minAttempts = Integer.MAX_VALUE;
  private int maxAttempts = 0;
  private int totalAttempts = 0;

  public NumberGuessingGame() {
    this.random = createRandom();
  }

  protected Random createRandom() {
    return new Random();
  }

  public void startGame() {
    boolean playAgain;
    do {
      int attempts = playRound();
      updateStatistics(attempts);
      playAgain = askPlayAgain();
    } while (playAgain);
    showStatistics();
  }

  public int playRound() {
    int secretNumber = random.nextInt(100) + 1;
    int attempts = 0;
    boolean guessed = false;

    System.out.println("Я загадал число от 1 до 100. Попробуйте угадать!");

    do {
      System.out.print("Ваш вариант: ");
      int guess;

      if (scanner.hasNextInt()) {
        guess = scanner.nextInt();
        scanner.nextLine();

        if (guess < 1 || guess > 100) {
          System.out.println("Ошибка! Введите число от 1 до 100.");
          continue;
        }

        attempts++;

        if (guess == secretNumber) {
          guessed = true;
          System.out.printf("Поздравляю! Вы угадали число %d за %d попыток.%n",
              secretNumber, attempts);
        } else if (guess < secretNumber) {
          System.out.println("Загаданное число больше.");
        } else {
          System.out.println("Загаданное число меньше.");
        }
      } else {
        System.out.println("Ошибка! Введите целое число.");
        scanner.nextLine();
      }

    } while (!guessed);

    return attempts;
  }

  private void updateStatistics(int attempts) {
    gamesPlayed++;
    totalAttempts += attempts;
    minAttempts = Math.min(minAttempts, attempts);
    maxAttempts = Math.max(maxAttempts, attempts);
  }

  public void showStatistics() {
    if (gamesPlayed == 0) {
      System.out.println("Статистика недоступна - не сыграно ни одной игры.");
      return;
    }

    System.out.println("\nСтатистика игр:");
    System.out.println("Сыграно игр: " + gamesPlayed);
    System.out.println("Минимальное количество попыток: " +
        (minAttempts == Integer.MAX_VALUE ? 0 : minAttempts));
    System.out.println("Максимальное количество попыток: " + maxAttempts);
    System.out.printf("Среднее количество попыток: %.1f%n",
        (double) totalAttempts / gamesPlayed);
  }

  private boolean askPlayAgain() {
    System.out.print("Хотите сыграть еще раз? (да/нет): ");
    String answer = scanner.nextLine().toLowerCase();
    return answer.equals("да") || answer.equals("yes") || answer.equals("y");
  }

  public void close() {
    scanner.close();
  }

  public static void main(String[] args) {
    NumberGuessingGame game = new NumberGuessingGame();

    try {
      game.startGame();
    } finally {
      game.close();
    }
  }
}