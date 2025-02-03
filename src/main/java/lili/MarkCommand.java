package lili;

import java.util.ArrayList;

/**
 * Command class that processes marking of tasks.
 */
public class MarkCommand extends Command {
    private final String taskNumber;

    public MarkCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Marks a task as done.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface that prints out messages.
     * @param storage The loading and saving files object.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) throws LiliException {
        int taskIndex = parseTaskNumber(taskNumber, taskList.size());
        Task task = taskList.get(taskIndex);
        task.markAsDone();
        ui.printChatText("MARK");
        System.out.println(task);
    }

    private int parseTaskNumber(String taskNumber, int size) throws InvalidTaskNumberException {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= size) {
                throw new InvalidTaskNumberException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException();
        }
    }
}
