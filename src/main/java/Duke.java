import java.util.Scanner;

public class Duke {
    public static final String HORIZONTAL_LINE = "\t____________________________________________________________";
    public static String[] tasks = new String[100]; // Store user's tasks
    public static int taskNumber = 0;               // Manage number of tasks added

    /**
     * Greet user.
     */
    public static void printHello() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("\t Hello dude! I'm Duke");
        System.out.println("\t How can I help you?");
        System.out.println(HORIZONTAL_LINE + "\n");
    }

    /**
     * Bye user.
     */
    public static void printBye() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("\t Bye buddy. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Returns user's command.
     *
     * @return User's command.
     */
    public static String getCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Echos user's command.
     * If the command is "bye", exits method.
     * Otherwise, echos user's command.
     */
    public static void echoCommand() {
        String command = getCommand();

        // Repeat until command is "bye"
        while (!command.equals("bye")) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("\t " + command);
            System.out.println(HORIZONTAL_LINE + "\n");
            command = getCommand();
        }
    }

    /**
     * Replies user's command.
     * If the command is "bye", exits method.
     * Otherwise, replies user accordingly to the command.
     */
    public static void replyCommand() {
        String command = getCommand();  // Get user's command

        // Repeat until command is "bye"
        while (!command.equals("bye")) {
            switch (command) {
            case "list":    // If command is "list", list tasks added so far
                listTasks();
                break;
            default:        // By default, add user's command as a new task to tasks array
                addTask(command);
                break;
            }

            command = getCommand(); // Get user's following command
        }
    }

    /**
     * Adds a new task to tasks array.
     * After the task is added, notifies user.
     *
     * @param task
     */
    public static void addTask(String task) {
        tasks[taskNumber] = task;
        taskNumber++;

        System.out.println(HORIZONTAL_LINE);
        System.out.println("\t added: " + task); //  Notify user that the task is recorded
        System.out.println(HORIZONTAL_LINE + "\n");
    }

    /**
     * Lists out tasks added so far with their status.
     */
    public static void listTasks() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("\t Here are the tasks in your list:");
        for (int i = 0; i < taskNumber; i++) {
            System.out.println("\t " + (i + 1) + ". " + tasks[i]);
        }
        System.out.println(HORIZONTAL_LINE + "\n");
    }

    public static void main(String[] args) {
        printHello();   // Greet user
        replyCommand(); // Get user's command and reply properly
        printBye();     // Bye user
    }
}
