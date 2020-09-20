package duke.task;

import static duke.commons.constants.DataFileConfig.TASK_DESCRIPTION_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_STATUS_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_TIME_INDEX;
import static duke.commons.constants.Messages.VERTICAL_BAR;
import static duke.commons.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.commons.utils.Utils.splitTaskFromDataLine;

public class Event extends Task {

    /**
     * Constructs a new Event object inheriting from Task class.
     *
     * @param description Event description.
     * @param at Event time.
     */
    public Event(String description, String at) {
        super(description);
        time = at;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and event time.
     *
     * @return Task type, status icon, description and event time.
     */
    @Override
    public String toString() {
        return "[" + EVENT_ABBREVIATION + "]" + super.toString() + " (at: " + time + ")";
    }

    /**
     * Overrides encodeTask method of class Task
     * to format information of an event for it to be saved and decoded in future.
     *
     * @return Encoded string with all information in the event.
     */
    @Override
    public String encodeTask() {
        return getTaskAbbreviation() + VERTICAL_BAR + getIsDone() + VERTICAL_BAR + description
                + VERTICAL_BAR + time + System.lineSeparator();
    }

    /**
     * Deciphers a string containing information of an event.
     *
     * @param encodedTask String containing encoded information of the event.
     * @return Event object with information from encodedTask.
     */
    public static Event decodeTask(String encodedTask) {
        String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];
        String taskTime = taskTypeAndDetails[TASK_TIME_INDEX];

        Event decodedEvent = new Event(taskDescription, taskTime);

        if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
            decodedEvent.isDone = true;
        }

        return decodedEvent;
    }
}
