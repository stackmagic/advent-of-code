package year2024.day02;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Year2024Day02Test {

    @Test
    void testPart1Example() {
        assertTrue(Year2024Day02.isSafePart1(List.of(7, 6, 4, 2, 1)));
        assertFalse(Year2024Day02.isSafePart1(List.of(1, 2, 7, 8, 9)));
        assertFalse(Year2024Day02.isSafePart1(List.of(9, 7, 6, 2, 1)));
        assertFalse(Year2024Day02.isSafePart1(List.of(1, 3, 2, 4, 5)));
        assertFalse(Year2024Day02.isSafePart1(List.of(8, 6, 4, 4, 1)));
        assertTrue(Year2024Day02.isSafePart1(List.of(1, 3, 6, 7, 9)));
        assertEquals(2, Year2024Day02.solvePart1("example.txt"));
    }

    @Test
    void testPart1() {
        assertEquals(371, Year2024Day02.solvePart1("data.txt"));
    }

    @Test
    void testPart2Example() {
        assertEquals(4, Year2024Day02.solvePart2("example.txt"));
    }

    @Test
    void testPart2() {
        assertEquals(426, Year2024Day02.solvePart2("data.txt"));
    }
}
