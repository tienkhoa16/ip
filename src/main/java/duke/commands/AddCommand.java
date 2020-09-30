package duke.commands;

import duke.components.Storage;
import duke.components.TasksList;
import duke.exceptions.DukeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;
import duke.exceptions.InvalidCommandWordException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import static duke.commands.CommandResult.createAcknowledgeMsg;
import static duke.components.Parser.extractDescriptionFromString;
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
    private String commandArgs;

    /**
     * Constructs AddCommand object inheriting abstract class Command.
     *
     * @param taskTypeAbbrev Task type abbreviation of the task to be added.
     * @param commandArgs Command arguments from user's input.
     */
    public AddCommand(char taskTypeAbbrev, String commandArgs) {
        this.taskTypeAbbrev = taskTypeAbbrev;
        this.commandArgs = commandArgs;
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
            Task task = createTask();

            tasks.getTasksList().add(task);
            storage.saveData(tasks);

            String acknowledgeMsg = createAcknowledgeMsg(MESSAGE_ADD_ACK, tasks, task);

            return new CommandResult(String.format(MESSAGE_FORMAT, acknowledgeMsg));
        } catch (DukeException e) {
            return new CommandResult(e.getMessage());
        }
    }

    /**
     * Creates a task based on user's input.
     *
     * @return Created task.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task date time is empty.
     * @throws InvalidCommandWordException If command word is invalid.
     */
    private Task createTask() throws EmptyDescriptionException, EmptyTimeException, InvalidCommandWordException {
        Task task = null;

        switch (taskTypeAbbrev) {
        case TODO_ABBREVIATION:
            task = new Todo(commandArgs);
            break;
        case DEADLINE_ABBREVIATION:
            //Fallthrough
        case EVENT_ABBREVIATION:
            String activity = extractDescriptionFromString(taskTypeAbbrev, commandArgs);
            String time = extractTimeFromString(commandArgs, taskTypeAbbrev);

            switch (taskTypeAbbrev) {
            case DEADLINE_ABBREVIATION:
                task = new Deadline(activity, time);
                break;
            case EVENT_ABBREVIATION:
                task = new Event(activity, time);
                break;
            default:
                throw new InvalidCommandWordException();
            }

            break;
        default:
            throw new InvalidCommandWordException();
        }

        return task;
    }
}
