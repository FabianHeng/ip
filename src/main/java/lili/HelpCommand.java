package lili;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Command class that displays the list of available commands and their syntax.
 */
public class HelpCommand extends Command {

    /**
     * Displays the list of available commands and their syntax.
     *
     * @param taskList The list of tasks (unused in this command).
     * @param ui       The user interface that prints out messages.
     * @param storage  The storage system (unused in this command).
     * @return The response message displaying available commands.
     */
    @Override
    public String execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        assert ui != null : "Ui object should not be null";

        return ui.getChatText("HELP") + "\n"
                + Arrays.stream(CommandType.values())
                        .map(commandType -> String.format("%s: %s",
                                commandType.name(), commandType.getSyntax()))
                        .collect(Collectors.joining("\n"));
    }
}
