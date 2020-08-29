import java.util.Scanner;

public class Duke {

    /**
     * A decorative prefix added to the beginning of lines.
     */
    public static final String LINE_PREFIX = "\t ";

    public static final String HORIZONTAL_LINE = LINE_PREFIX
            + "____________________________________________________________";

    /**
     * A platform independent line separator.
     */
    public static final String LS = System.lineSeparator() + LINE_PREFIX;

    public static final String COMMAND_LIST_WORD = "list";          // Keyword for listing tasks
    public static final String COMMAND_BYE_WORD = "bye";            // Keyword for exiting program
    public static final String COMMAND_ADD_WORD = "add";            // Keyword for adding a new task
    public static final String COMMAND_DONE_WORD = "done";          // Keyword for marking a task as done
    public static final String COMMAND_TODO_WORD = "todo";          // Keyword for adding a new todo
    public static final String COMMAND_DEADLINE_WORD = "deadline";  // Keyword for adding a new deadline
    public static final String COMMAND_EVENT_WORD = "event";        // Keyword for adding a new event

    public static final String MESSAGE_LIST_TITLE = "Here are the tasks in your list:";
    public static final String MESSAGE_DONE_TITLE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_ADD_TITLE = "Got it. I've added this task:";
    public static final String MESSAGE_ADD_CONCLUSION = "Now you have %d tasks in the list.";
    public static final String MESSAGE_FOR_INVALID_INPUT = "Invalid command.";

    public static final String SEPARATOR_TASK_NUMBER_TASK_DESC = ". ";

    // These are the prefix strings to define the data type of a command parameter
    public static final String TASK_DATA_PREFIX_DEADLINE = "/by";
    public static final String TASK_DATA_PREFIX_EVENT = "/at";

    /**
     * Maximum number of persons that can be held.
     */
    public static final int MAX_CAPACITY = 100;

    /*
     * This variable is declared for the whole class (instead of declaring it
     * inside the getCommand() method to facilitate automated testing using
     * the I/O redirection technique. If not, only the first line of the input
     * text file will be processed.
     */
    public static final Scanner SCANNER = new Scanner(System.in);

    /**
     * List of all tasks.
     */
    public static Task[] tasks;

    /**
     * Total number of tasks in the list.
     */
    public static int taskCount;

    /**
     * Initializes tasks list and the number of tasks in the list.
     */
    private static void initTasksList() {
        tasks = new Task[MAX_CAPACITY];
        taskCount = 0;
    }

