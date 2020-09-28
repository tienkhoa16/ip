package duke.commands;

import duke.exceptions.EmptyKeywordException;
import duke.components.Storage;
import duke.task.Task;
import duke.components.TasksList;

import static duke.constants.Messages.LISTING_FORMAT;
import static duke.constants.Messages.MESSAGE_FIND_TITLE;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_NO_MATCH;

public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs FindCommand object inheriting from Command class.
     *
     * @throws EmptyKeywordException If keyword is empty.
     */
    public FindCommand(String keyword) throws EmptyKeywordException {
        if (keyword.isEmpty()) {
            throw new EmptyKeywordException();
        }
        this.keyword = keyword.toLowerCase();
    }


    /**
     * Overrides execute method of class Command
     * to execute the find tasks by keyword command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        StringBuilder listingMessage = new StringBuilder(MESSAGE_FIND_TITLE);
        int matchingTasksCount = 0;

        for (Task task : tasks.getTasksList()) {
            if (task.toString().toLowerCase().contains(keyword)) {
                matchingTasksCount++;
                listingMessage.append(String.format(LISTING_FORMAT, matchingTasksCount, task.toString()));
            }
        }

        if (matchingTasksCount > 0) {
            return new CommandResult(String.format(MESSAGE_FORMAT, listingMessage));
        } else {
            return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_NO_MATCH));
        }
    }
}
