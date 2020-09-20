package duke.task;

import duke.exception.DukeException;

import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.commons.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TASK_DONE_ICON;
import static duke.commons.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.commons.constants.TaskConstants.TASK_UNDONE_ICON;
import static duke.commons.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;
import static duke.commons.constants.TaskConstants.TODO_ABBREVIATION;

public class Task {

    protected String description;
    protected boolean isDone;
    protected String taskTime;

    /**
     * Constructs a new Task object.
     * By default, initially, task status is set as not done and task time is empty.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        isDone = false;
        taskTime = "";
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
     * Returns task's time.
     * If task is a todo, empty string is returned.
     *
     * @return
     */
    public String getTaskTime() {
        return taskTime;
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
     * @throws DukeException If task has been marked as done.
     */
    public void markAsDone() throws DukeException {
        if (isDone) {
            throw new DukeException();
        }
        isDone = true;
    }

    /**
     * Returns string representing task type abbreviation.
     *
     * @return Task type abbreviation.
     */
    public String getTaskAbbreviation() {
        String abbreviation = "";

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
}
