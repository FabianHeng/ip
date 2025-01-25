import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple task manager chatbot called Lili.
 * Provides functionalities for managing tasks and more features will be added.
 * Part of CS2103 Individual Project requirements.
 *
 * @author FabianHeng
 */

public class Lili {
    /**
     * Enum for supported commands.
     */
    private enum Command {
        LIST,
        LS,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

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

    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static final String FILE_DIR = "src/main/data";
    private static final String FILE_PATH = "src/main/data/lili.txt";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) throws LiliException {
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
        Command command;

        try {
            command = Command.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }

        String argument = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
        case LIST:
        case LS:
            displayTaskList();
            break;
        case MARK:
            markTask(argument);
            break;
        case UNMARK:
            unmarkTask(argument);
            break;
        case TODO:
            addTodoTask(argument);
            break;
        case DEADLINE:
            addDeadlineTask(argument);
            break;
        case EVENT:
            addEventTask(argument);
            break;
        case DELETE:
            deleteTask(argument);
            break;
        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Displays the list of tasks.
     */
    private static void displayTaskList() {
        if (taskList.isEmpty()) {
            ui.printChatText("LIST_EMPTY");
        } else {
            ui.printChatText("LIST");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i).toString());
            }
        }
    }

    /**
     * Marks a task as done.
     *
     * @param taskNumber Task number to mark.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    private static void markTask(String taskNumber) throws InvalidTaskNumberException {
        int taskIndex = parseTaskNumber(taskNumber);
        Task task = taskList.get(taskIndex);
        task.markAsDone();
        ui.printChatText("MARK");
        System.out.println(task.toString());
    }

    /**
     * Unmarks a task as not done.
     *
     * @param taskNumber Task number to unmark.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    private static void unmarkTask(String taskNumber) throws InvalidTaskNumberException {
        int taskIndex = parseTaskNumber(taskNumber);
        Task task = taskList.get(taskIndex);
        task.markAsNotDone();
        ui.printChatText("UNMARK");
        System.out.println(task.toString());
    }

    /**
     * Adds a new Todo task.
     *
     * @param description Description of the task.
     * @throws InvalidTodoDescriptionException If the description is empty.
     */
    private static void addTodoTask(String description) throws InvalidTodoDescriptionException {
        if (description.isEmpty()) {
            throw new InvalidTodoDescriptionException();
        }
        Todo todo = new Todo(description);
        taskList.add(todo);
        displayTaskAdded(todo);
    }

    /**
     * Adds a new Deadline task.
     *
     * @param description Task description and deadline, separated by " /by ".
     * @throws InvalidDeadlineFormatException If the command format is invalid.
     */
    private static void addDeadlineTask(String description) throws InvalidDeadlineFormatException {
        String[] parts = description.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidDeadlineFormatException();
        }
        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            taskList.add(deadline);
            displayTaskAdded(deadline);
        } catch (DateTimeParseException e) {
            System.out.println("I can't understand the date given, make sure it is in this format (default time is 0000): yyyy-mm-dd HHmm.");
        }
    }

    /**
     * Adds a new Event task.
     *
     * @param description Task description with start and end time, separated by " /from " and " /to ".
     * @throws InvalidEventFormatException If the format is invalid.
     */
    private static void addEventTask(String description) throws InvalidEventFormatException {
        String[] parts = description.split(" /from | /to ");
        if (parts.length != 3) {
            throw new InvalidEventFormatException();
        }
        try {
            Event event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
            taskList.add(event);
            displayTaskAdded(event);
        } catch (DateTimeParseException e) {
            System.out.println("I can't understand the date(s) given, make sure it is in this format (default time is 0000): yyyy-mm-dd HHmm.");
        }
    }

    /**
     * Deletes a task.
     *
     * @param taskNumber Task number to delete.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    private static void deleteTask(String taskNumber) throws InvalidTaskNumberException {
        int taskIndex = parseTaskNumber(taskNumber);
        Task task = taskList.remove(taskIndex);
        ui.printChatText("DELETE");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
    }

    /**
     * Displays a message for a newly added task.
     *
     * @param task The task that was added.
     */
    private static void displayTaskAdded(Task task) {
        ui.printChatText("TASK");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
    }

    /**
     * Parses a task number from user input.
     *
     * @param taskNumber The task number as a string.
     * @return The zero-based index of the task.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    private static int parseTaskNumber(String taskNumber) throws InvalidTaskNumberException {
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new InvalidTaskNumberException();
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException();
        }
    }
}
