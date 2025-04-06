package ru.mentee.power.loop;

import java.util.Scanner;

public class FizzBuzz {

    public String[] generateFizzBuzz(int n) {

        String[] result = new String[n];

        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                result[i - 1] = "FizzBuzz";
            } else {
                if (i % 3 == 0) {
                    result[i - 1] = "Fizz";
                } else {
                    if (i % 5 == 0) {
                        result[i - 1] = "Buzz";
                    } else {
                        result[i - 1] = String.valueOf(i);
                    }
                }
            }
        }

        return result;
    }


    public void printFizzBuzz(int n) {

        String[] results = generateFizzBuzz(n);

        for (String result : results) {
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FizzBuzz fizzBuzz = new FizzBuzz();
        System.out.print("Введите число:");
        fizzBuzz.printFizzBuzz(scanner.nextInt());
    }

}