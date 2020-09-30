package duke.task;

import duke.exceptions.EmptyDescriptionException;

/**
 * A representation of a todo task.
 */
public class Todo extends Task {

    /**
     * Constructs Todo object inheriting abstract class Task.
     *
     * @param description Todo description.
     * @throws EmptyDescriptionException If todo description is empty.
     */
    public Todo(String description) throws EmptyDescriptionException {
        super(description);
    }
}
