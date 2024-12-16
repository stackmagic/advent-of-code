package year2024.day16;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day16Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(11048, Year2024Day16.solvePart1(toCharGrid("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(222, Year2024Day16.solvePart1(toCharGrid("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(333, Year2024Day16.solvePart2(toCharGrid("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(444, Year2024Day16.solvePart2(toCharGrid("data.txt")));
    }
}
