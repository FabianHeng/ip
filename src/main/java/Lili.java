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
        while (true) {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("------------------------------");
                System.out.println("Byee, talk to you again <3");
                System.out.println("------------------------------");
                break;
            }

            System.out.println("------------------------------");
            System.out.println(input);
            System.out.println("------------------------------");
        }

        scanner.close();
    }
}
