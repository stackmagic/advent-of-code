package year2024.day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Year2024Day01Test {

    @Test
    void testPart1Example() {
        assertEquals(11, Year2024Day01.solvePart1("example.txt"));
    }

    @Test
    void testPart1() {
        assertEquals(2367773, Year2024Day01.solvePart1("data.txt"));
    }

    @Test
    void testPart2Example() {
        assertEquals(31, Year2024Day01.solvePart2("example.txt"));
    }

    @Test
    void testPart2() {
        assertEquals(21271939, Year2024Day01.solvePart2("data.txt"));
    }
}
