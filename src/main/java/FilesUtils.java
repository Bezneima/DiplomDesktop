import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FilesUtils {

    static String readSettingsFile() {

        return readFile(getCurrentWorkedPath() + "\\.StankinSettings.st");
    }

    static void writeSettingFile(String text) {
        writeFile(getCurrentWorkedPath() + "\\.StankinSettings.st", text);
    }

    public static void writeFile(String path, String text) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println("Не могу записать в файл" + path + " информацию");
            System.out.println(ex.getMessage());
        }
    }

    static String readFile(String path) {
        File settingsFile = new File(path);
        StringBuilder SettingsFileText = new StringBuilder();
        if (!settingsFile.exists()) {//Создал файл настроек
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Нет разрешения, чтобы сотварить Файл настроект здесь.");
                e.printStackTrace();
                System.exit(0);
            }
            return "";
        } else {// Тут если файла такой уже есть
            try (FileInputStream fin = new FileInputStream(path)) {
                int i = -1;
                while ((i = fin.read()) != -1) {

                    SettingsFileText.append((char) i);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return SettingsFileText.toString();

            //Получил инфу с файла найстроек

        }
    }

    static String getCurrentWorkedPath() {
        return new File("").getAbsolutePath();
    }

    public static List<WebUtils.SendingFile> listFilesForFolder(final File folder) {
        List<WebUtils.SendingFile> allFiles = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                // щас в allFiles все файлы, что он добавил ранее;
                allFiles.addAll(listFilesForFolder(fileEntry));
            } else {
                WebUtils.SendingFile newfile = new WebUtils.SendingFile();
                newfile.file = fileEntry;
                newfile.path = fileEntry.getPath().replace(getCurrentWorkedPath(), "");
                allFiles.add(newfile);
                //System.out.println(fileEntry.getName());
                //System.out.println(fileEntry.getPath().replace(getCurrentWorkedPath(), ""));
            }
        }
        return allFiles;
    }


    public static void makePathToFile(String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();
    }

}
