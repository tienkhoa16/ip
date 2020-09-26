package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TasksList;

import static duke.commons.constants.Messages.MESSAGE_DONE_ACK;
import static duke.commons.constants.Messages.MESSAGE_FORMAT;
import static duke.commons.constants.Messages.MESSAGE_INVALID_ID;
import static duke.commons.constants.Messages.MESSAGE_INVALID_ID_RANGE;
import static duke.commons.utils.Utils.convertToZeroBased;

public class DoneCommand extends Command {
    private String index;

    /**
     * Public constructor for class.
     *
     * @param index Index in one-based of the task to be marked as done.
     */
    public DoneCommand(String index) {
        this.index = index;
    }

    /**
     * Executes the mark task as done command requested by user's input.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data when required.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        try {
            int indexZeroBased = convertToZeroBased(index);

            Task task = tasks.getTasksList().get(indexZeroBased);
            task.markAsDone();

            storage.saveData(tasks);

            String acknowledgeMsg = String.format(MESSAGE_DONE_ACK, task.toString());

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
