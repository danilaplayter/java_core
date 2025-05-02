package ru.mentee.power.exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManagerDemo {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      TaskManager account = null;
      while (account == null) {
        try {
          System.out.println("Введите ID для аккаунта: ");
          String accountID = scanner.nextLine();

          System.out.println("Введите начальный баланс: ");
          double initialBalance = scanner.nextDouble();
          scanner.nextLine();

          account = new TaskManager(accountID, initialBalance);
          System.out.println("Аккаунт создан успешно.");
        } catch (InputMismatchException e) {
          System.out.println("Ошибка. Введите корректные значения для баланса.");
          scanner.nextLine();
        } catch (IllegalArgumentException e) {
          System.out.println("Error: " + e.getMessage());
        }
      }

      boolean running = true;
      while (running) {
        printMenu();
        try {
          System.out.println("Введите ваш выбор: ");
          int choice = scanner.nextInt();
          scanner.nextLine();
          switch (choice) {
            case 1: {
              System.out.println("Введите сумму пополнения: ");
              double depositAmount = scanner.nextDouble();
              scanner.nextLine();
              account.deposit(depositAmount);
              System.out.println(
                  "Пополнение прошло успешно. Текущий баланс: " + account.getBalance());
              break;
            }
            case 2: {
              System.out.println("Введите сумму снятия: ");
              double withdrawAmount = scanner.nextDouble();
              scanner.nextLine();
              try {
                account.withdraw(withdrawAmount);
                System.out.println("Снятие прошло успешно. Текущий баланс: ");
              } catch (TaskValidationException e) {
                System.out.println("Ошибка снятия. " + e.getMessage());
                System.out.println(
                    "Подробности:\nID аккаунта: " + account.getId() + "\nТекущий баланс: "
                        + e.getBalance() + "\nЗапрошенная сумма: " + e.getWithdrawAmount());
              }
              break;
            }
            case 3: {
              System.out.println("Текущий баланс: " + account.getBalance());
              break;
            }
            case 4: {
              running = false;
              break;
            }
            default: {
              System.out.println("Ошибка выбора. Введите число от 1 до 4.");
            }

          }
        } catch (InputMismatchException e) {
          System.out.println("Ошибка: Неверный ввод. Введите число.");
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Ошибка: " + e.getMessage());
    }
    System.out.println("Программа выполнена. Благодарим за использование TaskManager");
  }


  private static void printMenu() {
    System.out.println("\n========== МЕНЮ УПРАВЛЕНИЯ ЗАДАЧАМИ ==========");
    System.out.println("1. Положить деньги");
    System.out.println("2. Снять деньги");
    System.out.println("3. Проверить счёт");
    System.out.println("4. Выйти");
    System.out.println("======================================");
  }
}