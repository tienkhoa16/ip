package duke.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configurations for data file.
 */
public final class DataFileConfig {
    public static final String DATA_FILE = "duke.txt";
    public static final String DATA_FOLDER = "data";
    public static final String PROJECT_ROOT = System.getProperty("user.dir");
    public static final Path PATH_TO_DATA_FOLDER = Paths.get(PROJECT_ROOT, DATA_FOLDER);
    public static final Path PATH_TO_DATA_FILE = Paths.get(PROJECT_ROOT, DATA_FOLDER, DATA_FILE);
}
