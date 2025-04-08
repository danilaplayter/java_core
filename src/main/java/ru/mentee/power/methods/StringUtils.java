package ru.mentee.power.methods;

public class StringUtils {

    public static int countChars(String str, char target) {
        if (str == null) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }

    public static String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }

        if (maxLength <= 3) {
            return str.length() > maxLength ? str.substring(0, maxLength) : str;
        }

        if (str.length() <= maxLength) {
            return str;
        }

        return str.substring(0, maxLength) + "...";
    }

    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        String cleaned = str.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();
        int length = cleaned.length();

        for (int i = 0; i < length / 2; i++) {
            if (cleaned.charAt(i) != cleaned.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static String normalizeSpaces(String str) {
        if (str == null) {
            return "";
        }

        return str.trim().replaceAll("\\s+", " ");
    }

    public static String join(String[] strings, String delimiter) {
        if (strings == null) {
            return "";
        }

        if (delimiter == null) {
            delimiter = "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                result.append(strings[i]);
            }
            else {
                continue;
            }

            if (i < strings.length - 1) {
                result.append(delimiter);
            }
        }
        return result.toString();
    }
}