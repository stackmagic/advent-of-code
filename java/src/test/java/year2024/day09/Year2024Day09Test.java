package year2024.day09;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.file;

class Year2024Day09Test {

    @Test
    void testPart1Example() throws IOException {
        List<Integer> blockMap = Year2024Day09.toBlockMap(file("example.txt"));
        assertEquals("00...111...2...333.44.5555.6666.777.888899", Year2024Day09.toString(blockMap));

        Year2024Day09.compact(blockMap);
        assertEquals("0099811188827773336446555566..............", Year2024Day09.toString(blockMap));

        assertEquals(1928, Year2024Day09.solvePart1(file("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(6367087064415L, Year2024Day09.solvePart1(file("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        List<Integer> blockMap = Year2024Day09.toBlockMap(file("example.txt"));
        assertEquals("00...111...2...333.44.5555.6666.777.888899", Year2024Day09.toString(blockMap));

        Year2024Day09.compact2(blockMap);
        assertEquals("00992111777.44.333....5555.6666.....8888..", Year2024Day09.toString(blockMap));

        assertEquals(2858, Year2024Day09.solvePart2(file("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(6390781891880L, Year2024Day09.solvePart2(file("data.txt")));
    }
}
