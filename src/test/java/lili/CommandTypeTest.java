package lili;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                lili.InvalidCommandException.class,
                () -> CommandType.fromString("invalidCommand")
        );
        assertEquals("Unknown command: invalidCommand", exception.getMessage());
    }
}
