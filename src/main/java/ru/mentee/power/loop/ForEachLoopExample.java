package ru.mentee.power.loop;

public class ForEachLoopExample {

  public static void main(String[] args) {
    String[] fruits = {"Яблоко", "Банан", "Апельсин"};

    System.out.println("Фрукты (без индекса):");

    for (String fruit : fruits) {
      System.out.println(fruit);
    }

  }
}