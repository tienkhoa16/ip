package duke.exceptions;

import static duke.constants.Messages.MESSAGE_EMPTY_TIME;

/**
 * Represents exception when deadline/event time is empty.
 */
public class EmptyTimeException extends DukeException {

    /**
     * Constructs EmptyTimeException object inheriting from DukeException.
     *
     * @param taskType Task type of the task with empty date time.
     */
    public EmptyTimeException(String taskType) {
        super(String.format(MESSAGE_EMPTY_TIME, taskType));
    }
}
