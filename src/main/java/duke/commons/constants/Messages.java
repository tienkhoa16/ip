package duke.commons.constants;

public final class Messages {

    /** A decorative prefix added to the beginning of lines */
    public static final String LINE_PREFIX = "\t ";
    public static final String HORIZONTAL_LINE = LINE_PREFIX
            + "____________________________________________________________";
    /** A platform independent line separator */
    public static final String LS = System.lineSeparator() + LINE_PREFIX;

    public static final String A_TODO = "a todo";
    public static final String A_DEADLINE = "a deadline";
    public static final String AN_EVENT = "an event";

    public static final String MESSAGE_ADD_TITLE = "Got it. I've added this task:";
    public static final String MESSAGE_CREATE_DATA_FOLDER = LINE_PREFIX
            + "Created ./data/duke.txt to store future tasks data.";
    public static final String MESSAGE_DATA_ERROR = LINE_PREFIX + "Data error.";
    public static final String MESSAGE_DATA_FILE_NOT_FOUND = LINE_PREFIX + "Data file not found.";
    public static final String MESSAGE_DELETE_TITLE = "Noted. I've removed this task:";
    public static final String MESSAGE_DONE_TITLE = "Nice! I've marked this task as done:";
    /** A common format for display message */
    public static final String MESSAGE_FORMAT = HORIZONTAL_LINE + LS
            + "%s" + System.lineSeparator()
            + HORIZONTAL_LINE + System.lineSeparator();
    public static final String MESSAGE_LIST_TITLE = "Here are the tasks in your list:";
    public static final String MESSAGE_NUMBER_OF_TASKS = "Now you have %d tasks in the list.";
    public static final String MESSAGE_ADD_ACK = MESSAGE_ADD_TITLE + LS
            + "%s" + LS
            + MESSAGE_NUMBER_OF_TASKS;
    public static final String MESSAGE_DELETE_ACK = MESSAGE_DELETE_TITLE + LS
            + "%s" + LS
            + MESSAGE_NUMBER_OF_TASKS;
    public static final String SAD_FACE = "\u2639  OOPS!!! ";
    public static final String MESSAGE_INVALID_INPUT_WORD = SAD_FACE +
            "I'm sorry, but I don't know what that means :-(";
    public static final String MESSAGE_EMPTY_DESCRIPTION = SAD_FACE +
            "The description of %s cannot be empty.";
    public static final String MESSAGE_EMPTY_TODO_DESC = SAD_FACE +
            "The description of a todo cannot be empty.";
    public static final String MESSAGE_EMPTY_EVENT_DESC = SAD_FACE +
            "The description of an event cannot be empty.";
    public static final String MESSAGE_EMPTY_DEADLINE_DESC = SAD_FACE +
            "The description of a deadline cannot be empty.";
    public static final String MESSAGE_EMPTY_TIME = SAD_FACE +
            "The date/time of %s cannot be empty.";
    public static final String MESSAGE_EMPTY_DEADLINE_TIME = SAD_FACE +
            "The date/time of a deadline cannot be empty.";
    public static final String MESSAGE_EMPTY_EVENT_TIME = SAD_FACE +
            "The date/time of an event cannot be empty.";
    public static final String MESSAGE_INVALID_ID_RANGE = SAD_FACE +
            "Task ID is out of range";
    public static final String MESSAGE_DUPLICATED_MARK = SAD_FACE +
            "%s has been done earlier.";
    public static final String MESSAGE_INVALID_ID = SAD_FACE +
            "Expected an integer for task ID.";
    public static final String MESSAGE_IO_EXCEPTION = SAD_FACE + "Something went wrong: ";
    public static final String MESSAGE_WRITE_FILE_UNSUCCESSFUL = SAD_FACE
            + "Unable to append %s to data file.";

    public static final String SEPARATOR_TASK_ID_TASK_DESC = ". ";
    public static final String VERTICAL_BAR = " | ";
    public static final String VERTICAL_BAR_REGREX = " \\| ";
}
