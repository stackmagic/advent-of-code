package year2024.day15;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day15Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(10092, Year2024Day15.solvePart1(lineList("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(1438161, Year2024Day15.solvePart1(lineList("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(9021, Year2024Day15.solvePart2(lineList("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(1437981, Year2024Day15.solvePart2(lineList("data.txt")));
    }
}
