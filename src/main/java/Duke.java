import java.util.Scanner;

public class Duke {
    public static final String HORIZONTAL_LINE = "\t____________________________________________________________";

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

    public static void main(String[] args) {
        printHello();   // Greet user
        echoCommand();  // Get user's command and echo
        printBye();     // Bye user
    }
}
