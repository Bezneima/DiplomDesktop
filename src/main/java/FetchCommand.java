import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchCommand {
    static class FetchedFile {
        public String fileName;
        public String path;
        public String text;
    }

    // Самый легкий варик - Все удалить кроме этого файла и скачать заного...
    // До сдачи предпоказа у меня 1 день, соу...
    public static void fetch() {
        boolean errorWhenDeleteFile = false;
        List<WebUtils.SendingFile> listOfDeletedFile = FilesUtils.listFilesForFolder(new File(FilesUtils.getCurrentWorkedPath() + "\\work"));
        for (WebUtils.SendingFile deletedFile : listOfDeletedFile) {
            if (!deletedFile.file.getName().equals("StankinCVS.jar")) {
                if (!deletedFile.file.delete()) {
                    System.out.println("Закройте файл " + deletedFile.file.getName() + " для продолжения работы");
                    errorWhenDeleteFile = true;
                }
            }
        }
        if (!errorWhenDeleteFile) {
            Gson gson = new Gson();
            //Тут я удалил все файлы и теперь просто скачиваю нужный коммит с ветки;
            try {
                // Возвращается мапа, лень думать поэтому кастыль
                List<LinkedTreeMap<String, String>> fetchedFiles = gson.fromJson(WebUtils.fetchFilesRequest("qwe", "first"), List.class);
                for (int i = 0; i < fetchedFiles.size(); i++) {
                    FilesUtils.makePathToFile(FilesUtils.getCurrentWorkedPath() + fetchedFiles.get(i).get("path"));
                    FilesUtils.writeFile(FilesUtils.getCurrentWorkedPath() + fetchedFiles.get(i).get("path"), fetchedFiles.get(i).get("text"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
