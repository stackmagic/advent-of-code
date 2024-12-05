package year2024.day05;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

public class Year2024Day05Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(143, Year2024Day05.solvePart1(lineStream("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(6384, Year2024Day05.solvePart1(lineStream("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(123, Year2024Day05.solvePart2(lineStream("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(5353, Year2024Day05.solvePart2(lineStream("data.txt")));
    }
}
