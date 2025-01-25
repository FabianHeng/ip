package lili;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime dateTimeFrom;
    protected LocalDateTime dateTimeTo;
    protected DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    protected String from;
    protected String to;

    public Event(String name, String from, String to) throws DateTimeParseException {
        super(name);
        this.from = from;
        this.to = to;
        this.dateTimeFrom = parseDateTime(from);
        this.dateTimeTo = parseDateTime(to);
    }

    public Event(String name, String from, String to, boolean isDone) throws DateTimeParseException {
        super(name, isDone);
        this.from = from;
        this.to = to;
        this.dateTimeFrom = parseDateTime(from);
        this.dateTimeTo = parseDateTime(to);
    }

    public LocalDateTime parseDateTime(String d) throws DateTimeParseException {
        LocalDateTime dateTime;
        if (d.contains(" ")) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            dateTime = LocalDateTime.parse(d, dateTimeFormatter);
        } else {
            LocalDate date = LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTime = date.atStartOfDay();
        }
        return dateTime;
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + name + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + dateTimeFrom.format(displayFormatter) + " to: " + dateTimeTo.format(displayFormatter) + ")";
    }
}