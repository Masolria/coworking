package com.masolria.out;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
class OutputTest {
    @Test
    void output() {
        Output output = new Output();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        output.output("Test message");
        String actualOutput = outputStream.toString().trim();
        assertEquals("Test message", actualOutput);
    }
}