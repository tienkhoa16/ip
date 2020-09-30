package duke.components;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.FindCommand;
import duke.commands.ListCommand;
import duke.exceptions.EmptyKeywordException;
import duke.exceptions.InvalidCommandWordException;
import duke.exceptions.InvalidTagException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static duke.constants.CommandConstants.COMMAND_ARGS_INDEX;
import static duke.constants.CommandConstants.COMMAND_SPLIT_LIMIT;
import static duke.constants.CommandConstants.COMMAND_TYPE_INDEX;
import static duke.constants.CommandConstants.COMMAND_WORD_BYE;
import static duke.constants.CommandConstants.COMMAND_WORD_DEADLINE;
import static duke.constants.CommandConstants.COMMAND_WORD_DELETE;
import static duke.constants.CommandConstants.COMMAND_WORD_DONE;
import static duke.constants.CommandConstants.COMMAND_WORD_EVENT;
import static duke.constants.CommandConstants.COMMAND_WORD_FIND;
import static duke.constants.CommandConstants.COMMAND_WORD_LIST;
import static duke.constants.CommandConstants.COMMAND_WORD_TODO;
import static duke.constants.CommandConstants.DESCRIPTION_START_INDEX;
import static duke.constants.CommandConstants.EMPTY_STRING;
import static duke.constants.CommandConstants.GREEDY_WHITE_SPACE;
import static duke.constants.CommandConstants.INDEX_NOT_EXIST;
import static duke.constants.Messages.VERTICAL_BAR_REGREX;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.DEADLINE_TAG;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_TAG;
import static duke.constants.TaskConstants.TASK_INPUT_DATE_TIME_FORMAT;
import static duke.constants.TaskConstants.TASK_OUTPUT_DATE_TIME_FORMAT;
import static duke.constants.TaskConstants.TODO_ABBREVIATION;

/**
 * A class that deals with making sense of the user input.
 */
public class Parser {

    /**
     * Parses and returns the Command associated with the user input.
     *
     * @param userInputString User's raw input string.
     * @return Associated command.
     * @throws InvalidCommandWordException If command word is invalid.
     * @throws EmptyKeywordException If user's keyword input for find command is empty.
     */
    public Command parseCommand(String userInputString) throws InvalidCommandWordException, EmptyKeywordException {
        String[] commandTypeAndParams = splitCommandWordAndArgs(userInputString);
        String commandType = commandTypeAndParams[COMMAND_TYPE_INDEX].toLowerCase();
        String commandArgs = commandTypeAndParams[COMMAND_ARGS_INDEX];

        switch (commandType) {
        case COMMAND_WORD_TODO:
            return new AddCommand(TODO_ABBREVIATION, commandArgs);
        case COMMAND_WORD_DEADLINE:
            return new AddCommand(DEADLINE_ABBREVIATION, commandArgs);
        case COMMAND_WORD_EVENT:
            return new AddCommand(EVENT_ABBREVIATION, commandArgs);
        case COMMAND_WORD_LIST:
            return new ListCommand();
        case COMMAND_WORD_DONE:
            return new DoneCommand(commandArgs);
        case COMMAND_WORD_DELETE:
            return new DeleteCommand(commandArgs);
        case COMMAND_WORD_FIND:
            return new FindCommand(commandArgs);
        case COMMAND_WORD_BYE:
            return new ExitCommand();
        default:
            throw new InvalidCommandWordException();
        }
    }

    /**
     * Splits raw user's input into command word and command arguments string.
     *
     * @param rawUserInput User's raw input.
     * @return Size 2 array; first element is the command type and second element is the arguments string.
     */
    private String[] splitCommandWordAndArgs(String rawUserInput) {
        String[] split = rawUserInput.trim().split(GREEDY_WHITE_SPACE,
                COMMAND_SPLIT_LIMIT);

        return split.length == COMMAND_SPLIT_LIMIT ? split : new String[]{split[COMMAND_TYPE_INDEX], EMPTY_STRING};
    }

    /**
     * Converts date and time in string format to LocalDateTime format.
     *
     * @param string Date and time in string format.
     * @return Date and time in LocalDateTime format.
     */
    public static LocalDateTime stringToDateTime(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(TASK_INPUT_DATE_TIME_FORMAT));
    }

    /**
     * Converts date and time in LocalDateTime format to string format.
     *
     * @param dateTime Date and time in LocalDateTime format.
     * @return Date and time in string format.
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(TASK_OUTPUT_DATE_TIME_FORMAT));
    }

    /**
     * Parses user's input date time format.
     *
     * @param stringFormatDateTime User's input date time format.
     * @return Parsed date and time format.
     */
    public static String parseStringFormatDateTime(String stringFormatDateTime) {
        return dateTimeToString(stringToDateTime(stringFormatDateTime));
    }

    /**
     * Extracts task description from command arguments.
     *
     * @param taskTypeAbbrev Task type abbreviation.
     * @param encoded Command arguments.
     * @return Task description.
     * @throws InvalidTagException If command tag is invalid in user's input.
     */
    public static String extractDescriptionFromString(Character taskTypeAbbrev, String encoded)
            throws InvalidTagException {
        int indexOfDeadlineTag = encoded.indexOf(DEADLINE_TAG);
        int indexOfEventTag = encoded.indexOf(EVENT_TAG);

        if (indexOfDeadlineTag >= DESCRIPTION_START_INDEX && indexOfEventTag >= DESCRIPTION_START_INDEX) {
            throw new InvalidTagException();
        }

        if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION) && (indexOfDeadlineTag == INDEX_NOT_EXIST)) {
            throw new InvalidTagException();
        }

        if (taskTypeAbbrev.equals(EVENT_ABBREVIATION) && (indexOfEventTag == INDEX_NOT_EXIST)) {
            throw new InvalidTagException();
        }

        /*
         * Description is leading substring up to command tag string.
         * If tag of deadline exists (indexOfDeadlineTag >= 0),
         * then tag of event doesn't (indexOfEventPrefix == -1) and vice versa.
         */
        int indexOfExistingTag = Math.max(indexOfDeadlineTag, indexOfEventTag);

        return encoded.substring(DESCRIPTION_START_INDEX, indexOfExistingTag).trim();
    }

    /**
     * Extracts task time from command arguments.
     *
     * @param encoded String containing task time including tag.
     * @param taskTypeAbbrev Abbreviation of task type.
     * @return Task time argument WITHOUT tag.
     */
    public static String extractTimeFromString(String encoded, Character taskTypeAbbrev) {
        String commandTag = null;

        if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
            commandTag = DEADLINE_TAG;
        } else if (taskTypeAbbrev.equals(EVENT_ABBREVIATION)) {
            commandTag = EVENT_TAG;
        }

        int indexOfExistingTag = encoded.indexOf(commandTag);
        String timeWithTag = encoded.substring(indexOfExistingTag, encoded.length()).trim();

        return removeTag(timeWithTag, commandTag);
    }

    /**
     * Removes a tag (/by, /at, etc.) from a string.
     *
     * @param string String to remove tag.
     * @param tag Tag to be removed.
     * @return String without the tag.
     */
    public static String removeTag(String string, String tag) {
        return string.replace(tag, EMPTY_STRING).trim();
    }

    /**
     * Splits data string from data file into task abbreviation and task details.
     *
     * @param dataLine Data line from data file.
     * @return Array containing task type, status, description and time (where applicable).
     */
    public static String[] splitTaskFromDataLine(String dataLine) {
        return dataLine.trim().split(VERTICAL_BAR_REGREX);
    }
}
