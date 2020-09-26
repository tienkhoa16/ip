package duke.exceptions;

import static duke.constants.Messages.MESSAGE_FORMAT;

/**
 * Base class for the checked exceptions in Duke.
 */
public abstract class DukeException extends Exception {
    /**
     * Constructor for class.
     *
     * @param message Error message.
     */
    public DukeException(String message) {
        super(String.format(MESSAGE_FORMAT, message));
    }
}