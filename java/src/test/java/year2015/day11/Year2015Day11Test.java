package year2015.day11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Year2015Day11Test {

    @Test
    void testPart1Example() {
        assertFalse(Year2015Day11.check("hijklmmn"));
        assertFalse(Year2015Day11.check("abbceffg"));
        assertFalse(Year2015Day11.check("abbcegjk"));
        
        assertFalse(Year2015Day11.check("abcdfffdcba"));
        assertFalse(Year2015Day11.check("abcdefgh"));
        assertTrue(Year2015Day11.check("abcdffaa"));
        assertEquals("aaab", Year2015Day11.increment("aaaa"));
        assertEquals("aaba", Year2015Day11.increment("aaaz"));
        assertEquals("bzba", Year2015Day11.increment("bzaz"));
        assertEquals("baaa", Year2015Day11.increment("azzz"));
        assertEquals("aaaaa", Year2015Day11.increment("zzzz"));
        assertEquals("abcdffaa", Year2015Day11.getNextPassword("abcdefgh"));

        assertFalse(Year2015Day11.check("ghijklmn"));
        assertTrue(Year2015Day11.check("ghjaabcc"));
        assertEquals("ghjaabcc", Year2015Day11.getNextPassword("ghijklmn"));
    }

    @Test
    void testPart1() {
        assertEquals("vzbxxyzz", Year2015Day11.getNextPassword("vzbxkghb"));
    }

    @Test
    void testPart2() {
        assertEquals("vzcaabcc", Year2015Day11.getNextPassword("vzbxxyzz"));
    }
}
