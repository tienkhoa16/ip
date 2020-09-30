package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.exceptions.DukeException;
import duke.task.Task;

import static duke.commands.CommandResult.createAcknowledgeMsg;
import static duke.components.Utils.convertToZeroBased;
import static duke.constants.Messages.MESSAGE_DELETE_ACK;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.Messages.MESSAGE_INVALID_ID;
import static duke.constants.Messages.MESSAGE_INVALID_ID_RANGE;

/**
 * A representation of the command for deleting a task from the list.
 */
public class DeleteCommand extends Command {
    private String index;

    /**
     * Constructs DeleteCommand object inheriting abstract class Command.
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
     * @param storage Storage to save data after deleting task.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        try {
            int indexZeroBased = convertToZeroBased(index);

            Task taskToDelete = tasks.getTasksList().get(indexZeroBased);
            tasks.getTasksList().remove(indexZeroBased);

            storage.saveData(tasks);

            String acknowledgeMsg = createAcknowledgeMsg(MESSAGE_DELETE_ACK, tasks, taskToDelete);

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
