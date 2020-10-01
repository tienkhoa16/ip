package duke.components;

import static duke.constants.CommandConstants.SCANNER;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_WELCOME;

/**
 * A class deals with interactions with the user.
 */
public class Ui {

    /**
     * Shows a result message to the user.
     *
     * @param result Result message to be displayed.
     */
    public void showResultToUser(String result) {
        System.out.println(result);
    }

    /**
     * Greets user.
     */
    public void greetUser() {
        showResultToUser(String.format(MESSAGE_FORMAT, MESSAGE_WELCOME));
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     *
     * @return Full line entered by the user.
     */
    public String getCommand() {
        System.out.print("Enter your command: ");

        String inputLine = SCANNER.nextLine();

        // Silently consume all blank lines
        while (inputLine.trim().isEmpty()) {
            System.out.print("Enter your command: ");
            inputLine = SCANNER.nextLine();
        }

        return inputLine;
    }
}
