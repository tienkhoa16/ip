package duke.storage;

import duke.exceptions.DukeException;
import duke.exceptions.LoadingException;
import duke.exceptions.SavingException;
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
import java.util.Scanner;

import static duke.constants.DataFileConfig.PATH_TO_DATA_FILE;
import static duke.constants.DataFileConfig.PATH_TO_DATA_FOLDER;
import static duke.constants.DataFileConfig.TASK_ABBREVIATION_INDEX;
import static duke.constants.Messages.MESSAGE_IO_EXCEPTION;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

public class Storage {

    /**
     * Returns tasks list from data file.
     *
     * @return Tasks list from data file.
     * @throws DukeException If there are exceptions while loading data.
     */
    public TasksList loadData() throws DukeException {
        TasksList tasks = new TasksList();

        if (Files.exists(PATH_TO_DATA_FOLDER)) {
            // Create a File in the given file path
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
                        throw new LoadingException();
                    }
                }
            } catch (FileNotFoundException e) {
                createDataFile(PATH_TO_DATA_FILE);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new LoadingException();
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
     * @throws DukeException If there are failed or interrupted I/O operations.
     */
    private void createDataFile(Path pathToDataFile) throws DukeException {
        try {
            Files.createFile(pathToDataFile);
        } catch (IOException e) {
            throw new DukeException(MESSAGE_IO_EXCEPTION + e.getMessage());
        }
    }

    /**
     * Creates data folder.
     *
     * @param pathToDataFolder Path to data file.
     * @throws DukeException If there are failed or interrupted I/O operations.
     */
    private void createDataFolder(Path pathToDataFolder) throws DukeException {
        try {
            Files.createDirectory(pathToDataFolder);
        } catch (IOException e) {
            throw new DukeException(MESSAGE_IO_EXCEPTION + e.getMessage());
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
            FileWriter fw = new FileWriter(PATH_TO_DATA_FILE.toString());

            for (Task task : tasks.getTasksList()) {
                fw.write(task.encodeTask());
            }
            fw.close();
        } catch (IOException e) {
            throw new SavingException();
        }
    }
}
