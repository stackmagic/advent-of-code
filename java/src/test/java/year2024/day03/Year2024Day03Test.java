package year2024.day03;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.file;

public class Year2024Day03Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(161, Year2024Day03.solvePart1("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(164730528, Year2024Day03.solvePart1(file("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(48, Year2024Day03.solvePart2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(70478672, Year2024Day03.solvePart2(file("data.txt")));
    }
}
