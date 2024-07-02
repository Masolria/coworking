package com.masolria.handleView;

import com.masolria.AppContext;
import com.masolria.AppState;
import com.masolria.controller.ConsoleController;
import com.masolria.entity.User;
import com.masolria.in.Input;
import com.masolria.out.Output;
/**
 * The Handle entry view class.
 * Represents methods for console input/output and interaction with console controller.
 */
public class HandleEntryView {

    /**
     * Handle registration.
     * Method asks credentials for registration and passes the arguments to the controller for saving.
     * @param input      the input
     * @param output     the output
     * @param controller the controller
     * @return the app state for further interaction with console.
     */
    public static AppState handleRegistration(Input input, Output output, ConsoleController controller) {
        String outputEmail = "Write your email";
        output.output(outputEmail);
        String email = input.input();
        String outputPassword = "Write your password";
        output.output(outputPassword);
        String password = input.input();
        User user = controller.register(email, password);
        AppContext.loadAuthorizedUser(user);
        return AppState.MENU;
    }
    /**
     * Handles the authorization process in the console application.
     *
     * @param input      The input object for user interaction.
     * @param output     The output object for displaying messages.
     * @param controller The controller object for handling business logic.
     * @return The next application state after successful authorization (AppState.MENU)
     */
    public static AppState handleAuthorization(Input input, Output output, ConsoleController controller) {
        String outputEmail = "Write your email";
        output.output(outputEmail);
        String email = input.input();
        String outputPassword = "Write your password";
        output.output(outputPassword);
        String password = input.input();
        User user = controller.authorize(email, password);
        AppContext.loadAuthorizedUser(user);
        return AppState.MENU;
    }
}