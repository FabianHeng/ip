package lili;

import java.util.Map;

/**
 * Ui class that processes the display messages.
 */
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

    /**
     * Returns the welcome message and logo.
     */
    public String displayWelcomeMessage() {
        return "------------------------------\n"
                + "Hello! I'm\n"
                + LOGO + "\n"
                + "What can I do for you?\n"
                + "------------------------------";
    }

    /**
     * Returns the exit message.
     */
    public String displayExitMessage() {
        return "------------------------------\n"
                + "Bye, talk to you again <3\n"
                + "------------------------------";
    }

    /**
     * Checks if the user input is an exit command.
     *
     * @param input User input.
     * @return True if the input is an exit command, false otherwise.
     */
    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye")
                || input.equalsIgnoreCase("exit")
                || input.equalsIgnoreCase("quit")
                || input.equalsIgnoreCase("q");
    }

    /**
     * Returns a separator line.
     */
    public String printLine() {
        return "------------------------------";
    }

    // Map for chat responses
    private static final Map<String, String> chatMessages = Map.of(
            "LIST", "Here are your list of tasks:",
            "LIST_EMPTY", "Nothing in list",
            "MARK", "Ok! I've marked it as done:",
            "UNMARK", "Ok! I've marked it as not done yet:",
            "TASK", "Nice! I've added it to your list:",
            "DELETE", "Done and dusted, I've removed this from your list:",
            "FIND", "I found these task(s) that match your search:",
            "HELP", "Here are the list of commands available:"
    );

    /**
     * Returns pre-defined chat text based on the given input.
     *
     * @param input Chat text key.
     * @return Corresponding chat message.
     */
    public String getChatText(String input) {
        assert input != null : "Input key should not be null";
        return chatMessages.getOrDefault(input, "");
    }
}
