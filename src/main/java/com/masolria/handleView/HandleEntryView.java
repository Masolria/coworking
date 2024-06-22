package com.masolria.handleView;

import com.masolria.AppContext;
import com.masolria.AppState;
import com.masolria.InputOutput.Input;
import com.masolria.InputOutput.Output;
import com.masolria.controller.ConsoleController;
import com.masolria.entity.User;


public class HandleEntryView {

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
