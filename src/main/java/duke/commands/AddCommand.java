package duke.commands;

import duke.exceptions.DukeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;
import duke.exceptions.InvalidCommandException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TasksList;
import duke.task.Todo;

import static duke.constants.Messages.MESSAGE_ADD_ACK;
import static duke.constants.Messages.MESSAGE_FORMAT;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;
import static duke.parser.Parser.extractActivityFromString;
import static duke.parser.Parser.extractTimeFromString;

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
        super();
        this.taskTypeAbbrev = taskTypeAbbrev;
        this.description = description;
    }

    /**
     * Creates the task according to the task type abbreviation and the description of the task.
     *
     * @return Created task.
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
     * @param tasks Task list.
     * @param storage Storage.
     * @return Output response.
     */
    @Override
    public CommandResult execute(TasksList tasks, Storage storage) {
        try {
            Task task = null;
            task = createTask();

            tasks.getTasksList().add(task);
            int numOfTasks = tasks.getNumberOfTasks();

            storage.saveData(tasks);

            String message = String.format(MESSAGE_ADD_ACK, task.toString(), numOfTasks);

            return new CommandResult(String.format(MESSAGE_FORMAT, message));
        } catch (DukeException e) {
            return new CommandResult(e.getMessage());
        }
    }
}