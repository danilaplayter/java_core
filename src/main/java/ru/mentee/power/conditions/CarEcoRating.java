package ru.mentee.power.conditions;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CarEcoRating {

    private static final int ERROR_CODE = -1;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 100;
    private static final int EURO_STANDARD_YEAR_THRESHOLD = 2020;

    private static final int BASE_RATING_ELECTRIC = 90;
    private static final int BASE_RATING_HYBRID = 70;
    private static final int BASE_RATING_DIESEL = 40;
    private static final int BASE_RATING_PETROL = 30;

    private static final List<String> VALID_FUEL_TYPES = Arrays.asList("Бензин", "Дизель", "Гибрид", "Электро");

    public int calculateEcoRating(String fuelType, double engineVolume,
                                  double fuelConsumption, int yearOfManufacture,
                                  boolean isEuroCompliant) {
        if (!validateInput(fuelType, engineVolume, fuelConsumption, yearOfManufacture)) {
            return ERROR_CODE;
        }

        int baseRating = getBaseFuelTypeRating(fuelType);
        int modifiedRating = applyRatingModifiers(baseRating, fuelType, engineVolume,
                fuelConsumption, yearOfManufacture, isEuroCompliant);

        return clampRating(modifiedRating);
    }

    private boolean validateInput(String fuelType, double engineVolume,
                                  double fuelConsumption, int yearOfManufacture) {
        if (!VALID_FUEL_TYPES.contains(fuelType)) {
            return false;
        }
        if (fuelType.equals("Электро") && engineVolume != 0) {
            return false;
        }
        if (engineVolume < 0 || fuelConsumption < 0) {
            return false;
        }
        return yearOfManufacture <= 2025;
    }

    private int getBaseFuelTypeRating(String fuelType) {
        return switch (fuelType) {
            case "Бензин" -> BASE_RATING_PETROL;
            case "Дизель" -> BASE_RATING_DIESEL;
            case "Гибрид" -> BASE_RATING_HYBRID;
            case "Электро" -> BASE_RATING_ELECTRIC;
            default -> ERROR_CODE;
        };
    }

    private int applyRatingModifiers(int baseRating, String fuelType, double engineVolume,
                                     double fuelConsumption, int yearOfManufacture,
                                     boolean isEuroCompliant) {
        double rating = baseRating;

        if (!fuelType.equals("Электро")) {
            if (engineVolume > 2.0) {
                rating -= 15;
            } else if (engineVolume > 1.6) {
                rating -= 10;
            } else if (engineVolume > 1.2) {
                rating -= 5;
            }
        }

        if (fuelType.equals("Бензин") || fuelType.equals("Дизель")) {
            if (fuelConsumption > 10) {
                rating -= 15;
            } else if (fuelConsumption > 7) {
                rating -= 10;
            } else if (fuelConsumption > 5) {
                rating -= 5;
            }
        }

        int currentYear = Year.now().getValue();
        int age = currentYear - yearOfManufacture;
        if (age > 10) {
            rating -= 20;
        } else if (age > 5) {
            rating -= 10;
        }

        if (isEuroCompliant && yearOfManufacture >= EURO_STANDARD_YEAR_THRESHOLD) {
            rating += 10;
        }
        if (fuelType.equals("Гибрид") && fuelConsumption < 5) {
            rating += 5;
        }

        return (int) Math.round(rating);
    }

    private int clampRating(int rating) {
        return Math.max(MIN_RATING, Math.min(rating, MAX_RATING));
    }

    public static void main(String[] args) {
        CarEcoRating ecoRating = new CarEcoRating();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Тип топлива (Бензин, Дизель, Гибрид, Электро):");
        String type = scanner.nextLine();

        System.out.println("Объем двигателя (л):");
        double volume = scanner.nextDouble();

        System.out.println("Расход топлива (л/100км):");
        double consumption = scanner.nextDouble();

        System.out.println("Год выпуска:");
        int year = scanner.nextInt();

        System.out.println("Соответствует стандарту Евро-6 (true/false):");
        boolean isEuro = scanner.nextBoolean();

        int rating = ecoRating.calculateEcoRating(type, volume, consumption, year, isEuro);
        if (rating == ERROR_CODE) {
            System.out.println("Ошибка: неверные входные данные");
        } else {
            System.out.println("Эко-рейтинг автомобиля: " + rating);
        }

        scanner.close();
    }
}