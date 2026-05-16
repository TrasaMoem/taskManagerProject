import java.util.ArrayList;
import java.util.List;

public class App {

    public void start() {
        final String EVERYDAY_FILE_NAME = "everydayTasks.json", GLOBAL_FILE_NAME = "globalTasks.json";
        TaskStorageService storage = new TaskStorageService();
        MenuController menuController = new MenuController();
        List<Task> listOfEverydayTasks = storage.loadTasks(EVERYDAY_FILE_NAME), listOfGlobalTasks = storage.loadTasks(GLOBAL_FILE_NAME);
        // If one of lists is equals null
        if (listOfEverydayTasks == null) {
            listOfEverydayTasks = new ArrayList<>();
        }
        if (listOfGlobalTasks == null) {
            listOfGlobalTasks = new ArrayList<>();
        }
        menuController.handleMainMenu(listOfEverydayTasks, listOfGlobalTasks, EVERYDAY_FILE_NAME, GLOBAL_FILE_NAME);
    }
}
