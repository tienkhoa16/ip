package duke.commands;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TasksList;

import static duke.commons.constants.Messages.LISTING_FORMAT;
import static duke.commons.constants.Messages.MESSAGE_FORMAT;
import static duke.commons.constants.Messages.MESSAGE_LIST_TITLE;
import static duke.commons.utils.Utils.convertToOneBased;

public class ListCommand extends Command {
    /**
     * Public constructor for class.
     */
    public ListCommand() {
        super();
    }

    /**
     * Executes the listing tasks list command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        StringBuilder listingMessage = new StringBuilder(MESSAGE_LIST_TITLE);
        int numOfTasks = tasks.getNumberOfTasks();

        for (int i = 0; i < numOfTasks; i++) {
            Task task = tasks.getTasksList().get(i);

            listingMessage.append(String.format(LISTING_FORMAT, convertToOneBased(i), task.toString()));
        }

        return new CommandResult(String.format(MESSAGE_FORMAT, listingMessage.toString()));
    }
}
