public class Todo extends Task {

    /**
     * Constructs a new Todo object inheriting from Task class.
     *
     * @param description Todo description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon and description.
     *
     * @return Task type, status icon and description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
