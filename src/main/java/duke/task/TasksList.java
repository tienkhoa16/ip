package duke.task;

import duke.exceptions.DuplicatedMarkAsDoneException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import java.util.ArrayList;

import static duke.commons.constants.Messages.LINE_PREFIX;
import static duke.commons.constants.Messages.LS;
import static duke.commons.constants.Messages.MESSAGE_ADD_TITLE;
import static duke.commons.constants.Messages.MESSAGE_DELETE_TITLE;
import static duke.commons.constants.Messages.MESSAGE_DONE_TITLE;
import static duke.commons.constants.Messages.MESSAGE_DUPLICATED_MARK;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_DEADLINE_DESC;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_DEADLINE_TIME;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_DESC;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_EVENT_DESC;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_EVENT_TIME;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_TIME;
import static duke.commons.constants.Messages.MESSAGE_EMPTY_TODO_DESC;
import static duke.commons.constants.Messages.MESSAGE_FORMAT;
import static duke.commons.constants.Messages.MESSAGE_INVALID_ID;
import static duke.commons.constants.Messages.MESSAGE_INVALID_ID_RANGE;
import static duke.commons.constants.Messages.MESSAGE_INVALID_INPUT_WORD;
import static duke.commons.constants.Messages.MESSAGE_LIST_TITLE;
import static duke.commons.constants.Messages.MESSAGE_NUMBER_OF_TASKS;
import static duke.commons.constants.Messages.SEPARATOR_TASK_ID_TASK_DESC;
import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.commons.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TASK_DATA_PREFIX_DEADLINE;
import static duke.commons.constants.TaskConstants.TASK_DATA_PREFIX_EVENT;
import static duke.commons.constants.TaskConstants.TODO_ABBREVIATION;
import static duke.commons.utils.Utils.convertToOneBased;
import static duke.commons.utils.Utils.convertToZeroBased;
import static duke.commons.utils.Utils.removePrefixSign;

public class TasksList {

    protected ArrayList<Task> tasks;

    /**
     * Constructs a new Task List instance.
     */
    public TasksList() {
        tasks = new ArrayList<>();
    }

    /**
     * Returns tasks list.
     *
     * @return Tasks list.
     */
    public ArrayList<Task> getTasksList() {
        return tasks;
    }

    /**
     * Returns the total number of tasks in tasks list.
     *
     * @return Number of tasks in tasks list.
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /**
     * Deletes a task in the tasks list.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for deleting the task.
     */
    public String executeDeleteTask(String commandArgs) {
        int taskIndex = 0;

        try {
            taskIndex = convertToZeroBased(commandArgs);
            Task deletedTask = tasks.get(taskIndex);

            // Delete task from task list
            tasks.remove(taskIndex);

            return getMessageForSuccessfulDelete(deletedTask);
        } catch (IndexOutOfBoundsException e) {
            return getMessageForInvalidIdRange();
        } catch (NumberFormatException e) {
            return getMessageForInvalidId();
        }
    }

    /**
     * Returns a message when user deletes a task successfully.
     *
     * @param task Task object to be marked as done.
     * @return Message for successfully delete a task.
     */
    public String getMessageForSuccessfulDelete(Task task) {
        String message = String.format(MESSAGE_DELETE_TITLE + LS
                + task.toString() + LS
                + MESSAGE_NUMBER_OF_TASKS, getNumberOfTasks());

        return String.format(MESSAGE_FORMAT, message);
    }

    /**
     * Adds a new event to tasks list.
     * If event description or event time is missing, error feedback message is returned.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for adding a new event.
     */
    public String executeAddEvent(String commandArgs) {
        String feedbackMessage = null;
        try {
            Event decodeResult = decodeEventFromString(commandArgs);
            feedbackMessage = executeAddTask(decodeResult);
        } catch (EmptyDescriptionException e) {
            feedbackMessage = getMessageForEmptyDescription(EVENT_ABBREVIATION);
        } catch (StringIndexOutOfBoundsException e) {
            feedbackMessage = getMessageForEmptyTime(EVENT_ABBREVIATION);
        } catch (EmptyTimeException e) {
            feedbackMessage = getMessageForEmptyTime(EVENT_ABBREVIATION);
        }

        return feedbackMessage;
    }

