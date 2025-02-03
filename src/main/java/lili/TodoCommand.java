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
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidTodoDescriptionException();
        }
        Todo todo = new Todo(name);
        taskList.add(todo);
        ui.printChatText("TASK");
        System.out.println(todo);
        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
    }
}
