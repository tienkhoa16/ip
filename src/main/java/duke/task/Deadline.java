package duke.task;

import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import static duke.constants.TaskConstants.DEADLINE_STRING_REPRESENTATION;

/**
 * A representation of a deadline task.
 */
public class Deadline extends TaskWithDateTime {

    /**
     * Constructs Deadline object inheriting abstract class TaskWithDateTime.
     *
     * @param description Deadline description.
     * @param by Deadline date time.
     * @throws EmptyDescriptionException If deadline description is empty.
     * @throws EmptyTimeException If deadline date time is empty.
     */
    public Deadline(String description, String by) throws EmptyDescriptionException, EmptyTimeException {
        super(description, by);
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and deadline date time.
     *
     * @return Task type, status icon, description and deadline date time.
     */
    @Override
    public String toString() {
        return String.format(DEADLINE_STRING_REPRESENTATION, super.toString(), getDateTime());
    }
}