    /**
     * Greet user.
     */
    public static void printHello() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(LINE_PREFIX + "Hello dude! I'm Duke");
        System.out.println(LINE_PREFIX + "How can I help you?");
        System.out.println(HORIZONTAL_LINE + System.lineSeparator());
    }

    /**
     * Bye user.
     */
    public static void printBye() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(LINE_PREFIX + "Bye buddy. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     *
     * @return Full line entered by the user.
     */
    public static String getCommand() {
        System.out.print("Enter your command: ");
        String inputLine = SCANNER.nextLine();
        // Silently consume all blank lines
        while (inputLine.trim().isEmpty()) {
            System.out.print("Enter your command: ");
            inputLine = SCANNER.nextLine();
        }
        return inputLine;
    }

    /**
     * Echos user's command.
     *
     * @param userCommand User's raw input.
     */
    public static void echoCommand(String userCommand) {
        showResultToUser(userCommand);
    }

    /**
     * Splits raw user input into command word and command arguments string.
     *
     * @param rawUserInput User's raw input.
     * @return size 2 array; first element is the command type and second element is the arguments string.
     */
    public static String[] splitCommandWordAndArgs(String rawUserInput) {
        final String[] split = rawUserInput.trim().split("\\s+", 2);
        return split.length == 2 ? split : new String[]{split[0], ""}; // else case: no parameters
    }

    /**
     * Replies to user's command.
     *
     * @param userInputString Raw input from user.
     * @return Feedback about how the command was executed.
     */
    public static String replyCommand(String userInputString) {

        final String[] commandTypeAndParams = splitCommandWordAndArgs(userInputString);
        final String commandType = commandTypeAndParams[0];
        final String commandArgs = commandTypeAndParams[1];

        switch (commandType) {
        case COMMAND_LIST_WORD:
            return executeListAllTasks();
        case COMMAND_DONE_WORD:
            return executeMarkTaskAsDone(commandArgs);
        case COMMAND_TODO_WORD:
            return executeAddTodo(commandArgs);
        case COMMAND_DEADLINE_WORD:
            return executeAddDeadline(commandArgs);
        case COMMAND_EVENT_WORD:
            return executeAddEvent(commandArgs);
        case COMMAND_BYE_WORD:
            executeExitProgramRequest();
            // Fallthrough
        default:
            return displayMessageForInvalidInput();
        }
    }

    /**
     * Adds a new event to tasks array.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for adding a new event.
     */
    public static String executeAddEvent(String commandArgs) {
        final Event decodeResult = decodeEventFromString(commandArgs);

        return executeAddTask(decodeResult);
    }

    /**
     * Decodes an event from it's supposed string representation.
     *
     * @param encoded string to be decoded.
     * @return Event object of description and time.
     */
    public static Event decodeEventFromString(String encoded) {
        final Event decodedEvent = makeEventFromData(
                extractDescriptionFromString(encoded),
                extractEventTimeFromString(encoded)
        );
        return decodedEvent;
    }

    /**
     * Creates an event from the given data.
     *
     * @param description Description of event.
     * @param time Event time without data prefix.
     * @return constructed person.
     */
    private static Event makeEventFromData(String description, String time) {
        return new Event(description, time);
    }

    /**
     * Extracts substring representing event time from command arguments.
     *
     * @param encoded string to be decoded.
     * @return Event time argument WITHOUT prefix.
     */
    public static String extractEventTimeFromString(String encoded) {
        final int indexOfEventPrefix = encoded.indexOf(TASK_DATA_PREFIX_EVENT);

        return removePrefixSign(encoded.substring(indexOfEventPrefix, encoded.length()).trim(),
                TASK_DATA_PREFIX_EVENT);
    }

    /**
     * Adds a new deadline to tasks array.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for adding a new deadline.
     */
    public static String executeAddDeadline(String commandArgs) {
        final Deadline decodeResult = decodeDeadlineFromString(commandArgs);

        return executeAddTask(decodeResult);
    }

    /**
     * Decodes a deadline from it's supposed string representation.
     *
     * @param encoded string to be decoded.
     * @return Deadline object of description and date.
     */
    public static Deadline decodeDeadlineFromString(String encoded) {
        final Deadline decodedDeadline = makeDeadlineFromData(
                extractDescriptionFromString(encoded),
                extractDeadlineDateFromString(encoded)
        );
        return decodedDeadline;
    }

    /**
     * Creates a deadline from the given data.
     *
     * @param description Description of deadline.
     * @param date Deadline date without data prefix.
     * @return constructed person.
     */
    private static Deadline makeDeadlineFromData(String description, String date) {
        return new Deadline(description, date);
    }

    /**
     * Extracts substring representing deadline date from command arguments.
     *
     * @param encoded string to be decoded.
     * @return deadline date argument WITHOUT prefix.
     */
    public static String extractDeadlineDateFromString(String encoded) {
        final int indexOfDeadlinePrefix = encoded.indexOf(TASK_DATA_PREFIX_DEADLINE);

        return removePrefixSign(encoded.substring(indexOfDeadlinePrefix, encoded.length()).trim(),
                TASK_DATA_PREFIX_DEADLINE);
    }

    /**
     * Extracts substring representing task description from command arguments.
     *
     * @param encoded command arguments.
     * @return Task description.
     */
    public static String extractDescriptionFromString(String encoded) {
        final int indexOfDeadlinePrefix = encoded.indexOf(TASK_DATA_PREFIX_DEADLINE);
        final int indexOfEventPrefix = encoded.indexOf(TASK_DATA_PREFIX_EVENT);

        /*
         * Description is leading substring up to data prefix string.
         * If prefix of deadline exits, prefix of event doesn't and vice versa.
         */
        int indexOfFirstPrefix = Math.max(indexOfDeadlinePrefix, indexOfEventPrefix);
        return encoded.substring(0, indexOfFirstPrefix).trim();
    }

    /**
     * Adds a new todo to tasks array.
     *
     * @param todoDescription Todo description.
     * @return Feedback display message for adding a new todo.
     */
    public static String executeAddTodo(String todoDescription) {
        // Create a new Todo instance
        Todo todo = new Todo(todoDescription);

        return executeAddTask(todo);
    }

    /**
     * Returns a message when user's command keyword does not belong to any valid keywords.
     *
     * @return Invalid input message.
     */
    public static String displayMessageForInvalidInput() {
        return MESSAGE_FOR_INVALID_INPUT;
    }

    /**
     * Adds a new task to tasks array.
     *
     * @param task Task object to be added.
     * @return Feedback display message for adding a new task.
     */
    public static String executeAddTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;

        return String.format(HORIZONTAL_LINE + LS
                + MESSAGE_ADD_TITLE + LS
                + task.toString() + LS
                + MESSAGE_ADD_CONCLUSION + System.lineSeparator()
                + HORIZONTAL_LINE, taskCount);
    }

    /**
     * Lists out tasks added so far with their status.
     */
    public static String executeListAllTasks() {
        String feedback = HORIZONTAL_LINE + LS
                + MESSAGE_LIST_TITLE + LS;

        // Iterate through tasks array and print each task with its status and description
        for (int i = 0; i < taskCount; i++) {
            feedback += (i + 1) + SEPARATOR_TASK_NUMBER_TASK_DESC
                    + tasks[i].toString() + LS;
        }

        feedback += System.lineSeparator() + HORIZONTAL_LINE + System.lineSeparator();
        return feedback;
    }

    /**
     * Marks a task in the tasks array as done.
     *
     * @param commandArgs Full command args string from the user.
     * @return Feedback display message for marking the task as done.
     */
    public static String executeMarkTaskAsDone(String commandArgs) {
        int taskIndex = extractTaskIndexFromInputString(commandArgs);

        // Update status of task
        tasks[taskIndex].markAsDone();

        return HORIZONTAL_LINE + LS
                + MESSAGE_DONE_TITLE + LS
                + tasks[taskIndex].toString() + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator();
    }

    /**
     * Converts task number in user's command (starting from 1)
     * to the corresponding task index  in tasks list (starting from 0).
     * In the case of "done ..." command,
     * the command argument is the task number to be marked as done.
     *
     * @param commandArgs User's argument passed in the command.
     * @return Task index.
     */
    public static int extractTaskIndexFromInputString(String commandArgs) {
        return Integer.parseInt(commandArgs) - 1;
    }

    public static void main(String[] args) {
        // Initialize tasks list
        initTasksList();

        // Greet user
        printHello();

        while (true) {
            String userCommand = getCommand();
            String feedback = replyCommand(userCommand);
            showResultToUser(feedback);
        }
    }

    /**
     * Shows a result message to the user.
     *
     * @param result Result message to be displayed.
     */
    public static void showResultToUser(String result) {
        System.out.println(result);
    }

    /**
     * Requests to terminate the program.
     */
    public static void executeExitProgramRequest() {
        exitProgram();
    }

    /**
     * Displays the goodbye message and exits the runtime.
     */
    public static void exitProgram() {
        printBye();
        System.exit(0);
    }

    /**
     * Removes sign(/by, /at, etc) from parameter string
     *
     * @param s Parameter as a string.
     * @param sign Parameter sign to be removed.
     * @return string without the sign.
     */
    public static String removePrefixSign(String s, String sign) {
        return s.replace(sign, "").trim();
    }
}
