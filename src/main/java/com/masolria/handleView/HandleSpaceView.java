package com.masolria.handleView;

import com.masolria.AppState;

import com.masolria.controller.ConsoleController;
import com.masolria.entity.Space;
import com.masolria.entity.SpaceType;
import com.masolria.in.Input;
import com.masolria.out.Output;


import java.util.List;

public class HandleSpaceView {
    public static void showSave(Input input, Output output, ConsoleController controller) {
        String spaceOutput = """
                Write the id of space you want to :
                1.working space
                2.conference hall
                """;

        output.output(spaceOutput);

        String stringType = input.input();
        SpaceType spaceType;
        if ("1".equals(stringType)) {
            spaceType = SpaceType.WorkingSpace;
        } else if (stringType.equals("2")) {
            spaceType = SpaceType.ConferenceHall;
        }else {
            output.output("Cannot add space with given type");
            return;
        }
        String locationOutput = """
                Write where the room is located, e.g., '3rd floor room 34'
                """;
        output.output(locationOutput);
        String location = input.input();
        Space space = Space.builder().spaceType(spaceType).location(location).build();
        controller.saveSpace(space);
        output.output("Your space added successfully");
    }

    public static void showWatchAll(Output output, ConsoleController controller) {
        String preWatchAll = """
                There are all existing conference halls and working spaces
                """;
        output.output(preWatchAll);
        List<Space> spaces = controller.getAllSpaces();
        for (Space space : spaces) {
            output.output(space.toString());
        }
    }
    //TODO использовать в консоли
    public static AppState delete(Input input, Output output, ConsoleController controller) {
        String preDeletion =
                """
                        Write  id of the space you want to delete
                        """;
        output.output(preDeletion);
        try {
            Long id = Long.parseLong(input.input());
            controller.deleteSpace(controller.getSpaceById(id).get());
        } catch (NumberFormatException e) {
            output.output("your input is not correct");
        }
        return AppState.MENU;
    }


}
