package year2024.day06;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

public class Year2024Day06Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(41, Year2024Day06.solvePart1(toCharGrid("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(4883, Year2024Day06.solvePart1(toCharGrid("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(6, Year2024Day06.solvePart2(toCharGrid("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(1655, Year2024Day06.solvePart2(toCharGrid("data.txt")));
    }
}
