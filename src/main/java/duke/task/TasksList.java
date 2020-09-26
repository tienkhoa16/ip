package duke.task;

import java.util.ArrayList;

public class TasksList {

    protected ArrayList<Task> tasks;

    /**
     * Constructs a new Task List instance.
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
