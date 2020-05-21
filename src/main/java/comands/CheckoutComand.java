package comands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckoutComand {

    public List<String> allCommits = new ArrayList<>();
    private static CheckoutComand instance;

    public static synchronized CheckoutComand getInstance() {
        if (instance == null) {
            instance = new CheckoutComand();
        }
        return instance;
    }

    public static void addCommit(String commit) {
        if (commit.equals("")) {
            System.out.print("Введите комментарий:");
            Scanner in = new Scanner(System.in);
            commit = in.nextLine();
        }
        CheckoutComand checkoutComand = CheckoutComand.getInstance();
        checkoutComand.allCommits.add(commit);
    }
}
