package duke.commands;

import duke.storage.Storage;
import duke.task.TasksList;

import static duke.constants.Messages.MESSAGE_EXIT;
import static duke.constants.Messages.MESSAGE_FORMAT;

/**
 * A representation of the command for exiting Duke.
 */
public class ExitCommand extends Command {

    /**
     * Constructs ExitCommand object inheriting from Command class.
     */
    public ExitCommand() {
        super();
    }

    /**
     * Overrides execute method of class Command to execute exit command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_EXIT));
    }

    /**
     * Checks if user wants to exit Duke by checking given command.
     *
     * @param command The command to be checked.
     * @return If the command is an object of type ExitCommand.
     */
    public static boolean isExit(Command command) {
        return command instanceof ExitCommand;
    }
}
