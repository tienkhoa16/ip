package duke.parser;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.exceptions.EmptyTimeException;
import duke.exceptions.InvalidCommandException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static duke.constants.CommandWords.COMMAND_BYE_WORD;
import static duke.constants.CommandWords.COMMAND_DEADLINE_WORD;
import static duke.constants.CommandWords.COMMAND_DELETE_WORD;
import static duke.constants.CommandWords.COMMAND_DONE_WORD;
import static duke.constants.CommandWords.COMMAND_EVENT_WORD;
import static duke.constants.CommandWords.COMMAND_LIST_WORD;
import static duke.constants.CommandWords.COMMAND_TODO_WORD;
import static duke.constants.Messages.VERTICAL_BAR_REGREX;
import static duke.constants.TaskConstants.AN_EVENT;
import static duke.constants.TaskConstants.A_DEADLINE;
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
     */
    public Command parseCommand(String userInputString) throws InvalidCommandException {
        String[] commandTypeAndParams = splitCommandWordAndArgs(userInputString);
        String commandType = commandTypeAndParams[0].toLowerCase();
        String commandArgs = commandTypeAndParams[1];

        switch (commandType) {
        case COMMAND_LIST_WORD:
            return new ListCommand();
        case COMMAND_DONE_WORD:
            return new DoneCommand(commandArgs);
        case COMMAND_TODO_WORD:
            return new AddCommand(TODO_ABBREVIATION, commandArgs);
        case COMMAND_DEADLINE_WORD:
            return new AddCommand(DEADLINE_ABBREVIATION, commandArgs);
        case COMMAND_EVENT_WORD:
            return new AddCommand(EVENT_ABBREVIATION, commandArgs);
        case COMMAND_DELETE_WORD:
            return new DeleteCommand(commandArgs);
        case COMMAND_BYE_WORD:
            return new ExitCommand();
        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Splits raw user input into command word and command arguments string.
     *
     * @param rawUserInput User's raw input.
     * @return Size 2 array; first element is the command type and second element is the arguments string.
     */
    private String[] splitCommandWordAndArgs(String rawUserInput) {
        String[] split = rawUserInput.trim().split("\\s+", 2);

        return split.length == 2 ? split : new String[]{split[0], ""};
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

        if (indexOfExistingPrefix == -1) {
            if (taskTypeAbbrev.equals(DEADLINE_ABBREVIATION)) {
                throw new EmptyTimeException(A_DEADLINE);
            }
            if (taskTypeAbbrev.equals(EVENT_ABBREVIATION)) {
                throw new EmptyTimeException(AN_EVENT);
            }
        }

        return encoded.substring(0, indexOfExistingPrefix).trim();
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
        return string.replace(sign, "").trim();
    }

    /**
     * Converts an integer in string representation from one-based numbering to zero-based numbering.
     *
     * @param index One-based integer in string representation to be converted.
     * @return Integer with zero-based numbering.
     */
    public static int convertToZeroBased(String index) {
        return Integer.parseInt(index) - 1;
    }

    /**
     * Converts an integer from zero-based numbering to one-based numbering.
     *
     * @param index Zero-based integer to be converted.
     * @return Integer with one-based numbering.
     */
    public static int convertToOneBased(int index) {
        return index + 1;
    }
}
