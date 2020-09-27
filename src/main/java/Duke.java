import duke.commands.Command;
import duke.commands.CommandResult;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.exceptions.DukeException;
import duke.exceptions.ExceptionHandler;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TasksList;
import duke.ui.Ui;

/**
 * The Duke program implements an application that keeps track of the user's tasks.
 *
 * @author Nguyen Tien Khoa
 * @version v2.0
 * @since 2020-08-23
 */
public class Duke {
    /** Ui object reference */
    private Ui ui;
    /** Storage object reference */
    private Storage storage;
    /** Parser object reference */
    private Parser parser;
    /** ExceptionHandler object reference */
    private ExceptionHandler exceptionHandler;
    /** TasksList object reference */
    private TasksList tasks;

    /**
     * Constructs Duke object.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        exceptionHandler = new ExceptionHandler();
        tasks = new TasksList();
    }

    /**
     * Main entry point to run Duke.
     *
     * @param args Unused in Duke.
     */
    public static void main(String[] args) {
        new Duke().run();
    }

    /**
     * Runs Duke.
     */
    public void run() {
        start();
        runCommandLoop();
        exit();
    }

    /**
     * Starts up Duke with welcome message and shows past tasks list.
     */
    private void start() {
        ui.greetUser();
        loadPastTasks();
        ui.showResultToUser((new ListCommand()).execute(tasks, storage).toString());
    }

    /**
     * Loads past tasks data.
     */
    private void loadPastTasks() {
        try {
            tasks = storage.loadData();
        } catch (DukeException e) {
            ui.showResultToUser(exceptionHandler.handleCheckedExceptions(e));
        } catch (Exception e) {
            ui.showResultToUser(exceptionHandler.handleUncheckedExceptions(e));
        }
    }

    /**
     * Gets user's command and executes repeatedly until user requests to exit Duke.
     */
    private void runCommandLoop() {
        Command command = null;

        do {
            try {
                String userCommand = ui.getCommand();
                command = parser.parseCommand(userCommand);
                CommandResult result = command.execute(tasks, storage);
                ui.showResultToUser(result.toString());
            } catch (DukeException e) {
                ui.showResultToUser(exceptionHandler.handleCheckedExceptions(e));
            } catch (Exception e) {
                ui.showResultToUser(exceptionHandler.handleUncheckedExceptions(e));
            }
        } while (!ExitCommand.isExit(command));
    }

    /**
     * Exits the runtime.
     */
    private void exit() {
        System.exit(0);
    }
}
