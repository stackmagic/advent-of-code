package year2024.day07;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

public class Year2024Day07Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(3749, Year2024Day07.solvePart1(lineStream("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(3598800864292L, Year2024Day07.solvePart1(lineStream("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(11387, Year2024Day07.solvePart2(lineStream("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(340362529351427L, Year2024Day07.solvePart2(lineStream("data.txt")));
    }
}
