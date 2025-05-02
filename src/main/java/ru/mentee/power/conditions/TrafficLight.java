package ru.mentee.power.conditions;

import java.util.Scanner;

public class TrafficLight {

  public static String getRecommendation(String signal) {

    if (signal == null) {
      return "Некорректный сигнал!";
    }

    if (signal.equalsIgnoreCase("Красный")) {
      System.out.println("Стой на месте!");
    }
    if (signal.equalsIgnoreCase("Желтый")) {
      System.out.println("Приготовься, но подожди!");
    }
    if (signal.equalsIgnoreCase("Зелёный")) {
      System.out.println("Можно переходить дорогу!");
    }

    if (signal.equalsIgnoreCase("Красный")) {
      return "Стой на месте!";
    } else if (signal.equalsIgnoreCase("Желтый")) {
      return "Приготовься, но подожди!";
    } else if (signal.equalsIgnoreCase("Зеленый")) {
      return "Можно переходить дорогу!";
    } else {
      return "Некорректный сигнал!";
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Какой сейчас сигнал светофора (Красный, Желтый, Зеленый)?");
    System.out.print("Введите название сигнала: ");

    String signal = scanner.nextLine();
    String recommendation = getRecommendation(signal);
    System.out.println(recommendation);

    scanner.close();
  }
}