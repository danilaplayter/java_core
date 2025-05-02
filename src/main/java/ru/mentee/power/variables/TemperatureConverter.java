package ru.mentee.power.variables;

public class TemperatureConverter {

  /*public static final double ABSOLUTE_ZERO_KELVIN = 0;
  public static final double ABSOLUTE_ZERO_CELSIUS = -273.15;
  public static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;
  */ //закинул в коментарии, потому что idea не даёт сделать commit and push
  public static void main(String[] args) {

    double celsius = 25;
    double fahrenheit = 77;
    double kelvin = 298.15;

    double cToF = celsiusToFahrenheit(celsius);
    double fToC = fahrenheitToCelsius(fahrenheit);
    double cToK = celsiusToKelvin(celsius);
    double kToC = kelvinToCelsius(kelvin);

    // Вывод результатов
    System.out.println("=== Конвертер температур ===");
    System.out.printf("%.2f°C = %.2f°F\n", celsius, cToF);
    System.out.printf("%.2f°F = %.2f°C\n", fahrenheit, fToC);
    System.out.printf("%.2f°C = %.2fK\n", celsius, cToK);
    System.out.printf("%.2fK = %.2f°C\n", kelvin, kToC);
  }

  public static double celsiusToFahrenheit(double celsius) {
    return (celsius * 9 / 5) + 32;
  }

  public static double fahrenheitToCelsius(double fahrenheit) {
    return (fahrenheit - 32) * 5 / 9;
  }

  public static double celsiusToKelvin(double celsius) {
    return celsius + 273.15;
  }

  public static double kelvinToCelsius(double kelvin) {
    return kelvin - 273.15;
  }
}