    /**
     * Decodes an event from it's supposed string representation.
     *
     * @param encoded string to be decoded.
     * @return Event object of description and time.
     * @throws EmptyDescriptionException If event description is empty.
     * @throws EmptyTimeException If event time is empty.
     */
    public Event decodeEventFromString(String encoded) throws EmptyDescriptionException, EmptyTimeException {
        Event decodedEvent = new Event(
                extractDescriptionFromString(encoded),
                extractTaskTimeFromString(encoded, EVENT_ABBREVIATION)
        );
        return decodedEvent;
    }

    /**
     * Adds a new deadline to tasks list.
     * If deadline description or deadline time is missing, error feedback message is returned.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for adding a new deadline.
     */
    public String executeAddDeadline(String commandArgs) {
        String feedbackMessage = null;

        try {
            Deadline decodeResult = decodeDeadlineFromString(commandArgs);
            feedbackMessage = executeAddTask(decodeResult);
        } catch (EmptyDescriptionException e) {
            feedbackMessage = getMessageForEmptyDescription(DEADLINE_ABBREVIATION);
        } catch (StringIndexOutOfBoundsException e) {
            feedbackMessage = getMessageForEmptyTime(DEADLINE_ABBREVIATION);
        } catch (EmptyTimeException e) {
            feedbackMessage = getMessageForEmptyTime(DEADLINE_ABBREVIATION);
        }

        return feedbackMessage;
    }

    /**
     * Decodes a deadline from it's supposed string representation.
     *
     * @param encoded string to be decoded.
     * @return Deadline object of description and time.
     * @throws EmptyDescriptionException If deadline description is empty.
     * @throws EmptyTimeException If deadline time is empty.
     */
    public Deadline decodeDeadlineFromString(String encoded) throws EmptyDescriptionException, EmptyTimeException {
        Deadline decodedDeadline = new Deadline(
                extractDescriptionFromString(encoded),
                extractTaskTimeFromString(encoded, DEADLINE_ABBREVIATION)
        );
        return decodedDeadline;
    }

    /**
     * Extracts substring representing task time from command arguments.
     *
     * @param encoded String to be decoded.
     * @param taskTypeAbbrev Abbreviation of task type.
     * @return Task time argument WITHOUT prefix.
     * @throws EmptyTimeException If deadline time is empty.
     */
    public String extractTaskTimeFromString(String encoded, Character taskTypeAbbrev) throws EmptyTimeException {
        String taskPrefix = "";

        if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
            taskPrefix = TASK_DATA_PREFIX_DEADLINE;
        } else if (taskTypeAbbrev.equals(EVENT_ABBREVIATION)) {
            taskPrefix = TASK_DATA_PREFIX_EVENT;
        }

        int indexOfDeadlinePrefix = encoded.indexOf(taskPrefix);

        String taskTime = removePrefixSign(encoded.substring(indexOfDeadlinePrefix, encoded.length()).trim(),
                taskPrefix);

        if (taskTime.isEmpty()) {
            throw new EmptyTimeException();
        }

