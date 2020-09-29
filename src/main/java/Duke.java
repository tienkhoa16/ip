import duke.commands.Command;
import duke.commands.CommandResult;
import duke.commands.ExitCommand;
import duke.components.ExceptionHandler;
import duke.components.Parser;
import duke.components.Storage;
import duke.components.TasksList;
import duke.components.Ui;
import duke.exceptions.DukeException;

import static duke.constants.Messages.MESSAGE_CREATED_NEW_DATA_FILE;
import static duke.constants.Messages.MESSAGE_LOAD_SUCCESSFUL;

/**
 * The Duke program implements an application that keeps track of the user's tasks.
 *
 * @author Nguyen Tien Khoa
 * @version v2.0
 * @since 2020-08-23
 */
public class Duke {
    private Ui ui;
    private Storage storage;
    private Parser parser;
    private ExceptionHandler exceptionHandler;
    private TasksList tasks;

    /**
     * Constructs Duke object by initialising Ui, Storage, Parser, ExceptionHandler objects
     * and loads saved tasks if applicable.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        exceptionHandler = new ExceptionHandler();

        try {
            tasks = storage.loadData();

            if (storage.getHasExistingDataFile()) {
                ui.showResultToUser(MESSAGE_LOAD_SUCCESSFUL);
            } else {
                ui.showResultToUser(MESSAGE_CREATED_NEW_DATA_FILE);
            }

        } catch (DukeException e) {
            ui.showResultToUser(exceptionHandler.handleCheckedExceptions(e));
        } catch (Exception e) {
            ui.showResultToUser(exceptionHandler.handleUncheckedExceptions(e));
        }
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
     * Starts up Duke with welcome message.
     */
    private void start() {
        ui.greetUser();
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
