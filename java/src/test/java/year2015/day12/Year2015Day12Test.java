package year2015.day12;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.file;

class Year2015Day12Test {

    @Test
    void testPart1Example() {
        assertEquals(6, Year2015Day12.process("[1,2,3]"));
        assertEquals(6, Year2015Day12.process("{\"a\":2,\"b\":4}"));
        assertEquals(3, Year2015Day12.process("[[[3]]]"));
        assertEquals(3, Year2015Day12.process("{\"a\":{\"b\":4},\"c\":-1}"));
        assertEquals(0, Year2015Day12.process("{\"a\":[-1,1]}"));
        assertEquals(0, Year2015Day12.process("[-1,{\"a\":1}]"));
        assertEquals(0, Year2015Day12.process("[]"));
        assertEquals(0, Year2015Day12.process("{}"));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(156366, Year2015Day12.process(file("data.json")));
    }

    @Test
    void testPart2Example() {
        assertEquals(6, Year2015Day12.processNoRed("[1,2,3]"));
        assertEquals(4, Year2015Day12.processNoRed("[1,{\"c\":\"red\",\"b\":2},3]"));
        assertEquals(0, Year2015Day12.processNoRed("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"));
        assertEquals(6, Year2015Day12.processNoRed("[1,\"red\",5]"));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(96852, Year2015Day12.processNoRed(file("data.json")));
    }
}
