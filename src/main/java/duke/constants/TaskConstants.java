package duke.constants;

/**
 * Constants related to task specifications.
 */
public final class TaskConstants {
    public static final char DEADLINE_ABBREVIATION = 'D';
    public static final String DEADLINE_STRING_REPRESENTATION = "%s (by: %s)";
    public static final String DEADLINE_TAG = "/by";
    public static final char EVENT_ABBREVIATION = 'E';
    public static final String EVENT_STRING_REPRESENTATION = "%s (at: %s)";
    public static final String EVENT_TAG = "/at";
    public static final int TASK_ABBREVIATION_INDEX = 0;
    public static final int TASK_DESCRIPTION_INDEX = 2;
    public static final int TASK_DETAILS_START_INDEX = 6;
    public static final String TASK_DONE_ICON = "/";
    public static final String TASK_DONE_STRING_REPRESENTATION = "1";
    public static final String TASK_INPUT_DATE_TIME_FORMAT = "d/M/yyyy HHmm";
    public static final String TASK_OUTPUT_DATE_TIME_FORMAT = "d MMMM yyyy, h.mma";
    public static final int TASK_STATUS_INDEX = 1;
    public static final String TASK_STRING_REPRESENTATION = "[%s][%s] %s";
    public static final int TASK_TIME_INDEX = 3;
    public static final String TASK_UNDONE_ICON = "X";
    public static final String TASK_UNDONE_STRING_REPRESENTATION = "0";
    public static final char TODO_ABBREVIATION = 'T';
}
