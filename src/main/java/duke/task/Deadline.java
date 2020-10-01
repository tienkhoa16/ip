package duke.task;

import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.DEADLINE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;

/**
 * A representation of a deadline task.
 */
public class Deadline extends TaskWithDateTime {

    /**
     * Constructs Deadline object inheriting abstract class TaskWithDateTime.
     * Creates an uncompleted deadline.
     *
     * @param description Deadline description.
     * @param by Deadline date time.
     * @throws EmptyDescriptionException If deadline description is empty.
     * @throws EmptyTimeException If deadline date time is empty.
     */
    public Deadline(String description, String by) throws EmptyDescriptionException, EmptyTimeException {
        this(description, by, TASK_UNDONE_STRING_REPRESENTATION);
    }

    /**
     * Constructs Deadline object inheriting abstract class TaskWithDateTime.
     * Creates a completed/uncompleted deadline.
     *
     * @param description Deadline description.
     * @param by Deadline date time.
     * @param isDone String representation of task status ("1" for completed, "0" for uncompleted).
     * @throws EmptyDescriptionException If deadline description is empty.
     * @throws EmptyTimeException If deadline date time is empty.
     */
    public Deadline(String description, String by, String isDone) throws EmptyDescriptionException, EmptyTimeException {
        super(description, by, isDone);
    }

    /**
     * Overrides toString method of class Task
     * to get string representation of Deadline including task type, status icon, description and deadline date time.
     *
     * @return String representation of Deadline.
     */
    @Override
    public String toString() {
        return String.format(DEADLINE_STRING_REPRESENTATION, super.toString(), getDateTime());
    }

    /**
     * Overrides getTaskTypeAbbrev of abstract class Task to return deadline abbreviation ('D').
     *
     * @return Deadline abbreviation ('D').
     */
    @Override
    public char getTaskTypeAbbrev() {
        return DEADLINE_ABBREVIATION;
    }
}
