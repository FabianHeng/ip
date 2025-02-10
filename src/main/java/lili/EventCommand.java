package lili;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Command class that processes addition of events.
 */
public class EventCommand extends Command {
    private final String name;

    public EventCommand(String name) {
        this.name = name;
    }

    /**
     * Adds a new event task into the task list.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface that prints out messages.
     * @param storage The loading and saving files object.
     * @throws InvalidEventFormatException If the command format is invalid.
     */
    @Override
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        assert taskList != null : "Task list should not be null";
        assert ui != null : "Ui object should not be null";
        assert storage != null : "Storage object should not be null";

        assert name.contains(" /from ") && name.contains(" /to ") :
                "Event format should contain '/from' and '/to'";

        String[] parts = name.split(" /from | /to ");
        if (parts.length != 3) {
            throw new InvalidEventFormatException();
        }
        try {
            Event event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
            assert event != null : "Event object should be successfully created";

            int initialSize = taskList.size();
            taskList.add(event);
            assert taskList.size() == initialSize + 1 : "Task list size should increase by 1";

            return ui.printChatText("TASK") + "\n"
                    + event + "\n"
                    + "Now you have " + taskList.size() + " task(s) in your list.";
        } catch (DateTimeParseException e) {
            return "I can't understand the date(s) given. "
                    + "Make sure it is in this format (default time is 0000): yyyy-MM-dd HHmm.";
        }
    }
}
