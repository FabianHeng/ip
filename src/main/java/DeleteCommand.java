import java.util.ArrayList;

public class DeleteCommand extends Command {
    private final String taskNumber;

    public DeleteCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

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
