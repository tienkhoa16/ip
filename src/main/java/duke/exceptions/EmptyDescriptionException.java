package duke.exceptions;

import static duke.constants.Messages.MESSAGE_EMPTY_DESCRIPTION;

/**
 * Represents exception when task description is empty.
 */
public class EmptyDescriptionException extends DukeException {

    /**
     * Constructs EmptyDescriptionException object inheriting abstract class DukeException.
     *
     * @param taskType Type of the task with empty description.
     */
    public EmptyDescriptionException(String taskType) {
        super(String.format(MESSAGE_EMPTY_DESCRIPTION, taskType));
    }
}
