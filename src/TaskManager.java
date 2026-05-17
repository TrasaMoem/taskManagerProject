import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    final String EVERYDAY_FILE_NAME = "everydayTasks.json", GLOBAL_FILE_NAME = "globalTasks.json";
    private List<Task> listOfEverydayTasks, listOfGlobalTasks;
    TaskStorageService storage = new TaskStorageService();

    public TaskManager() {
        listOfEverydayTasks = storage.loadTasks(EVERYDAY_FILE_NAME);
        listOfGlobalTasks = storage.loadTasks(GLOBAL_FILE_NAME);
        if (listOfEverydayTasks == null) {
            listOfEverydayTasks = new ArrayList<>();
        }
        if (listOfGlobalTasks == null) {
            listOfGlobalTasks = new ArrayList<>();
        }
    }
    public List<Task> getListOfEverydayTasks() {
        return listOfEverydayTasks;
    }
    public List<Task> getListOfGlobalTasks() {
        return listOfGlobalTasks;
    }
    public void saveTasks() {
        storage.saveTasks(listOfEverydayTasks, EVERYDAY_FILE_NAME);
        storage.saveTasks(listOfGlobalTasks, GLOBAL_FILE_NAME);
    }

}
