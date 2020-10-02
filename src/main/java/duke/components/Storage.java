package duke.components;

import duke.exceptions.EmptyDateTimeException;
import duke.exceptions.EmptyDescriptionException;
import duke.exceptions.InvalidSaveFormatException;
import duke.exceptions.LoadingException;
import duke.exceptions.SavingException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskWithDateTime;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static duke.components.Parser.splitTaskFromDataLine;
import static duke.constants.DataFileConfig.PATH_TO_DATA_FILE;
import static duke.constants.DataFileConfig.PATH_TO_DATA_FOLDER;
import static duke.constants.Messages.TASK_ENCODE_FORMAT;
import static duke.constants.Messages.TASK_ENCODE_FORMAT_DATE_TIME_EXTENSION;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_ABBREVIATION_INDEX;
import static duke.constants.TaskConstants.TASK_DESCRIPTION_INDEX;
import static duke.constants.TaskConstants.TASK_STATUS_INDEX;
import static duke.constants.TaskConstants.TASK_TIME_INDEX;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

/**
 * A class that deals with loading tasks from the data file and saving tasks in the data file.
 */
public class Storage {
    private boolean hasExistingDataFile;

    /**
     * Constructs Storage object where data file is assumed to be existed.
     */
    public Storage() {
        hasExistingDataFile = true;
    }

    /**
     * Returns boolean stating if data file is existed.
     *
     * @return Boolean stating if data file is existed.
     */
    public boolean getHasExistingDataFile() {
        return hasExistingDataFile;
    }

    /**
     * Returns tasks list from data file.
     *
     * @return Tasks list from data file.
     * @throws InvalidSaveFormatException If data line in Duke has invalid encode format.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyDateTimeException If task date time is empty.
     * @throws LoadingException If there are failed or interrupted I/O operations.
     */
    public TasksList loadData() throws InvalidSaveFormatException, EmptyDescriptionException, EmptyDateTimeException,
            LoadingException {
        TasksList tasks = new TasksList();

        if (Files.exists(PATH_TO_DATA_FOLDER)) {
            // Create a File in the given file path
            File file = new File(PATH_TO_DATA_FILE.toString());

            try {
                // Create a Scanner using the File as the source
                Scanner scanner = new Scanner(file);

                while (scanner.hasNext()) {
                    String encodedTask = scanner.nextLine();

                    Task decodedTask = decodeTask(encodedTask);
                    tasks.addTask(decodedTask);
                }
            } catch (FileNotFoundException e) {
                createDataFile(PATH_TO_DATA_FILE);
            }
        } else {
            createDataFolder(PATH_TO_DATA_FOLDER);
            createDataFile(PATH_TO_DATA_FILE);
        }

        return tasks;
    }

    /**
     * Creates data file.
     *
     * @param pathToDataFile Path to data file.
     * @throws LoadingException If there are failed or interrupted I/O operations.
     */
    private void createDataFile(Path pathToDataFile) throws LoadingException {
        try {
            hasExistingDataFile = false;
            Files.createFile(pathToDataFile);
        } catch (IOException e) {
            throw new LoadingException(e.getMessage());
        }
    }

    /**
     * Creates data folder.
     *
     * @param pathToDataFolder Path to data folder.
     * @throws LoadingException If there are failed or interrupted I/O operations.
     */
    private void createDataFolder(Path pathToDataFolder) throws LoadingException {
        try {
            Files.createDirectory(pathToDataFolder);
        } catch (IOException e) {
            throw new LoadingException(e.getMessage());
        }
    }

    /**
     * Saves tasks list data to hard disk when tasks list changes.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @throws SavingException If there are failed or interrupted I/O operations.
     */
    public void saveData(TasksList tasks) throws SavingException {
        try {
            StringBuilder tasksData = new StringBuilder();
            FileWriter fw = new FileWriter(PATH_TO_DATA_FILE.toString());

            for (Task task : tasks.getTasksList()) {
                tasksData.append(encodeTask(task) + System.lineSeparator());
            }

            fw.write(tasksData.toString());

            fw.close();
        } catch (IOException e) {
            throw new SavingException(e.getMessage());
        }
    }

    /**
     * Encodes information in a task for it to be saved and decoded in future.
     *
     * @param task Task to encode.
     * @return Encoded string with all information of the task.
     */
    private String encodeTask(Task task) {
        String encodedTaskString;

        String encodedTaskWithoutDateTime = String.format(
                TASK_ENCODE_FORMAT, task.getTaskTypeAbbrev(), task.getIsDone(), task.getDescription());

        if (task instanceof TaskWithDateTime) {
            String dateTime = ((TaskWithDateTime) task).getDateTime();

            encodedTaskString = String.format(TASK_ENCODE_FORMAT_DATE_TIME_EXTENSION, encodedTaskWithoutDateTime,
                    dateTime);
        } else {
            encodedTaskString = encodedTaskWithoutDateTime;
        }

        return encodedTaskString;
    }

    /**
     * Deciphers a string containing information of a task.
     *
     * @param encodedTask Encoded string with information of the task.
     * @return Task object with information decoded from encodedTask.
     * @throws InvalidSaveFormatException If data line in Duke has invalid encode format.
     * @throws EmptyDescriptionException If task description is empty.
     * @throws EmptyDateTimeException If task date time is empty.
     */
    private Task decodeTask(String encodedTask) throws InvalidSaveFormatException, EmptyDescriptionException,
            EmptyDateTimeException {
        try {

            String[] taskTypeAndDetails = splitTaskFromDataLine(encodedTask);

            char taskAbbrev = encodedTask.charAt(TASK_ABBREVIATION_INDEX);
            String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
            String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];

            Task decodedTask = null;

            switch (taskAbbrev) {
            case TODO_ABBREVIATION:
                decodedTask = new Todo(taskDescription, taskStatus);
                break;
            default:
                String taskTime = taskTypeAndDetails[TASK_TIME_INDEX];

                switch (taskAbbrev) {
                case DEADLINE_ABBREVIATION:
                    decodedTask = new Deadline(taskDescription, taskTime, taskStatus);
                    break;
                case EVENT_ABBREVIATION:
                    decodedTask = new Event(taskDescription, taskTime, taskStatus);
                    break;
                default:
                    throw new InvalidSaveFormatException(encodedTask);
                }
                break;
            }

            return decodedTask;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidSaveFormatException(encodedTask);
        }
    }
}
