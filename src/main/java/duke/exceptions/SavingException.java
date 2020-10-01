package duke.exceptions;

import static duke.constants.Messages.MESSAGE_SAVING_ERROR;

/**
 * Represents exception while saving data.
 */
public class SavingException extends DukeException {

    /**
     * Constructs SavingException object inheriting class DukeException.
     *
     * @param message The cause of the error.
     */
    public SavingException(String message) {
        super(String.format(MESSAGE_SAVING_ERROR, message));
    }
}
