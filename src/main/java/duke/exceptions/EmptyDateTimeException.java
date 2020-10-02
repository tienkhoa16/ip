package duke.exceptions;

import static duke.constants.Messages.MESSAGE_EMPTY_DATE_TIME;

/**
 * Represents exception when deadline/event date time is empty.
 */
public class EmptyDateTimeException extends DukeException {

    /**
     * Constructs EmptyDateTimeException object inheriting abstract class DukeException.
     *
     * @param taskType Type of task with empty date time.
     */
    public EmptyDateTimeException(String taskType) {
        super(String.format(MESSAGE_EMPTY_DATE_TIME, taskType));
    }
}
