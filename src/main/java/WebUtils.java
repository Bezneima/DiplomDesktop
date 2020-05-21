import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class WebUtils {
    static class SendingFile {// Был бы котлин, было бы проще;
        public String path;
        public File file;
    }

    static String getRequest(String url) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            //Статус
            //System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            //Header headers = entity.getContentType();
            //System.out.println(headers);

            if (entity != null) {
                //получаем ответ
                result = EntityUtils.toString(entity);
            }

        }


        httpClient.close();
        return result;
    }

    public static void sendAllFilesToServerFromPath(String Path, String comment) {

        final File folder = new File(Path);
        List<WebUtils.SendingFile> allFiles = FilesUtils.listFilesForFolder(folder);
        //Так, тут  я получил список всех файлов содержашихся в каталоге
        Login userInfo = Login.getInstance();
        Branch UserBranchInfo = Branch.getInstance();
        for (SendingFile sendingFile : allFiles) {
            try {
                uploadFile(sendingFile.file,
                        sendingFile.file.getName(),
                        userInfo.login,
                        UserBranchInfo.curentBranch,
                        sendingFile.path,
                        comment);
            } catch (IOException e) {
                System.out.println("Не могу послать файл:" + sendingFile.file.getName());
                e.printStackTrace();
            }
        }
    }

    public static void uploadFile(File file, String fileOfName, String userName, String branch, String path, String comment) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("nameOfFile", fileOfName);
        builder.addTextBody("userName", userName);
        builder.addTextBody("branch", branch);
        builder.addTextBody("path", path);
        builder.addTextBody("comment", comment);
        builder.addTextBody("hash", Integer.toString(comment.hashCode()));


        ContentType fileContentType = ContentType.create("text/html");
        String fileName = file.getName();
        builder.addBinaryBody("file", file, fileContentType, fileName);

        HttpEntity entity = builder.build();

        HttpPost request = new HttpPost("http://localhost:8080/upload");
        request.setEntity(entity);

        HttpClient client = HttpClients.createDefault();

        HttpResponse response = client.execute(request);
    }
}
