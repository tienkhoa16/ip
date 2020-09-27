package duke.exceptions;

import static duke.constants.Messages.MESSAGE_FORMAT;

/**
 * A class that deals with handling exceptions.
 */
public class ExceptionHandler {
    /**
     * Handles checked exceptions.
     *
     * @param e DukeException.
     * @return Error message.
     */
    public String handleCheckedExceptions(DukeException e) {
        return e.getMessage();
    }

    /**
     * Handles unchecked exceptions.
     *
     * @param e Exception.
     * @return Error message.
     */
    public String handleUncheckedExceptions(Exception e) {
        return String.format(MESSAGE_FORMAT, e.toString());
    }
}
