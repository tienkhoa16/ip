package duke.exceptions;

import static duke.constants.Messages.MESSAGE_EMPTY_DESCRIPTION;

/**
 * Represents exception when task description is empty.
 */
public class EmptyDescriptionException extends DukeException {
    public EmptyDescriptionException(String taskType) {
        super(String.format(MESSAGE_EMPTY_DESCRIPTION, taskType));
    }
}
