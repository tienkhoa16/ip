package duke.exceptions;

import static duke.constants.Messages.MESSAGE_EMPTY_KEYWORD;

/**
 * Represents exception when keyword parsed from user's input is empty.
 */
public class EmptyKeywordException extends DukeException {

    /**
     * Constructs EmptyKeywordException object inheriting abstract class DukeException.
     */
    public EmptyKeywordException() {
        super(String.format(MESSAGE_EMPTY_KEYWORD));
    }
}
