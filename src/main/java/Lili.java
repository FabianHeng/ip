import java.util.ArrayList;
import java.util.Scanner;

public class Lili {
    public static void main(String[] args) throws LiliException {
        String logo = "  .---.    .-./`)   .---.    .-./`) \n"
                + "  | ,_|    \\ .-.')  | ,_|    \\ .-.') \n"
                + ",-./  )    / `-' \\,-./  )    / `-' \\ \n"
                + "\\  '_ '`)   `-'`\"`\\  '_ '`)   `-'`\"` \n"
                + " > (_)  )   .---.  > (_)  )   .---.  \n"
                + "(  .  .-'   |   | (  .  .-'   |   |  \n"
                + " `-'`-'|___ |   |  `-'`-'|___ |   |  \n"
                + "  |        \\|   |   |        \\|   |  \n"
                + "  `--------`'---'   `--------`'---'";
        System.out.println("------------------------------");
        System.out.println("Hello! I'm");
        System.out.println(logo);
        System.out.println("What can I do for you?");
        System.out.println("------------------------------");

        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<Task> list = new ArrayList<>();
        while (true) {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye") || input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("------------------------------");
                System.out.println("Bye, talk to you again <3");
                System.out.println("------------------------------");
                break;
            }

            System.out.println("------------------------------");
            try {
                if (input.equalsIgnoreCase("list") || input.equalsIgnoreCase("ls")) {
                    if (list.isEmpty()) {
                        System.out.println("Nothing in list");
                    } else {
                        System.out.println("Here are your list of tasks:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println((i + 1) + ". " + list.get(i).toString());
                        }
                    }
                } else if (input.toLowerCase().startsWith("mark ")) {
                    String taskNumber = input.substring(5).trim();
                    if (taskNumber.isEmpty()) {
                        throw new InvalidTaskNumberException();
                    } else {
                        int taskNumberInt = Integer.parseInt(taskNumber);
                        if (taskNumberInt > 0 && taskNumberInt <= list.size()) {
                            Task task = list.get(taskNumberInt - 1);
                            task.markAsDone();
                            System.out.println("Ok! I've marked it as done:");
                            System.out.println(task.toString());
                        } else {
                            throw new InvalidTaskNumberException();
                        }
                    }
                } else if (input.toLowerCase().startsWith("unmark ")) {
                    String taskNumber = input.substring(7).trim();
                    if (taskNumber.isEmpty()) {
                        throw new InvalidTaskNumberException();
                    } else {
                        int taskNumberInt = Integer.parseInt(taskNumber);
                        if (taskNumberInt > 0 && taskNumberInt <= list.size()) {
                            Task task = list.get(taskNumberInt - 1);
                            task.markAsNotDone();
                            System.out.println("Ok! I've marked it as not done yet:");
                            System.out.println(task.toString());
                        } else {
                            throw new InvalidTaskNumberException();
                        }
                    }
                } else if (input.toLowerCase().startsWith("todo ")) {
                    String taskDescription = input.substring(5).trim();
                    if (taskDescription.isEmpty()) {
                        throw new InvalidTodoDescriptionException();
                    } else {
                        Todo todo = new Todo(taskDescription);
                        list.add(todo);
                        System.out.println("Nice! I've added it to your list:");
                        System.out.println(todo.toString());
                        System.out.println("Now you have " + list.size() + " task(s) in your list.");
                    }
                } else if (input.toLowerCase().startsWith("deadline ")) {
                    String taskDescription = input.substring(9).trim();
                    String[] parts = taskDescription.split(" /by ");
                    if (parts.length == 2) {
                        String name = parts[0].trim();
                        String by = parts[1].trim();
                        Deadline deadline = new Deadline(name, by);
                        list.add(deadline);
                        System.out.println("Remember to complete your task by the deadline! I've added it to your list:");
                        System.out.println(deadline.toString());
                        System.out.println("Now you have " + list.size() + " task(s) in your list.");
                    } else {
                        throw new InvalidDeadlineFormatException();
                    }
                } else if (input.toLowerCase().startsWith("event ")) {
                    String taskDescription = input.substring(6).trim();
                    String[] parts = taskDescription.split(" /from | /to ");
                    if (parts.length == 3) {
                        String name = parts[0].trim();
                        String from = parts[1].trim();
                        String to = parts[2].trim();
                        Event event = new Event(name, from, to);
                        list.add(event);
                        System.out.println("Enjoy your event! I've added it to your list:");
                        System.out.println(event.toString());
                        System.out.println("Now you have " + list.size() + " task(s) in your list.");
                    } else {
                        throw new InvalidEventFormatException();
                    }
                } else if (input.toLowerCase().startsWith("delete ")) {
                    String taskNumber = input.substring(7).trim();
                    if (taskNumber.isEmpty()) {
                        throw new InvalidTaskNumberException();
                    } else {
                        int taskNumberInt = Integer.parseInt(taskNumber);
                        if (taskNumberInt > 0 && taskNumberInt <= list.size()) {
                            Task task = list.get(taskNumberInt - 1);
                            list.remove(taskNumberInt - 1);
                            System.out.println("Done and dusted, I've removed this from your list:");
                            System.out.println(task.toString());
                            System.out.println("Now you have " + list.size() + " task(s) in your list.");
                        } else {
                            throw new InvalidTaskNumberException();
                        }
                    }
                }
                else {
                    throw new InvalidCommandException();
                }
            } catch (LiliException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("------------------------------");
        }

        scanner.close();
    }
}
