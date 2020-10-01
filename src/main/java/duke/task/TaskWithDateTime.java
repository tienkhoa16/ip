package duke.task;

import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import java.time.format.DateTimeParseException;

import static duke.components.Parser.parseStringFormatDateTime;
import static duke.constants.Messages.MESSAGE_AN_EVENT;
import static duke.constants.Messages.MESSAGE_A_DEADLINE;

/**
 * A base class for task with date time.
 */
public abstract class TaskWithDateTime extends Task {
    protected String dateTime;

    /**
     * Constructs TaskWithDateTime object inheriting abstract class Task.
     *
     * @param description Description of task.
     * @param dateTime Date and time of task.
     * @param isDone String representation of task status ("1" for completed, "0" for uncompleted).
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task date time is empty.
     */
    public TaskWithDateTime(String description, String dateTime, String isDone) throws EmptyDescriptionException,
            EmptyTimeException {
        super(description, isDone);

        if (dateTime.isEmpty()) {
            if (this instanceof Deadline) {
                throw new EmptyTimeException(MESSAGE_A_DEADLINE);
            } else if (this instanceof Event) {
                throw new EmptyTimeException(MESSAGE_AN_EVENT);
            }
        }

        try {
            this.dateTime = parseStringFormatDateTime(dateTime);
        } catch (DateTimeParseException e) {
            this.dateTime = dateTime;
        }
    }

    /**
     * Returns the date and time of the task.
     *
     * @return Task date and time.
     */
    public String getDateTime() {
        return dateTime;
    }
}
