package handleView;

import InputOutput.Input;
import InputOutput.Output;
import controller.ConsoleController;
import service.EntryService;

public class HandleEntryView {
    EntryService entryService;

    public void handleRegistration(Input input, Output output, ConsoleController controller ) {
        String outputEmail = "Write your email";
        output.output(outputEmail);
        String email = input.input();
        String outputPassword = "Write your password";
        output.output(outputPassword);
        String password = input.input();
        entryService.register(email, password);

    }

    public void handleAuthorization(Input input, Output output, ConsoleController controller) {
        String outputEmail = "Write your email";
        output.output(outputEmail);
        String email = input.input();
        String outputPassword = "Write your password";
        output.output(outputPassword);
        String password = input.input();
        entryService.authorize(email, password);
    }

}