        return taskTime;
    }

    /**
     * Extracts substring representing task description from command arguments.
     *
     * @param encoded command arguments.
     * @return Task description.
     * @throws EmptyDescriptionException If task description is empty.
     */
    public String extractDescriptionFromString(String encoded) throws EmptyDescriptionException {
        int indexOfDeadlinePrefix = encoded.indexOf(TASK_DATA_PREFIX_DEADLINE);
        int indexOfEventPrefix = encoded.indexOf(TASK_DATA_PREFIX_EVENT);

        /*
         * Description is leading substring up to data prefix string.
         * If prefix of deadline exists (indexOfDeadlinePrefix >= 0),
         * prefix of event doesn't (indexOfEventPrefix == -1) and vice versa.
         */
        int indexOfExistingPrefix = Math.max(indexOfDeadlinePrefix, indexOfEventPrefix);

        String description = encoded.substring(0, indexOfExistingPrefix).trim();

        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }

        return description;
    }

    /**
     * Adds a new Todo to tasks list.
     * If todo description is missing, error feedback message is returned.
     *
     * @param todoDescription Todo description.
     * @return Feedback display message for adding a new todo.
     */
    public String executeAddTodo(String todoDescription) {
        String taskType = "a todo";
        String feedbackMessage = "";

        if (todoDescription.isEmpty()) {
            feedbackMessage = getMessageForEmptyDescription(TODO_ABBREVIATION);
        } else {
            // Create a new Todo instance
            Todo todo = new Todo(todoDescription);
            feedbackMessage = executeAddTask(todo);
        }

        return feedbackMessage;
    }

    /**
     * Returns a message when task description is not found.
     *
     * @param taskTypeAbbrev Abbreviation of task type.
     * @return Empty description message.
     */
    public String getMessageForEmptyDescription(char taskTypeAbbrev) {
        switch (taskTypeAbbrev) {
        case DEADLINE_ABBREVIATION:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_DEADLINE_DESC);
        case EVENT_ABBREVIATION:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_EVENT_DESC);
        case TODO_ABBREVIATION:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_TODO_DESC);
        default:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_DESC);
        }
    }

    /**
     * Returns a message when time for deadline/event is not found.
     *
     * @param taskTypeAbbrev Abbreviation of task type.
     * @return Empty time message.
     */
    public String getMessageForEmptyTime(char taskTypeAbbrev) {
        switch (taskTypeAbbrev) {
        case EVENT_ABBREVIATION:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_EVENT_TIME);
        case DEADLINE_ABBREVIATION:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_DEADLINE_TIME);
        default:
            return String.format(MESSAGE_FORMAT, MESSAGE_EMPTY_TIME);
        }
    }

    /**
     * Returns a message when user's command keyword does not belong to any valid keywords.
     *
     * @return Invalid command message.
     */
    public String getMessageForInvalidCommandWord() {
        return String.format(MESSAGE_FORMAT, MESSAGE_INVALID_INPUT_WORD);
    }

    /**
     * Adds a new task to tasks list.
     *
     * @param task Task object to be added.
     * @return Feedback display message for adding a new task.
     */
    public String executeAddTask(Task task) {
        tasks.add(task);

        String message = String.format(MESSAGE_ADD_TITLE + LS
                + task.toString() + LS
                + MESSAGE_NUMBER_OF_TASKS, getNumberOfTasks());

        return String.format(MESSAGE_FORMAT, message);
    }

    /**
     * Lists out tasks added so far with their status.
     *
     * @return Feedback message for listing out all tasks.
     */
    public String executeListAllTasks() {
        String listingMessage = MESSAGE_LIST_TITLE;

        for (int i = 0; i < getNumberOfTasks(); i++) {
            listingMessage += System.lineSeparator() + LINE_PREFIX + convertToOneBased(i)
                    + SEPARATOR_TASK_ID_TASK_DESC + tasks.get(i).toString();
        }

        return String.format(MESSAGE_FORMAT, listingMessage);
    }

    /**
     * Marks a task in the tasks list as done.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for marking the task as done.
     */
    public String executeMarkTaskAsDone(String commandArgs) {
        int taskIndex = 0;

        try {
            taskIndex = convertToZeroBased(commandArgs);

            // Update status of task
            tasks.get(taskIndex).markAsDone();

            return getMessageForSuccessfulMark(tasks.get(taskIndex));
        } catch (DuplicatedMarkAsDoneException e) {
            return getMessageForDuplicatedMark(tasks.get(taskIndex));
        } catch (IndexOutOfBoundsException e) {
            return getMessageForInvalidIdRange();
        } catch (NumberFormatException e) {
            return getMessageForInvalidId();
        }
    }

    /**
     * Returns a message when task ID to be marked as done is not an integer.
     *
     * @return Message for invalid task ID.
     */
    public String getMessageForInvalidId() {
        return String.format(MESSAGE_FORMAT, MESSAGE_INVALID_ID);
    }

    /**
     * Returns a message when user inputs a task ID out of range.
     *
     * @return Message for invalid task ID.
     */
    public String getMessageForInvalidIdRange() {
        return String.format(MESSAGE_FORMAT, MESSAGE_INVALID_ID_RANGE);
    }

    /**
     * Returns a message when user marks a task as done successfully.
     *
     * @param task Task object to be marked as done.
     * @return Message for successfully mark task as done.
     */
    public String getMessageForSuccessfulMark(Task task) {
        String message = MESSAGE_DONE_TITLE + LS + task.toString();

        return String.format(MESSAGE_FORMAT, message);
    }

    /**
     * Returns an error message when user marks a task as done but it has been done earlier.
     *
     * @param task Task object marked as done earlier.
     * @return Duplicated marked as done message.
     */
    public String getMessageForDuplicatedMark(Task task) {
        String message = String.format(MESSAGE_DUPLICATED_MARK, task.getDescription());

        return String.format(MESSAGE_FORMAT, message);
    }
}
