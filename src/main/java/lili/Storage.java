package lili;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String fileDir;
    private final String filePath;

    /**
     * Constructor for lili.Storage.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.fileDir = filePath.substring(0, filePath.lastIndexOf('/'));
    }

    /**
     * Saves tasks to the storage file.
     *
     * @param taskList The list of tasks to save.
     */
    public void saveTasks(ArrayList<Task> taskList) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task task : taskList) {
                fw.write(task.toFileFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks to the file.");
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return The list of tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        File directory = new File(fileDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return taskList; // Return an empty list if no file exists
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while loading tasks from the file.");
        }

        return taskList;
    }

    /**
     * Parses a task from a line of text.
     *
     * @param line A line of text representing a task.
     * @return The parsed task, or null if parsing fails.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String name = parts[2];

        try {
            return switch (type) {
                case "T" -> new Todo(name, isDone);
                case "D" -> new Deadline(name, parts[3], isDone);
                case "E" -> new Event(name, parts[3], parts[4], isDone);
                default -> null;
            };
        } catch (DateTimeParseException e) {
            System.out.println("Error loading tasks. Ensure dates are in the format yyyy-MM-dd HHmm.");
            return null;
        }
    }
}
