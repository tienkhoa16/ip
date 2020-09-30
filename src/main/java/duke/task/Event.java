package duke.task;

import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import static duke.constants.TaskConstants.EVENT_STRING_REPRESENTATION;

/**
 * A representation of an event task.
 */
public class Event extends TaskWithDateTime {

    /**
     * Constructs Event object inheriting abstract class TaskWithDateTime.
     *
     * @param description Event description.
     * @param at Event date time.
     * @throws EmptyDescriptionException If event description is empty.
     * @throws EmptyTimeException If event date time is empty.
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
        return String.format(EVENT_STRING_REPRESENTATION, super.toString(), getDateTime());
    }
}
