package year2024.day04;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

public class Year2024Day04Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(18, Year2024Day04.solvePart1(file("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(2414, Year2024Day04.solvePart1(file("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(9, Year2024Day04.solvePart2(file("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(1871, Year2024Day04.solvePart2(file("data.txt")));
    }
}
