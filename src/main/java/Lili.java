import java.util.ArrayList;
import java.util.Scanner;

public class Lili {
    public static void main(String[] args) {
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
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                if (taskNumber <= 0 || taskNumber > list.size()) {
                    System.out.println("I can't find any such task, please try again >.<");
                } else {
                    Task task = list.get(taskNumber - 1);
                    task.markAsDone();
                    System.out.println("Yay! I've marked it as done:");
                    System.out.println(task.toString());
                }
            } else if (input.toLowerCase().startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                if (taskNumber <= 0 || taskNumber > list.size()) {
                    System.out.println("I can't find any such task, please try again >.<");
                } else {
                    Task task = list.get(taskNumber - 1);
                    task.markAsNotDone();
                    System.out.println("Ok! I've marked it as not done yet:");
                    System.out.println(task.toString());
                }
            }
            else {
                Task task = new Task(input);
                list.add(task);
                System.out.println("Nice! I've added it to your list:");
                System.out.println(task.toString());
            }
            System.out.println("------------------------------");
        }

        scanner.close();
    }
}
