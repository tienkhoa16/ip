package duke.task;

import duke.exceptions.EmptyDescriptionException;

import static duke.constants.TaskConstants.TODO_ABBREVIATION;

public class Todo extends Task {

    /**
     * Constructs a new Todo object inheriting from Task class.
     *
     * @param description Todo description.
     * @throws EmptyDescriptionException If task description is empty.
     */
    public Todo(String description) throws EmptyDescriptionException {
        super(description);
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon and description.
     *
     * @return Task type, status icon and description.
     */
    @Override
    public String toString() {
        return "[" + TODO_ABBREVIATION + "]" + super.toString();
    }
}
