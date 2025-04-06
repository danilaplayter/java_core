package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTest {

    @Test
    public void testFizzBuzzForFirst15Numbers() {

        FizzBuzz fizzBuzz = new FizzBuzz();

        String[] result = fizzBuzz.generateFizzBuzz(15);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(15);

        assertThat(result[0]).isEqualTo("1");    // 1
        assertThat(result[1]).isEqualTo("2");    // 2
        assertThat(result[2]).isEqualTo("Fizz"); // 3
        assertThat(result[4]).isEqualTo("Buzz"); // 5
        assertThat(result[14]).isEqualTo("FizzBuzz"); // 15
    }

    @Test
    public void testFizzBuzzWithZeroInput() {

        FizzBuzz fizzBuzz = new FizzBuzz();
        String[] result = fizzBuzz.generateFizzBuzz(0);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void testAllFizzValuesAreDivisibleBy3() {

        FizzBuzz fizzBuzz = new FizzBuzz();
        String[] result = fizzBuzz.generateFizzBuzz(3);

        assertThat(result[2]).isEqualTo("Fizz");
    }

    @Test
    public void testAllBuzzValuesAreDivisibleBy5() {

        FizzBuzz fizzBuzz = new FizzBuzz();
        String[] result = fizzBuzz.generateFizzBuzz(5);

        assertThat(result[4]).isEqualTo("Buzz");

    }

    @Test
    public void testAllFizzBuzzValuesAreDivisibleBy3And5() {

        FizzBuzz fizzBuzz= new FizzBuzz();
        String[] result = fizzBuzz.generateFizzBuzz(15);

        assertThat(result[14]).isEqualTo("FizzBuzz");

    }
}
