package lili;

import java.util.ArrayList;

/**
 * Abstract Command class.
 */
public abstract class Command {
    /**
     * Executes the command with the given arguments.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface for output.
     * @param storage The storage system for saving tasks.
     */
    public abstract String execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException;
}
