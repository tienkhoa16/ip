package duke.task;

import duke.exception.DukeException;
import duke.exception.EmptyTimeException;

import java.util.ArrayList;

import static duke.constant.Constant.HORIZONTAL_LINE;
import static duke.constant.Constant.LINE_PREFIX;
import static duke.constant.Constant.LS;
import static duke.constant.Constant.MESSAGE_ADD_TITLE;
import static duke.constant.Constant.MESSAGE_DELETE_TITLE;
import static duke.constant.Constant.MESSAGE_DONE_TITLE;
import static duke.constant.Constant.MESSAGE_DUPLICATED_MARK;
import static duke.constant.Constant.MESSAGE_EMPTY_DESCRIPTION;
import static duke.constant.Constant.MESSAGE_EMPTY_TIME;
import static duke.constant.Constant.MESSAGE_INVALID_ID;
import static duke.constant.Constant.MESSAGE_INVALID_ID_RANGE;
import static duke.constant.Constant.MESSAGE_INVALID_INPUT_WORD;
import static duke.constant.Constant.MESSAGE_LIST_TITLE;
import static duke.constant.Constant.MESSAGE_NUMBER_OF_TASKS;
import static duke.constant.Constant.SEPARATOR_TASK_ID_TASK_DESC;
import static duke.constant.Constant.TASK_DATA_PREFIX_DEADLINE;
import static duke.constant.Constant.TASK_DATA_PREFIX_EVENT;
import static duke.storage.Storage.saveData;


public class TaskList {

    protected ArrayList<Task> tasks;    // List of all tasks.

