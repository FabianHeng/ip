package lili;

import java.util.ArrayList;

/**
 * Command class that processes finding of tasks.
 */
public class FindCommand extends Command {
    private final String[] keywords;

    /**
     * Constructs a FindCommand with the specified keywords.
     *
     * @param keywords The keywords to search for in task descriptions.
     */
    public FindCommand(String... keywords) {
        this.keywords = keywords;
    }

    /**
     * Executes the find command by searching for tasks that contain any of the keywords.
     *
     * @param taskList The list of tasks.
     * @param ui       The Ui object for displaying messages to the user.
     * @param storage  The Storage object for saving and loading tasks (not used here).
     * @throws LiliException If no tasks match any of the keywords.
     */
    @Override
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : taskList) {
            for (String keyword : keywords) {
                if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    matchingTasks.add(task);
                    break;
                }
            }
        }

        if (matchingTasks.isEmpty()) {
            return "I can't find any tasks that match the keywords: "
                    + String.join(", ", keywords) + " :(";
        } else {
            StringBuilder response = new StringBuilder(ui.getChatText("FIND")).append("\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                response.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
            }
            return response.toString();
        }
    }
}
