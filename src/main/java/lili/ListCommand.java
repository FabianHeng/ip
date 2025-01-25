package lili;

import java.util.ArrayList;

public class ListCommand extends Command {
    /**
     * Displays the list of tasks.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface that prints out messages.
     * @param storage The loading and saving files object.
     */
    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        if (taskList.isEmpty()) {
            ui.printChatText("LIST_EMPTY");
        } else {
            ui.printChatText("LIST");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i).toString());
            }
        }
    }
}
