import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        final String EVERYDAY_FILE_NAME = "everydayTasks.json";
        final String GLOBAL_FILE_NAME = "globalTasks.json";
        InputHandler inputHandler = new InputHandler();
        TaskService taskService = new TaskService();
        TaskStorageService storage = new TaskStorageService();
        UISystem uiSystem = new UISystem();
        List<Task> listOfEverydayTasks = storage.loadTasks(EVERYDAY_FILE_NAME);
        List<Task> listOfGlobalTasks = storage.loadTasks(GLOBAL_FILE_NAME);
        // If one of lists is equals null
        if (listOfEverydayTasks == null) {
            listOfEverydayTasks = new ArrayList<>();
        }
        if (listOfGlobalTasks == null) {
            listOfGlobalTasks = new ArrayList<>();
        }

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
                        // Everydays task name
                        System.out.println("Enter the name of the Task: ");
                        task.setName(inputHandler.readString());

                        // Everydays task description
                        System.out.println("Enter the description of the Task: ");
                        task.setDescription(inputHandler.readString());

                        // Everydays task priority
                        System.out.println("Enter the priority of the Task (1 - low, 2 - medium, 3 - high, or more if you want): ");
                        task.setPriority(inputHandler.readNumber(Integer.MAX_VALUE));

                        // Everydays task deadlines
                        System.out.println("Enter the deadline (in hours) until the end: ");
                        task.setDeadline(inputHandler.readDateTime(TaskScale.EVERYDAY));

                        // Add new everyday task to the array
                        listOfEverydayTasks.add(task);
                        System.out.println("Successfully added an everyday task!");
                    }
                    // Add global task
                    else if (addAction == 2) {

                        // Global task name
                        System.out.println("Enter the name of the Task: ");
                        task.setName(inputHandler.readString());

                        // Global task description
                        System.out.println("Enter the description of the Task: ");
                        task.setDescription(inputHandler.readString());

                        // Global task priority
                        System.out.println("Enter the priority of the Task (1 - low, 2 - medium, 3 - high, or more if you want): ");
                        task.setPriority(inputHandler.readNumber(Integer.MAX_VALUE));

                        // Global task deadlines
                        System.out.println("Enter the deadline (in days) until the end of the Task: ");
                        task.setDeadline(inputHandler.readDateTime(TaskScale.GLOBAL));

                        // Add new global task to array
                        listOfGlobalTasks.add(task);
                        System.out.println("Successfully added global task!");
                    }
                    // Exit adding sector
                    else if (addAction == 3) {
                        break;
                    }
                }
            }
            // Edit tasks action
            else if (action == 2) {
                while (true) {
                    System.out.println("Which Task would you like to edit: ");
                    System.out.println("1. Edit an everyday Task");
                    System.out.println("2. Edit an global Task");
                    System.out.println("3. Back to previous page");

                    int editAction = inputHandler.readNumber(3);

                    // Edit everyday task
                    if (editAction == 1) {
                        while (true) {
                            System.out.println("Which Task would you like to edit: ");
                            for (int i = 0; i < listOfEverydayTasks.size(); i++) {
                                System.out.println((i+1) + ") " + listOfEverydayTasks.get(i).getName());
                            }
                            System.out.println((listOfEverydayTasks.size()+1) + ") Back to previous page");

                            int editEverydayAction = inputHandler.readNumber(listOfEverydayTasks.size() + 1);

                            // Searching for correct everyday task to edit
                            for (int j = 0; j < listOfEverydayTasks.size(); j++) {
                                // Exact everyday task to edit
                                if (editEverydayAction == j+1) {
                                    while (true) {
                                        Task t = listOfEverydayTasks.get(j);

                                        System.out.print("The " + (j+1) + " everydays Task: ");
                                        uiSystem.displaySpecificTask(t,TaskScale.EVERYDAY);

                                        System.out.println("Which parameter would you like to edit: ");
                                        System.out.println("1. Edit the name of the Task");
                                        System.out.println("2. Edit the description of the Task");
                                        System.out.println("3. Edit the priority of the Task");
                                        System.out.println("4. Edit the deadline of the Task");
                                        System.out.println("5. Back to previous page");

                                        int editSpecificParameter = inputHandler.readNumber(5);

                                        // Edit name of everyday task
                                        if (editSpecificParameter == 1) {
                                            System.out.println("Enter new name of the Task (old: " + listOfEverydayTasks.get(j).getName() + "): ");
                                            String newName = inputHandler.readString();
                                            Task exactTask = listOfEverydayTasks.get(j);
                                            taskService.editName(exactTask, newName);
                                            System.out.println("New name " + listOfEverydayTasks.get(j).getName() + " has been edited successfully!");
                                        }
                                        // Edit description of everyday task
                                        else if (editSpecificParameter == 2) {
                                            System.out.println("Enter new description of the Task (old: " + listOfEverydayTasks.get(j).getDescription() + "): ");
                                            String newDescription = inputHandler.readString();
                                            Task exactTask = listOfEverydayTasks.get(j);
                                            taskService.editDescription(exactTask, newDescription);
                                            System.out.println("New description " + listOfEverydayTasks.get(j).getDescription() + " has been edited successfully!");
                                        }
                                        // Edit priority of everyday task
                                        else if (editSpecificParameter == 3) {
                                            System.out.println("Enter new priority of the Task (old: " + listOfEverydayTasks.get(j).getPriority() + "), (1 - low, 2 - medium, 3 - high, or more if you want): ");
                                            int newPriority = inputHandler.readNumber(Integer.MAX_VALUE);
                                            Task exactTask = listOfEverydayTasks.get(j);
                                            taskService.editPriority(exactTask, newPriority);
                                            System.out.println("New priority " + listOfEverydayTasks.get(j).getPriority() + " has been edited successfully!");
                                        }
                                        // Edit deadline of everyday task
                                        else if (editSpecificParameter == 4) {
                                            System.out.print("Enter new deadline of the Task (in hours) until the end of the Task (old: ");
                                            uiSystem.displaySpecificTaskDeadline(listOfEverydayTasks.get(j), TaskScale.EVERYDAY);
                                            System.out.println("): ");
                                            LocalDateTime newDataTime = inputHandler.readDateTime(TaskScale.EVERYDAY);
                                            Task exactTask = listOfEverydayTasks.get(j);
                                            taskService.editDeadline(exactTask, newDataTime);
                                            uiSystem.displaySpecificTaskDeadline(listOfEverydayTasks.get(j), TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // Exit editing of everyday parameters
                                        else if (editSpecificParameter == 5) {
                                            break;
                                        }
                                    }
                                }
                            }
                            // Exit editing everyday tasks
                            if (editEverydayAction == listOfEverydayTasks.size()+1) {
                                break;
                            }
                        }
                    }
                    // Edit global task
                    else if (editAction == 2) {
                        while (true) {
                            System.out.println("Which Task would you like to edit: ");
                            for (int i = 0; i < listOfGlobalTasks.size(); i++) {
                                System.out.println((i+1) + ") " + listOfGlobalTasks.get(i).getName());
                            }
                            System.out.println((listOfGlobalTasks.size()+1) + ") Back to previous page");

                            int editGlobalAction = inputHandler.readNumber(listOfGlobalTasks.size() + 1);

                            // Searching for correct global task to edit
                            for (int j = 0; j < listOfGlobalTasks.size(); j++) {
                                // Exact global task to edit
                                if (editGlobalAction == j+1) {
                                    while (true) {
                                        Task t = listOfGlobalTasks.get(j);

                                        System.out.print("The " + (j+1) + " global Task: ");
                                        uiSystem.displaySpecificTask(t,TaskScale.GLOBAL);

                                        System.out.println("Which parameter would you like to edit: ");
                                        System.out.println("1. Edit the name of the Task");
                                        System.out.println("2. Edit the description of the Task");
                                        System.out.println("3. Edit the priority of the Task");
                                        System.out.println("4. Edit the deadline of the Task");
                                        System.out.println("5. Back to previous page");

                                        int editSpecificParameter = inputHandler.readNumber(5);

                                        // Edit name of the global task
                                        if (editSpecificParameter == 1) {
                                            System.out.println("Enter new name of the Task (old: " + listOfGlobalTasks.get(j).getName() + "): ");
                                            String newName = inputHandler.readString();
                                            Task exactTask = listOfGlobalTasks.get(j);
                                            taskService.editName(exactTask, newName);
                                            System.out.println("New name " + listOfGlobalTasks.get(j).getName() + " has been edited successfully!");
                                        }
                                        // Edit description of global task
                                        else if (editSpecificParameter == 2) {
                                            System.out.println("Enter new description of the Task (old: " + listOfGlobalTasks.get(j).getDescription() + "): ");
                                            String newDescription = inputHandler.readString();
                                            Task exactTask = listOfGlobalTasks.get(j);
                                            taskService.editDescription(exactTask, newDescription);
                                            System.out.println("New description " + listOfGlobalTasks.get(j).getDescription() + " has been edited successfully!");
                                        }
                                        // Edit priority of global task
                                        else if (editSpecificParameter == 3) {
                                            System.out.println("Enter new priority of the Task (old: " + listOfGlobalTasks.get(j).getPriority() + "), (1 - low, 2 - medium, 3 - high, or more if you want): ");
                                            int newPriority = inputHandler.readNumber(Integer.MAX_VALUE);
                                            Task exactTask = listOfGlobalTasks.get(j);
                                            taskService.editPriority(exactTask, newPriority);
                                            System.out.println("New priority " + listOfGlobalTasks.get(j).getPriority() + " has been edited successfully!");
                                        }
                                        // Edit deadline of global task
                                        else if (editSpecificParameter == 4) {
                                            System.out.print("Enter new deadline of the Task (in days) until the end of the Task (old: ");
                                            uiSystem.displaySpecificTaskDeadline(listOfGlobalTasks.get(j), TaskScale.GLOBAL);
                                            System.out.println("): ");
                                            LocalDateTime newDeadline = inputHandler.readDateTime(TaskScale.GLOBAL);
                                            Task exactTask = listOfGlobalTasks.get(j);
                                            taskService.editDeadline(exactTask, newDeadline);
                                            uiSystem.displaySpecificTaskDeadline(listOfGlobalTasks.get(j), TaskScale.GLOBAL);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // Exit editing global parameters
                                        else if (editSpecificParameter == 5) {
                                            break;
                                        }

                                    }
                                }
                            }
                            // Exit editing global task
                            if (editGlobalAction == listOfGlobalTasks.size()+1) {
                                break;
                            }
                        }
                    }
                    // Exit editing at all
                    else if (editAction == 3) {
                        break;
                    }
                }
            }
            // Delete the tasks
            else if (action == 3) {
                while (true) {
                    System.out.println("Which Task would you like to delete: ");
                    System.out.println("1. Delete the everydays Task");
                    System.out.println("2. Delete the global Task");
                    System.out.println("3. Back to previous page");
                    int deleteAction = inputHandler.readNumber(3);
                    // Delete everyday tasks
                    if (deleteAction == 1) {
                        while (true) {
                            System.out.println("Which everyday Task would you like to delete: ");
                            for (int i = 0; i < listOfEverydayTasks.size(); i++) {
                                System.out.println((i+1) + ") " + listOfEverydayTasks.get(i).getName());
                            }
                            System.out.println((listOfEverydayTasks.size()+1) + ") Delete all everyday Tasks");
                            System.out.println((listOfEverydayTasks.size()+2) + ") Back to previous page");

                            int deleteEverydayTaskAction = inputHandler.readNumber(listOfEverydayTasks.size() + 2);

                            // Searching everyday task to delete
                            if (deleteEverydayTaskAction > -1 && deleteEverydayTaskAction < listOfEverydayTasks.size()) {
                                for (int i = 0; i < listOfEverydayTasks.size(); i++) {
                                    // Exact everyday task to delete
                                    if (deleteEverydayTaskAction == (i+1)) {
                                        System.out.println("Task " + listOfEverydayTasks.get(i).getName() + " has been deleted successfully and permanently!");
                                        listOfEverydayTasks.remove(i);
                                        break;
                                    }
                                }
                            }
                            // Delete all the everyday tasks
                            else if (deleteEverydayTaskAction == listOfEverydayTasks.size()+1) {
                                    System.out.println("Are you sure you want to delete ALL the everyday Tasks permanently?");
                                    System.out.println("1. Yes, I sure");
                                    System.out.println("2. Dont delete ALL my everyday Tasks");

                                    int sure = inputHandler.readNumber(2);
                                    // Warning before deleting all everyday tasks
                                    if (sure == 1) {
                                        listOfEverydayTasks.clear();
                                        System.out.println("All everyday Tasks have been deleted successfully!");
                                    } else {
                                        System.out.println("No changes were made!");
                                    }
                            }
                            // Exit everyday tasks deleting sector
                            else if (deleteEverydayTaskAction == listOfEverydayTasks.size()+2) {
                                break;
                            }
                        }
                    }
                    // Delete global task
                    else if (deleteAction == 2) {
                        while (true) {
                            System.out.println("Which global Task would you like to delete: ");
                            for (int i = 0; i < listOfGlobalTasks.size(); i++) {
                                System.out.println((i+1) + ") " + listOfGlobalTasks.get(i).getName());
                            }
                            System.out.println((listOfGlobalTasks.size()+1) + ") Delete all global Tasks");
                            System.out.println((listOfGlobalTasks.size()+2) + ") Back to previous page");

                            int deleteGlobalTaskAction = inputHandler.readNumber(listOfGlobalTasks.size() + 2);

                            // Searching global task to delete
                            if (deleteGlobalTaskAction > -1 && deleteGlobalTaskAction < listOfGlobalTasks.size()) {
                                for (int i = 0; i < listOfGlobalTasks.size(); i++) {
                                    // Exact global task to delete
                                    if (deleteGlobalTaskAction == (i+1)) {
                                        System.out.println("Task " + listOfGlobalTasks.get(i).getName() + " has been deleted successfully and permanently!");
                                        listOfGlobalTasks.remove(i);
                                        break;
                                    }
                                }
                            }
                            // Delete all of global tasks
                            else if (deleteGlobalTaskAction == listOfGlobalTasks.size()+1) {
                                    System.out.println("Are you sure you want to delete ALL the global Tasks permanently?");
                                    System.out.println("1. Yes, I sure");
                                    System.out.println("2. Dont delete ALL my global Tasks");

                                    int sure = inputHandler.readNumber(2);
                                    // Warning before deleting all the global tasks
                                    if (sure==1) {
                                        listOfGlobalTasks.clear();
                                        System.out.println("All global Tasks have been deleted successfully!");
                                    } else {
                                        System.out.println("No changes were made!");
                                    }
                            }
                            // Exit deleting global tasks
                            else if (deleteGlobalTaskAction == listOfEverydayTasks.size()+2) {
                                break;
                            }
                        }
                    }
                    // Exit deleting sector at all
                    else if (deleteAction == 3) {
                        break;
                    }
                }
            }
            // Display all tasks
            else if (action == 4) {
                while (true) {
                    System.out.println("Choose which task you want to display: ");
                    System.out.println("1. Everyday Tasks");
                    System.out.println("2. Global Tasks");
                    System.out.println("3. Back to previous page");

                    int displayAction = inputHandler.readNumber(3);

                    // Display everyday tasks
                    if (displayAction == 1) {
                        uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                        uiSystem.delay();
                    }
                    // Display global tasks
                    else if (displayAction == 2) {
                        uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                        uiSystem.delay();
                    }
                    // Exit display action at all
                    if (displayAction == 3) {
                        break;
                    }
                }
            }
            // Advanced actions
            if (action == 5) {
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
                        while (true) {
                            System.out.println("Which type of task would you like to sort: ");
                            System.out.println("1. Sort everyday Tasks");
                            System.out.println("2. Sort global Tasks");
                            System.out.println("3. Back to previous page");

                            int sortAction = inputHandler.readNumber(3);

                            // Sort everyday tasks
                            if (sortAction == 1) {
                                while (true) {
                                    System.out.println("By what parameter do you want to sort everyday Tasks: ");
                                    System.out.println("1. Sort by first letter of name of the Task");
                                    System.out.println("2. Sort by priority of the Task");
                                    System.out.println("3. Sort by deadline time of the Task");
                                    System.out.println("4. Back to previous page");

                                    int sortEverydayTaskAction = inputHandler.readNumber(4);

                                    // First letter sort
                                    if (sortEverydayTaskAction == 1) {
                                        System.out.println("How would you like to sort everyday Tasks names: ");
                                        System.out.println("1. From A to Z");
                                        System.out.println("2. From Z to A");
                                        System.out.println("3. Back to previous page");

                                        int sortEverydayTaskByNames = inputHandler.readNumber(3);

                                        // First letter sort from A to Z
                                        if (sortEverydayTaskByNames == 1) {
                                            listOfEverydayTasks = taskService.sortByName(listOfEverydayTasks, SortParameters.fromAtoZ);
                                            System.out.println("Successfully sorted Everyday Tasks from A to Z!");
                                            uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // First letter sort from Z to A
                                        else if (sortEverydayTaskByNames == 2) {
                                            listOfEverydayTasks = taskService.sortByName(listOfEverydayTasks, SortParameters.fromZtoA);
                                            System.out.println("Successfully sorted Everyday Tasks from Z to A!");
                                            uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                    }
                                    // Priority sort
                                    else if (sortEverydayTaskAction == 2) {
                                        System.out.println("How would you like to sort everyday Tasks priority: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortEverydayTaskSmallestOrLargestAction = inputHandler.readNumber(3);

                                        // Priority sort from smallest to largest
                                        if (sortEverydayTaskSmallestOrLargestAction == 1) {
                                            listOfEverydayTasks = taskService.sortByPriority(listOfEverydayTasks, SortParameters.fromSmallestToLargest);
                                            System.out.println("Successfully completed sort of everyday Tasks by priority from Smallest to Largest");
                                            uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // Priority sort from largest to smallest
                                        else if (sortEverydayTaskSmallestOrLargestAction == 2) {
                                            listOfEverydayTasks = taskService.sortByPriority(listOfEverydayTasks, SortParameters.fromLargestToSmallest);
                                            System.out.println("Successfully completed sort of everyday Tasks by priority from Largest to Smallest");
                                            uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                    }
                                    // Deadline sort
                                    else if (sortEverydayTaskAction == 3) {
                                        System.out.println("How would you like to sort everyday Tasks deadline: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortEverydayTaskSmallestOrLargestAction = inputHandler.readNumber(3);

                                        // Sort deadline from smallest to largest
                                        if (sortEverydayTaskSmallestOrLargestAction == 1) {
                                            listOfEverydayTasks = taskService.sortByDeadline(listOfEverydayTasks, SortParameters.fromSmallestToLargest);
                                            System.out.println("Successfully completed sort of Everyday Tasks by deadline from Smallest to Largest");
                                            uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // Sort deadline from largest to smallest
                                        else if (sortEverydayTaskSmallestOrLargestAction == 2) {
                                            listOfEverydayTasks = taskService.sortByDeadline(listOfEverydayTasks, SortParameters.fromLargestToSmallest);
                                            System.out.println("Successfully completed sort of Everyday Tasks by deadline from Largest to Smallest");
                                            uiSystem.display(listOfEverydayTasks, TaskScale.EVERYDAY);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                    }
                                    else if (sortEverydayTaskAction == 4) {
                                        break;
                                    }
                                }
                            }
                            // Sort global tasks
                            else if (sortAction == 2) {
                                while (true) {
                                    System.out.println("By what parameter do you want to sort global Tasks: ");
                                    System.out.println("1. Sort by first letter of name of the Task (only english letters)");
                                    System.out.println("2. Sort by priority of the Task");
                                    System.out.println("3. Sort by deadline time of the Task");
                                    System.out.println("4. Back to previous page");

                                    int sortGlobalTaskAction = inputHandler.readNumber(4);

                                    // Sort name by first letter
                                    if (sortGlobalTaskAction == 1) {
                                        while (true) {
                                            System.out.println("How would you like to sort global Tasks names: ");
                                            System.out.println("1. From A to Z");
                                            System.out.println("2. From Z to A");
                                            System.out.println("3. Back to previous page");

                                            int sortGlobalTaskByNames = inputHandler.readNumber(3);

                                            // Sort global tasks from A to Z
                                            if (sortGlobalTaskByNames == 1) {
                                                listOfGlobalTasks = taskService.sortByName(listOfGlobalTasks, SortParameters.fromAtoZ);
                                                System.out.println("Successfully sorted Global Tasks from A to Z!");
                                                uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                                                System.out.println();
                                                uiSystem.delay();
                                            }
                                            // Sort global tasks from Z to A
                                            else if (sortGlobalTaskByNames == 2) {
                                                listOfGlobalTasks = taskService.sortByName(listOfGlobalTasks, SortParameters.fromZtoA);
                                                System.out.println("Successfully sorted Global Tasks from Z to A!");
                                                uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                                                System.out.println();
                                                uiSystem.delay();
                                            }
                                            // Exit global task sorting by names
                                            else if (sortGlobalTaskByNames == 3) {
                                                break;
                                            }
                                        }
                                    }
                                    // Sort global tasks priority
                                    else if (sortGlobalTaskAction == 2) {
                                        System.out.println("How would you like to sort global Tasks priority: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortGlobalTaskSmallestOrLargestAction = inputHandler.readNumber(3);

                                        // Sort global tasks priority from smallest to largest
                                        if (sortGlobalTaskSmallestOrLargestAction == 1) {
                                            listOfGlobalTasks = taskService.sortByPriority(listOfGlobalTasks, SortParameters.fromSmallestToLargest);
                                            System.out.println("Successfully completed sort of Global Tasks by priority from Smallest to Largest");
                                            uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // Sort global tasks priority from largest to smallest
                                        else if (sortGlobalTaskSmallestOrLargestAction == 2) {
                                            listOfGlobalTasks = taskService.sortByPriority(listOfGlobalTasks, SortParameters.fromLargestToSmallest);
                                            System.out.println("Successfully completed sort of Global Tasks by priority from Largest to Smallest");
                                            uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                    }
                                    // Sort global tasks deadline
                                    else if (sortGlobalTaskAction == 3) {
                                        System.out.println("How would you like to sort global Tasks deadline: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortGlobalTaskSmallestOrLargestAction = inputHandler.readNumber(3);

                                        // Sort global tasks by deadline from smallest to largest
                                        if (sortGlobalTaskSmallestOrLargestAction == 1) {
                                            listOfGlobalTasks = taskService.sortByDeadline(listOfGlobalTasks, SortParameters.fromSmallestToLargest);
                                            System.out.println("Successfully completed sort of Global Tasks by deadline from Smallest to Largest");
                                            uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                        // Sort global tasks by deadline from largest to smallest
                                        else if (sortGlobalTaskSmallestOrLargestAction == 2) {
                                            listOfGlobalTasks = taskService.sortByDeadline(listOfGlobalTasks, SortParameters.fromLargestToSmallest);
                                            System.out.println("Successfully completed sort of Global Tasks by deadline from Largest to Smallest");
                                            uiSystem.display(listOfGlobalTasks, TaskScale.GLOBAL);
                                            System.out.println();
                                            uiSystem.delay();
                                        }
                                    }
                                    // Exit sorting global tasks by deadline
                                    else if (sortGlobalTaskAction == 4) {
                                        break;
                                    }
                                }
                            }
                            // Exit sort actions at all
                            else if (sortAction == 3) {
                                break;
                            }
                        }
                    }
                    // Search action
                    else if (advancedAction == 2) {
                        while (true) {
                            System.out.println("Which Tasks would you like to search?");
                            System.out.println("1. Everyday tasks");
                            System.out.println("2. Global tasks");
                            System.out.println("3. Back to previous page");

                            int searchAction = inputHandler.readNumber(3);

                            // Search everyday tasks
                            if (searchAction == 1) {
                                System.out.println("Write first letter of the Task or full name of the Task: ");
                                String searchName = inputHandler.readString();
                                Task exactTask;
                                // Searching for exact everyday Task
                                if (searchName.length() == 1) {
                                    exactTask = taskService.searchByFirstLetter(listOfEverydayTasks, searchName);
                                } else {
                                    exactTask = taskService.searchByFullName(listOfEverydayTasks, searchName);
                                }
                                // If everyday task is found or not found
                                if (exactTask != null) {
                                    uiSystem.displaySpecificTask(exactTask, TaskScale.EVERYDAY);
                                    System.out.println();
                                    uiSystem.delay();
                                } else {
                                    System.out.println("No such Task, please try another word or letter");
                                }
                            }
                            // Search global tasks
                            else if (searchAction == 2) {
                                System.out.println("Write first letter of the Task or full name of the Task: ");
                                String searchName = inputHandler.readString();
                                Task exactTask;
                                // Searching for exact global Task
                                if (searchName.length() == 1) {
                                    exactTask = taskService.searchByFirstLetter(listOfGlobalTasks, searchName);
                                } else {
                                    exactTask = taskService.searchByFullName(listOfGlobalTasks, searchName);
                                }
                                // If global task is found or not found
                                if (exactTask != null) {
                                    uiSystem.displaySpecificTask(exactTask, TaskScale.GLOBAL);
                                    System.out.println();
                                    uiSystem.delay();
                                } else {
                                    System.out.println("No such Task, please try another word or letter");
                                }
                            }
                            // Exit search action
                            else if (searchAction == 3) {
                                break;
                            }
                        }
                    }
                    // Get task statistic
                    else if (advancedAction == 3) {
                        while (true) {
                            System.out.println("Which types of Tasks would you like to get statistics for?");
                            System.out.println("1. Everyday tasks statistics");
                            System.out.println("2. Global tasks statistics");
                            System.out.println("3. General statistics");
                            System.out.println("4. Back to previous page");

                            int getStatisticsAction = inputHandler.readNumber(4);

                            // Everyday tasks statistic
                            if (getStatisticsAction == 1) {
                                uiSystem.statistics(listOfEverydayTasks, TaskScale.EVERYDAY);
                                uiSystem.delay();
                            }
                            // Global statistics
                            else if (getStatisticsAction == 2) {
                                uiSystem.statistics(listOfGlobalTasks, TaskScale.GLOBAL);
                                uiSystem.delay();
                            }
                            // General statistics
                            else if (getStatisticsAction == 3) {
                                ArrayList<Task> allTasks = new ArrayList<>(listOfEverydayTasks);
                                allTasks.addAll(listOfGlobalTasks);
                                uiSystem.statistics(allTasks, TaskScale.GENERAL);
                                uiSystem.delay();
                            }
                            // Exit statistics display
                            else if (getStatisticsAction == 4) {
                                break;
                            }
                        }
                    }
                    // Actions with overdue tasks
                    else if (advancedAction == 4) {
                        while (true) {
                            System.out.println("What do you want to do with overdue tasks?");
                            System.out.println("1. Display all overdue tasks");
                            System.out.println("2. Add more time to overdue tasks");
                            System.out.println("3. Delete overdue tasks");
                            System.out.println("4. Back to previous page");

                            int overdueAction = inputHandler.readNumber(4);

                            // Display overdue tasks
                            if (overdueAction == 1) {
                                System.out.println("Overdue everyday tasks: ");
                                uiSystem.displayOverdueTasks(listOfEverydayTasks, TaskScale.EVERYDAY);
                                System.out.println();
                                System.out.println("Overdue global tasks: ");
                                uiSystem.displayOverdueTasks(listOfGlobalTasks, TaskScale.GLOBAL);
                                System.out.println();
                                uiSystem.delay();
                            }
                            // Add more time to deadline of overdue tasks
                            else if (overdueAction == 2) {
                                while (true) {
                                    System.out.println("Which type of overdue Tasks would you like to add more time to?");
                                    System.out.println("1. Everyday tasks");
                                    System.out.println("2. Global tasks");
                                    System.out.println("3. Back to previous page");

                                    int overdueTimeAddAction = inputHandler.readNumber(3);

                                    // Add time to everyday overdue tasks
                                    if (overdueTimeAddAction == 1) {
                                        System.out.println("Which task would you like to add more time to: ");
                                        uiSystem.displayOverdueTasks(listOfEverydayTasks, TaskScale.EVERYDAY);
                                        System.out.println();
                                        LinkedHashMap<Integer,Integer> trueIndexOfTasks = taskService.positionsOfOverdueTasks(listOfEverydayTasks);
                                        int actionsQuantity = trueIndexOfTasks.size();
                                        System.out.println((actionsQuantity+1) + ") Back to previous page");
                                        int overdueTaskAddTimeAction = inputHandler.readNumber(actionsQuantity + 1);
                                        if (!(overdueTaskAddTimeAction == (actionsQuantity+1))) {
                                            System.out.println("How much hours would you like to add?");
                                            int hoursToAdd = inputHandler.readNumber(Integer.MAX_VALUE);
                                            Task correctTask = listOfEverydayTasks.get(trueIndexOfTasks.get(overdueTaskAddTimeAction-1));
                                            taskService.addToOverdueTaskHours(correctTask, hoursToAdd);
                                            System.out.println("Successfully added more time to overdue everyday task");
                                        }
                                    }
                                    // Add time to global overdue tasks
                                    else if (overdueTimeAddAction == 2) {
                                        System.out.println("Which task would you like to add more time to: ");
                                        uiSystem.displayOverdueTasks(listOfGlobalTasks, TaskScale.GLOBAL);
                                        System.out.println();
                                        LinkedHashMap<Integer,Integer> trueIndexOfTasks = taskService.positionsOfOverdueTasks(listOfGlobalTasks);
                                        int actionsQuantity = trueIndexOfTasks.size();
                                        System.out.println((actionsQuantity+1) + ") Back to previous page");
                                        int overdueTaskAddTimeAction = inputHandler.readNumber(actionsQuantity + 1);
                                        if (!(overdueTaskAddTimeAction == (actionsQuantity+1))) {
                                            System.out.println("How much days would you like to add?");
                                            int daysToAdd = inputHandler.readNumber(Integer.MAX_VALUE);
                                            Task correctTask = listOfGlobalTasks.get(trueIndexOfTasks.get(overdueTaskAddTimeAction-1));
                                            taskService.addToOverdueTaskDays(correctTask, daysToAdd);
                                            System.out.println("Successfully added more time to overdue Global task");
                                        }
                                    }
                                    // Exit time adding
                                    else if (overdueTimeAddAction == 3) {
                                        break;
                                    }
                                }
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
                                        uiSystem.displayOverdueTasks(listOfEverydayTasks, TaskScale.EVERYDAY);
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
                                        uiSystem.displayOverdueTasks(listOfGlobalTasks, TaskScale.GLOBAL);
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
                                    else if (deleteOverdueAction == 3) {
                                        break;
                                    }
                                }
                            }
                            // Exit overdue tasks actions
                            else if (overdueAction == 4) {
                                break;
                            }
                        }
                    }
                    // Exit advanced actions
                    else if (advancedAction == 5) {
                        break;
                    }
                }
            }
            // Save data and exit program at all
            else if (action == 6) {
                storage.saveTasks(listOfEverydayTasks, EVERYDAY_FILE_NAME);
                storage.saveTasks(listOfGlobalTasks, GLOBAL_FILE_NAME);
                System.out.println("All data has been saved, Goodbye!");
                System.exit(0);
            }
        }
    }
}