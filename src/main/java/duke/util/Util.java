package duke.util;

import static duke.constant.Constant.SPACE_CHARACTER;
import static duke.constant.Constant.VERTICAL_BAR_WITH_PADDING;

public class Util {
    /**
     * Splits raw user input into command word and command arguments string.
     *
     * @param rawUserInput User's raw input.
     * @return Size 2 array; first element is the command type and second element is the arguments string.
     */
    public static String[] splitCommandWordAndArgs(String rawUserInput) {
        final String[] split = rawUserInput.trim().split("\\s+", 2);
        return split.length == 2 ? split : new String[]{split[0], ""}; // else case: no parameters
    }

    public static String[] splitTaskFromDataLine(String dataLine) {
        return dataLine.trim().replace(VERTICAL_BAR_WITH_PADDING, SPACE_CHARACTER).split(SPACE_CHARACTER);
    }
}