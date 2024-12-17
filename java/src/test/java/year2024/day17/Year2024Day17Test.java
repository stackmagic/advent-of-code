package year2024.day17;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

class Year2024Day17Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals("4,6,3,5,6,3,5,2,1,0", Year2024Day17.solvePart1(lineList("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals("asd", Year2024Day17.solvePart1(lineList("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals("asd", Year2024Day17.solvePart2(lineList("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals("we", Year2024Day17.solvePart2(lineList("data.txt")));
    }
}
