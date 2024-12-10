package year2024.day10;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day10Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(36, Year2024Day10.solvePart1(toCharGrid("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(582, Year2024Day10.solvePart1(toCharGrid("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(81, Year2024Day10.solvePart2(toCharGrid("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(1302, Year2024Day10.solvePart2(toCharGrid("data.txt")));
    }
}
