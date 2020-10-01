package duke.exceptions;

import static duke.constants.Messages.MESSAGE_LOADING_ERROR;

/**
 * Represents exception while loading data.
 */
public class LoadingException extends DukeException {

    /**
     * Constructs LoadingException object inheriting class DukeException.
     *
     * @param message The cause of the error.
     */
    public LoadingException(String message) {
        super(String.format(MESSAGE_LOADING_ERROR, message));
    }
}
