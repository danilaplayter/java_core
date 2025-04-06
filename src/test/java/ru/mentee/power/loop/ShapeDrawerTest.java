package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ShapeDrawerTest {

    private final ShapeDrawer drawer = new ShapeDrawer();

    @Test
    void testDrawSquare() {
        String expected = "***\n***\n***\n";
        String result = drawer.drawSquare(3);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawEmptySquare() {
        String expected = "***\n* *\n***\n";
        String result = drawer.drawEmptySquare(3);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawTriangle() {
        String expected = "*\n**\n***\n";
        String result = drawer.drawTriangle(3);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawRhombus() {
        String expected = " *\n***\n *\n";
        String result = drawer.drawRhombus(3);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testWithZeroOrNegativeSize() {
        String ERROR = "Ошибка";
        assertThat(drawer.drawEmptySquare(-1)).isEqualTo(ERROR);
        assertThat(drawer.drawRhombus(-1)).isEqualTo(ERROR);
        assertThat(drawer.drawSquare(-1)).isEqualTo(ERROR);
        assertThat(drawer.drawTriangle(-1)).isEqualTo(ERROR);
        assertThat(drawer.drawEmptySquare(0)).isEqualTo(ERROR);
        assertThat(drawer.drawRhombus(0)).isEqualTo(ERROR);
        assertThat(drawer.drawSquare(0)).isEqualTo(ERROR);
        assertThat(drawer.drawTriangle(0)).isEqualTo(ERROR);
    }

    @Test
    void testWithLargeSize() {
        //можете подсказать какое-то лаконичное решение?
    }

    @Test
    void testRhombusWithEvenSize() {
       assertThat(drawer.drawRhombus(1)).isEqualTo(" *\n***\n *\n");
        assertThat(drawer.drawRhombus(3)).isEqualTo("  *\n ***\n*****\n ***\n  *\n");
    }
}