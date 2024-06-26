package com.masolria.in;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputTest {
    @Test
    void input() {
        Scanner scannerMock = mock(Scanner.class);
        Input input = new Input();
        input.scanner = scannerMock;
        when(scannerMock.nextLine()).thenReturn("test input");
        assertEquals("test input", input.input());
        verify(scannerMock, times(1)).nextLine();
    }

}