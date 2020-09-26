package duke.task;

import duke.exceptions.DuplicatedMarkAsDoneException;
import duke.exceptions.EmptyDescriptionException;

import static duke.constants.TaskConstants.AN_EVENT;
import static duke.constants.TaskConstants.A_DEADLINE;
import static duke.constants.TaskConstants.A_TODO;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_DONE_ICON;
import static duke.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_UNDONE_ICON;
import static duke.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

public abstract class Task {

    protected String description;
    protected boolean isDone;
    protected String time;

    /**
     * Constructs a new Task object.
     * By default, initially, task status is set as not done and task time is empty.
     *
     * @param description Task description.
     * @throws EmptyDescriptionException If task description is empty.
     */
    public Task(String description) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            if (this instanceof Todo) {
                throw new EmptyDescriptionException(A_TODO);
            } else if (this instanceof Deadline) {
                throw new EmptyDescriptionException(A_DEADLINE);
            } else if (this instanceof Event) {
                throw new EmptyDescriptionException(AN_EVENT);
            }
        }

        this.description = description;
        isDone = false;
        time = "";
    }

    /**
     * Returns an icon according task's status.
     *
     * @return Icon according task's status.
     */
    public String getStatusIcon() {
        // Return task status icon
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
     * Returns task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks task status as done.
     *
     * @throws DuplicatedMarkAsDoneException If task has been marked as done.
     */
    public void markAsDone() throws DuplicatedMarkAsDoneException {
        if (isDone) {
            throw new DuplicatedMarkAsDoneException(description);
        }
        isDone = true;
    }

    /**
     * Returns string representing task type abbreviation.
     *
     * @return Task type abbreviation.
     */
    public char getTaskAbbreviation() {
        char abbreviation = 0;

        if (this instanceof Todo) {
            abbreviation = TODO_ABBREVIATION;
        } else if (this instanceof Deadline) {
            abbreviation = DEADLINE_ABBREVIATION;
        } else if (this instanceof Event) {
            abbreviation = EVENT_ABBREVIATION;
        }

        return abbreviation;
    }

    /**
     * Overrides toString method of class Object to return status icon and task description.
     *
     * @return Task status icon and task description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Formats information in a task for it to be saved and decoded in future.
     *
     * @return Encoded string with all information in the task.
     */
    public abstract String encodeTask();
}
