package lili;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Command class that processes addition of deadlines.
 */
public class DeadlineCommand extends Command {
    private final String name;

    public DeadlineCommand(String name) {
        this.name = name;
    }

    /**
     * Adds a new deadline task into the task list.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface that prints out messages.
     * @param storage The loading and saving files object.
     * @throws InvalidDeadlineFormatException If the command format is invalid.
     */
    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        String[] parts = name.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidDeadlineFormatException();
        }
        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            taskList.add(deadline);
            ui.printChatText("TASK");
            System.out.println(deadline);
            System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
        } catch (DateTimeParseException e) {
            System.out.println("I can't understand the date given, "
                    + "make sure it is in this format (default time is 0000): yyyy-mm-dd HHmm.");
        }
    }
}
