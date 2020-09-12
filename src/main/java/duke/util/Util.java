package duke.util;

import static duke.constant.Constant.VERTICAL_BAR_WITH_PADDING;

public class Util {
    /**
     * Splits raw user input into command word and command arguments string.
     *
     * @param rawUserInput User's raw input.
     * @return Size 2 array; first element is the command type and second element is the arguments string.
     */
    public static String[] splitCommandWordAndArgs(String rawUserInput) {
        String[] split = rawUserInput.trim().split("\\s+", 2);
        return split.length == 2 ? split : new String[]{split[0], ""}; // else case: no parameters
    }

    /**
     * Splits data string from data file into task abbreviation and task details.
     *
     * @param dataLine Data line from data file.
     * @return Array containing task type, status, description and time (where applicable).
     */
    public static String[] splitTaskFromDataLine(String dataLine) {
        return dataLine.trim().split(VERTICAL_BAR_WITH_PADDING);
    }
}