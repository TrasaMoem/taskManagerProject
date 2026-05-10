import java.time.LocalDateTime;
import java.util.*;

public class TaskService {

    public List<Task> sortByName(List<Task> list, SortParameters sortFromAtoZorZtoA) {
        List<Task> sortingList = new ArrayList<>(list);
        // Sort from A to Z
        sortingList.sort(Comparator.comparing(Task::getName));
        if (sortFromAtoZorZtoA != SortParameters.fromAtoZ) {
            // Sort from Z to A
            Collections.reverse(sortingList);
        }
        return sortingList;
    }

    public List<Task> sortByPriority(List<Task> list, SortParameters sortFromSmallestToLargestOrLargestToSmallest) {
        List<Task> sortingList = new ArrayList<>(list);
        // Sort by priority
        while (true) {
            boolean flag = false;
            for (int i = 1; i < sortingList.size(); i++) {
                if (sortingList.get(i).getPriority() < sortingList.get(i - 1).getPriority()) {
                    Task temp;
                    temp = sortingList.get(i);
                    sortingList.set(i, sortingList.get(i - 1));
                    sortingList.set(i - 1, temp);
                }
            }
            for (int j = 1; j < sortingList.size(); j++) {
                if (sortingList.get(j).getPriority() < sortingList.get(j - 1).getPriority()) {
                    flag = true;
                    break;
                }
            }
            if (flag) continue;

            break;
        }
        if (sortFromSmallestToLargestOrLargestToSmallest != SortParameters.fromSmallestToLargest) {
            Collections.reverse(sortingList);
        }
        return sortingList;
    }

    public List<Task> sortByDeadline(List<Task> list, SortParameters sortFromSmallestToLargestOrLargestToSmallest) {
        List<Task> sortingList = new ArrayList<>(list);
        // sort from Smallest to Largest deadline
        sortingList.sort(Comparator.comparing(Task::getDeadline));
        if (sortFromSmallestToLargestOrLargestToSmallest != SortParameters.fromSmallestToLargest) {
            // sort from Largest to smallest deadline
            Collections.reverse(sortingList);
        }
        return sortingList;
    }

    public void editName(Task exactTask, String newName) {
        exactTask.setName(newName);
    }
    public void editDescription(Task exactTask, String newDescription) {
        exactTask.setDescription(newDescription);
    }
    public void editPriority(Task exactTask, int newPriority) {
        exactTask.setPriority(newPriority);
    }
    public void editDeadline(Task exactTask, LocalDateTime newDeadline) {
        exactTask.setDeadline(newDeadline);
    }
    // Searching by first letter of the word
    public Task searchByFirstLetter(List<Task> list, String name) {
        for (Task task : list) {
            char letter = task.getName().charAt(0);
            if (String.valueOf(letter).equalsIgnoreCase(name)) {
                return task;
            }
        } return null;
    }
    // Searching by full name of the word
    public Task searchByFullName(List<Task> list, String name) {
        for (Task task : list) {
            if (task.getName().equals(name)) {
                return task;
            }
        } return null;
    }

    public void addToOverdueTaskHours(Task correctTask, int hoursToAdd) {
        correctTask.setDeadline(correctTask.getDeadline().plusHours(hoursToAdd));
    }
    public void addToOverdueTaskDays(Task correctTask, int daysToAdd) {
        correctTask.setDeadline(correctTask.getDeadline().plusDays(daysToAdd));

    }

    public LinkedHashMap<Integer,Integer> positionsOfOverdueTasks(List<Task> list) {
        int overdueCounter = 0;
        LinkedHashMap<Integer, Integer> overdueTasksHash = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime now = LocalDateTime.now();
            if (list.get(i).getDeadline().isBefore(now)) {
                overdueTasksHash.put(overdueCounter, i);
                overdueCounter++;
            }
        }
        return overdueTasksHash;
    }

    public void deleteAllOverdueTasks(List<Task> list, int actionsQuantity, Map<Integer,Integer> trueIndexOfTask) {
        for (int i = actionsQuantity; i > 0; i--) {
            int index = trueIndexOfTask.get(i-1);
            list.remove(index);
        }
    }

    public void deleteSpecificOverdueTask(List<Task> list, int index) {
        list.remove(index);
    }

}

