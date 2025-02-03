package lili;

import java.util.ArrayList;

/**
 * Command class that processes deletion of tasks.
 */
public class DeleteCommand extends Command {
    private final String taskNumber;

    /**
     * Creates a DeleteCommand with the task number to delete.
     *
     * @param taskNumber The task number to be deleted.
     */
    public DeleteCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the delete command, removing the specified task from the task list.
     *
     * @param taskList The list of tasks.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws LiliException If an error occurs while parsing the task number.
     */
    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        int taskIndex = parseTaskNumber(taskNumber, taskList.size());
        Task removedTask = taskList.remove(taskIndex);

        ui.printChatText("DELETE");
        System.out.println(removedTask.toString());
        System.out.println("Now you have " + taskList.size() + " task(s) in your list.");
    }

    private int parseTaskNumber(String taskNumber, int size) throws InvalidTaskNumberException {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= size) {
                throw new InvalidTaskNumberException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException("Invalid task number: " + taskNumber);
        }
    }
}
