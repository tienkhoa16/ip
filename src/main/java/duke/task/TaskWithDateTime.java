package duke.task;

import duke.exceptions.DukeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import java.time.format.DateTimeParseException;

import static duke.components.Parser.parseStringFormatDateTime;
import static duke.components.Parser.splitTaskFromDataLine;
import static duke.constants.Messages.MESSAGE_AN_EVENT;
import static duke.constants.Messages.MESSAGE_A_DEADLINE;
import static duke.constants.Messages.TASK_ENCODE_FORMAT_DATE_TIME_EXTENSION;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_ABBREVIATION_INDEX;
import static duke.constants.TaskConstants.TASK_DESCRIPTION_INDEX;
import static duke.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.constants.TaskConstants.TASK_STATUS_INDEX;
import static duke.constants.TaskConstants.TASK_TIME_INDEX;

/**
 * A base class for task with date time.
 */
public abstract class TaskWithDateTime extends Task {
    protected String dateTime;

    /**
     * Constructs TaskWithDateTime object.
     *
     * @param description Description of task.
     * @param dateTime Date and time of task.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task date time is empty.
     */
    public TaskWithDateTime(String description, String dateTime) throws EmptyDescriptionException, EmptyTimeException {
        super(description);

        if (dateTime.isEmpty()) {
            if (this instanceof Deadline) {
                throw new EmptyTimeException(MESSAGE_A_DEADLINE);
            } else if (this instanceof Event) {
                throw new EmptyTimeException(MESSAGE_AN_EVENT);
            }
        }

        try {
            this.dateTime = parseStringFormatDateTime(dateTime);
        } catch (DateTimeParseException e) {
            this.dateTime = dateTime;
        }
    }

    /**
     * Returns the date and time of the task.
     *
     * @return Date and time.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Overrides encodeTask method of class Task to encode task with date time information.
     *
     * @return Encoded string with all information in the task.
     */
    @Override
    public String encodeTask() {
        return String.format(TASK_ENCODE_FORMAT_DATE_TIME_EXTENSION, super.encodeTask(), dateTime);
    }

    /**
     * Deciphers a string containing information of a task.
     *
     * @param encodedTask Encoded string with information of the task.
     * @return TaskWithDateTime object with information decoded from encodedTask.
     * @throws DukeException If there is exception while decoding the task.
     */
    public static TaskWithDateTime decodeTask(String encodedTask) throws DukeException {
        String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

        Character taskAbbrev = encodedTask.charAt(TASK_ABBREVIATION_INDEX);
        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];
        String taskTime = taskTypeAndDetails[TASK_TIME_INDEX];

        TaskWithDateTime decodedTask = null;

        if (taskAbbrev.equals(DEADLINE_ABBREVIATION)) {
            decodedTask = new Deadline(taskDescription, taskTime);
        } else if (taskAbbrev.equals(EVENT_ABBREVIATION)) {
            decodedTask = new Event(taskDescription, taskTime);
        }

        if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
            decodedTask.markAsDone();
        }

        return decodedTask;
    }
}
