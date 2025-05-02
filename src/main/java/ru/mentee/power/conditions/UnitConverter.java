package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UnitConverter {

  private static final double ERROR_CODE = -1.0;

  private static final List<String> LENGTH_UNITS = Arrays.asList("Метр", "Сантиметр", "Дюйм",
      "Фут");
  private static final List<String> WEIGHT_UNITS = Arrays.asList("Килограмм", "Грамм", "Фунт",
      "Унция");
  private static final List<String> TEMP_UNITS = Arrays.asList("Цельсий", "Фаренгейт", "Кельвин");

  public double convert(double value, String fromUnit, String toUnit) {
    // Проверка на поддерживаемые единицы измерения
    if (!isUnitSupported(fromUnit) || !isUnitSupported(toUnit)) {
      return ERROR_CODE;
    }

    // Проверка, что единицы из одной категории
    if (!areSameCategory(fromUnit, toUnit)) {
      return ERROR_CODE;
    }

    // Выбор соответствующего метода конвертации
    String category = getCategory(fromUnit);
    switch (category) {
      case "Длина":
        return convertLength(value, fromUnit, toUnit);
      case "Вес":
        return convertWeight(value, fromUnit, toUnit);
      case "Температура":
        return convertTemperature(value, fromUnit, toUnit);
      default:
        return ERROR_CODE;
    }
  }

  private boolean isUnitSupported(String unit) {
    return LENGTH_UNITS.contains(unit) || WEIGHT_UNITS.contains(unit) || TEMP_UNITS.contains(unit);
  }

  private boolean areSameCategory(String unit1, String unit2) {
    if (unit1 == null || unit2 == null) {
      return false;
    }
    String category1 = getCategory(unit1);
    String category2 = getCategory(unit2);
    return category1 != null && category1.equals(category2);
  }

  private String getCategory(String unit) {
    if (LENGTH_UNITS.contains(unit)) {
      return "Длина";
    }
    if (WEIGHT_UNITS.contains(unit)) {
      return "Вес";
    }
    if (TEMP_UNITS.contains(unit)) {
      return "Температура";
    }
    return null;
  }

  private double convertLength(double value, String fromUnit, String toUnit) {
    // Сначала конвертируем в метры
    double inMeters;
    switch (fromUnit) {
      case "Метр":
        inMeters = value;
        break;
      case "Сантиметр":
        inMeters = value / 100;
        break;
      case "Дюйм":
        inMeters = value * 0.0254;
        break;
      case "Фут":
        inMeters = value * 0.3048;
        break;
      default:
        return ERROR_CODE;
    }

    // Затем конвертируем из метров в целевую единицу
    switch (toUnit) {
      case "Метр":
        return inMeters;
      case "Сантиметр":
        return inMeters * 100;
      case "Дюйм":
        return inMeters / 0.0254;
      case "Фут":
        return inMeters / 0.3048;
      default:
        return ERROR_CODE;
    }
  }

  private double convertWeight(double value, String fromUnit, String toUnit) {
    // Сначала конвертируем в килограммы
    double inKilograms;
    switch (fromUnit) {
      case "Килограмм":
        inKilograms = value;
        break;
      case "Грамм":
        inKilograms = value / 1000;
        break;
      case "Фунт":
        inKilograms = value * 0.453592;
        break;
      case "Унция":
        inKilograms = value * 0.0283495;
        break;
      default:
        return ERROR_CODE;
    }

    // Затем конвертируем из килограммов в целевую единицу
    switch (toUnit) {
      case "Килограмм":
        return inKilograms;
      case "Грамм":
        return inKilograms * 1000;
      case "Фунт":
        return inKilograms / 0.453592;
      case "Унция":
        return inKilograms / 0.0283495;
      default:
        return ERROR_CODE;
    }
  }

  private double convertTemperature(double value, String fromUnit, String toUnit) {
    if (fromUnit.equals(toUnit)) {
      return value;
    }

    // Сначала конвертируем в Цельсии
    double inCelsius;
    switch (fromUnit) {
      case "Цельсий":
        inCelsius = value;
        break;
      case "Фаренгейт":
        inCelsius = (value - 32) * 5 / 9;
        break;
      case "Кельвин":
        inCelsius = value - 273.15;
        break;
      default:
        return ERROR_CODE;
    }

    // Затем конвертируем из Цельсиев в целевую единицу
    switch (toUnit) {
      case "Цельсий":
        return inCelsius;
      case "Фаренгейт":
        return inCelsius * 9 / 5 + 32;
      case "Кельвин":
        return inCelsius + 273.15;
      default:
        return ERROR_CODE;
    }
  }

  public static void main(String[] args) {
    UnitConverter converter = new UnitConverter();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Доступные единицы измерения:");
    System.out.println("Длина: " + String.join(", ", LENGTH_UNITS));
    System.out.println("Вес: " + String.join(", ", WEIGHT_UNITS));
    System.out.println("Температура: " + String.join(", ", TEMP_UNITS));

    System.out.println("Введите значение:");
    double val = scanner.nextDouble();
    scanner.nextLine(); // Очистка буфера

    System.out.println("Введите исходную единицу:");
    String from = scanner.nextLine();

    System.out.println("Введите целевую единицу:");
    String to = scanner.nextLine();

    double result = converter.convert(val, from, to);
    if (result == ERROR_CODE) {
      System.out.println("Ошибка конвертации! Проверьте введенные единицы измерения.");
    } else {
      System.out.printf("Результат: %.2f %s = %.2f %s%n", val, from, result, to);
    }

    scanner.close();
  }
}