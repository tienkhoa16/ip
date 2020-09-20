package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TasksList;
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
import static duke.commons.constants.Messages.HORIZONTAL_LINE;
import static duke.commons.constants.Messages.MESSAGE_CREATE_DATA_FOLDER;
import static duke.commons.constants.Messages.MESSAGE_DATA_ERROR;
import static duke.commons.constants.Messages.MESSAGE_DATA_FILE_NOT_FOUND;
import static duke.commons.constants.Messages.MESSAGE_FORMAT;
import static duke.commons.constants.Messages.MESSAGE_IO_EXCEPTION;
import static duke.commons.constants.Messages.MESSAGE_WRITE_FILE_UNSUCCESSFUL;
import static duke.commons.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.commons.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.commons.constants.TaskConstants.TODO_ABBREVIATION;
import static duke.commons.utils.Utils.showResultToUser;

public class Storage {

    /**
     * Returns tasks list from data file.
     *
     * @return Tasks list from data file.
     */
    public static TasksList loadData() {
        TasksList tasks = new TasksList();

        showResultToUser(HORIZONTAL_LINE);

        if (Files.exists(PATH_TO_DATA_FOLDER)) {
            // Create a File for the given file path
            File file = new File(PATH_TO_DATA_FILE.toString());

            try {
                // Create a Scanner using the File as the source
                Scanner scanner = new Scanner(file);

                while (scanner.hasNext()) {
                    String encodedTask = scanner.nextLine();

                    switch (encodedTask.charAt(TASK_ABBREVIATION_INDEX)) {
                    case TODO_ABBREVIATION:
                        tasks.getTasksList().add(Todo.decodeTask(encodedTask));
                        break;
                    case DEADLINE_ABBREVIATION:
                        tasks.getTasksList().add(Deadline.decodeTask(encodedTask));
                        break;
                    case EVENT_ABBREVIATION:
                        tasks.getTasksList().add(Event.decodeTask(encodedTask));
                        break;
                    default:
                        showResultToUser(MESSAGE_DATA_ERROR);
                        break;
                    }
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
                fw.write(task.encodeTask());
            }
            fw.close();
        } catch (IOException e) {
            String errorMessage = String.format(MESSAGE_FORMAT, MESSAGE_WRITE_FILE_UNSUCCESSFUL);
            System.out.print(errorMessage);
        }
    }
}
