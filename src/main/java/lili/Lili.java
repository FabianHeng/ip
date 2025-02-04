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
        ui.displayWelcomeMessage();
        taskList.addAll(storage.loadTasks());
        startChat();
    }

    /**
     * Starts the chatbot interaction with the user.
     */
    private static void startChat() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            if (ui.isExitCommand(input)) {
                ui.displayExitMessage();
                break;
            }

            ui.printLine();
            try {
                handleCommand(input);
                storage.saveTasks(taskList);
            } catch (LiliException e) {
                System.out.println(e.getMessage());
            }
            ui.printLine();
        }

        scanner.close();
    }

    /**
     * Handles user commands.
     *
     * @param input User input.
     * @throws InvalidCommandException If the input is invalid or causes an error.
     */
    private static String handleCommand(String input) throws LiliException {
        if (ui.isExitCommand(input)) {
            return ui.displayExitMessage();
        }

        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toUpperCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        try {
            CommandType commandType = CommandType.fromString(commandWord);

            if (commandType == CommandType.FIND) {
                String[] keywords = argument.split("\\s+");
                Command command = new FindCommand(keywords);
                return command.execute(taskList, ui, storage);
            }

            Command command = commandType.createCommand(argument);
            return command.execute(taskList, ui, storage);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
    }

    /**
     * Returns welcome message and loads tasks.
     */
    public String getWelcomeMessage() {
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
        try {
            String response = handleCommand(input);
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
        return ui.isExitCommand(input);
    }
}
