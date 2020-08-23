public class Duke {
    public static final String HORIZONTAL_LINE = "____________________________________________________________";

    /**
     * Greet user.
     */
    public static void printHello() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" How can I help you?");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Bye user.
     */
    public static void printBye() {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    public static void main(String[] args) {
        printHello();   // Greet user
        printBye();     // Bye user
    }
}
