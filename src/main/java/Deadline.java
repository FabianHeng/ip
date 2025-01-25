import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime dateTime;
    protected DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String name, String by) {
        super(name);
        this.dateTime = parseDateTime(by);
    }

    public Deadline(String name, String by, boolean isDone) {
        super(name, isDone);
        this.dateTime = parseDateTime(by);
    }

    public LocalDateTime parseDateTime(String by) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(by, formatter);
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + name + " | " + dateTime.format(displayFormatter);
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + dateTime.format(displayFormatter) + ")";
    }
}