    /**
     * Constructs a new Task List instance.
     */
    public TaskList() {
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
     * Marks a task in the tasks list as done.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for marking the task as done.
     */
    public String executeDeleteTask(String commandArgs) {
        int taskIndex = 0;

        try {
            taskIndex = extractTaskIndexFromInputString(commandArgs);
            Task deletedTask = tasks.get(taskIndex);

            // Delete task from task list
            tasks.remove(taskIndex);

            // Save updated tasks list to hard disk
            saveData(tasks);

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
        return String.format(HORIZONTAL_LINE + LS
                + MESSAGE_DELETE_TITLE + LS
                + task.toString() + LS
                + MESSAGE_NUMBER_OF_TASKS + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator(), getNumberOfTasks());
    }

    /**
     * Adds a new event to tasks list.
     * If event description or event date is missing, error feedback message is returned.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for adding a new event.
     */
    public String executeAddEvent(String commandArgs) {
        final String TASK_TYPE = "an event";
        String feedbackMessage = null;
        try {
            Event decodeResult = decodeEventFromString(commandArgs);
            feedbackMessage = executeAddTask(decodeResult);
        } catch (DukeException e) {
            feedbackMessage = getMessageForEmptyDescription(TASK_TYPE);
        } catch (StringIndexOutOfBoundsException e) {
            feedbackMessage = getMessageForEmptyTime(TASK_TYPE);
        } catch (EmptyTimeException e) {
            feedbackMessage = getMessageForEmptyTime(TASK_TYPE);
        }

        return feedbackMessage;
    }

    /**
     * Decodes an event from it's supposed string representation.
     *
     * @param encoded string to be decoded.
     * @return Event object of description and time.
     * @throws DukeException If event description is empty.
     * @throws EmptyTimeException If event time is empty.
     */
    public Event decodeEventFromString(String encoded) throws DukeException, EmptyTimeException {
        Event decodedEvent = makeEventFromData(
                extractDescriptionFromString(encoded),
                extractEventTimeFromString(encoded)
        );
        return decodedEvent;
    }

    /**
     * Creates an event from the given data.
     *
     * @param description Description of event.
     * @param time Event time without data prefix.
     * @return Constructed event.
     * @throws DukeException If event description is empty.
     */
    public Event makeEventFromData(String description, String time) throws DukeException {
        return new Event(description, time);
    }

    /**
     * Extracts substring representing event time from command arguments.
     *
     * @param encoded String to be decoded.
     * @return Event time argument WITHOUT prefix.
     * @throws EmptyTimeException If event time is empty.
     */
    public String extractEventTimeFromString(String encoded) throws EmptyTimeException {
        int indexOfEventPrefix = encoded.indexOf(TASK_DATA_PREFIX_EVENT);

        String eventTime = removePrefixSign(encoded.substring(indexOfEventPrefix, encoded.length()).trim(),
                TASK_DATA_PREFIX_EVENT);

        if (eventTime.isEmpty()) {
            throw new EmptyTimeException();
        }
        return eventTime;
    }

    /**
     * Adds a new deadline to tasks list.
     * If deadline description or deadline date is missing, error feedback message is returned.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for adding a new deadline.
     */
    public String executeAddDeadline(String commandArgs) {
        final String TASK_TYPE = "a deadline";
        String feedbackMessage = null;

        try {
            Deadline decodeResult = decodeDeadlineFromString(commandArgs);
            feedbackMessage = executeAddTask(decodeResult);
        } catch (DukeException e) {
            feedbackMessage = getMessageForEmptyDescription(TASK_TYPE);
        } catch (StringIndexOutOfBoundsException e) {
            feedbackMessage = getMessageForEmptyTime(TASK_TYPE);
        } catch (EmptyTimeException e) {
            feedbackMessage = getMessageForEmptyTime(TASK_TYPE);
        }

        return feedbackMessage;
    }

    /**
     * Decodes a deadline from it's supposed string representation.
     *
     * @param encoded string to be decoded.
     * @return Deadline object of description and date.
     * @throws DukeException If deadline description is empty.
     * @throws EmptyTimeException If deadline time is empty.
     */
    public Deadline decodeDeadlineFromString(String encoded) throws DukeException, EmptyTimeException {
        Deadline decodedDeadline = makeDeadlineFromData(
                extractDescriptionFromString(encoded),
                extractDeadlineDateFromString(encoded)
        );
        return decodedDeadline;
    }

    /**
     * Creates a deadline from the given data.
     *
     * @param description Description of deadline.
     * @param date Deadline date without data prefix.
     * @return Constructed deadline.
     * @throws DukeException If deadline description is empty.
     */
    public Deadline makeDeadlineFromData(String description, String date) throws DukeException {
        return new Deadline(description, date);
    }

    /**
     * Extracts substring representing deadline date from command arguments.
     *
     * @param encoded string to be decoded.
     * @return Deadline date argument WITHOUT prefix.
     * @throws EmptyTimeException If deadline date is empty.
     */
    public String extractDeadlineDateFromString(String encoded) throws EmptyTimeException {
        int indexOfDeadlinePrefix = encoded.indexOf(TASK_DATA_PREFIX_DEADLINE);

        String deadlineDate = removePrefixSign(encoded.substring(indexOfDeadlinePrefix, encoded.length()).trim(),
                TASK_DATA_PREFIX_DEADLINE);

        if (deadlineDate.isEmpty()) {
            throw new EmptyTimeException();
        }
        return deadlineDate;
    }

    /**
     * Extracts substring representing task description from command arguments.
     *
     * @param encoded command arguments.
     * @return Task description.
     * @throws DukeException If task description is empty.
     */
    public String extractDescriptionFromString(String encoded) throws DukeException {
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
            throw new DukeException();
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
        String feedbackMessage = null;

        if (todoDescription.isEmpty()) {
            feedbackMessage = getMessageForEmptyDescription(taskType);
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
     * @param taskType String stating which task type's description is missing.
     * @return Empty description message.
     */
    public String getMessageForEmptyDescription(String taskType) {
        return String.format(HORIZONTAL_LINE + LS
                + MESSAGE_EMPTY_DESCRIPTION + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator(), taskType);
    }

    /**
     * Returns a message when date/time for deadline/event is not found.
     *
     * @param taskType String stating which task type's date/time is missing.
     * @return Empty date/time message.
     */
    public String getMessageForEmptyTime(String taskType) {
        return String.format(HORIZONTAL_LINE + LS
                + MESSAGE_EMPTY_TIME + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator(), taskType);
    }

    /**
     * Returns a message when user's command keyword does not belong to any valid keywords.
     *
     * @return Invalid input message.
     */
    public String getMessageForInvalidInputWord() {
        return HORIZONTAL_LINE + LS
                + MESSAGE_INVALID_INPUT_WORD + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator();
    }

    /**
     * Adds a new task to tasks list.
     *
     * @param task Task object to be added.
     * @return Feedback display message for adding a new task.
     */
    public String executeAddTask(Task task) {
        tasks.add(task);

        // Save updated tasks list to hard disk
        saveData(tasks);

        return String.format(HORIZONTAL_LINE + LS
                + MESSAGE_ADD_TITLE + LS
                + task.toString() + LS
                + MESSAGE_NUMBER_OF_TASKS + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator(), getNumberOfTasks());
    }

    /**
     * Lists out tasks added so far with their status.
     */
    public String executeListAllTasks() {
        String feedback = HORIZONTAL_LINE + LS
                + MESSAGE_LIST_TITLE + System.lineSeparator();

        // Iterate through tasks list and print each task with its status and description
        for (int i = 0; i < getNumberOfTasks(); i++) {
            feedback += LINE_PREFIX + (i + 1) + SEPARATOR_TASK_ID_TASK_DESC
                    + tasks.get(i).toString() + System.lineSeparator();
        }

        feedback += HORIZONTAL_LINE + System.lineSeparator();
        return feedback;
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
            taskIndex = extractTaskIndexFromInputString(commandArgs);

            // Update status of task
            tasks.get(taskIndex).markAsDone();

            // Save updated tasks list to hard disk
            saveData(tasks);

            return getMessageForSuccessfulMark(tasks.get(taskIndex));
        } catch (DukeException e) {
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
        return HORIZONTAL_LINE + LS
                + MESSAGE_INVALID_ID + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator();
    }

    /**
     * Returns a message when user inputs a task ID out of range.
     *
     * @return Message for invalid task ID.
     */
    public String getMessageForInvalidIdRange() {
        /*
         * Task ID displayed to user is task index in tasks list + 1
         * because task index in list starts from 0
         * while task ID displayed to user starts from 1.
         */
        return HORIZONTAL_LINE + LS
                + MESSAGE_INVALID_ID_RANGE + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator();
    }

    /**
     * Returns a message when user marks a task as done successfully.
     *
     * @param task Task object to be marked as done.
     * @return Message for successfully mark task as done.
     */
    public String getMessageForSuccessfulMark(Task task) {
        return HORIZONTAL_LINE + LS
                + MESSAGE_DONE_TITLE + LS
                + task.toString() + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator();
    }

    /**
     * Returns an error message when user marks a task as done but it has been done earlier.
     *
     * @param task Task object marked as done earlier.
     * @return Duplicated marked as done message.
     */
    public String getMessageForDuplicatedMark(Task task) {
        return String.format(HORIZONTAL_LINE + LS
                + MESSAGE_DUPLICATED_MARK + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator(), task.getDescription());
    }

    /**
     * Converts task ID in user's command (starting from 1)
     * to the corresponding task index  in tasks list (starting from 0).
     * In the case of "done X" command,
     * the command argument X is the task ID to be marked as done.
     *
     * @param commandArgs User's argument passed in the command.
     * @return Task index.
     */
    public int extractTaskIndexFromInputString(String commandArgs) {
        return Integer.parseInt(commandArgs) - 1;
    }

    /**
     * Removes a sign (/by, /at, etc.) from parameter string.
     *
     * @param string Parameter as a string.
     * @param sign Parameter sign to be removed.
     * @return String without the sign.
     */
    public String removePrefixSign(String string, String sign) {
        return string.replace(sign, "").trim();
    }
}
