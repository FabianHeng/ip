package lili;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class FindCommandTest {

    @Test
    void testFindValidTasks() throws LiliException {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Buy groceries"));
        taskList.add(new Todo("Read a book"));
        taskList.add(new Todo("Book flight tickets"));

        Ui ui = new Ui();
        Storage storage = new Storage("src/main/data/test.txt");

        FindCommand findCommand = new FindCommand("book");
        String response = findCommand.execute(taskList, ui, storage);

        assertTrue(response.contains("Read a book"));
        assertTrue(response.contains("Book flight tickets"));
    }

    @Test
    void testFindWithNoMatchingTasks() throws LiliException {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Buy groceries"));
        taskList.add(new Todo("Read a book"));

        Ui ui = new Ui();
        Storage storage = new Storage("src/main/data/test.txt");

        FindCommand command = new FindCommand("exercise");
        FindException thrown = assertThrows(FindException.class, () -> {
            command.execute(taskList, ui, storage);
        });
        assertEquals("I can't find any tasks that match the keywords: exercise :(",
                thrown.getMessage());
    }
}
