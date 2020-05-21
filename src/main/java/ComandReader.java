import comands.CheckoutComand;
import comands.HelpComand;

import java.util.Scanner;

public class ComandReader {

    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static void checkComand() {
        Login user = Login.getInstance();
        Branch branch = Branch.getInstance();
        Scanner in = new Scanner(System.in);

        if (user.login != null) {
            if (branch.curentBranch != null) {
                System.out.println("[" + ANSI_GREEN + user.login + ANSI_RESET + "] " + "[" + ANSI_RED + branch.curentBranch + ANSI_RESET + "] " + "Введите необходимую команду");
                String inputComand = in.nextLine();

                switch (inputComand.split(" ")[0]) {
                    case "/help":
                        HelpComand.invocation();
                        break;
                    case "/login":
                        Login.authorization();
                        break;
                    case "/checkout":
                        Branch.changeBranch();
                        break;
                    case "/commit":
                        CheckoutComand.addCommit("");
                        break;
                    case "/exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Вы ввели незвестную команду, введите /help для полного списка команд");
                        break;
                }
            } else {
                Branch.changeBranch();
            }
        } else {
            System.out.println("Пожалуйста авторизуйтесь");
            Login.authorization();
        }

        checkComand();
    }
}
