package lili;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TodoTest {
    @Test
    void testTodoCommandAddsTask() throws LiliException {
        ArrayList<Task> taskList = new ArrayList<>();
        Ui ui = new Ui();
        Storage storage = new Storage("data/lili.txt");
        TodoCommand todoCommand = new TodoCommand("read book");

        todoCommand.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertEquals("read book", taskList.get(0).getName());
    }

    @Test
    void testEmptyTodoDescriptionThrowsException() {
        ArrayList<Task> taskList = new ArrayList<>();
        Ui ui = new Ui();
        Storage storage = new Storage("data/lili.txt");
        TodoCommand todoCommand = new TodoCommand("");
        Exception exception = assertThrows(
                lili.InvalidTodoDescriptionException.class,
                () -> todoCommand.execute(taskList, ui, storage)
        );
        assertEquals("Please enter a valid todo name / description :((", exception.getMessage());
    }
}

