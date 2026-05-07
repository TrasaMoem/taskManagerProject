import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class TaskService extends InputHandler {

    public static final String EVERYDAY_FILE_NAME = "everydayTasks.json";
    public static final String GLOBAL_FILE_NAME = "globalTasks.json";
    public static final LocalDateTime now = LocalDateTime.now();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    public static void saveEverydayTasks(ArrayList<Task> tasks, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(tasks, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveGlobalTasks(ArrayList<Task> tasks, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(tasks, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Task> loadEverydayTasks(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch (Exception e) {
            System.out.println("Cant load lists data from file");
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Task> loadGlobalTasks(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch (Exception e) {
            System.out.println("Cant load lists data from file");
            throw new RuntimeException(e);
        }
    }

    public static void sortByName(Scanner sc, ArrayList<Task> list, boolean everydayOrGlobal, boolean SortFromAtoZorZtoA) {
        // Sort from A to Z (true)
        if (SortFromAtoZorZtoA) {
            list.sort(Comparator.comparing(Task::getName));
            System.out.println("Successfully sorted everyday Tasks from A to Z!");
        }
        // Sort from Z to A (false)
        else {
            list.sort(Comparator.comparing(Task::getName).reversed());
            System.out.println("Successfully sorted everyday Tasks from Z to A!");
        }

    }

    public static void sortByPriority(Scanner sc, ArrayList<Task> list, boolean everydayOrGlobal, boolean sortStartsFromSmallestOrLargestPriority) {
        // Sort by priority from smallest to largest
        if (sortStartsFromSmallestOrLargestPriority) {
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

    public static void sortByDeadline(Scanner sc, ArrayList<Task> list, boolean smallestOrLargestDeadline, boolean everydayOrGlobal) {
        if (smallestOrLargestDeadline) {
            list.sort(Comparator.comparing(Task::getDeadline));
            System.out.println("Successfully completed sort of everyday Tasks by deadline from Smallest to Largest");
        } else {
            list.sort(Comparator.comparing(Task::getDeadline).reversed());
            System.out.println("Successfully completed sort of everyday Tasks by deadline from Largest to Smallest");
        }
    }

    public static void edit(Scanner sc, ArrayList<Task> list, String nameOrDescriptionOrPriorityOrDeadline, int j, boolean everydayOrGlobal) {
        // Edit name of the task
        if (nameOrDescriptionOrPriorityOrDeadline.equalsIgnoreCase("name")) {
            System.out.println("Enter new name of the Task (old: " + list.get(j).getName() + "): ");
            list.get(j).setName(readString(sc));
            System.out.println("New name " + list.get(j).getName() + " has been edited successfully!");
        }
        // Edit description of the task
        else if (nameOrDescriptionOrPriorityOrDeadline.equalsIgnoreCase("description")) {
            System.out.println("Enter new description of the Task (old: " + list.get(j).getDescription() + "): ");
            list.get(j).setDescription(readString(sc));
            System.out.println("New description " + list.get(j).getDescription() + " has been edited successfully!");
        }
        // Edit priority of the task
        else if (nameOrDescriptionOrPriorityOrDeadline.equalsIgnoreCase("priority")) {
            System.out.println("Enter new priority of the Task (old: " + list.get(j).getPriority() + "), (1 - low, 2 - medium, 3 - high, or more if you want): ");
            list.get(j).setPriority(readNumber(sc,Integer.MAX_VALUE));
            System.out.println("New priority " + list.get(j).getPriority() + " has been edited successfully!");
        }
        // Edit deadline of the task
        else if (nameOrDescriptionOrPriorityOrDeadline.equalsIgnoreCase("deadline")) {
            Duration duration = Duration.between(now, list.get(j).getDeadline());
            boolean passed = duration.isNegative();
            duration = duration.abs();

            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;

            // Edit Everyday deadline
            if (everydayOrGlobal) {
                System.out.print("Enter new deadline of the Task (in hours) until the end of the Task (old: ");
                if (passed) {
                    System.out.print("passed " + (days * 24 + hours) + " hours " + minutes + " minutes ago");
                } else {
                    System.out.print((days * 24 + hours) + " hours " + minutes + " minutes left");
                }
                System.out.println("): ");

                // Set new deadline in hours
                list.get(j).setDeadline(readDateTime(sc,true));

                Duration duration2 = Duration.between(now, list.get(j).getDeadline());
                long days2 = duration2.toDays();
                long hours2 = duration2.toHours() % 24;
                long minutes2 = duration2.toMinutes() % 60;
                System.out.println("New deadline: " + (days2 * 24 + hours2) + " hours " + minutes2 + " minutes left has been edited successfully!" );
                //delay(sc);
            }
            // Edit global deadline
            else {
                System.out.print("Enter new deadline of the Task (in days) until the end of the Task (old: ");
                if (passed) {
                    System.out.print("passed " + days + " days " + hours + " hours " + minutes + " minutes ago");
                } else {
                    System.out.print(days + " days " + hours + " hours " + minutes + " minutes left");
                }
                System.out.println("): ");

                // Set new deadline in days
                list.get(j).setDeadline(readDateTime(sc,false));

                Duration duration2 = Duration.between(now, list.get(j).getDeadline());
                long days2 = duration2.toDays();
                long hours2 = duration2.toHours() % 24;
                long minutes2 = duration2.toMinutes() % 60;
                System.out.println("New deadline: " + days2 + " days " + hours2 + " hours " + minutes2 + " minutes left has been edited successfully!");
                //delay(sc);
            }
        }
    }

    public static void search(Scanner sc, ArrayList<Task> list, String name) {
        while (true) {
            // Search by first letter
            if (name.length() == 1) {
                boolean flag = false;
                LocalDateTime now = LocalDateTime.now();
                for (int i = 0; i < list.size(); i++) {
                    char letter = list.get(i).getName().charAt(0);
                    if (String.valueOf(letter).equalsIgnoreCase(name)) {
                        Task t = list.get(i);

                        Duration duration = Duration.between(now, t.getDeadline());
                        boolean passed = duration.isNegative();
                        duration = duration.abs();

                        long days = duration.toDays();
                        long hours = duration.toHours() % 24;
                        long minutes = duration.toMinutes() % 60;

                        System.out.print("Name: " + list.get(i).getName() + ", Description: " + list.get(i).getDescription() + ", Priority: " + list.get(i).getPriority() + " (1 - low, 2 - medium, 3 - high), Deadline time: ");
                        if (passed) {
                            System.out.println("passed " + (days * 24 + hours) + " hours " + minutes + " minutes ago");
                        } else {
                            System.out.println((days * 24 + hours) + " hours " + minutes + " minutes left");
                        }
                        flag = true;
                    }
                }
                if (flag) {
                    break;
                } else {
                    System.out.println("No such Task, please try another letter");
                    break;
                }
            } else {
                boolean flag = false;
                LocalDateTime now = LocalDateTime.now();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getName().equals(name)) {
                        Task t = list.get(i);

                        Duration duration = Duration.between(now, t.getDeadline());
                        boolean passed = duration.isNegative();
                        duration = duration.abs();

                        long days = duration.toDays();
                        long hours = duration.toHours() % 24;
                        long minutes = duration.toMinutes() % 60;

                        System.out.print("Name: " + list.get(i).getName() + ", Description: " + list.get(i).getDescription() + ", Priority: " + list.get(i).getPriority() + " (1 - low, 2 - medium, 3 - high), Deadline time: ");
                        if (passed) {
                            System.out.println("passed " + days + " days " + hours + " hours " + minutes + " minutes ago");
                        } else {
                            System.out.println(days + " days " + hours + " hours " + minutes + " minutes left");
                        }
                        flag = true;
                    }
                }
                if (flag) {
                    break;
                } else {
                    System.out.println("No such Task, please try another word");
                    break;
                }
            }
        }
    }

    public static void addOverdueTime(ArrayList<Task> list, Scanner sc, boolean everydayOrGlobal) {
        System.out.println("Which task would you like to add more time to: ");
        LinkedHashMap<Integer,Integer> trueIndexOfTask = displayOverdueTasks(list, everydayOrGlobal);
        int actionsQuantity = trueIndexOfTask.size();
        System.out.println((actionsQuantity+1) + ") Back to previous page");
        int overdueTaskAddTimeAction = readNumber(sc, actionsQuantity+1);

        if (!(overdueTaskAddTimeAction == (actionsQuantity+1))) {
            if (!everydayOrGlobal) {
                System.out.println("How much days would you like to add?");
                int daysToAdd = readNumber(sc, Integer.MAX_VALUE);
                Task correctTask = list.get(trueIndexOfTask.get(overdueTaskAddTimeAction-1));
                correctTask.setDeadline(correctTask.getDeadline().plusDays(daysToAdd));
                System.out.println("Successfully added more time to overdue global task");
            } else {
                System.out.println("How much hours would you like to add?");
                int hoursToAdd = readNumber(sc, Integer.MAX_VALUE);
                Task correctTask = list.get(trueIndexOfTask.get(overdueTaskAddTimeAction-1));
                correctTask.setDeadline(correctTask.getDeadline().plusHours(hoursToAdd));
                System.out.println("Successfully added more time to overdue everyday task");
            }
        }
    }

    public static LinkedHashMap<Integer,Integer> displayOverdueTasks(ArrayList<Task> list, boolean everydayOrGlobal) {
        int overdueCounter = 0;
        LinkedHashMap<Integer, Integer> overdueTasksHash = new LinkedHashMap<Integer, Integer>();
        for (Task task : list) {
            if (task.getDeadline().isBefore(now)) {
                overdueTasksHash.put(overdueCounter, list.indexOf(task));
                overdueCounter++;
                System.out.print(overdueCounter + ") " + task.getName());

                Duration duration = Duration.between(now, task.getDeadline()).abs();
                long days = duration.toDays(), hours = duration.toHours() % 24, minutes = duration.toMinutes() % 60;

                if (everydayOrGlobal) {
                    System.out.println(" - passed " + (days * 24 + hours) + " hours " + minutes + " minutes ago");
                } else {
                    System.out.println(" - passed " + days + " days " + hours + " hours " + minutes + " minutes ago");
                }
            }
        }
        if (overdueCounter == 0) {
            System.out.println("No overdue tasks");
        }
        return overdueTasksHash;
    }

    public static void deleteOverdueTask(ArrayList<Task> list, Scanner sc, boolean everydayOrGlobal) {
        System.out.println("Which Task would you like to delete?");
        LinkedHashMap<Integer,Integer> trueIndexOfTask = displayOverdueTasks(list, everydayOrGlobal);
        int actionsQuantity = trueIndexOfTask.size();
        System.out.println((actionsQuantity+1) + ") Delete all the overdue Tasks");
        System.out.println((actionsQuantity+2) + ") Back to previous page");
        int overdueEverydayTaskDeleteAction = readNumber(sc, actionsQuantity+2);

        if (overdueEverydayTaskDeleteAction == (actionsQuantity+1)) {
            System.out.println("Are you sure you want to delete ALL the overdue Tasks permanently?");
            System.out.println("1. Yes, I sure");
            System.out.println("2. Dont delete ALL my overdue Tasks");

            int sure = readNumber(sc, 2);
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

