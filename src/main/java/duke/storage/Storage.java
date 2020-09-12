package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskManager;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static duke.constant.Constant.DATA_FILE;
import static duke.constant.Constant.DATA_FOLDER;
import static duke.constant.Constant.DEADLINE_ABBREVIATION;
import static duke.constant.Constant.EVENT_ABBREVIATION;
import static duke.constant.Constant.HORIZONTAL_LINE;
import static duke.constant.Constant.MESSAGE_CREATE_DATA_FOLDER;
import static duke.constant.Constant.MESSAGE_DATA_ERROR;
import static duke.constant.Constant.MESSAGE_DATA_FILE_NOT_FOUND;
import static duke.constant.Constant.ONE;
import static duke.constant.Constant.TODO_ABBREVIATION;
import static duke.util.Util.splitTaskFromDataLine;

public class Storage {

    /**
     * Returns tasks list from data file.
     *
     * @return Tasks list from data file.
     */
    public static TaskManager loadData() {
        TaskManager tasks = new TaskManager();

        System.out.println(HORIZONTAL_LINE);

        // Get directory of project root
        String projectRoot = System.getProperty("user.dir");

        // Get directory of data folder and data file
        Path pathToDataFolder = Paths.get(projectRoot, DATA_FOLDER);
        Path pathToDataFile = Paths.get(projectRoot, DATA_FOLDER, DATA_FILE);

        if (Files.exists(pathToDataFolder)) {
            // Create a File for the given file path
            File file = new File(pathToDataFile.toString());

            try {
                // Create a Scanner using the File as the source
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String dataLine = scanner.nextLine();

                    String[] taskTypeAndDetails = splitTaskFromDataLine(dataLine);

                    String taskType = taskTypeAndDetails[0];
                    String taskStatus = taskTypeAndDetails[1];
                    String taskDescription = taskTypeAndDetails[2];

                    switch (taskType) {
                    case TODO_ABBREVIATION:
                        tasks.getTasks().add(new Todo(taskDescription));
                        break;
                    case EVENT_ABBREVIATION:
                        String eventTime = taskTypeAndDetails[3];
                        tasks.getTasks().add(new Event(taskDescription, eventTime));
                        break;
                    case DEADLINE_ABBREVIATION:
                        String deadlineTime = taskTypeAndDetails[3];
                        tasks.getTasks().add(new Deadline(taskDescription, deadlineTime));
                        break;
                    default:
                        System.out.println(MESSAGE_DATA_ERROR);
                        break;
                    }

                    if (taskStatus.equals(ONE)) {
                        String numberOfTasks = Integer.toString((tasks.getNumberOfTasks()));
                        tasks.executeMarkTaskAsDone(numberOfTasks);
                    }
                }
                System.out.println(tasks.executeListAllTasks());
            } catch (FileNotFoundException e) {
                System.out.println(MESSAGE_DATA_FILE_NOT_FOUND);
            }
        } else {
            // Create directory for data folder
            try {
                Files.createDirectory(pathToDataFolder);
                System.out.println(MESSAGE_CREATE_DATA_FOLDER);
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
        System.out.println(HORIZONTAL_LINE);
        return tasks;
    }
}
