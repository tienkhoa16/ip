package duke.exceptions;

import static duke.constants.Messages.MESSAGE_INVALID_TAG;

/**
 * Represents exception when command tag is invalid.
 */
public class InvalidTagException extends DukeException {

    /**
     * Constructs InvalidTagException object inheriting abstract class DukeException.
     */
    public InvalidTagException() {
        super(MESSAGE_INVALID_TAG);
    }
}
