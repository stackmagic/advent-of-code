package year2024.day14;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.lineStream;

class Year2024Day14Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(12, Year2024Day14.solvePart1(lineStream("example.txt"), 11, 7));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(218619120, Year2024Day14.solvePart1(lineStream("data.txt"), 101, 103));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(7055, Year2024Day14.solvePart2(lineStream("data.txt"), 101, 103));
    }
}
