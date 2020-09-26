package duke.exceptions;

import static duke.constants.Messages.MESSAGE_SAVING_ERROR;

/**
 * Represents exception while saving data.
 */
public class SavingException extends DukeException {
    public SavingException() {
        super(MESSAGE_SAVING_ERROR);
    }
}
