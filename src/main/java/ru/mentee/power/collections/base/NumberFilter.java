package ru.mentee.power.collections.base;

import java.util.List;
import java.util.ArrayList;

public class NumberFilter {

    public static List<Integer> filterEvenNumbers(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        if (numbers == null) {
            return result;
        }
        if (numbers.isEmpty()) {
            return result;
        }
        for (Integer number : numbers) {
            if(number == null){
                continue;
            }
            if (number % 2 == 0) {
                result.add(number);
            }
        }
        return result;
    }
}