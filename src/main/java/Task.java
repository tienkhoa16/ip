public class Task {
    protected String description;   // Task description
    protected boolean isDone;       // Task status

    /**
     * Constructs a new Task object.
     * Be default, initially, task status is set as not done.
     *
     * @param description Task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an icon according task's status.
     *
     * @return Icon according task's status.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); // Return tick or X symbols
    }

    /**
     * Returns task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks task status as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Overrides toString method of class Object to return status icon and task description.
     *
     * @return Task status icon and task description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
