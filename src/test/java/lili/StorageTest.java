package lili;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void testLoadNonExistentFile() throws LiliException {
        Storage storage = new Storage("src/main/data/non_existent_file.txt");
        ArrayList<Task> taskList = storage.loadTasks();
        assertTrue(taskList.isEmpty());
    }

    @Test
    void testSaveAndLoadTasks() throws LiliException {
        Storage storage = new Storage("src/main/data/test_storage.txt");
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Todo("Test task"));

        storage.saveTasks(taskList);

        ArrayList<Task> loadedTasks = storage.loadTasks();
        assertEquals(1, loadedTasks.size());
        assertEquals("Test task", loadedTasks.get(0).getName());

        // Cleanup
        new File("src/main/data/test_storage.txt").delete();
    }

    @Test
    void testHandleCorruptedFile() throws LiliException {
        Storage storage = new Storage("src/main/data/corrupted.txt");

        ArrayList<Task> taskList = storage.loadTasks();
        assertNotNull(taskList);
    }
}
