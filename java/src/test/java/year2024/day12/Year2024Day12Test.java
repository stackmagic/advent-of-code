package year2024.day12;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day12Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(1930, Year2024Day12.solvePart1(toCharGrid("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(1473408, Year2024Day12.solvePart1(toCharGrid("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(1206, Year2024Day12.solvePart2(toCharGrid("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(886364, Year2024Day12.solvePart2(toCharGrid("data.txt")));
    }
}
