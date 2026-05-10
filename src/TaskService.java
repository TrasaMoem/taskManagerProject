import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class TaskService {
    public enum SortByName {
        fromAtoZ, fromZtoA
    }
    public enum SortByScale {
        fromSmallestToLargest, fromLargestToSmallest
    }
    public enum TaskParameter {
        NAME, DESCRIPTION, PRIORITY, DEADLINE
    }
    private final InputHandler inputHandler = new InputHandler();
    private final LocalDateTime now = LocalDateTime.now();

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    public void saveEverydayTasks(ArrayList<Task> tasks, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(tasks, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGlobalTasks(ArrayList<Task> tasks, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(tasks, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> loadEverydayTasks(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch (Exception e) {
            System.out.println("Cant load lists data from file");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> loadGlobalTasks(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch (Exception e) {
            System.out.println("Cant load lists data from file");
            throw new RuntimeException(e);
        }
    }

    public void sortByName(ArrayList<Task> list, SortByName sortFromAtoZorZtoA) {
        // Sort from A to Z
        if (sortFromAtoZorZtoA == SortByName.fromAtoZ) {
            list.sort(Comparator.comparing(Task::getName));
            System.out.println("Successfully sorted everyday Tasks from A to Z!");
        }
        // Sort from Z to A
        else {
            list.sort(Comparator.comparing(Task::getName).reversed());
            System.out.println("Successfully sorted everyday Tasks from Z to A!");
        }

    }

    public void sortByPriority(ArrayList<Task> list, SortByScale sortFromSmallestToLargestOrLargestToSmallest) {
        // Sort by priority from smallest to largest
        if (sortFromSmallestToLargestOrLargestToSmallest == SortByScale.fromSmallestToLargest) {
            while (true) {
                boolean flag = false;
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i).getPriority() < list.get(i - 1).getPriority()) {
                        Task temp;
                        temp = list.get(i);
                        list.set(i, list.get(i - 1));
                        list.set(i - 1, temp);
                    }
                }
                for (int j = 1; j < list.size(); j++) {
                    if (list.get(j).getPriority() < list.get(j - 1).getPriority()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    System.out.println("Successfully completed sort of everyday Tasks by priority from Smallest to Largest");
                } else {
                    continue;
                }
                break;
            }
        }
        // Sort by priority from largest to smallest
        else {
            while (true) {
                boolean flag = false;
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i).getPriority() > list.get(i - 1).getPriority()) {
                        Task temp;
                        temp = list.get(i - 1);
                        list.set(i - 1, list.get(i));
                        list.set(i, temp);
                    }
                }
                for (int j = 1; j < list.size(); j++) {
                    if (list.get(j).getPriority() > list.get(j - 1).getPriority()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    System.out.println("Successfully completed sort of everyday Tasks by priority from Largest to Smallest");
                } else {
                    continue;
                }
                break;
            }
        }
    }

    public void sortByDeadline(ArrayList<Task> list, SortByScale sortFromSmallestToLargestOrLargestToSmallest) {
        if (sortFromSmallestToLargestOrLargestToSmallest == SortByScale.fromSmallestToLargest) {
            // sort from Smallest to Largest deadline
            list.sort(Comparator.comparing(Task::getDeadline));
            System.out.println("Successfully completed sort of everyday Tasks by deadline from Smallest to Largest");
        } else {
            // sort from Largest to smallest deadline
            list.sort(Comparator.comparing(Task::getDeadline).reversed());
            System.out.println("Successfully completed sort of everyday Tasks by deadline from Largest to Smallest");
        }
    }

    public void edit(ArrayList<Task> list, TaskParameter taskParameter, int specificTaskNumber, TaskScale scale) {
        // Edit name of the task
        if (taskParameter == TaskParameter.NAME) {
            System.out.println("Enter new name of the Task (old: " + list.get(specificTaskNumber).getName() + "): ");
            list.get(specificTaskNumber).setName(inputHandler.readString());
            System.out.println("New name " + list.get(specificTaskNumber).getName() + " has been edited successfully!");
        }
        // Edit description of the task
        else if (taskParameter == TaskParameter.DESCRIPTION) {
            System.out.println("Enter new description of the Task (old: " + list.get(specificTaskNumber).getDescription() + "): ");
            list.get(specificTaskNumber).setDescription(inputHandler.readString());
            System.out.println("New description " + list.get(specificTaskNumber).getDescription() + " has been edited successfully!");
        }
        // Edit priority of the task
        else if (taskParameter == TaskParameter.PRIORITY) {
            System.out.println("Enter new priority of the Task (old: " + list.get(specificTaskNumber).getPriority() + "), (1 - low, 2 - medium, 3 - high, or more if you want): ");
            list.get(specificTaskNumber).setPriority(inputHandler.readNumber(Integer.MAX_VALUE));
            System.out.println("New priority " + list.get(specificTaskNumber).getPriority() + " has been edited successfully!");
        }
        // Edit deadline of the task
        else if (taskParameter == TaskParameter.DEADLINE) {
            UISystem uiSystem = new UISystem();
            // Edit Everyday deadline
            if (scale == TaskScale.EVERYDAY) {
                System.out.print("Enter new deadline of the Task (in hours) until the end of the Task (old: ");
                uiSystem.displaySpecificTaskDeadline(list.get(specificTaskNumber), TaskScale.EVERYDAY);
                System.out.println("): ");

                // Set new deadline in hours
                list.get(specificTaskNumber).setDeadline(inputHandler.readDateTime(TaskScale.EVERYDAY));

                uiSystem.displaySpecificTaskDeadline(list.get(specificTaskNumber), TaskScale.EVERYDAY);
            }
            // Edit global deadline
            else {
                System.out.print("Enter new deadline of the Task (in days) until the end of the Task (old: ");
                uiSystem.displaySpecificTaskDeadline(list.get(specificTaskNumber), TaskScale.GLOBAL);
                System.out.println("): ");

                // Set new deadline in days
                list.get(specificTaskNumber).setDeadline(inputHandler.readDateTime(TaskScale.GLOBAL));

                uiSystem.displaySpecificTaskDeadline(list.get(specificTaskNumber), TaskScale.GLOBAL);
            }
        }
    }

    public void search(ArrayList<Task> list, String name, TaskScale scale) {
        while (true) {
            // Search by first letter
            UISystem uiSystem = new UISystem();
            boolean flag = false;
            if (name.length() == 1) {
                for (Task task : list) {
                    char letter = task.getName().charAt(0);
                    if (String.valueOf(letter).equalsIgnoreCase(name)) {
                        uiSystem.displaySpecificTask(task, scale);
                        flag = true;
                    }
                }
            } else {
                for (Task task : list) {
                    if (task.getName().equals(name)) {
                        uiSystem.displaySpecificTask(task, scale);
                        flag = true;
                    }
                }
            }
            if (!flag) {
                System.out.println("No such Task, please try another word or letter");
                continue;
            }
            break;
        }
    }

    public void addToOverdueTaskTime(ArrayList<Task> list, TaskScale scale) {
        LinkedHashMap<Integer,Integer> trueIndexOfTasks = positionsOfOverdueTasks(list);
        int actionsQuantity = trueIndexOfTasks.size();
        int overdueTaskAddTimeAction = inputHandler.readNumber(actionsQuantity + 1);

        if (!(overdueTaskAddTimeAction == (actionsQuantity+1))) {
            if (scale == TaskScale.EVERYDAY) {
                System.out.println("How much hours would you like to add?");
                int hoursToAdd = inputHandler.readNumber(Integer.MAX_VALUE);
                Task correctTask = list.get(trueIndexOfTasks.get(overdueTaskAddTimeAction-1));
                correctTask.setDeadline(correctTask.getDeadline().plusHours(hoursToAdd));
                System.out.println("Successfully added more time to overdue everyday task");
            } else {
                System.out.println("How much days would you like to add?");
                int daysToAdd = inputHandler.readNumber(Integer.MAX_VALUE);
                Task correctTask = list.get(trueIndexOfTasks.get(overdueTaskAddTimeAction-1));
                correctTask.setDeadline(correctTask.getDeadline().plusDays(daysToAdd));
                System.out.println("Successfully added more time to overdue global task");
            }
        }
    }

    public LinkedHashMap<Integer,Integer> positionsOfOverdueTasks(ArrayList<Task> list) {
        int overdueCounter = 0;
        LinkedHashMap<Integer, Integer> overdueTasksHash = new LinkedHashMap<>();
        for (Task task : list) {
            if (task.getDeadline().isBefore(now)) {
                overdueTasksHash.put(overdueCounter, list.indexOf(task));
                overdueCounter++;
            }
        }
        return overdueTasksHash;
    }

    public void deleteOverdueTask(ArrayList<Task> list) {
        LinkedHashMap<Integer,Integer> trueIndexOfTask = positionsOfOverdueTasks(list);
        int actionsQuantity = trueIndexOfTask.size();
        int overdueEverydayTaskDeleteAction = inputHandler.readNumber(actionsQuantity + 2);

        if (overdueEverydayTaskDeleteAction == (actionsQuantity+1)) {
            System.out.println("Are you sure you want to delete ALL the overdue Tasks permanently?");
            System.out.println("1. Yes, I sure");
            System.out.println("2. Dont delete ALL my overdue Tasks");

            int sure = inputHandler.readNumber(2);
            // Warning before deleting all overdue tasks
            if (sure == 1) {
                for (int i = actionsQuantity; i > 0; i--) {
                    int index = trueIndexOfTask.get(i-1);
                    list.remove(index);
                }
                System.out.println("Successfully deleted all overdue Tasks");
            } else {
                System.out.println("No changes were made!");
            }
        }
        else if (!(overdueEverydayTaskDeleteAction == (actionsQuantity+2))) {
            int index = trueIndexOfTask.get(overdueEverydayTaskDeleteAction-1);

            String rememberName = list.get(index).getName();
            list.remove(index);

            System.out.println("Successfully deleted overdue Task " + rememberName + "!");
        }
    }

}

