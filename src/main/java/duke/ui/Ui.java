package duke.ui;

import java.util.Scanner;

import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_WELCOME;

public class Ui {

    /**
     * This variable is declared for the whole class (instead of declaring it
     * inside the getCommand() method to facilitate automated testing using
     * the I/O redirection technique. If not, only the first line of the input
     * text file will be processed.
     */
    public static final Scanner SCANNER = new Scanner(System.in);

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
