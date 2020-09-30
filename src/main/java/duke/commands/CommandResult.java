package duke.commands;

import duke.components.TasksList;
import duke.task.Task;

import static duke.constants.Messages.MESSAGE_PLURAL_NOUN;
import static duke.constants.Messages.MESSAGE_SINGULAR_NOUN;

/**
 * Result shown to user after executing the requested command.
 */
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

    /**
     * Creates acknowledge message after executing a command.
     *
     * @param messageFormat Format of the acknowledge message.
     * @param tasks Tasks list managing all user's tasks.
     * @param task Task applied the command.
     * @return Acknowledge message after executing the command.
     */
    public static String createAcknowledgeMsg(String messageFormat, TasksList tasks, Task task) {
        int numOfTasks = tasks.getNumberOfTasks();

        if (numOfTasks > 1) {
            return String.format(messageFormat, task.toString(), numOfTasks, MESSAGE_PLURAL_NOUN);
        } else {
            return String.format(messageFormat, task.toString(), numOfTasks, MESSAGE_SINGULAR_NOUN);
        }
    }
}
