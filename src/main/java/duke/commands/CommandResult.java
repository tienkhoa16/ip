package duke.commands;

public class CommandResult {
    private String feedbackMessage;

    public CommandResult(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    @Override
    public String toString() {
        return feedbackMessage;
    }
}
