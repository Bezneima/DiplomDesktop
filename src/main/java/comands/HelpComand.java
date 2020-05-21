package comands;

public class HelpComand {
    public static void invocation() {
        System.out.println("=========================ПОЛНЫЙ СПИСОК КОМАНД=========================");
        System.out.println("/help   - Посмотреть полный список команд");
        System.out.println("/login - Авторизоваться на сервере");
        System.out.println("/checkout - Сменить ветку на другую");
        System.out.println("/commit [Комментарий] - Обозначить, что вы изменили ");
        System.out.println("/fetch - получить данные хранимые на сервере на данной ветке");
        System.out.println("/push - загрузить изменения на сервер");
        System.out.println("/server settings - редактирование/просмотр настроек сервера");
        System.out.println("/merge - Слияние веток");
        System.out.println("/reverse - откатить проект до необхоидмого коммита ");
        System.out.println("========================/ПОЛНЫЙ СПИСОК КОМАНД/========================\n");
    }
}
