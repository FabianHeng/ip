package lili;

import java.util.ArrayList;

/**
 * Command class that processes listing of tasks.
 */
public class ListCommand extends Command {
    /**
     * Displays the list of tasks.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface that prints out messages.
     * @param storage The loading and saving files object.
     * @return The response message of Lilibot.
     */
    @Override
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        assert taskList != null : "Task list should not be null";
        assert ui != null : "Ui object should not be null";
        assert storage != null : "Storage object should not be null";

        if (taskList.isEmpty()) {
            return "No tasks available! Perhaps you can add some tasks?";
        } else {
            StringBuilder response = new StringBuilder(ui.getChatText("LIST"));
            response.append("\n");
            for (int i = 0; i < taskList.size(); i++) {
                response.append((i + 1)).append(". ").append(taskList.get(i).toString()).append("\n");
            }
            return response.toString();
        }
    }
}
