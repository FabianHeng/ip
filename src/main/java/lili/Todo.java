package lili;

/**
 * Todo class.
 */
public class Todo extends Task {

    /**
     * Creates a Todo task with a specified name.
     *
     * @param name Name of the task.
     */
    public Todo(String name) {
        super(name);
    }

    /**
     * Creates a Todo task with a specified name and completion status.
     *
     * @param name Name of the task.
     * @param isDone Completion status of the task.
     */
    public Todo(String name, boolean isDone) {
        super(name, isDone);
    }

    /**
     * Converts the Todo task into a file-friendly format.
     *
     * @return String representation of the Todo task for file storage.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + name;
    }

    /**
     * Returns the string representation of the Todo task.
     *
     * @return String representation of the Todo task.
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
