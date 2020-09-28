package duke.task;

import duke.exceptions.DukeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import java.time.format.DateTimeParseException;

import static duke.constants.TaskConstants.TASK_ABBREVIATION_INDEX;
import static duke.constants.TaskConstants.TASK_DESCRIPTION_INDEX;
import static duke.constants.TaskConstants.TASK_STATUS_INDEX;
import static duke.constants.TaskConstants.TASK_TIME_INDEX;
import static duke.constants.Messages.TASK_SAVE_FORMAT_DATE_TIME_EXTENSION;
import static duke.constants.TaskConstants.AN_EVENT;
import static duke.constants.TaskConstants.A_DEADLINE;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.parser.Parser.parseStringFormatDateTime;
import static duke.parser.Parser.splitTaskFromDataLine;

public abstract class TaskWithDateTime extends Task {
    private String dateTime;

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
                throw new EmptyTimeException(A_DEADLINE);
            } else if (this instanceof Event) {
                throw new EmptyTimeException(AN_EVENT);
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
     * Converts the task into a string in save format.
     *
     * @return A string in save format.
     */
    @Override
    public String encodeTask() {
        return String.format(TASK_SAVE_FORMAT_DATE_TIME_EXTENSION, super.encodeTask(), dateTime);
    }

    /**
     * Deciphers a string containing information of a task.
     *
     * @param encodedTask String containing encoded information of the task.
     * @return TaskWithDateTime object with information from encodedTask.
     */
    public static TaskWithDateTime decodeTask(String encodedTask) {
        String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

        Character taskAbbrev = encodedTask.charAt(TASK_ABBREVIATION_INDEX);
        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];
        String taskTime = taskTypeAndDetails[TASK_TIME_INDEX];

        TaskWithDateTime decodedTask = null;

        try {
            if (taskAbbrev.equals(DEADLINE_ABBREVIATION)) {
                decodedTask = new Deadline(taskDescription, taskTime);
            } else if (taskAbbrev.equals(EVENT_ABBREVIATION)) {
                decodedTask = new Event(taskDescription, taskTime);
            }

            if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
                decodedTask.isDone = true;
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        return decodedTask;
    }
}
