package ru.mentee.power.conditions;

import java.util.Scanner;

public class UserInput {

  public static void main(String[] args) {
    Person[] person = new Person[3];

    for (int i = 0; i < 3; i++) {
      person[i] = new Person();  // Создаём новый объект Person
    }

    for (int i = 0; i < 3; i++) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Введите ваш возраст: ");
      person[i].setAge(scanner.nextInt());
      System.out.print("Есть ли у вас права на вождение(Да/Нет): ");
      String input = scanner.next();
      person[i].setLicence(input.equals("Да") || input.equals("да"));
    }

    System.out.println("========Информация========");
    for (int i = 0; i < 3; i++) {
      System.out.println("Ваш возраст: " + person[i].getAge());
      System.out.println("Есть ли у вас права на вождение: " + person[i].isLicence());
      if (person[i].ableToRent()) {
        System.out.println("Человек может арендовать автомобиль.\n");
      } else {
        System.out.println("Человек не может арендовать автомобиль.\n");
      }
    }
    System.out.println("==========================");
  }
}