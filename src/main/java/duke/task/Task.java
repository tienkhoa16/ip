package duke.task;

import duke.exceptions.DuplicatedMarkAsDoneException;
import duke.exceptions.EmptyDescriptionException;

import static duke.constants.Messages.MESSAGE_AN_EVENT;
import static duke.constants.Messages.MESSAGE_A_DEADLINE;
import static duke.constants.Messages.MESSAGE_A_TODO;
import static duke.constants.TaskConstants.TASK_DONE_ICON;
import static duke.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_UNDONE_ICON;
import static duke.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;

/**
 * A base class for task.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs Task object.
     *
     * @param description Task description.
     * @param isDone String representation of task status ("1" for completed, "0" for uncompleted).
     * @throws EmptyDescriptionException If task description is empty.
     */
    public Task(String description, String isDone) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            if (this instanceof Todo) {
                throw new EmptyDescriptionException(MESSAGE_A_TODO);
            } else if (this instanceof Deadline) {
                throw new EmptyDescriptionException(MESSAGE_A_DEADLINE);
            } else if (this instanceof Event) {
                throw new EmptyDescriptionException(MESSAGE_AN_EVENT);
            }
        }

        this.description = description;
        this.isDone = (isDone.equals(TASK_DONE_STRING_REPRESENTATION));
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
     * @return 'T', 'D' or 'E' depending on task type.
     */
    public abstract char getTaskTypeAbbrev();

    /**
     * Marks task status as done.
     *
     * @throws DuplicatedMarkAsDoneException If task has been marked as done previously.
     */
    public void markAsDone() throws DuplicatedMarkAsDoneException {
        if (isDone) {
            throw new DuplicatedMarkAsDoneException(description);
        }

        isDone = true;
    }

    /**
     * Overrides toString method of class Object to get string representation of Task
     * including task type abbreviation, status icon and description.
     *
     * @return String representation of Task.
     */
    @Override
    public String toString() {
        return String.format(TASK_STRING_REPRESENTATION, getTaskTypeAbbrev(), getStatusIcon(), getDescription());
    }
}
