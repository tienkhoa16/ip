package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;

/**
 * A base class for command.
 */
public abstract class Command {

    /**
     * Executes a specific command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    public abstract CommandResult execute(TasksList tasks, Storage storage);
}
