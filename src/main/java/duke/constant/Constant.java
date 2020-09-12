package duke.constant;

public final class Constant {

    /** A decorative prefix added to the beginning of lines */
    public static final String LINE_PREFIX = "\t ";

    public static final String HORIZONTAL_LINE = LINE_PREFIX
            + "____________________________________________________________";

    public static final String VERTICAL_BAR_WITH_PADDING = " \\| ";

    public static final String ONE = "1";

    /** A platform independent line separator */
    public static final String LS = System.lineSeparator() + LINE_PREFIX;

    public static final String SAD_FACE = "\u2639  OOPS!!! ";
    public static final String MESSAGE_LIST_TITLE = "Here are the tasks in your list:";
    public static final String MESSAGE_DONE_TITLE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_ADD_TITLE = "Got it. I've added this task:";
    public static final String MESSAGE_ADD_CONCLUSION = "Now you have %d tasks in the list.";
    public static final String MESSAGE_INVALID_INPUT_WORD = SAD_FACE +
            "I'm sorry, but I don't know what that means :-(";
    public static final String MESSAGE_EMPTY_DESCRIPTION = SAD_FACE +
            "The description of %s cannot be empty.";
    public static final String MESSAGE_EMPTY_TIME = SAD_FACE +
            "The date/time of %s cannot be empty.";
    public static final String MESSAGE_DUPLICATED_MARK = SAD_FACE +
            "%s has been done earlier.";
    public static final String MESSAGE_INVALID_MARK = SAD_FACE +
            "Task ID is out of range.";
    public static final String MESSAGE_INVALID_ID = SAD_FACE +
            "Expected an integer for task ID.";
    public static final String MESSAGE_DATA_ERROR = LINE_PREFIX + "Data error.";
    public static final String MESSAGE_DATA_FILE_NOT_FOUND = LINE_PREFIX + "Data file not found.";
    public static final String MESSAGE_CREATE_DATA_FOLDER = LINE_PREFIX
            + "Created /data to store future data tasks.";

    public static final String SEPARATOR_TASK_ID_TASK_DESC = ". ";

    public static final String COMMAND_LIST_WORD = "list";          // Keyword for listing tasks
    public static final String COMMAND_BYE_WORD = "bye";            // Keyword for exiting program
    public static final String COMMAND_ADD_WORD = "add";            // Keyword for adding a new task
    public static final String COMMAND_DONE_WORD = "done";          // Keyword for marking a task as done
    public static final String COMMAND_TODO_WORD = "todo";          // Keyword for adding a new todo
    public static final String COMMAND_DEADLINE_WORD = "deadline";  // Keyword for adding a new deadline
    public static final String COMMAND_EVENT_WORD = "event";        // Keyword for adding a new event

    public static final String TODO_ABBREVIATION = "T";
    public static final String EVENT_ABBREVIATION = "E";
    public static final String DEADLINE_ABBREVIATION = "D";

    /** These are the prefix strings to define the data type of a command parameter */
    public static final String TASK_DATA_PREFIX_DEADLINE = "/by";
    public static final String TASK_DATA_PREFIX_EVENT = "/at";

    public static final String DATA_FOLDER = "data";
    public static final String DATA_FILE = "duke.txt";
}
