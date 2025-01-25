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

    /**
     * Creates an Event task with a specified name, start time, and end time.
     *
     * @param name Name of the event.
     * @param from Start time in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @param to End time in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @throws DateTimeParseException If the start or end time format is invalid.
     */
    public Event(String name, String from, String to) throws DateTimeParseException {
        super(name);
        this.from = from;
        this.to = to;
        this.dateTimeFrom = parseDateTime(from);
        this.dateTimeTo = parseDateTime(to);
    }

    /**
     * Creates an Event task with a specified name, start time, end time, and completion status.
     *
     * @param name Name of the event.
     * @param from Start time in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @param to End time in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @param isDone Completion status of the event.
     * @throws DateTimeParseException If the start or end time format is invalid.
     */
    public Event(String name, String from, String to, boolean isDone) throws DateTimeParseException {
        super(name, isDone);
        this.from = from;
        this.to = to;
        this.dateTimeFrom = parseDateTime(from);
        this.dateTimeTo = parseDateTime(to);
    }

    private LocalDateTime parseDateTime(String d) throws DateTimeParseException {
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

    /**
     * Converts the event into a file-friendly format.
     *
     * @return String representation of the event for file storage.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + name + " | " + from + " | " + to;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return String representation of the event.
     */
    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + dateTimeFrom.format(displayFormatter) + " to: " + dateTimeTo.format(displayFormatter) + ")";
    }
}