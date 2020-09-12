package duke.task;

import static duke.constant.Constant.EVENT_ABBREVIATION;

public class Event extends Task {

    /**
     * Constructs a new Event object inheriting from Task class.
     *
     * @param description Event description.
     * @param at Event time.
     */
    public Event(String description, String at) {
        super(description);
        taskTime = at;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and event time.
     *
     * @return Task type, status icon, description and event time.
     */
    @Override
    public String toString() {
        return "[" + EVENT_ABBREVIATION + "]" + super.toString() + " (at: " + taskTime + ")";
    }
}
