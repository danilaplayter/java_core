package ru.mentee.power.variables;

public class PersonalCard {

  public static void main(String[] args) {

    String firstName = "Данила";
    String lastName = "Март";
    String city = "Минск";
    int age = 18;
    int height = 178;
    double weight = 68;
    boolean student = true;
    char firstLetter = 'Д';

    // Вывод информации
    System.out.println("==== ЛИЧНАЯ КАРТОЧКА ====");
    System.out.println("Имя: " + firstName);
    System.out.println("Фамилия: " + lastName);
    System.out.println("Возраст: " + age + " лет");
    System.out.println("Город: " + city);
    System.out.println("Рост: " + height + " см");
    System.out.println("Вес: " + weight + " кг");
    System.out.println("Студент: " + student);
    System.out.println("Первая буква имени: " + firstLetter);
    System.out.println("=========================");
  }
}