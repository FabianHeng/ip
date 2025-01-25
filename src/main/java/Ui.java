public class Ui {
    private static final String LOGO = """
              .---.    .-./`)   .---.    .-./`)
              | ,_|    \\ .-.')  | ,_|    \\ .-.')
            ,-./  )    / `-' \\,-./  )    / `-' \\
            \\  '_ '`)   `-'`"`\\  '_ '`)   `-'`"`
             > (_)  )   .---.  > (_)  )   .---.
            (  .  .-'   |   | (  .  .-'   |   |
             `-'`-'|___ |   |  `-'`-'|___ |   |
              |        \\|   |   |        \\|   |
              `--------`'---'   `--------`'---'""";

    public Ui() {
    }

    /**
     * Displays the welcome message and logo.
     */
    public void displayWelcomeMessage() {
        System.out.println("------------------------------");
        System.out.println("Hello! I'm");
        System.out.println(LOGO);
        System.out.println("What can I do for you?");
        System.out.println("------------------------------");
    }

    /**
     * Displays the exit message.
     */
    public void displayExitMessage() {
        System.out.println("------------------------------");
        System.out.println("Bye, talk to you again <3");
        System.out.println("------------------------------");
    }

    /**
     * Checks if the user input is an exit command.
     *
     * @param input User input.
     * @return True if the input is an exit command, false otherwise.
     */
    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye") || input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q");
    }

    public void printLine() {
        System.out.println("------------------------------");
    }

    public void printChatText(String input) {
        switch (input) {
        case "LIST":
            System.out.println("Here are your list of tasks:");
            break;
        case "LIST_EMPTY":
            System.out.println("Nothing in list");
            break;
        case "MARK":
            System.out.println("Ok! I've marked it as done:");
            break;
        case "UNMARK":
            System.out.println("Ok! I've marked it as not done yet:");
            break;
        case "TASK":
            System.out.println("Nice! I've added it to your list:");
            break;
        case "DELETE":
            System.out.println("Done and dusted, I've removed this from your list:");
            break;
        default:
            break;
        }
    }
}
