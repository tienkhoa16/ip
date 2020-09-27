package duke.exceptions;

import static duke.constants.Messages.MESSAGE_INVALID_INPUT_WORD;

/**
 * Represents exception when command is invalid.
 */
public class InvalidCommandException extends DukeException {

    /**
     * Constructs InvalidCommandException object inheriting from DukeException.
     */
    public InvalidCommandException() {
        super(MESSAGE_INVALID_INPUT_WORD);
    }
}
