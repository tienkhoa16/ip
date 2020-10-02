package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.exceptions.EmptyKeywordException;
import duke.task.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static duke.components.Utils.convertTasksListToString;
import static duke.constants.CommandConstants.ONE_APPEARANCE;
import static duke.constants.Messages.MESSAGE_FIND_FORMAT;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_NO_MATCH;
import static duke.constants.TaskConstants.TASK_DETAILS_START_INDEX;

/**
 * A representation of the command for finding tasks by keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs FindCommand object inheriting abstract class Command.
     *
     * @param keyword Keyword for searching.
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

        ArrayList<Task> matchingTasks = (ArrayList<Task>) tasks.getTasksList().stream()
                .filter(task -> task.toString().substring(TASK_DETAILS_START_INDEX).toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        String listingMessage = null;

        if (matchingTasks.size() >= ONE_APPEARANCE) {
            listingMessage = String.format(MESSAGE_FIND_FORMAT, convertTasksListToString(matchingTasks));
        } else {
            listingMessage = MESSAGE_NO_MATCH;
        }

        return new CommandResult(String.format(MESSAGE_FORMAT, listingMessage));
    }
}
