package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.exceptions.DukeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;
import duke.exceptions.InvalidCommandException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import static duke.components.Parser.extractActivityFromString;
import static duke.components.Parser.extractTimeFromString;
import static duke.constants.Messages.MESSAGE_ADD_ACK;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

/**
 * A representation of the command for adding a task to the list.
 */
public class AddCommand extends Command {
    private char taskTypeAbbrev;
    private String description;

    /**
     * Constructs AddCommand object inheriting from Command class.
     *
     * @param taskTypeAbbrev Task type abbreviation of the task to be added.
     * @param description Description of the task to be added.
     */
    public AddCommand(char taskTypeAbbrev, String description) {
        this.taskTypeAbbrev = taskTypeAbbrev;
        this.description = description;
    }

    /**
     * Creates the task according to the task type abbreviation and the description of the task.
     *
     * @return Created task.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task date/time is empty.
     * @throws InvalidCommandException If command word is invalid.
     */
    private Task createTask() throws EmptyDescriptionException, EmptyTimeException, InvalidCommandException {
        Task task = null;

        switch (taskTypeAbbrev) {
        case TODO_ABBREVIATION:
            task = new Todo(description);
            break;
        case DEADLINE_ABBREVIATION:
            //Fallthrough
        case EVENT_ABBREVIATION:
            String activity = extractActivityFromString(taskTypeAbbrev, description);
            String time = null;

            switch (taskTypeAbbrev) {
            case DEADLINE_ABBREVIATION:
                time = extractTimeFromString(description, DEADLINE_ABBREVIATION);
                task = new Deadline(activity, time);
                break;
            case EVENT_ABBREVIATION:
                time = extractTimeFromString(description, EVENT_ABBREVIATION);
                task = new Event(activity, time);
                break;
            default:
                throw new InvalidCommandException();
            }

            break;
        default:
            throw new InvalidCommandException();
        }

        return task;
    }

    /**
     * Overrides execute method of class Command to execute the add command.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @param storage Storage to save data after adding task.
     * @return Result of command execution.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        try {
            Task task = null;
            task = createTask();

            tasks.getTasksList().add(task);
            storage.saveData(tasks);

            int numOfTasks = tasks.getNumberOfTasks();
            String acknowledgeMsg = String.format(MESSAGE_ADD_ACK, task.toString(), numOfTasks);

            return new CommandResult(String.format(MESSAGE_FORMAT, acknowledgeMsg));
        } catch (DukeException e) {
            return new CommandResult(e.getMessage());
        }
    }
}