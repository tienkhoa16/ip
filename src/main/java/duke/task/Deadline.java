package duke.task;

import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;

public class Deadline extends Task {

    /**
     * Constructs a new Deadline object inheriting from Task class.
     *
     * @param description Deadline description.
     * @param by Deadline date.
     */
    public Deadline(String description, String by) {
        super(description);
        taskTime = by;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and deadline date.
     *
     * @return Task type, status icon, description and deadline date.
     */
    @Override
    public String toString() {
        return "[" + DEADLINE_ABBREVIATION + "]" + super.toString() + " (by: " + taskTime + ")";
    }
}
