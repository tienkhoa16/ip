package duke.task;

import duke.exception.DukeException;

import static duke.constant.Constant.DEADLINE_ABBREVIATION;
import static duke.constant.Constant.EVENT_ABBREVIATION;
import static duke.constant.Constant.TICK_SYMBOL;
import static duke.constant.Constant.TODO_ABBREVIATION;
import static duke.constant.Constant.X_SYMBOL;

public class Task {

    protected String description;   // Task description
    protected boolean isDone;       // Task status
    protected String taskTime;      // Task time

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
        // Return tick or X symbols
        return (isDone ? TICK_SYMBOL : X_SYMBOL);
    }

    /**
     * Returns task's status in integer (0 or 1).
     *
     * @return Number (0 or 1) according task's status.
     */
    public int getIsDone() {
        return isDone? 1 : 0;
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
