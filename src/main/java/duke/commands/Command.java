package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.task.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static duke.components.Parser.convertToOneBased;
import static duke.constants.Messages.LISTING_FORMAT;

/**
 * A base class for command.
 */
public abstract class Command {

    /**
     * Executes the specific command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    public abstract CommandResult execute(TasksList tasks, Storage storage);

    /**
     * Converts tasks list to string representation.
     */
    protected String convertTasksListToString(ArrayList<Task> tasks) {
        String tasksString = IntStream.range(0, tasks.size())
                .mapToObj(index -> String.format(LISTING_FORMAT, convertToOneBased(index), tasks.get(index).toString()))
                .collect(Collectors.joining());

        return tasksString;
    }
}
