import java.util.ArrayList;

public abstract class Command {
    protected String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void execute(ArrayList<Task> tasks, Ui ui);
}
