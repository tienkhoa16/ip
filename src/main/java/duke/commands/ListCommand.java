package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;

import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_LIST_FORMAT;

/**
 * A representation of the command for listing tasks list.
 */
public class ListCommand extends Command {

    /**
     * Overrides execute method of class Command to execute the listing tasks list command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        String listingMessage = String.format(MESSAGE_LIST_FORMAT,
                super.convertTasksListToString(tasks.getTasksList()));

        return new CommandResult(String.format(MESSAGE_FORMAT, listingMessage.toString()));
    }
}
