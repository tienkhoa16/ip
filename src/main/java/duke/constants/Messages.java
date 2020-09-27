package duke.constants;

/**
 * Messages shown in Duke application.
 */
public final class Messages {

    /** A decorative prefix added to the beginning of lines */
    public static final String LINE_PREFIX = "\t ";
    public static final String HORIZONTAL_LINE = LINE_PREFIX
            + "____________________________________________________________";
    /** A platform independent line separator */
    public static final String LS = System.lineSeparator() + LINE_PREFIX;

    public static final String MESSAGE_ADD_TITLE = "Got it. I've added this task:";
    public static final String MESSAGE_DELETE_TITLE = "Noted. I've removed this task:";
    public static final String MESSAGE_DONE_TITLE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_LIST_TITLE = "Here are the tasks in your list:";
    public static final String MESSAGE_NUMBER_OF_TASKS = "Now you have %d tasks in the list.";
    public static final String SAD_FACE = "\u2639  OOPS!!! ";
    public static final String MESSAGE_LOADING_ERROR = SAD_FACE + "An error has occurred while loading data.";
    public static final String MESSAGE_SAVING_ERROR = SAD_FACE + "An error has occurred while saving data.";
    public static final String MESSAGE_INVALID_INPUT_WORD = SAD_FACE +
            "I'm sorry, but I don't know what that means :-(";
    public static final String MESSAGE_EMPTY_DESCRIPTION = SAD_FACE +
            "The description of %s cannot be empty.";
    public static final String MESSAGE_EMPTY_TIME = SAD_FACE +
            "The date/time of %s cannot be empty.";
    public static final String MESSAGE_INVALID_ID_RANGE = SAD_FACE +
            "Task ID is out of range.";
    public static final String MESSAGE_DUPLICATED_MARK = SAD_FACE +
            "%s has been done earlier.";
    public static final String MESSAGE_INVALID_ID = SAD_FACE +
            "Expected an integer for task ID.";
    public static final String MESSAGE_IO_EXCEPTION = SAD_FACE + "Something went wrong: ";
    public static final String MESSAGE_WRITE_FILE_UNSUCCESSFUL = SAD_FACE
            + "Unable to append %s to data file.";
    public static final String MESSAGE_WELCOME = "Hello dude! I'm Duke" + LS
            + "How can I help you?";
    public static final String MESSAGE_EXIT = "Bye buddy. Hope to see you again soon!";

    /** A common format for displaying message */
    public static final String MESSAGE_FORMAT = HORIZONTAL_LINE + LS
            + "%s" + System.lineSeparator()
            + HORIZONTAL_LINE + System.lineSeparator();
    public static final String LISTING_FORMAT = System.lineSeparator() + LINE_PREFIX + "%d. %s";
    public static final String MESSAGE_DELETE_ACK = MESSAGE_DELETE_TITLE + LS
            + "%s" + LS
            + MESSAGE_NUMBER_OF_TASKS;
    public static final String MESSAGE_ADD_ACK = MESSAGE_ADD_TITLE + LS
            + "%s" + LS
            + MESSAGE_NUMBER_OF_TASKS;
    public static final String MESSAGE_DONE_ACK = MESSAGE_DONE_TITLE + LS + "%s";

    public static final String VERTICAL_BAR = " | ";
    public static final String VERTICAL_BAR_REGREX = " \\| ";
}
