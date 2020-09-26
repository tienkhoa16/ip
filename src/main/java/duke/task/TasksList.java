package duke.task;

import duke.exceptions.DuplicatedMarkAsDoneException;

import java.util.ArrayList;

import static duke.commons.constants.Messages.LS;
import static duke.commons.constants.Messages.MESSAGE_DELETE_ACK;
import static duke.commons.constants.Messages.MESSAGE_DONE_TITLE;
import static duke.commons.constants.Messages.MESSAGE_DUPLICATED_MARK;
import static duke.commons.constants.Messages.MESSAGE_FORMAT;
import static duke.commons.constants.Messages.MESSAGE_INVALID_ID;
import static duke.commons.constants.Messages.MESSAGE_INVALID_ID_RANGE;
import static duke.commons.utils.Utils.convertToZeroBased;

public class TasksList {

    protected ArrayList<Task> tasks;

    /**
     * Constructs a new Task List instance.
     */
    public TasksList() {
        tasks = new ArrayList<>();
    }

    /**
     * Returns tasks list.
     *
     * @return Tasks list.
     */
    public ArrayList<Task> getTasksList() {
        return tasks;
    }

    /**
     * Returns the total number of tasks in tasks list.
     *
     * @return Number of tasks in tasks list.
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /**
     * Deletes a task in the tasks list.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for deleting the task.
     */
    public String executeDeleteTask(String commandArgs) {
        int taskIndex = 0;

        try {
            taskIndex = convertToZeroBased(commandArgs);
            Task deletedTask = tasks.get(taskIndex);

            // Delete task from task list
            tasks.remove(taskIndex);

            return getMessageForSuccessfulDelete(deletedTask);
        } catch (IndexOutOfBoundsException e) {
            return getMessageForInvalidIdRange();
        } catch (NumberFormatException e) {
            return getMessageForInvalidId();
        }
    }

    /**
     * Returns a message when user deletes a task successfully.
     *
     * @param task Task object to be marked as done.
     * @return Message for successfully delete a task.
     */
    public String getMessageForSuccessfulDelete(Task task) {
        String message = String.format(MESSAGE_DELETE_ACK, task.toString(), getNumberOfTasks());

        return String.format(MESSAGE_FORMAT, message);
    }

    /**
     * Marks a task in the tasks list as done.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for marking the task as done.
     */
    public String executeMarkTaskAsDone(String commandArgs) {
        int taskIndex = 0;

        try {
            taskIndex = convertToZeroBased(commandArgs);

            // Update status of task
            tasks.get(taskIndex).markAsDone();

            return getMessageForSuccessfulMark(tasks.get(taskIndex));
        } catch (DuplicatedMarkAsDoneException e) {
            return getMessageForDuplicatedMark(tasks.get(taskIndex));
        } catch (IndexOutOfBoundsException e) {
            return getMessageForInvalidIdRange();
        } catch (NumberFormatException e) {
            return getMessageForInvalidId();
        }
    }

    /**
     * Returns a message when task ID to be marked as done is not an integer.
     *
     * @return Message for invalid task ID.
     */
    public String getMessageForInvalidId() {
        return String.format(MESSAGE_FORMAT, MESSAGE_INVALID_ID);
    }

    /**
     * Returns a message when user inputs a task ID out of range.
     *
     * @return Message for invalid task ID.
     */
    public String getMessageForInvalidIdRange() {
        return String.format(MESSAGE_FORMAT, MESSAGE_INVALID_ID_RANGE);
    }

    /**
     * Returns a message when user marks a task as done successfully.
     *
     * @param task Task object to be marked as done.
     * @return Message for successfully mark task as done.
     */
    public String getMessageForSuccessfulMark(Task task) {
        String message = MESSAGE_DONE_TITLE + LS + task.toString();

        return String.format(MESSAGE_FORMAT, message);
    }

    /**
     * Returns an error message when user marks a task as done but it has been done earlier.
     *
     * @param task Task object marked as done earlier.
     * @return Duplicated marked as done message.
     */
    public String getMessageForDuplicatedMark(Task task) {
        String message = String.format(MESSAGE_DUPLICATED_MARK, task.getDescription());

        return String.format(MESSAGE_FORMAT, message);
    }
}
