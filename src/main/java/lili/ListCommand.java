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
     */
    @Override
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        if (taskList.isEmpty()) {
            return "LIST_EMPTY\nNo tasks available.";
        } else {
            StringBuilder response = new StringBuilder("LIST\n");
            for (int i = 0; i < taskList.size(); i++) {
                response.append((i + 1)).append(". ").append(taskList.get(i).toString()).append("\n");
            }
            return response.toString();
        }
    }
}
