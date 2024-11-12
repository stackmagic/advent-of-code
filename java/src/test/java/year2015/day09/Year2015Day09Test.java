package year2015.day09;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Year2015Day09Test {

    @Test
    void solvePart1Example() throws IOException {
        assertEquals(605, Year2015Day09.solvePart1("example.txt"));
    }

    @Test
    void solvePart1() throws IOException {
        assertEquals(141, Year2015Day09.solvePart1("data.txt"));
    }

    @Test
    void solvePart2Example() throws IOException {
        assertEquals(982, Year2015Day09.solvePart2("example.txt"));
    }

    @Test
    void solvePart2() throws IOException {
        assertEquals(736, Year2015Day09.solvePart2("data.txt"));
    }
}
