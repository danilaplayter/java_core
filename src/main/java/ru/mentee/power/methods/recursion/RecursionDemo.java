package ru.mentee.power.methods.recursion;

public class RecursionDemo {
    public static void main(String[] args) {
        System.out.println("=== Факториал ===");
        System.out.println("5! = " + RecursionExercises.factorial(5));

        System.out.println("\n=== Числа Фибоначчи ===");
        System.out.println("Наивная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(RecursionExercises.fibonacci(i) + " ");
        }
        System.out.println("\nОптимизированная реализация:");
        for(int i = 0 ; i < 30; i++) {
            System.out.println(RecursionExercises.fibonacciOptimized(i) + " ");
        }
        System.out.println("\n\n=== Проверка палиндрома ===");
        System.out.println("Палиндром 12321");
        String testStrings = "12321";
        System.out.println(RecursionExercises.isPalindrome(testStrings));

        System.out.println("\n=== Сумма цифр ===");
        System.out.println("Сумма цифр числа '12345': " + RecursionExercises.sumOfDigits(12345));

        System.out.println("\n=== Возведение в степень ===");
        System.out.println("возведения числа 2 в степени 10: " + RecursionExercises.power(2, 10));
        System.out.println("возведения числа 2 в степени -3: " + RecursionExercises.power(2, -3));

        System.out.println("\n=== Наибольший общий делитель ===");
        System.out.println("НОД чисел 48 и 36: " + RecursionExercises.gcd(48, 36));
    }
}