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
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        assert taskList != null : "Task list should not be null";
        assert ui != null : "Ui object should not be null";
        assert storage != null : "Storage object should not be null";

        String[] parts = name.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidDeadlineFormatException();
        }

        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            assert deadline != null : "Deadline object should be successfully created";

            int initialSize = taskList.size();
            taskList.add(deadline);
            assert taskList.size() == initialSize + 1 : "Task list size should increase by 1";

            return ui.printChatText("TASK") + "\n"
                    + deadline.toString() + "\n"
                    + "Now you have " + taskList.size() + " task(s) in your list.";
        } catch (DateTimeParseException e) {
            return "I can't understand the date given, "
                    + "make sure it is in this format (default time is 0000): yyyy-MM-dd HHmm.";
        }
    }
}
