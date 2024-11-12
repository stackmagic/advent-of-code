package year2015.day07;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Year2015Day07Test {

    @Test
    void testPart() throws IOException {
        Year2015Day07.setup();
        char sigA = Year2015Day07.evaluate();
        assertEquals(16076, sigA);

        Year2015Day07.overrideSignalB(sigA);
        sigA = Year2015Day07.evaluate();
        assertEquals(2797, sigA);
    }
}
