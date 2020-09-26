package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.task.TasksList;

public abstract class Command {

    /**
     * Executes the specific command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     * @throws DukeException DukeException.
     */
    public abstract CommandResult execute(TasksList tasks, Storage storage);
}
