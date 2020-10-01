package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.exceptions.DukeException;
import duke.task.Task;

import static duke.components.Utils.convertToZeroBased;
import static duke.constants.Messages.MESSAGE_DONE_ACK;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_INVALID_INDEX;
import static duke.constants.Messages.MESSAGE_INVALID_INDEX_RANGE;

/**
 * A representation of the command for marking a task from the list as done.
 */
public class DoneCommand extends Command {
    private String index;

    /**
     * Constructs DoneCommand object inheriting abstract class Command.
     *
     * @param index Index in one-based numbering of the task to be marked as done.
     */
    public DoneCommand(String index) {
        this.index = index;
    }

    /**
     * Overrides execute method of class Command to execute the mark task as done command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data after marking task as done.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        try {
            int indexZeroBased = convertToZeroBased(index);

            Task task = tasks.getTask(indexZeroBased);
            task.markAsDone();

            storage.saveData(tasks);

            String acknowledgeMsg = String.format(MESSAGE_DONE_ACK, task.toString());

            return new CommandResult(String.format(MESSAGE_FORMAT, acknowledgeMsg));
        } catch (DukeException e) {
            return new CommandResult(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_INVALID_INDEX_RANGE));
        } catch (NumberFormatException e) {
            return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_INVALID_INDEX));
        }
    }
}
