public class Deadline extends Task {

    protected String by;    // Deadline date

    /**
     * Constructs a new Deadline object inheriting from Task class.
     *
     * @param description Deadline description.
     * @param by          Deadline date.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Overrides toString method of class Task
     * to return task type, status icon, description and deadline date.
     *
     * @return Task type, status icon, description and deadline date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}