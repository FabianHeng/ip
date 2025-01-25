package lili;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime dateTime;
    protected DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    protected String by;

    /**
     * Creates a Deadline task with a specified name and deadline.
     *
     * @param name Name of the task.
     * @param by Deadline of the task in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @throws DateTimeParseException If the deadline format is invalid.
     */
    public Deadline(String name, String by) throws DateTimeParseException {
        super(name);
        this.by = by;
        this.dateTime = parseDateTime(by);
    }

    /**
     * Creates a Deadline task with a specified name, deadline, and completion status.
     *
     * @param name Name of the task.
     * @param by Deadline of the task in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @param isDone Completion status of the task.
     * @throws DateTimeParseException If the deadline format is invalid.
     */
    public Deadline(String name, String by, boolean isDone) throws DateTimeParseException {
        super(name, isDone);
        this.by = by;
        this.dateTime = parseDateTime(by);
    }

    private LocalDateTime parseDateTime(String by) throws DateTimeParseException {
        if (!by.contains(" ")) {
            LocalDate date = LocalDate.parse(by, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTime = date.atStartOfDay();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            dateTime = LocalDateTime.parse(by, dateTimeFormatter);
        }
        return dateTime;
    }

    /**
     * Converts the deadline task into a file-friendly format.
     *
     * @return String representation of the deadline task for file storage.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + name + " | " + by;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return String representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + dateTime.format(displayFormatter) + ")";
    }
}
