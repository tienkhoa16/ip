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
}