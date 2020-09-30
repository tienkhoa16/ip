package duke.task;

import duke.exceptions.DukeException;
import duke.exceptions.DuplicatedMarkAsDoneException;
import duke.exceptions.EmptyDescriptionException;

import static duke.components.Parser.splitTaskFromDataLine;
import static duke.constants.Messages.MESSAGE_AN_EVENT;
import static duke.constants.Messages.MESSAGE_A_DEADLINE;
import static duke.constants.Messages.MESSAGE_A_TODO;
import static duke.constants.Messages.TASK_ENCODE_FORMAT;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_ABBREVIATION_INDEX;
import static duke.constants.TaskConstants.TASK_DESCRIPTION_INDEX;
import static duke.constants.TaskConstants.TASK_DONE_ICON;
import static duke.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_STATUS_INDEX;
import static duke.constants.TaskConstants.TASK_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_UNDONE_ICON;
import static duke.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

/**
 * A base class for task.
 */
public abstract class Task {
    protected char taskTypeAbbrev;
    protected String description;
    protected boolean isDone;

    /**
     * Constructs Task object. By default, initially, task status is set as not done.
     *
     * @param description Task description.
     * @throws EmptyDescriptionException If task description is empty.
     */
    public Task(String description) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            if (this instanceof Todo) {
                throw new EmptyDescriptionException(MESSAGE_A_TODO);
            } else if (this instanceof Deadline) {
                throw new EmptyDescriptionException(MESSAGE_A_DEADLINE);
            } else if (this instanceof Event) {
                throw new EmptyDescriptionException(MESSAGE_AN_EVENT);
            }
        }

        setTaskTypeAbbrev();
        setDescription(description);
        setIsDone(false);
    }

    /**
     * Sets task description.
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets task status.
     */
    private void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Sets task type abbreviation.
     */
    private void setTaskTypeAbbrev() {
        if (this instanceof Todo) {
            taskTypeAbbrev = TODO_ABBREVIATION;
        } else if (this instanceof Deadline) {
            taskTypeAbbrev = DEADLINE_ABBREVIATION;
        } else if (this instanceof Event) {
            taskTypeAbbrev = EVENT_ABBREVIATION;
        }
    }

    /**
     * Returns task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns an icon representing task's status.
     *
     * @return Icon representing task's status.
     */
    public String getStatusIcon() {
        return (isDone ? TASK_DONE_ICON : TASK_UNDONE_ICON);
    }

    /**
     * Returns task's status in string representation ("0" or "1").
     *
     * @return String representation ("0" or "1") of task's status.
     */
    public String getIsDone() {
        return isDone ? TASK_DONE_STRING_REPRESENTATION : TASK_UNDONE_STRING_REPRESENTATION;
    }

    /**
     * Returns task type abbreviation.
     *
     * @return Task type abbreviation.
     */
    public char getTaskTypeAbbrev() {
        return taskTypeAbbrev;
    }

    /**
     * Marks task status as done.
     *
     * @throws DuplicatedMarkAsDoneException If task has been marked as done previously.
     */
    public void markAsDone() throws DuplicatedMarkAsDoneException {
        if (isDone) {
            throw new DuplicatedMarkAsDoneException(description);
        }

        setIsDone(true);
    }

    /**
     * Overrides toString method of class Object to return task type abbreviation, status icon and description.
     *
     * @return Task type, status icon and description.
     */
    @Override
    public String toString() {
        return String.format(TASK_STRING_REPRESENTATION, getTaskTypeAbbrev(), getStatusIcon(), getDescription());
    }

    /**
     * Encodes information in a task for it to be saved and decoded in future.
     *
     * @return Encoded string with all information in the task.
     */
    public String encodeTask() {
        return String.format(TASK_ENCODE_FORMAT, getTaskTypeAbbrev(), getIsDone(), getDescription());
    }

    /**
     * Deciphers a string containing information of a task.
     *
     * @param encodedTask Encoded string with information of the task.
     * @return Task object with information decoded from encodedTask.
     * @throws DukeException If there is exception while decoding the task.
     */
    public static Task decodeTask(String encodedTask) throws DukeException {
        String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

        Character taskAbbrev = encodedTask.charAt(TASK_ABBREVIATION_INDEX);
        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];

        Task decodedTask = null;

        if (taskAbbrev.equals(TODO_ABBREVIATION)) {
            decodedTask = new Todo(taskDescription);
        }

        if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
            decodedTask.markAsDone();
        }

        return decodedTask;
    }
}
