package duke.task;

import duke.exception.DukeException;

public class Task {

    protected String description;   // Task description
    protected boolean isDone;       // Task status

    /**
     * Constructs a new Task object.
     * By default, initially, task status is set as not done.
     *
     * @param description Task description.
     * @throws DukeException If task description is empty.
     */
    public Task(String description) throws DukeException {
        if (description.isEmpty()) {
            throw new DukeException();
        }
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an icon according task's status.
     *
     * @return Icon according task's status.
     */
    public String getStatusIcon() {
        // Return tick or X symbols
        return (isDone ? "\u2713" : "\u2718");
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
     * Overrides toString method of class Object to return status icon and task description.
     *
     * @return Task status icon and task description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
