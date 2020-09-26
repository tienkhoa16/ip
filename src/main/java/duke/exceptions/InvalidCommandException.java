package duke.exceptions;

import static duke.commons.constants.Messages.MESSAGE_INVALID_INPUT_WORD;

/**
 * Represents exception when command is invalid.
 */
public class InvalidCommandException extends DukeException {
    public InvalidCommandException() {
        super(MESSAGE_INVALID_INPUT_WORD);
    }
}
