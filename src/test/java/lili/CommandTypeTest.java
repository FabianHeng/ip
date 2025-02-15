package lili;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CommandTypeTest {
    @Test
    void testCommandTypeResolvesList() throws Exception {
        CommandType commandType = CommandType.fromString("list");
        assertNotNull(commandType);
        assertEquals(CommandType.LIST, commandType);

        commandType = CommandType.fromString("ls");
        assertNotNull(commandType);
        assertEquals(CommandType.LIST, commandType);

        commandType = CommandType.fromString("del");
        assertNotNull(commandType);
        assertEquals(CommandType.DELETE, commandType);
    }

    @Test
    void testInvalidCommandThrowsException() {
        Exception exception = assertThrows(
                lili.InvalidCommandException.class, (
                ) -> CommandType.fromString("invalidCommand")
        );
        assertEquals("I don't understand your command, type \"help\" to see the list of commands >.<", exception.getMessage());
    }
}
