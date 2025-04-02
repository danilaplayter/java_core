package ru.mentee.power.variables;

public class StringOperations {
    public static void main(String[] args) {

        String str1 = "Hello";
        String str2 = "World";
        char ch = 'A';
        int num = 42;
        String numStr = "100";
        String language = "Java";

        String concat = str1 + str2;
        String charToString = String.valueOf(ch); // не знал функций приведения int'a к string'у
        String numToString = String.valueOf(num);
        int stringToNum = Integer.parseInt(numStr);
        char charFromString = language.charAt(1);

        System.out.println("Конкатенация: \"" + str1 + "\" + \"" + str2 + "\" = \"" + concat + "\"");
        System.out.println("Символ в строку: '" + ch + "' -> \"" + charToString + "\"");
        System.out.println("Число в строку: " + num + " -> \"" + numToString + "\"");
        System.out.println("Строка в число: \"" + numStr + "\" -> " + stringToNum);
        System.out.println("Символ из строки \"" + language + "\": индекс 1 -> '" + charFromString + "'");
    }
}