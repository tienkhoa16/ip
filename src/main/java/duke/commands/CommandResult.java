package duke.commands;

public class CommandResult {
    private String feedbackMessage;

    /**
     * Constructs CommandResult object.
     *
     * @param feedbackMessage Feedback message after executing command.
     */
    public CommandResult(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    /**
     * Overrides toString method of class Object to get string representation of CommandResult object.
     *
     * @return String representation of CommandResult object.
     */
    @Override
    public String toString() {
        return feedbackMessage;
    }
}
