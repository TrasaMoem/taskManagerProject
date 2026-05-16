import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SubMenuController {
    TaskService taskService = new TaskService();
    InputHandler inputHandler = new InputHandler();
    UISystem uiSystem = new UISystem();

    public void addActionMenu(Task task, List<Task> list, TaskScale scale) {
        // Task name
        System.out.println("Enter the name of the Task: ");
        task.setName(inputHandler.readString());

        // Task description
        System.out.println("Enter the description of the Task: ");
        task.setDescription(inputHandler.readString());

        // Task priority
        System.out.println("Enter the priority of the Task (1 - low, 2 - medium, 3 - high): ");
        task.setPriority(inputHandler.readNumber(3));

        // Task deadline
        System.out.println("Enter the deadline (in hours) until the end: ");
        task.setDeadline(taskService.getTimeFromInt(inputHandler.readNumber(Integer.MAX_VALUE), scale));

        // Add new task to the array
        list.add(task);
        System.out.println("Successfully added an everyday task!");
    }
    public void editActionMenu(List<Task> list, TaskScale scale) {
        while (true) {
            System.out.println("Which Task would you like to edit: ");
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i+1) + ") " + list.get(i).getName());
            }
            System.out.println((list.size()+1) + ") Back to previous page");

            int editAction = inputHandler.readNumber(list.size() + 1);

            // Searching for correct task to edit
            for (int j = 0; j < list.size(); j++) {
                // Exact task to edit
                if (editAction == j+1) {
                    while (true) {
                        Task t = list.get(j);

                        System.out.print("The " + (j+1) + " " + scale + " Task: ");
                        uiSystem.displaySpecificTask(t,scale);
                        System.out.println();

                        System.out.println("Which parameter would you like to edit: ");
                        System.out.println("1. Edit the name of the Task");
                        System.out.println("2. Edit the description of the Task");
                        System.out.println("3. Edit the priority of the Task");
                        System.out.println("4. Edit the deadline of the Task");
                        System.out.println("5. Back to previous page");

                        int editSpecificParameter = inputHandler.readNumber(5);

                        // Edit name of the task
                        if (editSpecificParameter == 1) {
                            editNameAction(list, j);
                        }
                        // Edit description of the task
                        else if (editSpecificParameter == 2) {
                            editDescriptionAction(list, j);
                        }
                        // Edit priority of the task
                        else if (editSpecificParameter == 3) {
                            editPriorityAction(list, j);
                        }
                        // Edit deadline of the task
                        else if (editSpecificParameter == 4) {
                            editDeadlineAction(list, j, scale);
                        }
                        // Exit editing of the parameters
                        else {
                            break;
                        }
                    }
                }
            }
            // Exit editing everyday tasks
            if (editAction == list.size()+1) {
                break;
            }
        }
    }
    public void editNameAction(List<Task> list, int exactTaskIndex) {
        System.out.println("Enter new name of the Task (old: " + list.get(exactTaskIndex).getName() + "): ");
        String newName = inputHandler.readString();
        Task exactTask = list.get(exactTaskIndex);
        taskService.editName(exactTask, newName);
        System.out.println("New name " + list.get(exactTaskIndex).getName() + " has been edited successfully!");
    }
    public void editDescriptionAction(List<Task> list, int exactTaskIndex) {
        System.out.println("Enter new description of the Task (old: " + list.get(exactTaskIndex).getDescription() + "): ");
        String newDescription = inputHandler.readString();
        Task exactTask = list.get(exactTaskIndex);
        taskService.editDescription(exactTask, newDescription);
        System.out.println("New description " + list.get(exactTaskIndex).getDescription() + " has been edited successfully!");
    }
    public void editPriorityAction(List<Task> list, int exactTaskIndex) {
        System.out.println("Enter new priority of the Task (old: " + list.get(exactTaskIndex).getPriority() + "), (1 - low, 2 - medium, 3 - high): ");
        int newPriority = inputHandler.readNumber(3);
        Task exactTask = list.get(exactTaskIndex);
        taskService.editPriority(exactTask, newPriority);
        System.out.println("New priority " + list.get(exactTaskIndex).getPriority() + " has been edited successfully!");
    }
    public void editDeadlineAction(List<Task> list, int exactTaskIndex, TaskScale scale) {
        System.out.print("Enter new deadline of the Task (in " + scale.getValue() + "s) until the end of the Task (old: ");
        uiSystem.displaySpecificTaskDeadline(list.get(exactTaskIndex), scale);
        System.out.println("): ");
        LocalDateTime newDataTime = taskService.getTimeFromInt(inputHandler.readNumber(Integer.MAX_VALUE), scale);
        Task exactTask = list.get(exactTaskIndex);
        taskService.editDeadline(exactTask, newDataTime);
        uiSystem.displaySpecificTaskDeadline(list.get(exactTaskIndex), scale);
        System.out.println();
        uiSystem.delay();
    }
    public void deleteActionMenu(List<Task> list, TaskScale scale) {
        while (true) {
            System.out.println("Which everyday Task would you like to delete: ");
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i+1) + ") " + list.get(i).getName());
            }
            System.out.println((list.size()+1) + ") Delete all " + scale + " Tasks");
            System.out.println((list.size()+2) + ") Back to previous page");

            int deleteTaskAction = inputHandler.readNumber(list.size() + 2);

            // Searching the task to delete
            if (deleteTaskAction < list.size()) {
                for (int i = 0; i < list.size(); i++) {
                    // Exact task to delete
                    if (deleteTaskAction == (i+1)) {
                        list.remove(i);
                        System.out.println("Task " + list.get(i).getName() + " has been deleted successfully and permanently!");
                        break;
                    }
                }
            }
            // Delete all the tasks
            else if (deleteTaskAction == list.size()+1) {
                // Warning before deleting all everyday tasks
                boolean sure = uiSystem.sure();
                if (sure) {
                    taskService.deleteAllTasks(list);
                    System.out.println("All " + scale + " Tasks has been deleted successfully!");
                } else {
                    System.out.println("No changes were made!");
                }
            }
            // Exit tasks deleting actions
            else if (deleteTaskAction == list.size()+2) {
                break;
            }
        }
    }
    public void displayActionMenu(List<Task> list, TaskScale scale) {
        uiSystem.display(list, scale);
        uiSystem.delay();
    }
    public void sortActionMenu(List<Task> list, TaskScale scale) {
        while (true) {
            System.out.println("By what parameter do you want to sort " + scale + " Tasks: ");
            System.out.println("1. Sort by first letter of name of the Task");
            System.out.println("2. Sort by priority of the Task");
            System.out.println("3. Sort by deadline time of the Task");
            System.out.println("4. Back to previous page");

            int sortTaskAction = inputHandler.readNumber(4);

            // First letter sort
            if (sortTaskAction == 1) {
                sortByNameActionMenu(list, scale);
            }
            // Priority sort
            else if (sortTaskAction == 2) {
                sortByPriorityActionMenu(list, scale);
            }
            // Deadline sort
            else if (sortTaskAction == 3) {
                sortByDeadlineActionMenu(list, scale);
            }
            else {
                break;
            }
        }
    }
    public void sortByNameActionMenu(List<Task> list, TaskScale scale) {
        System.out.println("How would you like to sort " + scale + " Tasks names: ");
        System.out.println("1. From A to Z");
        System.out.println("2. From Z to A");
        System.out.println("3. Back to previous page");

        int sortTaskByNames = inputHandler.readNumber(3);

        // First letter sort from A to Z
        if (sortTaskByNames == 1) {
            sortByNameParameterActionMenu(list, scale, SortParameters.fromAtoZ);
        }
        // First letter sort from Z to A
        else if (sortTaskByNames == 2) {
            sortByNameParameterActionMenu(list, scale, SortParameters.fromZtoA);
        }
    }
    public void sortByNameParameterActionMenu(List<Task> list, TaskScale scale, SortParameters sortParameter) {
        list = taskService.sortByName(list, sortParameter);
        System.out.println("Successfully sorted " + scale + " Tasks " + sortParameter.getParameter() + "!");
        uiSystem.display(list, scale);
        System.out.println();
        uiSystem.delay();
    }
    public void sortByPriorityActionMenu(List<Task> list, TaskScale scale) {
        System.out.println("How would you like to sort " + scale + " Tasks priority: ");
        System.out.println("1. Sort from LOW to HIGH");
        System.out.println("2. Sort from HIGH to LOW");
        System.out.println("3. Back to previous page");

        int sortTaskLOWorHIGHAction = inputHandler.readNumber(3);

        // Priority sort from low to high
        if (sortTaskLOWorHIGHAction == 1) {
            sortByPriorityParameterActionMenu(list, scale, SortParameters.fromLOWtoHiGH);
        }
        // Priority sort from high to low
        else if (sortTaskLOWorHIGHAction == 2) {
            sortByPriorityParameterActionMenu(list, scale, SortParameters.fromHIGHToLOW);
        }
    }
    public void sortByPriorityParameterActionMenu(List<Task> list, TaskScale scale, SortParameters sortParameter) {
        list = taskService.sortByPriority(list, sortParameter);
        System.out.println("Successfully completed sort of " + scale + " Tasks by priority " + sortParameter.getParameter() + "!");
        uiSystem.display(list, scale);
        System.out.println();
        uiSystem.delay();
    }
    public void sortByDeadlineActionMenu(List<Task> list, TaskScale scale) {
        System.out.println("How would you like to sort " + scale + " Tasks deadline: ");
        System.out.println("1. Sort from Nearest to Farthest");
        System.out.println("2. Sort from Farthest to Nearest");
        System.out.println("3. Back to previous page");

        int sortTaskNearestOrFarthestAction = inputHandler.readNumber(3);

        // Sort deadline from nearest to farthest
        if (sortTaskNearestOrFarthestAction == 1) {
            sortByDeadlineParameterActionMenu(list, scale, SortParameters.fromNearestToFarthest);
        }
        // Sort deadline from farthest to nearest
        else if (sortTaskNearestOrFarthestAction == 2) {
            sortByDeadlineParameterActionMenu(list, scale, SortParameters.fromFarthestToNearest);
        }
    }
    public void sortByDeadlineParameterActionMenu(List<Task> list, TaskScale scale, SortParameters sortParameter) {
        list = taskService.sortByDeadline(list, sortParameter);
        System.out.println("Successfully completed sort of " + scale + " Tasks by deadline " + sortParameter.getParameter() + "!");
        uiSystem.display(list, scale);
        System.out.println();
        uiSystem.delay();
    }
    public void searchActionMenu(List<Task> list, TaskScale scale) {
        System.out.println("Write first letter of the Task or full name of the Task: ");
        String searchName = inputHandler.readString();
        ArrayList<Task> exactTasks;
        // Searching for exact Tasks
        if (searchName.length() == 1) {
            exactTasks = (ArrayList<Task>) taskService.searchByFirstLetter(list, searchName);
        } else {
            exactTasks = (ArrayList<Task>) taskService.searchByFullName(list, searchName);
        }
        // If the task is found or not found
        if (exactTasks != null) {
            uiSystem.display(exactTasks, scale);
            System.out.println();
            uiSystem.delay();
        } else {
            System.out.println("No such Tasks, please try another word or letter");
        }
    }
    public void statisticsActionMenu(List<Task> list, TaskScale scale, Statistics statistics) {
        uiSystem.statistics(list, scale, statistics);
        uiSystem.delay();
    }
    public void displayOverdueTasksActionMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        System.out.println("Overdue everyday tasks: ");
        List<Task> overdueTasks = taskService.getOverdueTasks(listOfEverydayTasks);
        uiSystem.display(overdueTasks, TaskScale.EVERYDAY);
        System.out.println();
        System.out.println("Overdue global tasks: ");
        overdueTasks = taskService.getOverdueTasks(listOfGlobalTasks);
        uiSystem.display(overdueTasks, TaskScale.GLOBAL);
        System.out.println();
        uiSystem.delay();
    }
    public void addTimeToOverdueTaskActionMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Which type of overdue Tasks would you like to add more time to?");
            System.out.println("1. Everyday tasks");
            System.out.println("2. Global tasks");
            System.out.println("3. Back to previous page");

            int overdueTimeAddAction = inputHandler.readNumber(3);

            // Add time to everyday overdue tasks
            if (overdueTimeAddAction == 1) {
                addTimeToExactOverdueTaskActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Add time to global overdue tasks
            else if (overdueTimeAddAction == 2) {
                addTimeToExactOverdueTaskActionMenu(listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit time adding
            else {
                break;
            }
        }
    }
    public void addTimeToExactOverdueTaskActionMenu(List<Task> list, TaskScale scale) {
        System.out.println("Which task would you like to add more time to: ");
        List<Task> overdueTasks = taskService.getOverdueTasks(list);
        uiSystem.display(overdueTasks, scale);
        System.out.println();
        LinkedHashMap<Integer,Integer> trueIndexOfTasks = taskService.positionsOfOverdueTasks(list);
        int actionsQuantity = trueIndexOfTasks.size();
        System.out.println((actionsQuantity+1) + ") Back to previous page");
        int overdueTaskAddTimeAction = inputHandler.readNumber(actionsQuantity + 1);
        if (!(overdueTaskAddTimeAction == (actionsQuantity+1))) {
            System.out.println("How much " + scale.getValue() + "s would you like to add?");
            int timeToAdd = inputHandler.readNumber(Integer.MAX_VALUE);
            Task correctTask = list.get(trueIndexOfTasks.get(overdueTaskAddTimeAction-1));
            taskService.addToOverdueTaskHours(correctTask, timeToAdd);
            System.out.println("Successfully added more time to overdue " + scale + " task");
        }
    }
    public void deleteOverdueTaskActionMenu(List<Task> list, TaskScale scale) {
        System.out.println("Which Task would you like to delete?");
        List<Task> overdueTasks = taskService.getOverdueTasks(list);
        uiSystem.display(overdueTasks, scale);
        System.out.println();
        LinkedHashMap<Integer,Integer> trueIndexOfTask = taskService.positionsOfOverdueTasks(list);
        int actionsQuantity = trueIndexOfTask.size();
        System.out.println((actionsQuantity+1) + ") Delete all Everyday overdue Tasks");
        System.out.println((actionsQuantity+2) + ") Back to previous page");
        int overdueTaskDeleteAction = inputHandler.readNumber(actionsQuantity + 2);
        if (overdueTaskDeleteAction == actionsQuantity+1) {
            boolean sure = uiSystem.sure();
            if (sure) {
                taskService.deleteAllOverdueTasks(list, actionsQuantity, trueIndexOfTask);
            } else {
                System.out.println("No changes were made");
            }
        } else if (overdueTaskDeleteAction != actionsQuantity+2) {
            int index = trueIndexOfTask.get(overdueTaskDeleteAction -1);
            String rememberName = list.get(index).getName();
            taskService.deleteSpecificOverdueTask(list, index);
            System.out.println("Successfully deleted overdue Task " + rememberName + "!");
        }
    }
}
