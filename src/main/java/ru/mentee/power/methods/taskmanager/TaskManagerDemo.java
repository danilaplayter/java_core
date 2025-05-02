package ru.mentee.power.methods.taskmanager;

import java.time.LocalDate;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TaskManagerDemo {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    TaskManager taskManager = new TaskManager();

    taskManager.addTask("123", "123", LocalDate.of(2023, Month.JUNE, 15), Task.Priority.HIGH);

    boolean exit = false;
    while (!exit) {
      System.out.println("\n=== Управление задачами ===");
      System.out.println("1. Показать все задачи");
      System.out.println("2. Добавить задачу");
      System.out.println("3. Найти задачу по ID");
      System.out.println("4. Удалить задачу");
      System.out.println("5. Отметить задачу как выполненную");
      System.out.println("6. Показать выполненные задачи");
      System.out.println("7. Показать невыполненные задачи");
      System.out.println("8. Показать просроченные задачи");
      System.out.println("9. Фильтровать по приоритету");
      System.out.println("10. Поиск по названию/описанию");
      System.out.println("11. Сортировать по дате");
      System.out.println("12. Сортировать по приоритету");
      System.out.println("0. Выход");
      System.out.print("Выберите действие: ");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Очистка буфера
      try {
        switch (choice) {
          case 1: // Показать все задачи
            taskManager.printAllTasks();
            break;

          case 2: // Добавить задачу
            System.out.print("Введите название: ");
            String title = scanner.nextLine();
            System.out.print("Введите описание (Enter чтобы пропустить): ");
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
              description = null;
            }

            LocalDate dueDate = null;
            System.out.print("Установить срок? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {

              System.out.print("Год: ");
              int year = scanner.nextInt();
              System.out.print("Месяц (1-12): ");
              int month = scanner.nextInt();
              System.out.print("День: ");
              int day = scanner.nextInt();
              scanner.nextLine();
              dueDate = LocalDate.of(year, month, day);
            }

            Task.Priority priority = null;
            System.out.print("Приоритет (1-HIGH, 2-MEDIUM, 3-LOW, Enter-без приоритета): ");
            String priorityInput = scanner.nextLine().trim();

            Task newTask = taskManager.addTask(title, description, dueDate, priority);
            System.out.println("Добавлена задача: " + newTask);
            break;

          case 3: // Найти по ID
            try {
              System.out.print("Введите ID задачи: ");
              int id = scanner.nextInt();
              scanner.nextLine();
              if (id < 0) {
                throw new IllegalArgumentException("ID не может быть отрицательным.\n");
              }
              Task task = taskManager.getTaskById(id);
              if (task != null) {
                System.out.println(task);
                try {
                  if (Math.random() > 0.999) {
                    Task nullTask = null;
                    nullTask.getTitle();
                  }
                } catch (NullPointerException e) {
                  System.out.println("Демонстрация NPE: " + e.getMessage());
                  System.out.println("Лучше всегда проверять задачи на null!");
                }
              } else {
                System.out.println("Задача не найдена");
              }
            } catch (InputMismatchException e) {
              System.out.println("Ошибка ввода: Пожалуйста, введите ID задачи (целое число).");
            }
            break;

          case 4: // Удалить задачу
            try {
              System.out.print("Введите ID задачи для удаления: ");
              int removeId = scanner.nextInt();
              scanner.nextLine();
              if (removeId < 0) {
                throw new IllegalArgumentException("ID не может быть отрицательным.\n");
              }
              if (taskManager.removeTask(removeId)) {
                System.out.println("Задача удалена");
              } else {
                throw new NoSuchElementException("Задача с ID " + removeId + " не найдена");
              }
            } catch (InputMismatchException e) {
              System.out.println("Ошибка ввода: Пожалуйста, введите ID задачи (целое число).");
            }
            break;

          case 5: // Отметить как выполненную
            try {
              System.out.print("Введите ID задачи: ");
              int completeId = scanner.nextInt();
              scanner.nextLine();
              if (completeId < 0) {
                throw new IllegalArgumentException("ID не может быть отрицательным.\n");
              }
              if (taskManager.markTaskAsCompleted(completeId)) {
                System.out.println("Статус задачи обновлен");
              } else {
                throw new NoSuchElementException("Задача с ID " + completeId + " не найдена");
              }
            } catch (InputMismatchException e) {
              System.out.println("Ошибка ввода: Пожалуйста, введите ID задачи (целое число).");
            }
            break;

          case 6: // Выполненные задачи
            taskManager.printTasks(taskManager.getCompletedTasks(), "Выполненные задачи");
            break;

          case 7: // Невыполненные задачи
            taskManager.printTasks(taskManager.getIncompleteTasks(), "Невыполненные задачи");
            break;

          case 8: // Просроченные задачи
            taskManager.printTasks(taskManager.getOverdueTasks(), "Просроченные задачи");
            break;

          case 9: // Фильтр по приоритету
            System.out.print("Приоритет (1-HIGH, 2-MEDIUM, 3-LOW): ");
            int priorityChoice = scanner.nextInt();
            scanner.nextLine();
            Task.Priority filterPriority = Task.Priority.values()[priorityChoice - 1];
            taskManager.printTasks(taskManager.getTasksByPriority(filterPriority),
                "Задачи с приоритетом " + filterPriority);
            break;

          case 10: // Поиск
            System.out.print("Введите текст для поиска: ");
            String query = scanner.nextLine();
            taskManager.printTasks(taskManager.searchTasks(query), "Результаты поиска");
            break;

          case 11:
            taskManager.printTasks(taskManager.sortTasksByDueDate(),
                "Задачи, отсортированные по дате");
            break;

          case 12: // Сортировка по приоритету
            taskManager.printTasks(taskManager.sortTasksByPriority(),
                "Задачи, отсортированные по приоритету");
            break;

          case 0: // Выход
            exit = true;
            break;

          default:
            System.out.println("Неверный выбор");
        }
      } catch (InputMismatchException e) {
        System.out.println("Ошибка введите число.");
        scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.println("Ошибка: " + e.getMessage());
      }
      scanner.close();
      System.out.println("Программа завершена");
    }

  }
}