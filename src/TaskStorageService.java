import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class TaskStorageService {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();

    public void saveTasks(List<Task> tasks, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(tasks, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Task> loadTasks(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type taskListType = new TypeToken<List<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch (Exception e) {
            System.out.println("Cant load lists data from file");
            throw new RuntimeException(e);
        }
    }
}
