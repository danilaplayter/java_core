package ru.mentee.power.collections.base;

import java.util.ArrayList;
import java.util.List;

public class CollectionDemo {

  public static void main(String[] args) {

    List<String> fruits = new ArrayList<>();

    // 1. Добавление элементов (add)
    fruits.add("Яблоко");
    fruits.add("Банан");
    fruits.add("Апельсин");
    System.out.println("После добавления: " + fruits); // Вывод: [Яблоко, Банан, Апельсин]

    // 2. Размер коллекции (size)
    System.out.println("Размер: " + fruits.size()); // Вывод: 3

    // 3. Проверка на пустоту (isEmpty)
    System.out.println("Коллекция пуста? " + fruits.isEmpty()); // Вывод: false

    // 4. Проверка наличия элемента (contains)
    System.out.println("Содержит 'Банан'? " + fruits.contains("Банан")); // Вывод: true
    System.out.println("Содержит 'Груша'? " + fruits.contains("Груша")); // Вывод: false

    // 5. Удаление элемента (remove)
    boolean removed = fruits.remove("Банан");
    System.out.println("Удален ли 'Банан'? " + removed); // Вывод: true
    System.out.println("После удаления: " + fruits); // Вывод: [Яблоко, Апельсин]

    // 6. Итерация (обход) элементов с помощью for-each
    System.out.println("Элементы коллекции:");
    for (String fruit : fruits) { // Цикл for-each работает благодаря интерфейсу Iterable
      System.out.println("- " + fruit.toUpperCase()); // Преобразуем в верхний регистр
    }

    // 7. Очистка коллекции (clear)
    fruits.clear();
    System.out.println("После очистки: " + fruits); // Вывод: []
    System.out.println("Размер после очистки: " + fruits.size()); // Вывод: 0
    System.out.println("Коллекция пуста? " + fruits.isEmpty()); // Вывод: true
  }
}