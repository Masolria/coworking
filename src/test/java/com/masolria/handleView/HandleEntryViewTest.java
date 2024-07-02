package com.masolria.handleView;


import com.masolria.controller.ConsoleController;
import com.masolria.entity.User;
import com.masolria.in.Input;
import com.masolria.out.Output;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HandleEntryViewTest {
    @Mock
    ConsoleController controller;
    @Mock
    Input input;
    @Mock
    Output output;

    @Test
    void handleRegistration() {
        when(input.input()).thenReturn("mail@mail.com").thenReturn("password");

        HandleEntryView.handleRegistration(input, output, controller);
        verify(output).output("Write your email");
        verify(output).output("Write your password");
    }

    @Test
    void handleAuthorization() {
        when(input.input()).thenReturn("mail@mail.com").thenReturn("password");
        when(controller.authorize("mail@mail.com", "password")).thenReturn(new User());
        HandleEntryView.handleAuthorization(input, output, controller);
        verify(output).output("Write your email");
        verify(output).output("Write your password");
    }
}