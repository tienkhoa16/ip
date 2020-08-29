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
    private static final String LS = System.lineSeparator() + LINE_PREFIX;

    public static final String COMMAND_LIST_WORD = "list";      // Keyword for listing tasks
    public static final String COMMAND_BYE_WORD = "bye";        // Keyword for exiting program
    public static final String COMMAND_ADD_WORD = "add";        // Keyword for adding a new task
    public static final String COMMAND_DONE_WORD = "done";      // Keyword for marking a task as done
    public static final String COMMAND_TODO_WORD = "todo";      // Keyword for adding a new todo

    public static final String COMMAND_LIST_MESSAGE_TITLE = "Here are the tasks in your list:";
    public static final String COMMAND_DONE_MESSAGE_TITLE = "Nice! I've marked this task as done:";
    public static final String COMMAND_ADD_MESSAGE_TITLE = "added: ";
    public static final String COMMAND_TODO_MESSAGE_TITLE = "Got it. I've added this task:";
    public static final String COMMAND_TODO_MESSAGE_CONCLUSION = "Now you have %d tasks in the list.";
    public static final String MESSAGE_FOR_INVALID_INPUT = "Invalid command.";

    public static final String SEPARATOR_TASK_NUMBER_TASK_DESC = ". ";

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
        case COMMAND_BYE_WORD:
            executeExitProgramRequest();
            // Fallthrough
        default:
            return displayMessageForInvalidInput();
        }
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

        // Add the new todo to tasks array
        tasks[taskCount] = todo;
        taskCount++;

        return String.format(HORIZONTAL_LINE + LS
                + COMMAND_TODO_MESSAGE_TITLE + LS
                + todo.toString() + LS
                + COMMAND_TODO_MESSAGE_CONCLUSION + System.lineSeparator()
                + HORIZONTAL_LINE, taskCount);
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
     * @param taskDescription Task description.
     * @return Feedback display message for adding a new task.
     */
    public static String executeAddTask(String taskDescription) {
        // Create a new Task instance
        tasks[taskCount] = new Task(taskDescription);
        taskCount++;

        return HORIZONTAL_LINE + LS
                + COMMAND_ADD_MESSAGE_TITLE + taskDescription + System.lineSeparator()
                + HORIZONTAL_LINE;
    }

    /**
     * Lists out tasks added so far with their status.
     */
    public static String executeListAllTasks() {
        String feedback = HORIZONTAL_LINE + LS
                + COMMAND_LIST_MESSAGE_TITLE + LS;

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
                + COMMAND_DONE_MESSAGE_TITLE + LS
                + tasks[taskIndex].toString() + System.lineSeparator()
                + HORIZONTAL_LINE + System.lineSeparator();
    }

    /**
     * Converts task number in user's command (starting from 1)
     * to the corresponding task index  in tasks list (starting from 0).
     * In the case of "done ..." command, the command argument is the task number
     * to be marked as done.
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
    private static void exitProgram() {
        printBye();
        System.exit(0);
    }
}
