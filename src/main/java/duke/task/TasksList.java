package duke.task;

import java.util.ArrayList;

/**
 * A class to represent a list of tasks.
 */
public class TasksList {
    /** List of tasks */
    protected ArrayList<Task> tasks;

    /**
     * Constructs Task List object.
     */
    public TasksList() {
        tasks = new ArrayList<>();
    }

    /**
     * Returns tasks list.
     *
     * @return Tasks list.
     */
    public ArrayList<Task> getTasksList() {
        return tasks;
    }

    /**
     * Returns the total number of tasks in tasks list.
     *
     * @return Number of tasks in tasks list.
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }
}
