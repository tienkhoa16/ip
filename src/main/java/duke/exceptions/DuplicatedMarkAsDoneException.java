package duke.exceptions;

import static duke.commons.constants.Messages.MESSAGE_DUPLICATED_MARK;

/**
 * Represents exception when task to be marked as done was marked as done previously.
 */
public class DuplicatedMarkAsDoneException extends DukeException {
    public DuplicatedMarkAsDoneException(String taskDescription) {
        super(String.format(MESSAGE_DUPLICATED_MARK, taskDescription));
    }
}
