package com.masolria;

import com.masolria.InputOutput.Input;
import com.masolria.InputOutput.Output;
import com.masolria.controller.ConsoleController;


import static com.masolria.handleView.HandleBookingView.*;
import static com.masolria.handleView.HandleEntryView.handleAuthorization;
import static com.masolria.handleView.HandleEntryView.handleRegistration;
import static com.masolria.handleView.HandleSpaceView.showSave;
import static com.masolria.handleView.HandleSpaceView.showWatchAll;

public class AppRunner {
    static final String ENTRY = """
            Choose action:
            1.registration
            2.authorization
            3.quit
            """;
    static final String BOOKING_ACTIONS = """
            Choose action:
            1.watch free slots for booking
            2.book working space/conference hall
            3.watch slots for a specific date
            4.watch slots for a specific user
            5.watch slots  for a specific type of space
            6.release booking
            7.quit
            """;
    static final String SPACE_ACTIONS = """
            1.watch all existing spaces
            2.add new working space/conference hall
            3.quit
            """;

    static final String MENU_ACTIONS = """
            Choose section
            1.booking
            2.spaces
            3.quit
            """;
    static AppState appState = AppState.ENTRY;

    public static void run() {
        AppContext.loadForInjection();
        ConsoleController controller = (ConsoleController) AppContext.getBean("consoleController");
        Input input = (Input) AppContext.getBean("input");
        Output output = (Output) AppContext.getBean("output");
        boolean isAppRunning = true;
        while (isAppRunning) {
            switch (appState) {
                case MENU -> runMenu(input, output, controller);
                case ENTRY -> runEntry(input, output, controller);
                case BOOKINGS -> runBookings(input, output, controller);
                case SPACES -> runSpaces(input, output, controller);
                case QUIT -> isAppRunning = false;
            }
        }
    }

    private static void runSpaces(Input input, Output output, ConsoleController controller) {
        output.output(SPACE_ACTIONS);
        switch (input.input()) {
            case "1" -> showWatchAll(output, controller);
            case "2" -> showSave(input, output, controller);
            case "3" -> appState = AppState.QUIT;
            default -> output.output("Write one of the given commands");
        }
    }

        private static void runBookings (Input input, Output output, ConsoleController controller){
            output.output(BOOKING_ACTIONS);
            switch (input.input()) {
                case "1" -> showFreeSlots(output, controller);
                case "2" -> ShowReserve(input, output, controller);
                case "3" -> ShowByDate(input, output, controller);
                case "4" -> ShowByUser(input, output, controller);
                case "5" -> ShowByType(input, output, controller);
                case "6"->ShowReleaseBooking(input,output,controller);
                case "7" -> appState = AppState.QUIT;
                default -> output.output("Write one of the given commands");

            }
        }


        private static void runMenu (Input input, Output output, ConsoleController controller){
            output.output(MENU_ACTIONS);
            int section = Integer.parseInt(input.input());
            if (section == 1) {
                appState = AppState.BOOKINGS;
            } else if (section == 2) {
                appState = AppState.SPACES;
            } else if (section == 3) {
                appState = AppState.QUIT;
            } else {
                output.output("Write correct number");
            }
        }

        private static void runEntry (Input input, Output output, ConsoleController controller){
            output.output(ENTRY);
            switch (input.input()) {
                case "1" -> appState =handleRegistration(input, output, controller);
                case "2" -> appState =handleAuthorization(input, output, controller);
                case "3" -> appState = AppState.QUIT;
                default -> output.output("Write one of the given commands");
            }
        }
    }
