package duke.components;

import duke.exceptions.DukeException;

import static duke.constants.Messages.MESSAGE_FORMAT;

/**
 * A class that deals with handling exceptions.
 */
public class ExceptionHandler {

    /**
     * Handles checked exceptions.
     *
     * @param e Checked exceptions in Duke.
     * @return Error message.
     */
    public String handleCheckedExceptions(DukeException e) {
        return e.getMessage();
    }

    /**
     * Handles unchecked exceptions.
     *
     * @param e Unchecked exception in Duke.
     * @return Error message.
     */
    public String handleUncheckedExceptions(Exception e) {
        return String.format(MESSAGE_FORMAT, e.toString());
    }
}
