import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    private static final String LOGO = "  .---.    .-./`)   .---.    .-./`)\n"
            + "  | ,_|    \\ .-.')  | ,_|    \\ .-.')\n"
            + ",-./  )    / `-' \\,-./  )    / `-' \\\n"
            + "\\  '_ '`)   `-'`\"`\\  '_ '`)   `-'`\"`\n"
            + " > (_)  )   .---.  > (_)  )   .---.\n"
            + "(  .  .-'   |   | (  .  .-'   |   |\n"
            + " `-'`-'|___ |   |  `-'`-'|___ |   |\n"
            + "  |        \\|   |   |        \\|   |\n"
            + "  `--------`'---'   `--------`'---'";

    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static final String FILE_PATH = "src/main/data/lili.txt";

    public static void main(String[] args) throws LiliException {
        displayWelcomeMessage();
        loadTasks();
        startChat();
    }

    /**
     * Displays the welcome message and logo.
     */
    private static void displayWelcomeMessage() {
        System.out.println("------------------------------");
        System.out.println("Hello! I'm");
        System.out.println(LOGO);
        System.out.println("What can I do for you?");
        System.out.println("------------------------------");
    }

    /**
     * Starts the chatbot interaction with the user.
     */
    private static void startChat() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            if (isExitCommand(input)) {
                displayExitMessage();
                break;
            }

            System.out.println("------------------------------");
            try {
                handleCommand(input);
                saveTasks();
            } catch (LiliException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("------------------------------");
        }

        scanner.close();
    }

    /**
     * Saves the tasks to a text file.
     */
    private static void saveTasks() {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            if (!taskList.isEmpty()) {
                for (Task task : taskList) {
                    fw.write(task.toFileFormat() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks to the file.");
        }
    }

    /**
     * Loads the tasks from a text file.
     */
    private static void loadTasks() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks from the file.");
        }
    }

    /**
     * Parses all tasks from the text file.
     *
     * @param line Text line from file
     * @return The task, else null.
     */
    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String name = parts[2];

        return switch (type) {
            case "T" -> new Todo(name, isDone);
            case "D" -> new Deadline(name, parts[3], isDone);
            case "E" -> new Event(name, parts[3], parts[3], isDone);
            default -> null;
        };
    }

    /**
     * Checks if the user input is an exit command.
     *
     * @param input User input.
     * @return True if the input is an exit command, false otherwise.
     */
    private static boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye") || input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit");
    }

    /**
     * Displays the exit message.
     */
    private static void displayExitMessage() {
        System.out.println("------------------------------");
        System.out.println("Bye, talk to you again <3");
        System.out.println("------------------------------");
    }

    /**
     * Handles user commands.
     *
     * @param input User input.
     * @throws LiliException If the input is invalid or causes an error.
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
            System.out.println("Nothing in list");
        } else {
            System.out.println("Here are your list of tasks:");
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
        System.out.println("Ok! I've marked it as done:");
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
        System.out.println("Ok! I've marked it as not done yet:");
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
     * @throws InvalidDeadlineFormatException If the format is invalid.
     */
    private static void addDeadlineTask(String description) throws InvalidDeadlineFormatException {
        String[] parts = description.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidDeadlineFormatException();
        }
        Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
        taskList.add(deadline);
        displayTaskAdded(deadline);
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
        Event event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        taskList.add(event);
        displayTaskAdded(event);
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
        System.out.println("Done and dusted, I've removed this from your list:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
    }

    /**
     * Displays a message for a newly added task.
     *
     * @param task The task that was added.
     */
    private static void displayTaskAdded(Task task) {
        System.out.println("Nice! I've added it to your list:");
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
