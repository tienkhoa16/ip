package duke.task;

import static duke.commons.constants.DataFileConfig.TASK_DESCRIPTION_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_STATUS_INDEX;
import static duke.commons.constants.Messages.VERTICAL_BAR;
import static duke.commons.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.commons.constants.TaskConstants.TODO_ABBREVIATION;
import static duke.commons.utils.Utils.splitTaskFromDataLine;

public class Todo extends Task {

    /**
     * Constructs a new Todo object inheriting from Task class.
     *
     * @param description Todo description.
     */
    public Todo(String description) {
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

    /**
     * Overrides encodeTask method of class Task
     * to format information of a todo for it to be saved and decoded in future.
     *
     * @return Encoded string with all information in the todo.
     */
    @Override
    public String encodeTask() {
        return getTaskAbbreviation() + VERTICAL_BAR + getIsDone()
                + VERTICAL_BAR + description + System.lineSeparator();
    }

    /**
     * Deciphers a string containing information of a todo.
     *
     * @param encodedTask String containing encoded information of the todo.
     * @return Todo object with information from encodedTask.
     */
    public static Todo decodeTask(String encodedTask) {
        String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];

        Todo decodedTodo = new Todo(taskDescription);

        if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
            decodedTodo.isDone = true;
        }

        return decodedTodo;
    }
}
