package year2024.day13;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.lineList;

class Year2024Day13Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(480, Year2024Day13.solvePart1(lineList("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(32026, Year2024Day13.solvePart1(lineList("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(875318608908L, Year2024Day13.solvePart2(lineList("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(89013607072065L, Year2024Day13.solvePart2(lineList("data.txt")));
    }
}
