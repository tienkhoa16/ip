package duke.task;

import duke.exceptions.EmptyDescriptionException;

import static duke.constants.TaskConstants.TASK_UNDONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

/**
 * A representation of a todo task.
 */
public class Todo extends Task {

    /**
     * Constructs Todo object inheriting abstract class Task.
     * Creates an uncompleted todo.
     *
     * @param description Todo description.
     * @throws EmptyDescriptionException If todo description is empty.
     */
    public Todo(String description) throws EmptyDescriptionException {
        this(description, TASK_UNDONE_STRING_REPRESENTATION);
    }

    /**
     * Constructs Todo object inheriting abstract class Task. Creates a completed/uncompleted todo.
     *
     * @param description Description of todo.
     * @param isDone String representation of task status ("1" for completed, "0" for uncompleted).
     */
    public Todo(String description, String isDone) throws EmptyDescriptionException {
        super(description, isDone);
    }

    /**
     * Overrides getTaskTypeAbbrev of abstract class Task to return todo abbreviation ('T').
     *
     * @return Todo abbreviation ('T').
     */
    @Override
    public char getTaskTypeAbbrev() {
        return TODO_ABBREVIATION;
    }
}
