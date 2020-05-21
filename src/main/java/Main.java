import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Вас приветсвует Система контроля версий от МГТУ «Станкин»");
        System.out.println("Для полного списка команд введите /help");
        try {
            Branch.getLastSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComandReader.checkComand();


    }
}
