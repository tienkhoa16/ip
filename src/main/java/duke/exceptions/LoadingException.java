package duke.exceptions;

import static duke.constants.Messages.MESSAGE_LOADING_ERROR;

/**
 * Represents exception while loading data.
 */
public class LoadingException extends DukeException {

    /**
     * Constructs LoadingException object inheriting from DukeException.
     */
    public LoadingException() {
        super(MESSAGE_LOADING_ERROR);
    }
}