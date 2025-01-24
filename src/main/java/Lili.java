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

    public static void main(String[] args) throws LiliException {
        displayWelcomeMessage();
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
            } catch (LiliException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("------------------------------");
        }

        scanner.close();
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
        if (input.equalsIgnoreCase("list") || input.equalsIgnoreCase("ls")) {
            displayTaskList();
        } else if (input.toLowerCase().startsWith("mark ")) {
            markTask(input.substring(5).trim());
        } else if (input.toLowerCase().startsWith("unmark ")) {
            unmarkTask(input.substring(7).trim());
        } else if (input.toLowerCase().startsWith("todo ")) {
            addTodoTask(input.substring(5).trim());
        } else if (input.toLowerCase().startsWith("deadline ")) {
            addDeadlineTask(input.substring(9).trim());
        } else if (input.toLowerCase().startsWith("event ")) {
            addEventTask(input.substring(6).trim());
        } else if (input.toLowerCase().startsWith("delete ")) {
            deleteTask(input.substring(7).trim());
        } else {
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
