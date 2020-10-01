package duke.components;

import duke.task.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static duke.constants.CommandConstants.OFFSET;
import static duke.constants.Messages.LISTING_FORMAT;

/**
 * A class containing utility methods used in Duke.
 */
public class Utils {

    /**
     * Converts an integer in string format from one-based numbering to zero-based numbering.
     *
     * @param index One-based integer in string format to be converted.
     * @return Integer with zero-based numbering.
     */
    public static int convertToZeroBased(String index) {
        return Integer.parseInt(index) - OFFSET;
    }

    /**
     * Converts an integer from zero-based numbering to one-based numbering.
     *
     * @param index Zero-based integer to be converted.
     * @return Integer with one-based numbering.
     */
    public static int convertToOneBased(int index) {
        return index + OFFSET;
    }

    /**
     * Converts tasks list from ArrayList to string representation.
     *
     * @param tasks Tasks list managing all user's tasks.
     * @return String representation of tasks list.
     */
    public static String convertTasksListToString(ArrayList<Task> tasks) {
        String tasksString = IntStream.range(0, tasks.size())
                .mapToObj(index -> String.format(LISTING_FORMAT, convertToOneBased(index), tasks.get(index).toString()))
                .collect(Collectors.joining());

        return tasksString;
    }
}
