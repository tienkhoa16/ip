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

import static duke.commons.constants.Messages.AN_EVENT;
import static duke.commons.constants.Messages.A_DEADLINE;
import static duke.commons.constants.Messages.MESSAGE_ADD_ACK;
import static duke.commons.constants.Messages.MESSAGE_FORMAT;
import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.commons.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TASK_DATA_PREFIX_DEADLINE;
import static duke.commons.constants.TaskConstants.TASK_DATA_PREFIX_EVENT;
import static duke.commons.constants.TaskConstants.TODO_ABBREVIATION;
import static duke.commons.utils.Utils.removePrefixSign;

public class AddCommand extends Command {
    /** Task type of the task to be added. */
    private char taskTypeAbbrev;
    /** Description of the task to be added. */
    private String description;

    /**
     * Public constructor for class.
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
     * Executes the add command.
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

    /**
     * Extracts substring representing task activity description from command arguments.
     *
     * @param taskTypeAbbrev Task type abbreviation.
     * @param encoded command arguments.
     * @return Task activity description.
     */
    public String extractActivityFromString(Character taskTypeAbbrev, String encoded) throws EmptyTimeException {
        int indexOfDeadlinePrefix = encoded.indexOf(TASK_DATA_PREFIX_DEADLINE);
        int indexOfEventPrefix = encoded.indexOf(TASK_DATA_PREFIX_EVENT);

        /*
         * Description is leading substring up to data prefix string.
         * If prefix of deadline exists (indexOfDeadlinePrefix >= 0),
         * prefix of event doesn't (indexOfEventPrefix == -1) and vice versa.
         */
        int indexOfExistingPrefix = Math.max(indexOfDeadlinePrefix, indexOfEventPrefix);

        if (indexOfExistingPrefix == -1) {
            if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
                throw new EmptyTimeException(A_DEADLINE);
            }
            if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
                throw new EmptyTimeException(AN_EVENT);
            }
        }

        String activity = encoded.substring(0, indexOfExistingPrefix).trim();

        return activity;
    }

    /**
     * Extracts substring representing task time from command arguments.
     *
     * @param encoded String to be decoded.
     * @param taskTypeAbbrev Abbreviation of task type.
     * @return Task time argument WITHOUT prefix.
     */
    public String extractTimeFromString(String encoded, Character taskTypeAbbrev) {
        String taskPrefix = "";

        if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
            taskPrefix = TASK_DATA_PREFIX_DEADLINE;
        } else if (taskTypeAbbrev.equals(EVENT_ABBREVIATION)) {
            taskPrefix = TASK_DATA_PREFIX_EVENT;
        }

        int indexOfDeadlinePrefix = encoded.indexOf(taskPrefix);

        return removePrefixSign(encoded.substring(indexOfDeadlinePrefix, encoded.length()).trim(), taskPrefix);
    }
}