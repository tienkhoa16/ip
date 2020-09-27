package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TasksList;

import static duke.constants.Messages.MESSAGE_DELETE_ACK;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_INVALID_ID;
import static duke.constants.Messages.MESSAGE_INVALID_ID_RANGE;
import static duke.parser.Parser.convertToZeroBased;

/**
 * A representation of the command for deleting a task from the list.
 */
public class DeleteCommand extends Command {
    /** Index in one-based numbering of the task to be deleted */
    private String index;

    /**
     * Constructs DeleteCommand object inheriting from Command class.
     *
     * @param index Index in one-based numbering of the task to be deleted.
     */
    public DeleteCommand(String index) {
        this.index = index;
    }

    /**
     * Overrides execute method of class Command to execute the delete task command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        try {
            int indexZeroBased = convertToZeroBased(index);

            Task taskToDelete = tasks.getTasksList().get(indexZeroBased);
            tasks.getTasksList().remove(indexZeroBased);

            int numOfTasks = tasks.getNumberOfTasks();

            storage.saveData(tasks);

            String acknowledgeMsg = String.format(MESSAGE_DELETE_ACK, taskToDelete.toString(), numOfTasks);

            return new CommandResult(String.format(MESSAGE_FORMAT, acknowledgeMsg));
        } catch (DukeException e) {
            return new CommandResult(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_INVALID_ID_RANGE));
        } catch (NumberFormatException e) {
            return new CommandResult(String.format(MESSAGE_FORMAT, MESSAGE_INVALID_ID));
        }
    }
}
