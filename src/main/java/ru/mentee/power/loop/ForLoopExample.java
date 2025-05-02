package ru.mentee.power.loop;

public class ForLoopExample {

  public static void main(String[] args) {

    String[] fruits = {"Яблоко", "Банан", "Апельсин"};

    System.out.println("Фрукты (с индексом):");

    for (int index = 0; index < fruits.length; index++) {
      System.out.println("Индекс " + index + ": " + fruits[index]);
    }

  }
}