package lili;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Event class.
 */
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
        assert name != null && !name.trim().isEmpty() : "Event name must not be null or empty";
        assert from != null && !from.trim().isEmpty() : "Start time (from) must not be null or empty";
        assert to != null && !to.trim().isEmpty() : "End time (to) must not be null or empty";

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
        assert name != null && !name.trim().isEmpty() : "Event name must not be null or empty";
        assert from != null && !from.trim().isEmpty() : "Start time (from) must not be null or empty";
        assert to != null && !to.trim().isEmpty() : "End time (to) must not be null or empty";

        this.from = from;
        this.to = to;
        this.dateTimeFrom = parseDateTime(from);
        this.dateTimeTo = parseDateTime(to);
    }

    /**
     * Parses a date or date-time string into LocalDateTime.
     *
     * @param dateTimeString The date/time string in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @return Parsed LocalDateTime object.
     * @throws DateTimeParseException If the input format is invalid.
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return dateTimeString.contains(" ")
                ? parseFullDateTime(dateTimeString)
                : parseDateOnly(dateTimeString);
    }

    /**
     * Parses a full date-time string in "yyyy-MM-dd HHmm" format.
     *
     * @param dateTimeString The date-time string.
     * @return Parsed LocalDateTime object.
     * @throws DateTimeParseException If the input format is invalid.
     */
    private LocalDateTime parseFullDateTime(String dateTimeString) throws DateTimeParseException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date-time format. "
                    + "Expected 'yyyy-MM-dd HHmm'.", dateTimeString, 0);
        }
    }

    /**
     * Parses a date string in "yyyy-MM-dd" format and returns it as LocalDateTime (midnight).
     *
     * @param dateString The date string.
     * @return LocalDateTime set to the start of the day.
     * @throws DateTimeParseException If the input format is invalid.
     */
    private LocalDateTime parseDateOnly(String dateString) throws DateTimeParseException {
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format. Expected 'yyyy-MM-dd'.", dateString, 0);
        }
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
        return "[E] " + super.toString() + " (from: " + dateTimeFrom.format(displayFormatter)
                + " to: " + dateTimeTo.format(displayFormatter) + ")";
    }
}
