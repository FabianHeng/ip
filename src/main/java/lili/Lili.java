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
    private static void handleCommand(String input) throws LiliException {
        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toUpperCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        try {
            CommandType commandType = CommandType.fromString(commandWord);
            Command command = commandType.createCommand(argument);
            command.execute(taskList, ui, storage);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
    }

//    /**
//     * Adds a new lili.Todo task.
//     *
//     * @param description Description of the task.
//     * @throws lili.InvalidTodoDescriptionException If the description is empty.
//     */
//    private static void addTodoTask(String description) throws lili.InvalidTodoDescriptionException {
//        if (description.isEmpty()) {
//            throw new lili.InvalidTodoDescriptionException();
//        }
//        lili.Todo todo = new lili.Todo(description);
//        taskList.add(todo);
//        displayTaskAdded(todo);
//    }
//
//    /**
//     * Adds a new lili.Deadline task.
//     *
//     * @param description lili.Task description and deadline, separated by " /by ".
//     * @throws lili.InvalidDeadlineFormatException If the command format is invalid.
//     */
//    private static void addDeadlineTask(String description) throws lili.InvalidDeadlineFormatException {
//        String[] parts = description.split(" /by ");
//        if (parts.length != 2) {
//            throw new lili.InvalidDeadlineFormatException();
//        }
//        try {
//            lili.Deadline deadline = new lili.Deadline(parts[0].trim(), parts[1].trim());
//            taskList.add(deadline);
//            displayTaskAdded(deadline);
//        } catch (DateTimeParseException e) {
//            System.out.println("I can't understand the date given, make sure it is in this format (default time is 0000): yyyy-mm-dd HHmm.");
//        }
//    }
//
//    /**
//     * Adds a new lili.Event task.
//     *
//     * @param description lili.Task description with start and end time, separated by " /from " and " /to ".
//     * @throws lili.InvalidEventFormatException If the format is invalid.
//     */
//    private static void addEventTask(String description) throws lili.InvalidEventFormatException {
//        String[] parts = description.split(" /from | /to ");
//        if (parts.length != 3) {
//            throw new lili.InvalidEventFormatException();
//        }
//        try {
//            lili.Event event = new lili.Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
//            taskList.add(event);
//            displayTaskAdded(event);
//        } catch (DateTimeParseException e) {
//            System.out.println("I can't understand the date(s) given, make sure it is in this format (default time is 0000): yyyy-mm-dd HHmm.");
//        }
//    }
//
//    /**
//     * Deletes a task.
//     *
//     * @param taskNumber lili.Task number to delete.
//     * @throws lili.InvalidTaskNumberException If the task number is invalid.
//     */
//    private static void deleteTask(String taskNumber) throws lili.InvalidTaskNumberException {
//        int taskIndex = parseTaskNumber(taskNumber);
//        lili.Task task = taskList.remove(taskIndex);
//        ui.printChatText("DELETE");
//        System.out.println(task.toString());
//        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
//    }
//
//    /**
//     * Displays a message for a newly added task.
//     *
//     * @param task The task that was added.
//     */
//    private static void displayTaskAdded(lili.Task task) {
//        ui.printChatText("TASK");
//        System.out.println(task.toString());
//        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
//    }
//
//    /**
//     * Parses a task number from user input.
//     *
//     * @param taskNumber The task number as a string.
//     * @return The zero-based index of the task.
//     * @throws lili.InvalidTaskNumberException If the task number is invalid.
//     */
//    private static int parseTaskNumber(String taskNumber) throws lili.InvalidTaskNumberException {
//        try {
//            int taskIndex = Integer.parseInt(taskNumber) - 1;
//            if (taskIndex < 0 || taskIndex >= taskList.size()) {
//                throw new lili.InvalidTaskNumberException();
//            }
//            return taskIndex;
//        } catch (NumberFormatException e) {
//            throw new lili.InvalidTaskNumberException();
//        }
//    }
}
