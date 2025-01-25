import java.util.*;

/**
 * Enum for supported commands.
 */

public enum CommandType {
    LIST("list", "ls", "tasks") {
        @Override
        public Command createCommand(String argument) {
            return new ListCommand();
        }
    },
    TODO("todo", "add") {
        @Override
        public Command createCommand(String argument) {
            return new TodoCommand(argument);
        }
    },
    DELETE("delete", "del", "remove") {
        @Override
        public Command createCommand(String argument) {
            return new DeleteCommand(argument);
        }
    },
    MARK("mark", "done", "finish", "complete", "check") {
        @Override
        public Command createCommand(String argument) {
            return new MarkCommand(argument);
        }
    },
    UNMARK("unmark", "uncheck", "undo") {
        @Override
        public Command createCommand(String argument) {
            return new UnmarkCommand(argument);
        }
    },
    DEADLINE("deadline") {
        @Override
        public Command createCommand(String argument) {
            return new DeadlineCommand(argument);
        }
    },
    EVENT("event") {
        @Override
        public Command createCommand(String argument) {
            return new EventCommand(argument);
        }
    };

    private static final Map<String, CommandType> aliasMap = new HashMap<>();
    private final String[] aliases;

    static {
        for (CommandType type : CommandType.values()) {
            for (String alias : type.aliases) {
                aliasMap.put(alias.toLowerCase(), type);
            }
        }
    }

    CommandType(String... aliases) {
        this.aliases = aliases;
    }

    /**
     * Creates a specific command instance based on the enum type.
     *
     * @param argument The argument string passed with the command.
     * @return An instance of the respective Command.
     */
    public abstract Command createCommand(String argument);

    /**
     * Resolves a command type from a string input, considering aliases.
     *
     * @param command The user input command.
     * @return The matching CommandType.
     * @throws InvalidCommandException If the command is unknown.
     */
    public static CommandType fromString(String command) throws LiliException {
        CommandType type = aliasMap.get(command.toLowerCase());
        if (type == null) {
            throw new InvalidCommandException("Unknown command: " + command);
        }
        return type;
    }
}