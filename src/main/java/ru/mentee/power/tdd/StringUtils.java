package ru.mentee.power.tdd;

public class StringUtils {

    public String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString(); // Пример возможной реализации
    }
}