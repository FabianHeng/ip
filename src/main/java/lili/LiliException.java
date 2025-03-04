package lili;

/**
 * Exception class that contains all Exceptions to Lili class.
 */
public class LiliException extends Exception {
    public LiliException() {
        super("Something went wrong, please try again :/");
    }

    public LiliException(String message) {
        super(message);
    }
}

class StorageException extends LiliException {
    public StorageException() {
        super("Oops! An error occurred while accessing storage.");
    }

    public StorageException(String message) {
        super(message);
    }
}

class TaskParseException extends LiliException {
    public TaskParseException() {
        super("Oops! The task file could be corrupted, I couldn't understand the contents");
    }

    public TaskParseException(String message) {
        super(message);
    }
}

class InvalidTaskNumberException extends LiliException {
    public InvalidTaskNumberException() {
        super("I can't find any such task, please try again >.<");
    }

    public InvalidTaskNumberException(String message) {
        super(message);
    }
}

class InvalidTodoDescriptionException extends LiliException {
    public InvalidTodoDescriptionException() {
        super("Please enter a valid todo name / description :((");
    }

    public InvalidTodoDescriptionException(String message) {
        super(message);
    }
}

class InvalidDeadlineFormatException extends LiliException {
    public InvalidDeadlineFormatException() {
        super("Please enter a valid deadline name or by date :((");
    }

    public InvalidDeadlineFormatException(String message) {
        super(message);
    }
}

class InvalidEventFormatException extends LiliException {
    public InvalidEventFormatException() {
        super("Please enter a valid event name or from / to date :((");
    }

    public InvalidEventFormatException(String message) {
        super(message);
    }
}

class FindException extends LiliException {
    public FindException(String... keywords) {
        super("I can't find any tasks that match the keywords: " + String.join(", ", keywords) + " :(");
    }

    public FindException(String message) {
        super(message);
    }
}

class InvalidCommandException extends LiliException {
    public InvalidCommandException() {
        super("I don't understand your command, type \"help\" to see the list of commands >.<");
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
