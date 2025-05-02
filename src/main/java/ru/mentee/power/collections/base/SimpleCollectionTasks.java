package ru.mentee.power.collections.base;

import java.util.List;

public class SimpleCollectionTasks {

  public static int countStringsStartingWith(List<String> strings, char startChar) {
    if (strings == null || strings.isEmpty()) {
      return 0;
    }

    int amountStringsStartingWith = 0;
    char lowerCaseStartChar = Character.toLowerCase(startChar);

    for (String string : strings) {
      if (string == null || string.isEmpty()) {
        continue;
      }
      if (Character.toLowerCase(string.charAt(0)) == lowerCaseStartChar) {
        amountStringsStartingWith++;
      }
    }
    return amountStringsStartingWith;
  }
}