import duke.storage.Storage;
import duke.task.TaskList;
import duke.util.Util;

import java.util.Scanner;

import static duke.constant.Constant.COMMAND_BYE_WORD;
import static duke.constant.Constant.COMMAND_DEADLINE_WORD;
import static duke.constant.Constant.COMMAND_DELETE_WORD;
import static duke.constant.Constant.COMMAND_DONE_WORD;
import static duke.constant.Constant.COMMAND_EVENT_WORD;
import static duke.constant.Constant.COMMAND_LIST_WORD;
import static duke.constant.Constant.COMMAND_TODO_WORD;
import static duke.constant.Constant.HORIZONTAL_LINE;
import static duke.constant.Constant.LINE_PREFIX;

public class Duke {

    /**
     * This variable is declared for the whole class (instead of declaring it
     * inside the getCommand() method to facilitate automated testing using
     * the I/O redirection technique. If not, only the first line of the input
     * text file will be processed.
     */
    public static final Scanner SCANNER = new Scanner(System.in);

    /** List of all tasks */
    protected static TaskList tasks;

    public static void main(String[] args) {
        // Initialize tasks list
        tasks = Storage.loadData();

        // Greet user
        printHello();

        while (true) {
            String userCommand = getCommand();
            String feedback = replyCommand(userCommand);
            Util.showResultToUser(feedback);
        }
    }

    /**
     * Greets user.
     */
    public static void printHello() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(LINE_PREFIX + "Hello dude! I'm Duke");
        System.out.println(LINE_PREFIX + "How can I help you?");
        System.out.println(HORIZONTAL_LINE + System.lineSeparator());
    }

    /**
     * Farewells user.
     */
    public static void printFarewell() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(LINE_PREFIX + "Bye buddy. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     *
     * @return Full line entered by the user.
     */
    public static String getCommand() {
        System.out.print("Enter your command: ");
        String inputLine = SCANNER.nextLine();
        // Silently consume all blank lines
        while (inputLine.trim().isEmpty()) {
            System.out.print("Enter your command: ");
            inputLine = SCANNER.nextLine();
        }
        return inputLine;
    }

    /**
     * Replies to user's command.
     *
     * @param userInputString Raw input from user.
     * @return Feedback about how the command was executed.
     */
    public static String replyCommand(String userInputString) {

        String[] commandTypeAndParams = Util.splitCommandWordAndArgs(userInputString);
        String commandType = commandTypeAndParams[0].toLowerCase();
        String commandArgs = commandTypeAndParams[1];

        switch (commandType) {
        case COMMAND_LIST_WORD:
            return tasks.executeListAllTasks();
        case COMMAND_DONE_WORD:
            return tasks.executeMarkTaskAsDone(commandArgs);
        case COMMAND_TODO_WORD:
            return tasks.executeAddTodo(commandArgs);
        case COMMAND_DEADLINE_WORD:
            return tasks.executeAddDeadline(commandArgs);
        case COMMAND_EVENT_WORD:
            return tasks.executeAddEvent(commandArgs);
        case COMMAND_DELETE_WORD:
            return tasks.executeDeleteTask(commandArgs);
        case COMMAND_BYE_WORD:
            executeExitProgramRequest();
            // Fallthrough
        default:
            return tasks.getMessageForInvalidInputWord();
        }
    }

    /**
     * Requests to terminate the program.
     */
    public static void executeExitProgramRequest() {
        exitProgram();
    }

    /**
     * Displays the farewell message and exits the runtime.
     */
    public static void exitProgram() {
        printFarewell();
        System.exit(0);
    }

}
