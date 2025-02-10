package lili;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum for supported commands.
 */

public enum CommandType {
    LIST("list", "ls", "tasks", "list") {
        @Override
        public Command createCommand(String argument) {
            return new ListCommand();
        }
    },
    TODO("todo", "add", "t", "todo <taskname>") {
        @Override
        public Command createCommand(String argument) {
            return new TodoCommand(argument);
        }
    },
    DEADLINE("deadline", "dl", "deadline <taskname> /by <yyyy-MM-dd HHmm>") {
        @Override
        public Command createCommand(String argument) {
            return new DeadlineCommand(argument);
        }
    },
    EVENT("event", "e", "event <taskname> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>") {
        @Override
        public Command createCommand(String argument) {
            return new EventCommand(argument);
        }
    },
    DELETE("delete", "del", "remove", "delete <tasknum>") {
        @Override
        public Command createCommand(String argument) {
            return new DeleteCommand(argument);
        }
    },
    MARK("mark", "done", "finish", "complete", "check", "mark <tasknum>") {
        @Override
        public Command createCommand(String argument) {
            return new MarkCommand(argument);
        }
    },
    UNMARK("unmark", "uncheck", "undo", "unmark <tasknum>") {
        @Override
        public Command createCommand(String argument) {
            return new UnmarkCommand(argument);
        }
    },
    FIND("find", "search", "f", "find <taskname>") {
        @Override
        public Command createCommand(String argument) { return new FindCommand(argument); }
    },
    HELP("help", "commands", "h", "help") {
        @Override
        public Command createCommand(String argument) { return new HelpCommand(); }
    };

    private static final Map<String, CommandType> aliasMap = new HashMap<>();
    private final String[] aliases;
    private final String syntax;

    static {
        for (CommandType type : CommandType.values()) {
            for (String alias : type.aliases) {
                aliasMap.put(alias.toLowerCase(), type);
            }
        }
    }

    CommandType(String... aliases) {
        this.aliases = Arrays.copyOf(aliases, aliases.length - 1);
        this.syntax = aliases[aliases.length - 1]; // Last element is the syntax
    }

    /**
     * Returns the lists of syntax for all commands.
     *
     * @return Lists of syntax for all commands.
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Creates a specific command instance based on the enum type.
     *
     * @param argument The argument string passed with the command.
     * @return An instance of the respective lili.Command.
     */
    public abstract Command createCommand(String argument);

    /**
     * Resolves a command type from a string input, considering aliases.
     *
     * @param command The user input command.
     * @return The matching lili.CommandType.
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
