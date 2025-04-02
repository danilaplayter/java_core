package ru.mentee.power.variables;

public class ConstantAndScope {
    // Константы класса
    public static final double PI = 3.14159;
    private static final String APP_NAME = "ScopeDemo";

    public static void main(String[] args) {

        int localVar = 10;
        final int LOCAL_CONST = 20;

        {
            int blockVar = 30;
            System.out.println("Внутри блока:");
            System.out.println("blockVar = " + blockVar);
            System.out.println("localVar = " + localVar);
            System.out.println("LOCAL_CONST = " + LOCAL_CONST);
            System.out.println("PI = " + PI);
        }

        System.out.println("\nВне блока:");
        System.out.println("localVar = " + localVar);
        System.out.println("APP_NAME = " + APP_NAME);

        someMethod();
    }

    public static void someMethod() {
        int methodVar = 50;
        System.out.println("\nВ методе someMethod:");
        System.out.println("methodVar = " + methodVar);
        System.out.println("PI = " + PI);
    }
}