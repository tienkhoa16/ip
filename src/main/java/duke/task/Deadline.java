package duke.task;

import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;

public class Deadline extends TaskWithDateTime {

    /**
     * Constructs a new Deadline object inheriting from Task class.
     *
     * @param description Deadline description.
     * @param by Deadline date.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task time is empty.
     */
    public Deadline(String description, String by) throws EmptyDescriptionException, EmptyTimeException {
        super(description, by);
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and deadline date.
     *
     * @return Task type, status icon, description and deadline date.
     */
    @Override
    public String toString() {
        return "[" + DEADLINE_ABBREVIATION + "]" + super.toString() + " (by: " + getDateTime() + ")";
    }
}
