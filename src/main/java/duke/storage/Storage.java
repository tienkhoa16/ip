package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static duke.commons.constants.DataFileConfig.PATH_TO_DATA_FILE;
import static duke.commons.constants.DataFileConfig.PATH_TO_DATA_FOLDER;
import static duke.commons.constants.DataFileConfig.TASK_ABBREVIATION_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_DESCRIPTION_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_STATUS_INDEX;
import static duke.commons.constants.DataFileConfig.TASK_TIME_INDEX;
import static duke.commons.constants.Messages.HORIZONTAL_LINE;
import static duke.commons.constants.Messages.MESSAGE_CREATE_DATA_FOLDER;
import static duke.commons.constants.Messages.MESSAGE_DATA_ERROR;
import static duke.commons.constants.Messages.MESSAGE_DATA_FILE_NOT_FOUND;
import static duke.commons.constants.Messages.MESSAGE_IO_EXCEPTION;
import static duke.commons.constants.Messages.MESSAGE_WRITE_FILE_UNSUCCESSFUL;
import static duke.commons.constants.Messages.VERTICAL_BAR;
import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.commons.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TASK_DONE_STRING_REPRESENTATION;
import static duke.commons.constants.TaskConstants.TODO_ABBREVIATION;
import static duke.commons.utils.Utils.showResultToUser;
import static duke.commons.utils.Utils.splitTaskFromDataLine;

public class Storage {

    /**
     * Returns tasks list from data file.
     *
     * @return Tasks list from data file.
     */
    public static TaskList loadData() {
        TaskList tasks = new TaskList();

        showResultToUser(HORIZONTAL_LINE);

        if (Files.exists(PATH_TO_DATA_FOLDER)) {
            // Create a File for the given file path
            File file = new File(PATH_TO_DATA_FILE.toString());

            try {
                // Create a Scanner using the File as the source
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String dataLine = scanner.nextLine();

                    String[] taskTypeAndDetails = splitTaskFromDataLine(dataLine);
                    handleAddTask(tasks, taskTypeAndDetails);
                }
                System.out.print(tasks.executeListAllTasks());
            } catch (FileNotFoundException e) {
                showResultToUser(MESSAGE_DATA_FILE_NOT_FOUND);
                createDataFile(PATH_TO_DATA_FILE);
            } catch (ArrayIndexOutOfBoundsException e) {
                showResultToUser(MESSAGE_DATA_ERROR);
            }
        } else {
            createDataFolder(PATH_TO_DATA_FOLDER);
            createDataFile(PATH_TO_DATA_FILE);
        }

        showResultToUser(HORIZONTAL_LINE + System.lineSeparator());

        return tasks;
    }

    /**
     * Adds task from its data string representation to tasks list.
     *
     * @param tasks Tasks list.
     * @param taskTypeAndDetails String representation of task in data file.
     */
    private static void handleAddTask(TaskList tasks, String[] taskTypeAndDetails) {
        String taskType = taskTypeAndDetails[TASK_ABBREVIATION_INDEX];
        String taskStatus = taskTypeAndDetails[TASK_STATUS_INDEX];
        String taskDescription = taskTypeAndDetails[TASK_DESCRIPTION_INDEX];

        switch (taskType) {
        case TODO_ABBREVIATION:
            addTodo(tasks, taskDescription);
            break;
        case EVENT_ABBREVIATION:
            String eventTime = taskTypeAndDetails[TASK_TIME_INDEX];
            addEvent(tasks, taskDescription, eventTime);
            break;
        case DEADLINE_ABBREVIATION:
            String deadlineTime = taskTypeAndDetails[TASK_TIME_INDEX];
            addDeadline(tasks, taskDescription, deadlineTime);
            break;
        default:
            showResultToUser(MESSAGE_DATA_ERROR);
            break;
        }

        if (taskStatus.equals(TASK_DONE_STRING_REPRESENTATION)) {
            String numberOfTasks = Integer.toString((tasks.getNumberOfTasks()));
            tasks.executeMarkTaskAsDone(numberOfTasks);
        }
    }

    /**
     * Adds a new deadline to tasks list.
     *
     * @param tasks Tasks list.
     * @param deadlineDescription Deadline description.
     * @param deadlineTime Deadline time.
     */
    private static void addDeadline(TaskList tasks, String deadlineDescription, String deadlineTime) {
        tasks.getTasksList().add(new Deadline(deadlineDescription, deadlineTime));
    }

    /**
     * Adds a new event to tasks list.
     *
     * @param tasks Tasks list.
     * @param eventDescription Event description.
     * @param eventTime Event time.
     */
    private static void addEvent(TaskList tasks, String eventDescription, String eventTime) {
        tasks.getTasksList().add(new Event(eventDescription, eventTime));
    }

    /**
     * Adds a new todo to tasks list.
     *
     * @param tasks Tasks list.
     * @param todoDescription Todo description.
     */
    private static void addTodo(TaskList tasks, String todoDescription) {
        tasks.getTasksList().add(new Todo(todoDescription));
    }

    /**
     * Creates data file.
     *
     * @param pathToDataFile Path to data file.
     */
    private static void createDataFile(Path pathToDataFile) {
        try {
            Files.createFile(pathToDataFile);
            showResultToUser(MESSAGE_CREATE_DATA_FOLDER);
        } catch (IOException e) {
            showResultToUser(MESSAGE_IO_EXCEPTION + e.getMessage());
        }
    }

    /**
     * Creates data folder.
     *
     * @param pathToDataFolder Path to data file.
     */
    private static void createDataFolder(Path pathToDataFolder) {
        try {
            Files.createDirectory(pathToDataFolder);
        } catch (IOException e) {
            showResultToUser(MESSAGE_IO_EXCEPTION + e.getMessage());
        }
    }

    /**
     * Saves tasks list data to hard disk when tasks list changes.
     *
     * @param tasks Tasks list.
     */
    public static void saveData(ArrayList<Task> tasks) {
        try {
            // Create a FileWriter in append mode
            FileWriter fw = new FileWriter(PATH_TO_DATA_FILE.toString());

            for (Task task : tasks) {
                String taskData = createTaskDataString(task);
                fw.write(taskData);
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(HORIZONTAL_LINE + System.lineSeparator()
                    + MESSAGE_WRITE_FILE_UNSUCCESSFUL
                    + HORIZONTAL_LINE);
        }
    }

    /**
     * Creates task data string to write to data file.
     *
     * @param task Task being written to data file.
     * @return String representation of task.
     */
    private static String createTaskDataString(Task task) {
        String taskData = task.getTaskAbbreviation() + VERTICAL_BAR + task.getIsDone()
                + VERTICAL_BAR + task.getDescription();

        if (task instanceof Deadline || task instanceof Event) {
            taskData += VERTICAL_BAR + task.getTaskTime();
        }
        return taskData + System.lineSeparator();
    }
}
