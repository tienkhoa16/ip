package duke.task;

import duke.exceptions.DukeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.EmptyTimeException;

import static duke.constants.DataFileConfig.TASK_DESCRIPTION_INDEX;
import static duke.constants.DataFileConfig.TASK_STATUS_INDEX;
import static duke.constants.DataFileConfig.TASK_TIME_INDEX;
import static duke.constants.TaskConstants.A_DEADLINE;
import static duke.constants.Messages.VERTICAL_BAR;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.parser.Parser.splitTaskFromDataLine;

public class Deadline extends Task {

    /**
     * Constructs a new Deadline object inheriting from Task class.
     *
     * @param description Deadline description.
     * @param by Deadline date.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyTimeException If task time is empty.
     */
    public Deadline(String description, String by) throws EmptyDescriptionException, EmptyTimeException {
        super(description);

        if (by.isEmpty()) {
            throw new EmptyTimeException(A_DEADLINE);
        }

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

        Deadline decodedDeadline = null;

        try {
            decodedDeadline = new Deadline(taskDescription, taskTime);

            if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
                decodedDeadline.isDone = true;
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        return decodedDeadline;
    }
}
