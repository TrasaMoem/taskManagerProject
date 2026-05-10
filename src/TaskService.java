import java.time.LocalDateTime;
import java.util.*;

public class TaskService {

    public List<Task> sortByName(List<Task> list, SortParameters sortFromAtoZorZtoA) {
        List<Task> sortingList = new ArrayList<>(list);
        // Sort from A to Z
        if (sortFromAtoZorZtoA == SortParameters.fromAtoZ) {
            sortingList.sort(Comparator.comparing(Task::getName));
        }
        // Sort from Z to A
        else {
            sortingList.sort(Comparator.comparing(Task::getName).reversed());
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
        if (sortFromSmallestToLargestOrLargestToSmallest == SortParameters.fromSmallestToLargest) {
            return sortingList;
        } else {
            return sortingList.reversed();
        }
    }

    public List<Task> sortByDeadline(List<Task> list, SortParameters sortFromSmallestToLargestOrLargestToSmallest) {
        List<Task> sortingList = new ArrayList<>(list);
        if (sortFromSmallestToLargestOrLargestToSmallest == SortParameters.fromSmallestToLargest) {
            // sort from Smallest to Largest deadline
            sortingList.sort(Comparator.comparing(Task::getDeadline));
        } else {
            // sort from Largest to smallest deadline
            sortingList.sort(Comparator.comparing(Task::getDeadline).reversed());
        }
        return sortingList;
    }

    public void editName(List<Task> list, int specificTaskNumber, String newName) {
        list.get(specificTaskNumber).setName(newName);
    }
    public void editDescription(List<Task> list, int specificTaskNumber, String newDescription) {
        list.get(specificTaskNumber).setDescription(newDescription);
    }
    public void editPriority(List<Task> list, int specificTaskNumber, int newPriority) {
        list.get(specificTaskNumber).setPriority(newPriority);
    }
    public void editDeadline(List<Task> list, int specificTaskNumber, LocalDateTime newDeadline) {
        list.get(specificTaskNumber).setDeadline(newDeadline);
    }

    public Task search(List<Task> list, String name) {
        // Search by first letter
        if (name.length() == 1) {
            for (Task task : list) {
                char letter = task.getName().charAt(0);
                if (String.valueOf(letter).equalsIgnoreCase(name)) {
                    return task;
                }
            }
        } else {
            for (Task task : list) {
                if (task.getName().equals(name)) {
                    return task;
                }
            }
        }
        return null;
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
        for (Task task : list) {
            LocalDateTime now = LocalDateTime.now();
            if (task.getDeadline().isBefore(now)) {
                overdueTasksHash.put(overdueCounter, list.indexOf(task));
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

