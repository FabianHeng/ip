package lili;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple task manager chatbot called lili.Lili.
 * Provides functionalities for managing tasks and more features will be added.
 * Part of CS2103 Individual Project requirements.
 *
 * @author FabianHeng
 */

public class Lili {

    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static final String FILE_PATH = "src/main/data/lili.txt";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) {
        initialize();
        startChat();
    }

    /**
     * Initializes the chatbot by displaying a welcome message and loading tasks.
     */
    private static void initialize() {
        ui.displayWelcomeMessage();
        assert taskList.isEmpty();
        taskList.addAll(storage.loadTasks());
    }

    /**
     * Starts the chatbot interaction with the user.
     */
    private static void startChat() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            assert input != null : "User input is null";
            if (processExitCommand(input)) break;

            ui.printLine();
            processUserCommand(input);
            ui.printLine();
        }

        scanner.close();
    }

    /**
     * Processes exit command.
     *
     * @param input User input.
     * @return True if exit command is detected, otherwise false.
     */
    private static boolean processExitCommand(String input) {
        if (!ui.isExitCommand(input)) {
            return false;
        }
        ui.displayExitMessage();
        return true;
    }

    /**
     * Processes user commands and handles exceptions.
     *
     * @param input User input.
     */
    private static void processUserCommand(String input) {
        try {
            handleCommand(input);
            storage.saveTasks(taskList);
        } catch (LiliException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles user commands.
     *
     * @param input User input.
     * @throws InvalidCommandException If the input is invalid or causes an error.
     */
    private static String handleCommand(String input) throws LiliException {
        assert input != null && !input.trim().isEmpty() : "handleCommand should not receive null or empty input";

        if (ui.isExitCommand(input)) {
            return ui.displayExitMessage();
        }

        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toUpperCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        return executeCommand(commandWord, argument);
    }

    /**
     * Executes the parsed command.
     *
     * @param commandWord Command keyword.
     * @param argument Arguments associated with the command.
     * @return The response message.
     * @throws LiliException If the command is invalid.
     */
    private static String executeCommand(String commandWord, String argument) throws LiliException {
        assert !commandWord.isEmpty() : "Command word should not be empty";

        try {
            CommandType commandType = CommandType.fromString(commandWord);
            return createAndExecuteCommand(commandType, argument);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
    }

    /**
     * Creates the FindCommand Object if command is "Find", with varargs.
     *
     * @param commandType The type of command.
     * @param argument The command argument.
     * @return The response from executing the command.
     * @throws LiliException If execution fails.
     */
    private static String createAndExecuteCommand(CommandType commandType, String argument) throws LiliException {
        Command command = (commandType == CommandType.FIND)
                ? new FindCommand(argument.split("\\s+"))
                : commandType.createCommand(argument);

        return command.execute(taskList, ui, storage);
    }

    /**
     * Returns welcome message and loads tasks.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        assert taskList.isEmpty() : "Task list should be empty before loading tasks";

        taskList.addAll(storage.loadTasks());
        return ui.displayWelcomeMessage();
    }

    /**
     * Returns the response from Lili.
     *
     * @param input User input.
     * @return Lili's response.
     */
    public String getResponse(String input) {
        assert input != null && !input.trim().isEmpty() : "getResponse should not receive null or empty input";

        try {
            String response = handleCommand(input);

            assert response != null : "Response should not be null";

            storage.saveTasks(taskList);
            return response;
        } catch (LiliException e) {
            return e.getMessage();
        }
    }

    /**
     * Checks whether the user's input is an exit command.
     *
     * @param input User input.
     * @return True if it is an exit command, false if otherwise.
     */
    public Boolean isExitCommand(String input) {
        assert input != null : "isExitCommand should not receive null input";

        return ui.isExitCommand(input);
    }
}
