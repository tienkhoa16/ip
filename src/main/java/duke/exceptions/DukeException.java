package duke.exceptions;

import static duke.constants.Messages.MESSAGE_FORMAT;

/**
 * A base class for the checked exceptions in Duke.
 */
public abstract class DukeException extends Exception {

    /**
     * Constructs DukeException object inheriting class Exception.
     *
     * @param message Error message.
     */
    public DukeException(String message) {
        super(String.format(MESSAGE_FORMAT, message));
    }
}