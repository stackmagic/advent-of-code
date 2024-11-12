package year2015.day04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Year2015Day04Test {

    @Test
    void solvePart1() throws Exception {
        assertEquals(346386, Year2015Day04.solve("00000"));
    }

    @Test
    void solvePart2() throws Exception {
        assertEquals(9958218, Year2015Day04.solve("000000"));
    }
}
