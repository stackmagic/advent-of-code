package year2024.day11;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day11Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(55312, Year2024Day11.solvePart1(file("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(172484, Year2024Day11.solvePart1(file("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(65601038650482L, Year2024Day11.solvePart2(file("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(205913561055242L, Year2024Day11.solvePart2(file("data.txt")));
    }
}
