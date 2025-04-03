package ru.mentee.power.datatypes;

public class SimpleCalculator {
    public static void main(String[] args) {
    // 1. Объявление переменных примитивных типов
    byte byteVar = 127;
    short shortVar = 32767;
    int intVar = 2147483647;
    long longVar = 9223372036854775807L;
    float floatVar = 3.14f;
    double doubleVar = 2.71828;
    char charVar = 'A';
    boolean booleanVar = true;

    // 2. Создание строк и массивов
    String str1 = "Hello";
    String str2 = "World";
    int[] intArray = {1, 2, 3, 4, 5};
    String[] stringArray = {"Java", "Python", "C++"};

    // 3. Математические операции с разными типами данных
    int sumInt = intVar + 10;
    double sumDouble = doubleVar + floatVar;
    float product = floatVar * 2;
    double division = doubleVar / floatVar;
    int mixedOperation = (int) (intVar + doubleVar); // с преобразованием типа

    // 4. Преобразование типов
    int intFromDouble = (int) doubleVar; // явное приведение (дробная часть теряется)
    double doubleFromInt = intVar;      // неявное приведение
    char charFromInt = (char) (charVar + 1); // 'A' -> 'B'
    String stringFromInt = Integer.toString(intVar);

    // 5. Вывод результатов в консоль
        System.out.println("\n----- Строки и массивы -----");
        System.out.println("str1: " + str1);
        System.out.println("str2: " + str2);
        System.out.println("Объединение строк: " + str1 + " " + str2);
        System.out.print("intArray: ");
        for (int num : intArray) {
        System.out.print(num + " ");
    }
        System.out.print("\nstringArray: ");
        for (String lang : stringArray) {
        System.out.print(lang + " ");
    }

        System.out.println("\n\n----- Математические операции -----");
        System.out.println("intVar + 10 = " + sumInt);
        System.out.println("doubleVar + floatVar = " + sumDouble);
        System.out.println("floatVar * 2 = " + product);
        System.out.println("doubleVar / floatVar = " + division);
        System.out.println("intVar + doubleVar (с приведением к int) = " + mixedOperation);

        System.out.println("\n----- Преобразование типов -----");
        System.out.println("double -> int: " + doubleVar + " -> " + intFromDouble); // 2 так как при приведении типов int сохраняет число только до запятой
        System.out.println("int -> double: " + intVar + " -> " + doubleFromInt); // в java'е при приведении к double только первый элемент числа ставится перед точкой
        System.out.println("char + 1: " + charVar + " -> " + charFromInt); // после добавления единицы A превратилась в B так, как в таблице кодов A = 48, а В = 49
        System.out.println("int -> String: " + intVar + " -> \"" + stringFromInt + "\"");

    }
}
