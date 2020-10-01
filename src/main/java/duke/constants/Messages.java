package duke.constants;

/**
 * Messages shown in Duke application.
 */
public final class Messages {

    public static final String LINE_PREFIX = "\t ";
    public static final String HORIZONTAL_LINE = LINE_PREFIX
            + "____________________________________________________________";
    public static final String LS = System.lineSeparator() + LINE_PREFIX;
    public static final String LISTING_FORMAT = LS + "%d. %s";
    public static final String MESSAGE_ADD_TITLE = "Got it. I've added this task:";
    public static final String MESSAGE_AN_EVENT = "an event";
    public static final String MESSAGE_A_DEADLINE = "a deadline";
    public static final String MESSAGE_A_TODO = "a todo";
    public static final String MESSAGE_DELETE_TITLE = "Noted. I've removed this task:";
    public static final String MESSAGE_DONE_TITLE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_DONE_ACK = MESSAGE_DONE_TITLE + LS + "%s";
    public static final String MESSAGE_EMPTY_TASKS_LIST = "You don't have any tasks.";
    public static final String MESSAGE_EXIT = "Bye buddy. Hope to see you again soon!";
    public static final String MESSAGE_FIND_FORMAT = "Here is the list of matching tasks:%s";
    public static final String MESSAGE_FORMAT = HORIZONTAL_LINE + LS + "%s" + System.lineSeparator()
            + HORIZONTAL_LINE + System.lineSeparator();
    public static final String MESSAGE_CREATED_NEW_DATA_FILE = String.format(
            MESSAGE_FORMAT, "Created data/duke.txt to store future tasks.");
    public static final String MESSAGE_LIST_FORMAT = "Here is the list of your tasks:%s";
    public static final String MESSAGE_LOAD_SUCCESSFUL = String.format(
            MESSAGE_FORMAT, "Loading saved tasks completed.");
    public static final String MESSAGE_NUMBER_OF_TASKS = "Now you have %d task%s in the list.";
    public static final String MESSAGE_DELETE_ACK = MESSAGE_DELETE_TITLE + LS + "%s" + LS + MESSAGE_NUMBER_OF_TASKS;
    public static final String MESSAGE_ADD_ACK = MESSAGE_ADD_TITLE + LS + "%s" + LS + MESSAGE_NUMBER_OF_TASKS;
    public static final String MESSAGE_PLURAL_NOUN = "s";
    public static final String MESSAGE_SINGULAR_NOUN = "";
    public static final String MESSAGE_WELCOME = "Hello dude! I'm Duke" + LS + "How can I help you?";
    public static final String SAD_FACE = ":(  OOPS!!! ";
    public static final String MESSAGE_NO_MATCH = SAD_FACE + "There are no results that match your keyword.";
    public static final String MESSAGE_LOADING_ERROR = SAD_FACE + "An error has occurred while loading data."
            + LS + "%s";
    public static final String MESSAGE_SAVING_ERROR = SAD_FACE + "An error has occurred while saving data."
            + LS + "%s";
    public static final String MESSAGE_INVALID_COMMAND_WORD = SAD_FACE + "Sorry, but I don't know what that means.";
    public static final String MESSAGE_INVALID_TAG = SAD_FACE + "Command tag is invalid." + LS
            + "- For deadline, use tag /by D/M/YYYY HHMM" + LS
            + "- For event, use tag /at D/M/YYYY HHMM";
    public static final String MESSAGE_EMPTY_DESCRIPTION = SAD_FACE + "The description of %s cannot be empty.";
    public static final String MESSAGE_EMPTY_KEYWORD = SAD_FACE + "Keyword cannot be empty.";
    public static final String MESSAGE_EMPTY_TIME = SAD_FACE + "The date and time of %s cannot be empty.";
    public static final String MESSAGE_REDUNDANT_PARAM = SAD_FACE + "%s command does not take parameters.";
    public static final String MESSAGE_INVALID_INDEX_RANGE = SAD_FACE + "INDEX is out of range.";
    public static final String MESSAGE_DUPLICATED_MARK = SAD_FACE + "%s has been done earlier.";
    public static final String MESSAGE_INVALID_INDEX = SAD_FACE + "Expected an integer for INDEX.";
    public static final String MESSAGE_INVALID_SAVE_FORMAT = SAD_FACE + "Save format '%s' is invalid.";
    public static final String TASK_ENCODE_FORMAT = "%s | %s | %s";
    public static final String TASK_ENCODE_FORMAT_DATE_TIME_EXTENSION = "%s | %s";
    public static final String VERTICAL_BAR_REGREX = " \\| ";
}
