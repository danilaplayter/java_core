package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SmartThermostat {

  private static final double ERROR_TEMP_CODE = -100.0;

  private static final List<String> WEEKDAYS = Arrays.asList("Понедельник", "Вторник", "Среда",
      "Четверг", "Пятница");
  private static final List<String> WEEKENDS = Arrays.asList("Суббота", "Воскресенье");

  public double getTargetTemperature(int timeOfDay, String dayOfWeek, boolean isOccupied,
      double currentOutsideTemperature) {
    // Проверка корректности входных данных
    if (timeOfDay < 0 || timeOfDay > 23) {
      return ERROR_TEMP_CODE;
    }

    if (!WEEKDAYS.contains(dayOfWeek) && !WEEKENDS.contains(dayOfWeek)) {
      return ERROR_TEMP_CODE;
    }

    double baseTemp;
    boolean isWeekend = WEEKENDS.contains(dayOfWeek);

    if (isWeekend) {

      if (timeOfDay >= 7 && timeOfDay <= 9) {
        if (isOccupied) {
          baseTemp = 23.0;
        } else {
          baseTemp = 18.0;
        }
      } else if (timeOfDay >= 10 && timeOfDay <= 17) {
        if (isOccupied) {
          baseTemp = 22.0;
        } else {
          baseTemp = 17.0;
        }
      } else if (timeOfDay >= 18 && timeOfDay <= 23) {
        if (isOccupied) {
          baseTemp = 22.0;
        } else {
          baseTemp = 17.0;
        }
      } else {
        if (isOccupied) {
          baseTemp = 20.0;
        } else {
          baseTemp = 16.0;
        }
      }
    } else {
      if (timeOfDay >= 6 && timeOfDay <= 8) {
        if (isOccupied) {
          baseTemp = 22.0;
        } else {
          baseTemp = 18.0;
        }
      } else if (timeOfDay >= 9 && timeOfDay <= 17) {
        if (isOccupied) {
          baseTemp = 20.0;
        } else {
          baseTemp = 16.0;
        }
      } else if (timeOfDay >= 18 && timeOfDay <= 22) {
        if (isOccupied) {
          baseTemp = 22.0;
        } else {
          baseTemp = 17.0;
        }
      } else {
        if (isOccupied) {
          baseTemp = 19.0;
        } else {
          baseTemp = 16.0;
        }
      }
    }

    if (currentOutsideTemperature > 25) {
      baseTemp = baseTemp + 1.0;
    } else if (currentOutsideTemperature < 0) {
      baseTemp = baseTemp - 1.0;
    }

    return baseTemp;
  }

  public static void main(String[] args) {
    SmartThermostat thermostat = new SmartThermostat();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Введите время суток (0-23):");
    int time = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Введите день недели (например, Понедельник):");
    String day = scanner.nextLine();

    System.out.println("Есть ли кто-то дома? (true/false):");
    boolean occupied = scanner.nextBoolean();

    System.out.println("Введите текущую температуру на улице:");
    double outsideTemp = scanner.nextDouble();

    double targetTemp = thermostat.getTargetTemperature(time, day, occupied, outsideTemp);
    if (targetTemp == ERROR_TEMP_CODE) {
      System.out.println("Ошибка: Некорректные входные данные.");
    } else {
      System.out.println("Рекомендуемая температура: " + targetTemp + "°C");
    }

    scanner.close();
  }
}
