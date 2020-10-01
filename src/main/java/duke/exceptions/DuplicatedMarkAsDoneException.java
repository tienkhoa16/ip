package duke.exceptions;

import static duke.constants.Messages.MESSAGE_DUPLICATED_MARK;

/**
 * Represents exception when task to be marked as done was marked as done previously.
 */
public class DuplicatedMarkAsDoneException extends DukeException {

    /**
     * Constructs DuplicatedMarkAsDoneException object inheriting abstract class DukeException.
     *
     * @param taskDescription Description of duplicated marked as done task.
     */
    public DuplicatedMarkAsDoneException(String taskDescription) {
        super(String.format(MESSAGE_DUPLICATED_MARK, taskDescription));
    }
}
