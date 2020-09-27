package duke.task;

import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import static duke.constants.TaskConstants.EVENT_ABBREVIATION;

/**
 * A representation of an event task.
 */
public class Event extends TaskWithDateTime {

    /**
     * Constructs a new Event object inheriting from Task class.
     *
     * @param description Event description.
     * @param at Event time.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task time is empty.
     */
    public Event(String description, String at) throws EmptyDescriptionException, EmptyTimeException {
        super(description, at);
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and event time.
     *
     * @return Task type, status icon, description and event time.
     */
    @Override
    public String toString() {
        return "[" + EVENT_ABBREVIATION + "]" + super.toString() + " (at: " + getDateTime() + ")";
    }
}
