package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskManager;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import static duke.constant.Constant.DEADLINE_ABBREVIATION;
import static duke.constant.Constant.EVENT_ABBREVIATION;
import static duke.constant.Constant.HORIZONTAL_LINE;
import static duke.constant.Constant.MESSAGE_CREATE_DATA_FOLDER;
import static duke.constant.Constant.MESSAGE_DATA_ERROR;
import static duke.constant.Constant.MESSAGE_DATA_FILE_NOT_FOUND;
import static duke.constant.Constant.MESSAGE_WRITE_FILE_UNSUCCESSFUL;
import static duke.constant.Constant.ONE;
import static duke.constant.Constant.PATH_TO_DATA_FILE;
import static duke.constant.Constant.PATH_TO_DATA_FOLDER;
import static duke.constant.Constant.TODO_ABBREVIATION;
import static duke.constant.Constant.VERTICAL_BAR;
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

        if (Files.exists(PATH_TO_DATA_FOLDER)) {
            // Create a File for the given file path
            File file = new File(PATH_TO_DATA_FILE.toString());

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
                        tasks.getTasksList().add(new Todo(taskDescription));
                        break;
                    case EVENT_ABBREVIATION:
                        String eventTime = taskTypeAndDetails[3];
                        tasks.getTasksList().add(new Event(taskDescription, eventTime));
                        break;
                    case DEADLINE_ABBREVIATION:
                        String deadlineTime = taskTypeAndDetails[3];
                        tasks.getTasksList().add(new Deadline(taskDescription, deadlineTime));
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
                System.out.print(tasks.executeListAllTasks());
            } catch (FileNotFoundException e) {
                System.out.println(MESSAGE_DATA_FILE_NOT_FOUND);
                createDataFile(PATH_TO_DATA_FILE);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(MESSAGE_DATA_ERROR);
            }
        } else {
            createDataFolder(PATH_TO_DATA_FOLDER);
            createDataFile(PATH_TO_DATA_FILE);
        }
        System.out.println(HORIZONTAL_LINE + System.lineSeparator());
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
            System.out.println(MESSAGE_CREATE_DATA_FOLDER);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
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
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void saveData(ArrayList<Task> tasks) {
        try {
            // Create a FileWriter in append mode
            FileWriter fw = new FileWriter(PATH_TO_DATA_FILE.toString());

            for (Task task : tasks) {
                String taskData = task.getTaskAbbreviation() + VERTICAL_BAR + task.getIsDone()
                        + VERTICAL_BAR + task.getDescription();

                if (task instanceof Deadline || task instanceof Event) {
                    taskData += VERTICAL_BAR + task.getTaskTime();
                }
                fw.write(taskData + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(HORIZONTAL_LINE + System.lineSeparator()
                    + MESSAGE_WRITE_FILE_UNSUCCESSFUL
                    + HORIZONTAL_LINE);
        }
    }
}
