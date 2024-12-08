package year2024.day08;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day08Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(14, Year2024Day08.solvePart1(toCharGrid("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(369, Year2024Day08.solvePart1(toCharGrid("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(34, Year2024Day08.solvePart2(toCharGrid("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(1169, Year2024Day08.solvePart2(toCharGrid("data.txt")));
    }
}
