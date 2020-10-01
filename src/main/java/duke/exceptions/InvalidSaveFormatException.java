package duke.exceptions;

import static duke.constants.Messages.MESSAGE_INVALID_SAVE_FORMAT;

/**
 * Represents exception when there is corruption in data save format.
 */
public class InvalidSaveFormatException extends DukeException {

    /**
     * Constructs InvalidSaveFormatException object inheriting class DukeException.
     *
     * @param encodedLine The specific line in the data file that is causing the error.
     */
    public InvalidSaveFormatException(String encodedLine) {
        super(String.format(MESSAGE_INVALID_SAVE_FORMAT, encodedLine));
    }
}
