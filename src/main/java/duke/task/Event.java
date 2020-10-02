package duke.task;

import duke.exceptions.EmptyDateTimeException;
import duke.exceptions.EmptyDescriptionException;

import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;

/**
 * A representation of an event task.
 */
public class Event extends TaskWithDateTime {

    /**
     * Constructs Event object inheriting abstract class TaskWithDateTime.
     * Creates an uncompleted event.
     *
     * @param description Event description.
     * @param at Event date time.
     * @throws EmptyDescriptionException If event description is empty.
     * @throws EmptyDateTimeException If event date time is empty.
     */
    public Event(String description, String at) throws EmptyDescriptionException, EmptyDateTimeException {
        this(description, at, TASK_UNDONE_STRING_REPRESENTATION);
    }

    /**
     * Constructs Event object inheriting abstract class TaskWithDateTime.
     * Creates a completed/uncompleted event.
     *
     * @param description Event description.
     * @param at Event date time.
     * @param isDone String representation of task status ("1" for completed, "0" for uncompleted).
     * @throws EmptyDescriptionException If event description is empty.
     * @throws EmptyDateTimeException If event date time is empty.
     */
    public Event(String description, String at, String isDone) throws EmptyDescriptionException,
            EmptyDateTimeException {
        super(description, at, isDone);
    }

    /**
     * Overrides toString method of class Task
     * to get string representation of Event including task type, status icon, description and event time.
     *
     * @return String representation of Event.
     */
    @Override
    public String toString() {
        return String.format(EVENT_STRING_REPRESENTATION, super.toString(), getDateTime());
    }

    /**
     * Overrides getTaskTypeAbbrev of abstract class Task to return event abbreviation ('E').
     *
     * @return Event abbreviation ('E').
     */
    @Override
    public char getTaskTypeAbbrev() {
        return EVENT_ABBREVIATION;
    }
}
