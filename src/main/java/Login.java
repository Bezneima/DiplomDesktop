import java.util.Scanner;

class Login {
    String login;
    String password;


    private static Login instance;

    static synchronized Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;

    }

    static void authorization() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите ваш Логин:");
        String login = in.nextLine();
        System.out.print("Введите ваш Пароль:");
        String password = in.nextLine();
        Login myObj = Login.getInstance();

        try {
            if (checkUserInWeb(login, password).equals("true")) {
                myObj.login = login;
                Branch.changeSetting("login", login);
                myObj.password = password;
                Branch.changeSetting("password", password);
                System.out.println("Вы успешно авторизовались как:" + login);
            } else {
                System.out.println("Вы ввели не верно логин или пароль!");
                authorization();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Редкий каличь, переделать быстро писал
    private static String checkUserInWeb(String login, String password) throws Exception {
        return WebUtils.getRequest("http://localhost:8080/authorization?login=" + login + "&password=" + password);
    }

}
