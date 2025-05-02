package ru.mentee.power.methods.overloading;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DataFormatter {

  // Форматирование целого числа с разделением разрядов
  public static String format(int value) {
    if (value == 0) {
      return "0";
    }

    boolean isNegative = value < 0;
    String stringNumber = Integer.toString(Math.abs(value));

    StringBuilder formatted = new StringBuilder();
    int count = 0;

    for (int i = stringNumber.length() - 1; i >= 0; i--) {
      formatted.insert(0, stringNumber.charAt(i));
      count++;

      if (count % 3 == 0 && i != 0) {
        formatted.insert(0, ' ');
      }
    }

    return isNegative ? "-" + formatted.toString() : formatted.toString();
  }

  // Форматирование целого числа с префиксом и суффиксом
  public static String format(int value, String prefix, String suffix) {
    return prefix + format(value) + " " + suffix;
  }

  // Форматирование числа с плавающей точкой (2 знака после запятой)
  public static String format(double value) {
    return format(value, 2);
  }

  // Форматирование числа с плавающей точкой с указанным количеством знаков
  public static String format(double value, int decimalPlaces) {
    if (decimalPlaces < 0) {
      throw new IllegalArgumentException("Decimal places cannot be negative");
    }

    // Создаем собственные символы форматирования
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
    symbols.setDecimalSeparator(',');
    symbols.setGroupingSeparator(' ');

    // Строим шаблон форматирования
    StringBuilder pattern = new StringBuilder("#,##0");
    if (decimalPlaces > 0) {
      pattern.append(".");
      for (int i = 0; i < decimalPlaces; i++) {
        pattern.append("0");
      }
    }

    DecimalFormat df = new DecimalFormat(pattern.toString(), symbols);
    df.setGroupingSize(3);

    // Особый случай для decimalPlaces = 0 (без десятичной части)
    if (decimalPlaces == 0) {
      df.setMaximumFractionDigits(0);
      df.setMinimumFractionDigits(0);
    }

    return df.format(value);
  }

  // Форматирование даты в формате "дд.мм.гггг"
  public static String format(Date date) {
    if (date == null) {
      return "";
    }
    return new SimpleDateFormat("dd.MM.yyyy").format(date);
  }

  // Форматирование даты по указанному шаблону
  public static String format(Date date, String pattern) {
    if (date == null || pattern == null || pattern.isEmpty()) {
      return "";
    }
    return new SimpleDateFormat(pattern).format(date);
  }

  // Форматирование списка строк через запятую
  public static String format(List<String> items) {
    return format(items, ", ");
  }

  // Форматирование списка строк через указанный разделитель
  public static String format(List<String> items, String delimiter) {
    if (items == null || items.isEmpty()) {
      return "";
    }
    if (delimiter == null) {
      delimiter = "";
    }
    return items.stream().filter(item -> item != null && !item.isEmpty())
        .collect(Collectors.joining(delimiter));
  }
}