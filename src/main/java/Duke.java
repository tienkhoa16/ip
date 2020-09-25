import duke.commons.utils.Utils;
import duke.storage.Storage;
import duke.task.TasksList;
import duke.ui.Ui;

import static duke.commons.constants.CommandWords.COMMAND_BYE_WORD;
import static duke.commons.constants.CommandWords.COMMAND_DEADLINE_WORD;
import static duke.commons.constants.CommandWords.COMMAND_DELETE_WORD;
import static duke.commons.constants.CommandWords.COMMAND_DONE_WORD;
import static duke.commons.constants.CommandWords.COMMAND_EVENT_WORD;
import static duke.commons.constants.CommandWords.COMMAND_LIST_WORD;
import static duke.commons.constants.CommandWords.COMMAND_TODO_WORD;

public class Duke {
    private Storage storage;
    private static TasksList tasks;
    private Ui ui;

    /**
     * Constructs Duke object by instantiating Ui, Storage and TaskManager classes
     * then loads saved data if any.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        tasks = storage.loadData();
    }

    public static void main(String[] args) {
        new Duke().run();
    }

    private void runCommandLoop() {
        while (true) {
            String userCommand = ui.getCommand();
            String feedback = executeCommand(userCommand);
            ui.showResultToUser(feedback);
        }
    }

    /**
     * Runs Duke.
     */
    public void run() {
        start();
        runCommandLoop();
    }

    /**
     * Starts up Duke with welcome message and past tasks list.
     */
    private void start() {
        ui.printHello();
        ui.showResultToUser(tasks.executeListAllTasks());
    }

    /**
     * Replies to user's command.
     *
     * @param userInputString Raw input from user.
     * @return Feedback about how the command was executed.
     */
    public String executeCommand(String userInputString) {

        String[] commandTypeAndParams = Utils.splitCommandWordAndArgs(userInputString);
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
            return tasks.getMessageForInvalidCommandWord();
        }
    }

    /**
     * Requests to terminate the program.
     */
    public void executeExitProgramRequest() {
        exitProgram();
    }

    /**
     * Displays the farewell message and exits the runtime.
     */
    public void exitProgram() {
        ui.printFarewell();
        System.exit(0);
    }

}
