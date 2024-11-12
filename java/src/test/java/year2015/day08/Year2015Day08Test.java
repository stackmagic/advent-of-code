package year2015.day08;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Year2015Day08Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(12, Year2015Day08.solvePart1("example.txt"));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(1342, Year2015Day08.solvePart1("data.txt"));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(19, Year2015Day08.solvePart2("example.txt"));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(2074, Year2015Day08.solvePart2("data.txt"));
    }
}
