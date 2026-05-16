import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MenuController {
    InputHandler inputHandler = new InputHandler();
    TaskService taskService = new TaskService();
    UISystem uiSystem = new UISystem();
    SubMenuController subMenuController = new SubMenuController();
    TaskStorageService storage = new TaskStorageService();
    Statistics statistics = new Statistics();

    public void handleMainMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks, String EVERYDAY_FILE_NAME, String GLOBAL_FILE_NAME) {
        while(true) {
            System.out.println("Choose an action: ");
            System.out.println("1. Add new Task");
            System.out.println("2. Edit the Task");
            System.out.println("3. Delete the Task");
            System.out.println("4. Display all the Tasks");
            System.out.println("5. Advanced actions with ready Tasks");
            System.out.println("6. Exit");
            int action = inputHandler.readNumber(6);
            if (action == 1) {
                handleAddMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Edit tasks action
            else if (action == 2) {
                handleEditMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Delete the tasks
            else if (action == 3) {
                handleDeleteMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Display all tasks
            else if (action == 4) {
                handleDisplayMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Advanced actions
            if (action == 5) {
                handleAdvancedMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Save data and exit program at all
            else {
                handleExitMenu(listOfEverydayTasks, listOfGlobalTasks, EVERYDAY_FILE_NAME, GLOBAL_FILE_NAME);
                break;
            }
        }
    }
    public void handleAddMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        // Adding new task
        while (true) {
            Task task = new Task();
            System.out.println("Choose which task you want to add: ");
            System.out.println("1. Everyday Task");
            System.out.println("2. Global Task");
            System.out.println("3. Back to previous page");
            int addAction = inputHandler.readNumber(3);
            // Add everyday task
            if (addAction == 1) {
                subMenuController.addActionMenu(task, listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Add global task
            else if (addAction == 2) {
                subMenuController.addActionMenu(task, listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit adding sector
            else {
                break;
            }
        }
    }
    public void handleEditMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Which Task would you like to edit: ");
            System.out.println("1. Edit an everyday Task");
            System.out.println("2. Edit an global Task");
            System.out.println("3. Back to previous page");

            int editAction = inputHandler.readNumber(3);

            // Edit everyday task
            if (editAction == 1) {
                subMenuController.editActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Edit global task
            else if (editAction == 2) {
                subMenuController.editActionMenu(listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit editing at all
            else {
                break;
            }
        }
    }
    public void handleDeleteMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Which Task would you like to delete: ");
            System.out.println("1. Delete the everydays Task");
            System.out.println("2. Delete the global Task");
            System.out.println("3. Back to previous page");
            int deleteAction = inputHandler.readNumber(3);
            // Delete everyday tasks
            if (deleteAction == 1) {
                subMenuController.deleteActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Delete global task
            else if (deleteAction == 2) {
                subMenuController.deleteActionMenu(listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit deleting sector at all
            else {
                break;
            }
        }
    }
    public void handleDisplayMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Choose which task you want to display: ");
            System.out.println("1. Everyday Tasks");
            System.out.println("2. Global Tasks");
            System.out.println("3. Back to previous page");

            int displayAction = inputHandler.readNumber(3);

            // Display everyday tasks
            if (displayAction == 1) {
                subMenuController.displayActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Display global tasks
            else if (displayAction == 2) {
                subMenuController.displayActionMenu(listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit display action at all
            else {
                break;
            }
        }
    }
    public void handleAdvancedMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Advanced actions: ");
            System.out.println("1. Sort Tasks");
            System.out.println("2. Search Tasks");
            System.out.println("3. Get Tasks Statistic");
            System.out.println("4. Overdue Tasks");
            System.out.println("5. Back to previous page");
            int advancedAction = inputHandler.readNumber(5);
            // Sort any tasks
            if (advancedAction == 1) {
                handleSortMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Search action
            else if (advancedAction == 2) {
                handleSearchMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Get task statistic
            else if (advancedAction == 3) {
                handleStatisticsMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Actions with overdue tasks
            else if (advancedAction == 4) {
                handleOverdueTasksMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Exit advanced actions
            else {
                break;
            }
        }
    }
    public void handleSortMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Which type of task would you like to sort: ");
            System.out.println("1. Sort everyday Tasks");
            System.out.println("2. Sort global Tasks");
            System.out.println("3. Back to previous page");

            int sortAction = inputHandler.readNumber(3);

            // Sort everyday tasks
            if (sortAction == 1) {
                subMenuController.sortActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Sort global tasks
            else if (sortAction == 2) {
                subMenuController.sortActionMenu(listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit sort actions at all
            else {
                break;
            }
        }
    }
    public void handleSearchMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Which Tasks would you like to search?");
            System.out.println("1. Search everyday tasks");
            System.out.println("2. Search global tasks");
            System.out.println("3. Back to previous page");

            int searchAction = inputHandler.readNumber(3);

            // Search everyday tasks
            if (searchAction == 1) {
                subMenuController.searchActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY);
            }
            // Search global tasks
            else if (searchAction == 2) {
                subMenuController.searchActionMenu(listOfGlobalTasks, TaskScale.GLOBAL);
            }
            // Exit search action
            else {
                break;
            }
        }
    }
    public void handleStatisticsMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("Which types of Tasks would you like to get statistics for?");
            System.out.println("1. Everyday tasks statistics");
            System.out.println("2. Global tasks statistics");
            System.out.println("3. General statistics");
            System.out.println("4. Back to previous page");

            int getStatisticsAction = inputHandler.readNumber(4);
            ArrayList<Task> allTasks = new ArrayList<>(listOfEverydayTasks);
            allTasks.addAll(listOfGlobalTasks);

            // Everyday tasks statistic
            if (getStatisticsAction == 1) {
                subMenuController.statisticsActionMenu(listOfEverydayTasks, TaskScale.EVERYDAY, statistics);
            }
            // Global statistics
            else if (getStatisticsAction == 2) {
                subMenuController.statisticsActionMenu(listOfGlobalTasks, TaskScale.GLOBAL, statistics);
            }
            // General statistics
            else if (getStatisticsAction == 3) {
                subMenuController.statisticsActionMenu(allTasks, TaskScale.GENERAL, statistics);
            }
            // Exit statistics display
            else {
                break;
            }
        }
    }
    public void handleOverdueTasksMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks) {
        while (true) {
            System.out.println("What do you want to do with overdue tasks?");
            System.out.println("1. Display all overdue tasks");
            System.out.println("2. Add more time to overdue tasks");
            System.out.println("3. Delete overdue tasks");
            System.out.println("4. Back to previous page");

            int overdueAction = inputHandler.readNumber(4);

            // Display overdue tasks
            if (overdueAction == 1) {
                subMenuController.displayOverdueTasksActionMenu(listOfEverydayTasks, listOfGlobalTasks);
            }
            // Add more time to deadline of overdue tasks
            else if (overdueAction == 2) {
                subMenuController.addTimeToOverdueTaskActionMenu();
            }
            // Delete overdue tasks
            else if (overdueAction == 3) {
                while (true) {
                    System.out.println("Which type of overdue Tasks would you like to delete?");
                    System.out.println("1. Overdue everyday tasks");
                    System.out.println("2. Overdue global tasks");
                    System.out.println("3. Back to previous page");

                    int deleteOverdueAction = inputHandler.readNumber(3);

                    // Delete everyday overdue tasks
                    if (deleteOverdueAction == 1) {
                        System.out.println("Which Task would you like to delete?");
                        List<Task> overdueTasks = taskService.getOverdueTasks(listOfEverydayTasks);
                        uiSystem.display(overdueTasks, TaskScale.EVERYDAY);
                        System.out.println();
                        LinkedHashMap<Integer,Integer> trueIndexOfTask = taskService.positionsOfOverdueTasks(listOfEverydayTasks);
                        int actionsQuantity = trueIndexOfTask.size();
                        System.out.println((actionsQuantity+1) + ") Delete all Everyday overdue Tasks");
                        System.out.println((actionsQuantity+2) + ") Back to previous page");
                        int overdueEverydayTaskDeleteAction = inputHandler.readNumber(actionsQuantity + 2);
                        if (overdueEverydayTaskDeleteAction == actionsQuantity+1) {
                            boolean sure = uiSystem.sure();
                            if (sure) {
                                taskService.deleteAllOverdueTasks(listOfEverydayTasks, actionsQuantity, trueIndexOfTask);
                            } else {
                                System.out.println("No changes were made");
                            }
                        } else if (overdueEverydayTaskDeleteAction != actionsQuantity+2) {
                            int index = trueIndexOfTask.get(overdueEverydayTaskDeleteAction-1);
                            String rememberName = listOfEverydayTasks.get(index).getName();
                            taskService.deleteSpecificOverdueTask(listOfEverydayTasks, index);
                            System.out.println("Successfully deleted overdue Task " + rememberName + "!");
                        }
                    }
                    // Delete global overdue tasks
                    else if (deleteOverdueAction == 2) {
                        System.out.println("Which Task would you like to delete?");
                        List<Task> overdueTasks = taskService.getOverdueTasks(listOfGlobalTasks);
                        uiSystem.display(overdueTasks, TaskScale.GLOBAL);
                        System.out.println();
                        LinkedHashMap<Integer,Integer> trueIndexOfTask = taskService.positionsOfOverdueTasks(listOfGlobalTasks);
                        int actionsQuantity = trueIndexOfTask.size();
                        System.out.println((actionsQuantity+1) + ") Delete all Global overdue Tasks");
                        System.out.println((actionsQuantity+2) + ") Back to previous page");
                        int overdueGlobalTaskDeleteAction = inputHandler.readNumber(actionsQuantity + 2);
                        if (overdueGlobalTaskDeleteAction == actionsQuantity+1) {
                            boolean sure = uiSystem.sure();
                            if (sure) {
                                taskService.deleteAllOverdueTasks(listOfGlobalTasks, actionsQuantity, trueIndexOfTask);
                            } else {
                                System.out.println("No changes were made");
                            }
                        } else if (overdueGlobalTaskDeleteAction != actionsQuantity+2) {
                            int index = trueIndexOfTask.get(overdueGlobalTaskDeleteAction-1);
                            String rememberName = listOfGlobalTasks.get(index).getName();
                            taskService.deleteSpecificOverdueTask(listOfGlobalTasks, index);
                            System.out.println("Successfully deleted overdue Task " + rememberName + "!");
                        }
                    }
                    // Exit deleting overdue tasks
                    else {
                        break;
                    }
                }
            }
            // Exit overdue tasks actions
            else {
                break;
            }
        }
    }
    public void handleExitMenu(List<Task> listOfEverydayTasks, List<Task> listOfGlobalTasks, String EVERYDAY_FILE_NAME, String GLOBAL_FILE_NAME) {
        storage.saveTasks(listOfEverydayTasks, EVERYDAY_FILE_NAME);
        storage.saveTasks(listOfGlobalTasks, GLOBAL_FILE_NAME);
        System.out.println("All data has been saved, Goodbye!");
        System.exit(0);
    }
}
