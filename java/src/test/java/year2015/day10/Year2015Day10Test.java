package year2015.day10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Year2015Day10Test {

    @Test
    void testExample() {
        assertEquals("11", Year2015Day10.process("1"));
        assertEquals("21", Year2015Day10.process("11"));
        assertEquals("1211", Year2015Day10.process("21"));
        assertEquals("111221", Year2015Day10.process("1211"));
        assertEquals("312211", Year2015Day10.process("111221"));
    }

    @Test
    void testPart1() {
        assertEquals(252594, run(40));
    }

    @Test
    void testPart2() {
        assertEquals(3579328, run(50));
    }

    int run(int rounds) {
        String value = "1113222113";
        for (int i = 0; i < rounds; i++) {
            value = Year2015Day10.process(value);
        }
        return value.length();
    }
}
