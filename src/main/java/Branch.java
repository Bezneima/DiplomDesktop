import java.io.IOException;
import java.util.Scanner;

public class Branch {
    private static Branch instance;
    public String curentBranch;
    public String currentWorkspace;
    public String allCommits = "Start branch ";

    public static synchronized Branch getInstance() {
        if (instance == null) {
            instance = new Branch();
        }
        return instance;
    }

    static void changeBranch() {
        Scanner in = new Scanner(System.in);
        Branch branch = Branch.getInstance();
        branch.currentWorkspace = FilesUtils.getCurrentWorkedPath();
        System.out.print("Введите название ветки:");
        String userBranchInput = in.nextLine();

        if (branch.curentBranch == null || !branch.curentBranch.equals(userBranchInput)) {
            if (branch.curentBranch == null)
                branch.allCommits += userBranchInput;
            branch.curentBranch = userBranchInput;
            WebUtils.sendAllFilesToServerFromPath(FilesUtils.getCurrentWorkedPath() + "\\work", branch.allCommits);//TODO убрать потом "work" чтобы он прям из папки все брал
            Branch.changeSetting("currentBranch", branch.curentBranch);
        }
    }


    public static void getLastSettings() throws IOException {
        setSettings(FilesUtils.readSettingsFile().split("\n"));
    }

    private String checkBranchInServer(String checkedBranch) throws Exception {
        return WebUtils.getRequest("http://localhost:8080/authorization?curentBranch=" + checkedBranch + "&currentWorkspace=" + currentWorkspace);
    }

    static void changeSetting(String settingName, String value) {
        boolean settingsChanged = false;
        StringBuilder allTextFromSettingsFile = new StringBuilder();
        for (String loadedSettings : FilesUtils.readSettingsFile().split("\n")) {
            String tmp = loadedSettings.split(":")[0];
            if (tmp.equals(settingName)) {
                settingsChanged = true;
                allTextFromSettingsFile.append(settingName).append(":").append(value).append("\r\n");
            } else {
                if (!loadedSettings.equals(""))
                    allTextFromSettingsFile.append(loadedSettings).append("\n");
            }
        }
        if (!settingsChanged)
            allTextFromSettingsFile.append(settingName).append(":").append(value).append("\r\n");
        FilesUtils.writeSettingFile(allTextFromSettingsFile.toString());
    }

    //Получает настройки в случайно порядке и ставит их в синглтоны
    private static void setSettings(String[] inputSettings) {
        Login login = Login.getInstance();
        Branch branch = Branch.getInstance();
        for (String inputSetting : inputSettings) {
            String[] temp = inputSetting.split(":");
            switch (temp[0]) {
                case "login":
                    login.login = temp[1].replaceAll("\r", "");
                    break;
                case "password":
                    login.password = temp[1].replaceAll("\r", "");
                    break;
                case "currentBranch":
                    branch.curentBranch = temp[1].replaceAll("\r", "");
                    break;
            }
        }
    }
}
