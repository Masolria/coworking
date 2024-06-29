package com.masolria.handleView;

import com.masolria.AppState;
import com.masolria.controller.ConsoleController;
import com.masolria.entity.Space;
import com.masolria.in.Input;
import com.masolria.out.Output;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandleSpaceViewTest {
    @Mock
    private Input input;
    @Mock
    private Output output;
    @Mock
    private ConsoleController controller;

    @Test
    void showSave() {
        when(input.input()).thenReturn("1").thenReturn("3rd floor room 34");
        HandleSpaceView.showSave(input,output,controller);

        verify(output).output("""
                Write the id of space you want to :
                1.working space
                2.conference hall
                """);
        verify(output).output("""
                Write where the room is located, e.g., '3rd floor room 34'
                """);
    }

    @Test
    void showWatchAll() {
        Space created = Space.builder().build();
        List<Space> spaces = List.of(created);
        when(controller.getAllSpaces()).thenReturn(spaces);
        HandleSpaceView.showWatchAll(output,controller);

        verify(output).output("""
                There are all existing conference halls and working spaces
                """);
        verify(output).output(created.toString());
    }

    @Test
    void delete() {
        when(input.input()).thenReturn("3");
        when(controller.getSpaceById(3L)).thenReturn(Optional.of(Space.builder().id(3L).build()));
        //doNothing().when(controller).deleteSpace(any());
        AppState result = HandleSpaceView.delete(input,output,controller);
        verify(output).output("""
                 Write  id of the space you want to delete
                 """);
        verify(controller).deleteSpace(argThat(space->space.getId().equals(3L)));
        assertThat(AppState.MENU).isEqualTo(result);
    }
}