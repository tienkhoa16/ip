package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.exceptions.RedundantParamException;

import static duke.constants.CommandConstants.COMMAND_WORD_BYE;
import static duke.constants.Messages.MESSAGE_EXIT;
import static duke.constants.Messages.MESSAGE_FORMAT;

/**
 * A representation of the command for exiting Duke.
 */
public class ExitCommand extends Command {

    /**
     * Constructs ExitCommand object inheriting abstract class Command.
     *
     * @param commandArgs Command arguments from user's input.
     * @throws RedundantParamException If parameters are provided to Exit Command.
     */
    public ExitCommand(String commandArgs) throws RedundantParamException {
        if (!commandArgs.isEmpty()) {
            throw new RedundantParamException(COMMAND_WORD_BYE);
        }
    }

    /**
     * Overrides execute method of class Command to execute exit command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_EXIT));
    }

    /**
     * Checks if user wants to exit Duke by checking given command.
     *
     * @param command The command to be checked.
     * @return Whether the command is an object of type ExitCommand.
     */
    public static boolean isExit(Command command) {
        return command instanceof ExitCommand;
    }
}
