package duke.commons.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class DataFileConfig {
    public static final String DATA_FILE = "duke.txt";
    public static final String DATA_FOLDER = "data";
    /** Directory of project root */
    public static final String PROJECT_ROOT = System.getProperty("user.dir");
    /** Directory of data folder and data file */
    public static final Path PATH_TO_DATA_FOLDER = Paths.get(PROJECT_ROOT, DATA_FOLDER);
    public static final Path PATH_TO_DATA_FILE = Paths.get(PROJECT_ROOT, DATA_FOLDER, DATA_FILE);
    /** Index of data field in data file string */
    public static final int TASK_ABBREVIATION_INDEX = 0;
    public static final int TASK_DESCRIPTION_INDEX = 2;
    public static final int TASK_STATUS_INDEX = 1;
    public static final int TASK_TIME_INDEX = 3;
}