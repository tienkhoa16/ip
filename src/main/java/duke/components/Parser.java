package duke.components;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.FindCommand;
import duke.commands.ListCommand;
import duke.exceptions.EmptyKeywordException;
import duke.exceptions.EmptyTimeException;
import duke.exceptions.InvalidCommandException;

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
import static duke.constants.Messages.MESSAGE_AN_EVENT;
import static duke.constants.Messages.MESSAGE_A_DEADLINE;
import static duke.constants.Messages.VERTICAL_BAR_REGREX;
import static duke.constants.TaskConstants.DEADLINE_ABBREVIATION;
import static duke.constants.TaskConstants.EVENT_ABBREVIATION;
import static duke.constants.TaskConstants.TASK_DATA_PREFIX_DEADLINE;
import static duke.constants.TaskConstants.TASK_DATA_PREFIX_EVENT;
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
     * @throws InvalidCommandException If command word is invalid.
     * @throws EmptyKeywordException If user's keyword input for find command is empty.
     */
    public Command parseCommand(String userInputString) throws InvalidCommandException, EmptyKeywordException {
        String[] commandTypeAndParams = splitCommandWordAndArgs(userInputString);
        String commandType = commandTypeAndParams[COMMAND_TYPE_INDEX].toLowerCase();
        String commandArgs = commandTypeAndParams[COMMAND_ARGS_INDEX];

        switch (commandType) {
        case COMMAND_WORD_LIST:
            return new ListCommand();
        case COMMAND_WORD_DONE:
            return new DoneCommand(commandArgs);
        case COMMAND_WORD_TODO:
            return new AddCommand(TODO_ABBREVIATION, commandArgs);
        case COMMAND_WORD_DEADLINE:
            return new AddCommand(DEADLINE_ABBREVIATION, commandArgs);
        case COMMAND_WORD_EVENT:
            return new AddCommand(EVENT_ABBREVIATION, commandArgs);
        case COMMAND_WORD_DELETE:
            return new DeleteCommand(commandArgs);
        case COMMAND_WORD_FIND:
            return new FindCommand(commandArgs);
        case COMMAND_WORD_BYE:
            return new ExitCommand();
        default:
            throw new InvalidCommandException();
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
     * Parses input user's data and time format.
     *
     * @param stringFormatDateTime Input user's date and time format.
     * @return Parsed date and time format.
     */
    public static String parseStringFormatDateTime(String stringFormatDateTime) {
        return dateTimeToString(stringToDateTime(stringFormatDateTime));
    }

    /**
     * Extracts substring representing task activity description from command arguments.
     *
     * @param taskTypeAbbrev Task type abbreviation.
     * @param encoded command arguments.
     * @return Task activity description.
     * @throws EmptyTimeException If no prefix (/by, /at) is found in user's input.
     */
    public static String extractActivityFromString(Character taskTypeAbbrev, String encoded)
            throws EmptyTimeException {
        int indexOfDeadlinePrefix = encoded.indexOf(TASK_DATA_PREFIX_DEADLINE);
        int indexOfEventPrefix = encoded.indexOf(TASK_DATA_PREFIX_EVENT);

        /*
         * Description is leading substring up to data prefix string.
         * If prefix of deadline exists (indexOfDeadlinePrefix >= 0),
         * then prefix of event doesn't (indexOfEventPrefix == -1) and vice versa.
         */
        int indexOfExistingPrefix = Math.max(indexOfDeadlinePrefix, indexOfEventPrefix);

        if (indexOfExistingPrefix == INDEX_NOT_EXIST) {
            if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
                throw new EmptyTimeException(MESSAGE_A_DEADLINE);
            }
            if (taskTypeAbbrev.equals(EVENT_ABBREVIATION)) {
                throw new EmptyTimeException(MESSAGE_AN_EVENT);
            }
        }

        return encoded.substring(DESCRIPTION_START_INDEX, indexOfExistingPrefix).trim();
    }

    /**
     * Extracts substring representing task time from command arguments.
     *
     * @param encoded String to be decoded.
     * @param taskTypeAbbrev Abbreviation of task type.
     * @return Task time argument WITHOUT prefix.
     */
    public static String extractTimeFromString(String encoded, Character taskTypeAbbrev) {
        String taskPrefix = "";

        if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
            taskPrefix = TASK_DATA_PREFIX_DEADLINE;
        } else if (taskTypeAbbrev.equals(EVENT_ABBREVIATION)) {
            taskPrefix = TASK_DATA_PREFIX_EVENT;
        }

        int indexOfDeadlinePrefix = encoded.indexOf(taskPrefix);

        return removePrefixSign(encoded.substring(indexOfDeadlinePrefix, encoded.length()).trim(), taskPrefix);
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

    /**
     * Removes a sign (/by, /at, etc.) from parameter string.
     *
     * @param string Parameter as a string.
     * @param sign Parameter sign to be removed.
     * @return String without the sign.
     */
    public static String removePrefixSign(String string, String sign) {
        return string.replace(sign, EMPTY_STRING).trim();
    }
}
