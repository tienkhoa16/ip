package duke.task;

import duke.exception.DukeException;

public class Deadline extends Task {

    protected String by;    // Deadline date

    /**
     * Constructs a new Deadline object inheriting from Task class.
     *
     * @param description Deadline description.
     * @param by Deadline date.
     * @throws DukeException If deadline description is empty.
     */
    public Deadline(String description, String by) throws DukeException {
        super(description);
        this.by = by;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and deadline date.
     *
     * @return Task type, status icon, description and deadline date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
