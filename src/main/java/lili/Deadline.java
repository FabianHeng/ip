package lili;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime dateTime;
    protected DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    protected String by;

    public Deadline(String name, String by) throws DateTimeParseException {
        super(name);
        this.by = by;
        this.dateTime = parseDateTime(by);
    }

    public Deadline(String name, String by, boolean isDone) throws DateTimeParseException {
        super(name, isDone);
        this.by = by;
        this.dateTime = parseDateTime(by);
    }

    public LocalDateTime parseDateTime(String by) throws DateTimeParseException {
        if (by.contains(" ")) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            dateTime = LocalDateTime.parse(by, dateTimeFormatter);
        } else {
            LocalDate date = LocalDate.parse(by, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTime = date.atStartOfDay();
        }
        return dateTime;
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + name + " | " + by;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + dateTime.format(displayFormatter) + ")";
    }
}
