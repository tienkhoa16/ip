import java.util.Scanner;

public class Duke {
    public static final String HORIZONTAL_LINE = "\t____________________________________________________________";
    public static Task[] tasks = new Task[100];     // Store user's tasks
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
            String[] words = command.split(" ");    // Split command using space as delimiter

            switch (words[0]) {
            case "list":    // If command is "list", list tasks added so far
                listTasks();
                break;
            case "done":    // If command is "done ...", mark the respective task as done
                // Get index of the task user wants to mark as done
                int taskIndex = Integer.parseInt(words[1]) - 1;
                markTaskAsDone(taskIndex);
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
        tasks[taskNumber] = new Task(task);
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
            System.out.println("\t " + (i + 1) + ". " + tasks[i].toString());
        }
        System.out.println(HORIZONTAL_LINE + "\n");
    }

    /**
     * Marks a task of taskIndex as done and notifies user.
     *
     * @param taskIndex
     */
    public static void markTaskAsDone(int taskIndex) {
        tasks[taskIndex].markAsDone();  // Update status of task

        // Notify user that task is marked as done
        System.out.println(HORIZONTAL_LINE);
        System.out.println("\t Nice! I've marked this task as done: ");
        System.out.println("\t   " + tasks[taskIndex].toString());
        System.out.println(HORIZONTAL_LINE + "\n");
    }

    public static void main(String[] args) {
        printHello();   // Greet user
        replyCommand(); // Get user's command and reply properly
        printBye();     // Bye user
    }
}
