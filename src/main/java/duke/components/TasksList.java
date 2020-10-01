package duke.components;

import duke.task.Task;

import java.util.ArrayList;

/**
 * A class to represent a list of tasks.
 */
public class TasksList {
    protected ArrayList<Task> tasks;

    /**
     * Constructs TasksList object.
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

    /**
     * Adds a task to tasks list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the specified index in tasks list.
     *
     * @param index Index of task in zero-based numbering.
     * @return Task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Removes the task at the specified index in tasks list.
     *
     * @param index Index of task in zero-based numbering.
     */
    public void removeTask(int index) {
        tasks.remove(index);
    }
}
