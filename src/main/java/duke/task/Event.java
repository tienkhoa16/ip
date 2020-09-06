package duke.task;

import duke.exception.DukeException;

public class Event extends Task {

    protected String at;    // Event time

    /**
     * Constructs a new Event object inheriting from Task class.
     *
     * @param description Event description.
     * @param at Event time.
     * @throws DukeException If event description is empty.
     */
    public Event(String description, String at) throws DukeException {
        super(description);
        this.at = at;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and event time.
     *
     * @return Task type, status icon, description and event time.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}
