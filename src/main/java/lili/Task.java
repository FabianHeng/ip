package lili;

public abstract class Task {
    protected String name;
    protected boolean isDone;

    /**
     * Creates a task with a specified name and marks it as not done.
     *
     * @param name Name of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Creates a task with a specified name and completion status.
     *
     * @param name Name of the task.
     * @param isDone Completion status of the task.
     */
    public Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    /**
     * Converts the task into a file-friendly format.
     *
     * @return String representation of the task for file storage.
     */
    public abstract String toFileFormat();

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Retrieves the status icon of the task.
     *
     * @return "X" if the task is done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Retrieves the name of the task.
     *
     * @return Name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return String representation of the task.
     */
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + name;
    }
}
