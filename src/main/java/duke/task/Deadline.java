package duke.task;

import static duke.commons.constants.DataFileConfig.TASK_DESCRIPTION_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_STATUS_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_TIME_INDEX;
import static duke.commons.constants.Messages.VERTICAL_BAR;
import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.commons.utils.Utils.splitTaskFromDataLine;

public class Deadline extends Task {

    /**
     * Constructs a new Deadline object inheriting from Task class.
     *
     * @param description Deadline description.
     * @param by Deadline date.
     */
    public Deadline(String description, String by) {
        super(description);
        time = by;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and deadline date.
     *
     * @return Task type, status icon, description and deadline date.
     */
    @Override
    public String toString() {
        return "[" + DEADLINE_ABBREVIATION + "]" + super.toString() + " (by: " + time + ")";
    }

    /**
     * Overrides encodeTask method of class Task
     * to format information of a deadline for it to be saved and decoded in future.
     *
     * @return Encoded string with all information in the deadline.
     */
    @Override
    public String encodeTask() {
        return getTaskAbbreviation() + VERTICAL_BAR + getIsDone() + VERTICAL_BAR + description
                + VERTICAL_BAR + time + System.lineSeparator();
    }

    /**
     * Deciphers a string containing information of a deadline.
     *
     * @param encodedTask String containing encoded information of the deadline.
     * @return Deadline object with information from encodedTask.
     */
    public static Deadline decodeTask(String encodedTask) {
        String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];
        String taskTime = taskTypeAndDetails[TASK_TIME_INDEX];

        Deadline decodedDeadline = new Deadline(taskDescription, taskTime);

        if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
            decodedDeadline.isDone = true;
        }

        return decodedDeadline;
    }
}
