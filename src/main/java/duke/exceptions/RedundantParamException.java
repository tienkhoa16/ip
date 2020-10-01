package duke.exceptions;

import static duke.constants.Messages.MESSAGE_REDUNDANT_PARAM;

/**
 * Represents exception when input parameters are redundant.
 */
public class RedundantParamException extends DukeException {

    /**
     * Constructs RedundantParamException object inheriting abstract class DukeException.
     *
     * @param commandWord Command with redundant input parameters.
     */
    public RedundantParamException(String commandWord) {
        super(String.format(MESSAGE_REDUNDANT_PARAM, commandWord.toUpperCase()));
    }
}
