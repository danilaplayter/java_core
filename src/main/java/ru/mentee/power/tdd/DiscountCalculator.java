package ru.mentee.power.tdd;

public class DiscountCalculator {

    public double calculateDiscountedPrice(double amount) {
        double discountRate = 0.0;

        if (amount > 1000) {
            discountRate = 0.10; // 10%
        }
        if (amount > 5000) {//Исправил ошибку, вынес данную проверку в отдельный if
            discountRate = 0.20; // 20%
        }

        double discountValue = amount * discountRate;
        double finalPrice = amount - discountValue;

        System.out.printf("Сумма: %.2f, Скидка: %.0f%%, Итого: %.2f%n",
                amount, discountRate * 100, finalPrice);

        return finalPrice;
    }

    public static void main(String[] args) {
        DiscountCalculator calculator = new DiscountCalculator();

        System.out.println("Тестируем калькулятор:");
        calculator.calculateDiscountedPrice(800);    // Ожидаем: 800.00 (скидка 0%)
        calculator.calculateDiscountedPrice(1200);   // Ожидаем: 1080.00 (скидка 10%)
        calculator.calculateDiscountedPrice(5500);   // Ожидаем: 4400.00 (скидка 20%) - Тут проблема!
        calculator.calculateDiscountedPrice(6000);   // Ожидаем: 4800.00 (скидка 20%) - И тут!
    }
}