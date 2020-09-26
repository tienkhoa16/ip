package duke.commons.utils;

import static duke.commons.constants.Messages.VERTICAL_BAR_REGREX;

public class Utils {

    /**
     * Splits raw user input into command word and command arguments string.
     *
     * @param rawUserInput User's raw input.
     * @return Size 2 array; first element is the command type and second element is the arguments string.
     */
    public static String[] splitCommandWordAndArgs(String rawUserInput) {
        String[] split = rawUserInput.trim().split("\\s+", 2);

        return split.length == 2 ? split : new String[]{split[0], ""};
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
