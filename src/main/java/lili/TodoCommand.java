package lili;

import java.util.ArrayList;

/**
 * Command class that processes addition of tasks.
 */
public class TodoCommand extends Command {
    private final String name;

    public TodoCommand(String name) {
        this.name = name;
    }

    /**
     * Adds a new todo task into the task list.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface that prints out messages.
     * @param storage The loading and saving files object.
     * @throws InvalidTodoDescriptionException If the command format is invalid.
     */
    @Override
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        assert taskList != null : "Task list should not be null";
        assert ui != null : "Ui object should not be null";
        assert storage != null : "Storage object should not be null";

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidTodoDescriptionException();
        }

        Todo todo = new Todo(name);
        assert todo != null : "Todo object should be successfully created";

        int initialSize = taskList.size();
        taskList.add(todo);
        assert taskList.size() == initialSize + 1 : "Task list size should increase by 1";

        return ui.printChatText("TASK") + "\n"
                + todo + "\n"
                + "Now you have " + taskList.size() + " task(s) in your list.";
    }
}
