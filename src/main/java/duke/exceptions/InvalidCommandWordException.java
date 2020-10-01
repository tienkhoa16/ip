package duke.exceptions;

import static duke.constants.Messages.MESSAGE_INVALID_COMMAND_WORD;

/**
 * Represents exception when command word is invalid.
 */
public class InvalidCommandWordException extends DukeException {

    /**
     * Constructs InvalidCommandException object inheriting abstract class DukeException.
     */
    public InvalidCommandWordException() {
        super(MESSAGE_INVALID_COMMAND_WORD);
    }
}
