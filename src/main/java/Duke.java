import duke.commands.Command;
import duke.commands.CommandResult;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.exceptions.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TasksList;
import duke.ui.Ui;

public class Duke {
    private Ui ui;
    private Storage storage;
    private Parser parser;
    private TasksList tasks;

    /**
     * Constructs Duke object.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        try {
            tasks = storage.loadData();
        } catch (DukeException e) {
            ui.showResultToUser(e.getMessage());
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
     * Starts up Duke with welcome message and shows past tasks list.
     */
    private void start() {
        ui.greetUser();
        ui.showResultToUser((new ListCommand()).execute(tasks, storage).toString());
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
                ui.showResultToUser(e.getMessage());
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
