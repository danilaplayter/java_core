package ru.mentee.power.tdd;

import java.util.List;

public class AdvancedCalculator {

    public int sumIgnoringOver1000(List<Integer> numbers) {
        if (numbers == null) { // Обработка null списка
            return 0;
        }
        int sum = 0;
        for (Integer number : numbers) {//Измен int на Integer, чтобы можно было проверить на null
            if (number != null && number <= 1000) {
                sum += number;
            }
        }
        return sum;
    }
}