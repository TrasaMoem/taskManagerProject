import java.util.*;
import java.time.Duration;

public class Main extends UISystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> listOfEverydayTasks = loadEverydayTasks(EVERYDAY_FILE_NAME);
        ArrayList<Task> listOfGlobalTasks = loadGlobalTasks(GLOBAL_FILE_NAME);
        // If one of lists is equals null
        if (listOfEverydayTasks == null) {
            listOfEverydayTasks = new ArrayList<>();
        } else if (listOfGlobalTasks == null) {
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
            int action = readNumber(sc, 6);
            if (action == 1) {
                // Adding new task
                while (true) {
                    System.out.println("Choose which task you want to add: ");
                    System.out.println("1. Everyday Task");
                    System.out.println("2. Global Task");
                    System.out.println("3. Back to previous page");
                    int addAction = readNumber(sc, 3);
                    // Add everyday task
                    if (addAction == 1) {
                        Task everydaytask = new Task();

                        // Everydays task name
                        System.out.println("Enter the name of the Task: ");
                        everydaytask.setName(readString(sc));

                        // Everydays task description
                        System.out.println("Enter the description of the Task: ");
                        everydaytask.setDescription(readString(sc));

                        // Everydays task priority
                        System.out.println("Enter the priority of the Task (1 - low, 2 - medium, 3 - high, or more if you want): ");
                        everydaytask.setPriority(readNumber(sc, Integer.MAX_VALUE));

                        // Everydays task deadlines
                        System.out.println("Enter the deadline (in hours) until the end: ");
                        everydaytask.setDeadline(readDateTime(sc,true));

                        // Add new everyday task to the array
                        listOfEverydayTasks.add(everydaytask);
                        System.out.println("Successfully added an everyday task!");
                    }
                    // Add global task
                    else if (addAction == 2) {
                        Task globaltask = new Task();

                        // Global task name
                        System.out.println("Enter the name of the Task: ");
                        globaltask.setName(readString(sc));

                        // Global task description
                        System.out.println("Enter the description of the Task: ");
                        globaltask.setDescription(readString(sc));

                        // Global task priority
                        System.out.println("Enter the priority of the Task (1 - low, 2 - medium, 3 - high, or more if you want): ");
                        globaltask.setPriority(readNumber(sc, Integer.MAX_VALUE));

                        // Global task deadlines
                        System.out.println("Enter the deadline (in days) until the end of the Task: ");
                        globaltask.setDeadline(readDateTime(sc,false));

                        // Add new global task to array
                        listOfGlobalTasks.add(globaltask);
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

                    int editAction = readNumber(sc, 3);

                    // Edit everyday task
                    if (editAction == 1) {
                        while (true) {
                            System.out.println("Which Task would you like to edit: ");
                            for (int i = 0; i < listOfEverydayTasks.size(); i++) {
                                System.out.println((i+1) + ") " + listOfEverydayTasks.get(i).getName());
                            }
                            System.out.println((listOfEverydayTasks.size()+1) + ") Back to previous page");

                            int editEverydayAction = readNumber(sc, listOfEverydayTasks.size()+1);

                            // Searching for correct everyday task to edit
                            for (int j = 0; j < listOfEverydayTasks.size(); j++) {
                                // Exact everyday task to edit
                                if (editEverydayAction == j+1) {
                                    while (true) {
                                        Task t = listOfEverydayTasks.get(j);

                                        System.out.print("The " + (j+1) + " everydays Task: Name: " + t.getName() +
                                                ", Description: " + t.getDescription() +
                                                ", Priority: " + t.getPriority() +
                                                ", Deadline time: ");

                                        Duration duration = Duration.between(now, t.getDeadline());
                                        boolean passed = duration.isNegative();
                                        duration = duration.abs();

                                        long days = duration.toDays();
                                        long hours = duration.toHours() % 24;
                                        long minutes = duration.toMinutes() % 60;

                                        if (passed) {
                                            System.out.println("passed " + (days * 24 + hours) + " hours " + minutes + " minutes ago");
                                        } else {
                                            System.out.println((days * 24 + hours) + " hours " + minutes + " minutes left");
                                        }

                                        System.out.println("Which parameter would you like to edit: ");
                                        System.out.println("1. Edit the name of the Task");
                                        System.out.println("2. Edit the description of the Task");
                                        System.out.println("3. Edit the priority of the Task");
                                        System.out.println("4. Edit the deadline of the Task");
                                        System.out.println("5. Back to previous page");

                                        int editSpecificParameter = readNumber(sc, 5);

                                        // Edit name of everyday task
                                        if (editSpecificParameter == 1) {
                                            edit(sc, listOfEverydayTasks, "name", j, true);
                                        }
                                        // Edit description of everyday task
                                        else if (editSpecificParameter == 2) {
                                            edit(sc, listOfEverydayTasks, "description", j, true);
                                        }
                                        // Edit priority of everyday task
                                        else if (editSpecificParameter == 3) {
                                            edit(sc, listOfEverydayTasks, "priority", j, true);
                                        }
                                        // Edit deadline of everyday task
                                        else if (editSpecificParameter == 4) {
                                            edit(sc, listOfEverydayTasks, "deadline", j, true);
                                            delay(sc);
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

                            int editGlobalAction = readNumber(sc, listOfGlobalTasks.size()+1);

                            // Searching for correct global task to edit
                            for (int j = 0; j < listOfGlobalTasks.size(); j++) {
                                // Exact global task to edit
                                if (editGlobalAction == j+1) {
                                    while (true) {
                                        Task t = listOfGlobalTasks.get(j);

                                        System.out.print("The " + (j+1) + " global Task: Name: " + t.getName() +
                                                ", Description: " + t.getDescription() +
                                                ", Priority: " + t.getPriority() +
                                                ", Deadline time: ");

                                        Duration duration = Duration.between(now, t.getDeadline());
                                        boolean passed = duration.isNegative();
                                        duration = duration.abs();

                                        long days = duration.toDays();
                                        long hours = duration.toHours() % 24;
                                        long minutes = duration.toMinutes() % 60;

                                        if (passed) {
                                            System.out.println("passed " + days + " days " + hours + " hours " + minutes + " minutes ago");
                                        } else {
                                            System.out.println(days + " days " + hours + " hours " + minutes + " minutes left");
                                        }
                                        System.out.println("Which parameter would you like to edit: ");
                                        System.out.println("1. Edit the name of the Task");
                                        System.out.println("2. Edit the description of the Task");
                                        System.out.println("3. Edit the priority of the Task");
                                        System.out.println("4. Edit the deadline of the Task");
                                        System.out.println("5. Back to previous page");

                                        int editSpecificParameter = readNumber(sc, 5);

                                        // Edit name of the global task
                                        if (editSpecificParameter == 1) {
                                            edit(sc, listOfGlobalTasks, "name", j, false);
                                        }
                                        // Edit description of global task
                                        else if (editSpecificParameter == 2) {
                                            edit(sc, listOfGlobalTasks, "description", j, false);
                                        }
                                        // Edit priority of global task
                                        else if (editSpecificParameter == 3) {
                                            edit(sc, listOfGlobalTasks, "priority", j, false);
                                        }
                                        // Edit deadline of global task
                                        else if (editSpecificParameter == 4) {
                                            edit(sc, listOfGlobalTasks, "deadline", j, false);
                                            delay(sc);
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
                    int deleteAction = readNumber(sc, 3);
                    // Delete everyday tasks
                    if (deleteAction == 1) {
                        while (true) {
                            System.out.println("Which everyday Task would you like to delete: ");
                            for (int i = 0; i < listOfEverydayTasks.size(); i++) {
                                System.out.println((i+1) + ") " + listOfEverydayTasks.get(i).getName());
                            }
                            System.out.println((listOfEverydayTasks.size()+1) + ") Delete all everyday Tasks");
                            System.out.println((listOfEverydayTasks.size()+2) + ") Back to previous page");

                            int deleteEverydayTaskAction = readNumber(sc, listOfEverydayTasks.size()+2);

                            // Searching everyday task to delete
                            if (deleteEverydayTaskAction > -1 && deleteEverydayTaskAction < listOfEverydayTasks.size()) {
                                for (int i = 0; i < listOfEverydayTasks.size(); i++) {
                                    // Exact everyday task to delete
                                    if (deleteEverydayTaskAction == (i+1)) {
                                        System.out.println("Task " + listOfEverydayTasks.get(i).getName() + " has been deleted successfully and permanently!");
                                        listOfEverydayTasks.remove(i);
                                    }
                                }
                            }
                            // Delete all the everyday tasks
                            else if (deleteEverydayTaskAction == listOfEverydayTasks.size()+1) {
                                    System.out.println("Are you sure you want to delete ALL the everyday Tasks permanently?");
                                    System.out.println("1. Yes, I sure");
                                    System.out.println("2. Dont delete ALL my everyday Tasks");

                                    int sure = readNumber(sc, 2);
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

                            int deleteGlobalTaskAction = readNumber(sc, listOfGlobalTasks.size()+2);

                            // Searching global task to delete
                            if (deleteGlobalTaskAction > -1 && deleteGlobalTaskAction < listOfGlobalTasks.size()) {
                                for (int i = 0; i < listOfGlobalTasks.size(); i++) {
                                    // Exact global task to delete
                                    if (deleteGlobalTaskAction == (i+1)) {
                                        System.out.println("Task " + listOfGlobalTasks.get(i).getName() + " has been deleted successfully and permanently!");
                                        listOfGlobalTasks.remove(i);
                                    }
                                }
                            }
                            // Delete all of global tasks
                            else if (deleteGlobalTaskAction == listOfGlobalTasks.size()+1) {
                                    System.out.println("Are you sure you want to delete ALL the global Tasks permanently?");
                                    System.out.println("1. Yes, I sure");
                                    System.out.println("2. Dont delete ALL my global Tasks");

                                    int sure = readNumber(sc, 2);
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

                    int displayAction = readNumber(sc, 3);

                    // Display everyday tasks
                    if (displayAction == 1) {
                        display(sc, listOfEverydayTasks, true);
                    }
                    // Display global tasks
                    else if (displayAction == 2) {
                        display(sc, listOfGlobalTasks, false);
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
                    int advancedAction = readNumber(sc, 5);
                    // Sort any tasks
                    if (advancedAction == 1) {
                        while (true) {
                            System.out.println("Which type of task would you like to sort: ");
                            System.out.println("1. Sort everyday Tasks");
                            System.out.println("2. Sort global Tasks");
                            System.out.println("3. Back to previous page");

                            int sortAction = readNumber(sc, 3);

                            // Sort everyday tasks
                            if (sortAction == 1) {
                                while (true) {
                                    System.out.println("By what parameter do you want to sort everyday Tasks: ");
                                    System.out.println("1. Sort by first letter of name of the Task");
                                    System.out.println("2. Sort by priority of the Task");
                                    System.out.println("3. Sort by deadline time of the Task");
                                    System.out.println("4. Back to previous page");

                                    int sortEverydayTaskAction = readNumber(sc, 4);

                                    // First letter sort
                                    if (sortEverydayTaskAction == 1) {
                                        System.out.println("How would you like to sort everyday Tasks names: ");
                                        System.out.println("1. From A to Z");
                                        System.out.println("2. From Z to A");
                                        System.out.println("3. Back to previous page");

                                        int sortEverydayTaskByNames = readNumber(sc, 3);

                                        // First letter sort from A to Z
                                        if (sortEverydayTaskByNames == 1) {
                                            sortByName(sc, listOfEverydayTasks,true,true);
                                            display(sc, listOfEverydayTasks, true);
                                        }
                                        // First letter sort from Z to A
                                        else if (sortEverydayTaskByNames == 2) {
                                            sortByName(sc, listOfEverydayTasks,true,false);
                                            display(sc, listOfEverydayTasks, true);
                                        }
                                    }
                                    // Priority sort
                                    else if (sortEverydayTaskAction == 2) {
                                        System.out.println("How would you like to sort everyday Tasks priority: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortEverydayTaskSmallestOrLargestAction = readNumber(sc, 3);

                                        // Priority sort from smallest to largest
                                        if (sortEverydayTaskSmallestOrLargestAction == 1) {
                                            sortByPriority(sc, listOfEverydayTasks,true,true);
                                            display(sc, listOfEverydayTasks, true);
                                        }
                                        // Priority sort from largest to smallest
                                        else if (sortEverydayTaskSmallestOrLargestAction == 2) {
                                            sortByPriority(sc, listOfEverydayTasks,true,false);
                                            display(sc, listOfEverydayTasks, true);
                                        }
                                    }
                                    // Deadline sort
                                    else if (sortEverydayTaskAction == 3) {
                                        System.out.println("How would you like to sort everyday Tasks deadline: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortEverydayTaskSmallestOrLargestAction = readNumber(sc, 3);

                                        // Sort deadline from smallest to largest
                                        if (sortEverydayTaskSmallestOrLargestAction == 1) {
                                            sortByDeadline(sc, listOfEverydayTasks, true, true);
                                            display(sc, listOfEverydayTasks, true);
                                        }
                                        // Sort deadline from largest to smallest
                                        else if (sortEverydayTaskSmallestOrLargestAction == 2) {
                                            sortByDeadline(sc, listOfEverydayTasks, false, true);
                                            display(sc, listOfEverydayTasks, true);
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

                                    int sortGlobalTaskAction = readNumber(sc, 4);

                                    // Sort name by first letter
                                    if (sortGlobalTaskAction == 1) {
                                        while (true) {
                                            System.out.println("How would you like to sort global Tasks names: ");
                                            System.out.println("1. From A to Z");
                                            System.out.println("2. From Z to A");
                                            System.out.println("3. Back to previous page");

                                            int sortGlobalTaskByNames = readNumber(sc, 3);

                                            // Sort global tasks from A to Z
                                            if (sortGlobalTaskByNames == 1) {
                                                sortByName(sc, listOfGlobalTasks,false,true);
                                                display(sc, listOfGlobalTasks, false);
                                            }
                                            // Sort global tasks from Z to A
                                            else if (sortGlobalTaskByNames == 2) {
                                                sortByName(sc, listOfGlobalTasks,false,false);
                                                display(sc, listOfGlobalTasks, false);
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

                                        int sortGlobalTaskSmallestOrLargestAction = readNumber(sc, 3);

                                        // Sort global tasks priority from smallest to largest
                                        if (sortGlobalTaskSmallestOrLargestAction == 1) {
                                            sortByPriority(sc, listOfGlobalTasks,false,true);
                                            display(sc, listOfGlobalTasks, false);
                                        }
                                        // Sort global tasks priority from largest to smallest
                                        else if (sortGlobalTaskSmallestOrLargestAction == 2) {
                                            sortByPriority(sc, listOfGlobalTasks,false,false);
                                            display(sc, listOfGlobalTasks, false);
                                        }
                                    }
                                    // Sort global tasks deadline
                                    else if (sortGlobalTaskAction == 3) {
                                        System.out.println("How would you like to sort global Tasks deadline: ");
                                        System.out.println("1. Sort from smallest to largest");
                                        System.out.println("2. Sort from largest to smallest");
                                        System.out.println("3. Back to previous page");

                                        int sortGlobalTaskSmallestOrLargestAction = readNumber(sc, 3);

                                        // Sort global tasks by deadline from smallest to largest
                                        if (sortGlobalTaskSmallestOrLargestAction == 1) {
                                            sortByDeadline(sc, listOfGlobalTasks, true, false);
                                            display(sc, listOfGlobalTasks, false);
                                        }
                                        // Sort global tasks by deadline from largest to smallest
                                        else if (sortGlobalTaskSmallestOrLargestAction == 2) {
                                            sortByDeadline(sc, listOfGlobalTasks, false, false);
                                            display(sc, listOfGlobalTasks, false);
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

                            int searchAction = readNumber(sc, 3);

                            // Search everyday tasks
                            if (searchAction == 1) {
                                System.out.println("Write first letter of the Task or full name of the Task: ");
                                String searchName = readString(sc);
                                search(sc, listOfEverydayTasks, searchName);
                                delay(sc);
                            }
                            // Search global tasks
                            else if (searchAction == 2) {
                                System.out.println("Write first letter of the Task or full name of the Task: ");
                                String searchName = readString(sc);
                                search(sc, listOfGlobalTasks, searchName);
                                delay(sc);
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

                            int getStatisticsAction = readNumber(sc, 4);

                            // Everyday tasks statistic
                            if (getStatisticsAction == 1) {
                                statistics(sc, listOfEverydayTasks, "everyday");
                                delay(sc);
                            }
                            // Global statistics
                            else if (getStatisticsAction == 2) {
                                statistics(sc, listOfGlobalTasks, "global");
                                delay(sc);
                            }
                            // General statistics
                            else if (getStatisticsAction == 3) {
                                ArrayList<Task> allTasks = new ArrayList<>(listOfEverydayTasks);
                                allTasks.addAll(listOfGlobalTasks);
                                statistics(sc, allTasks, "general");
                                delay(sc);
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

                            int overdueAction = readNumber(sc, 4);

                            // Display overdue tasks
                            if (overdueAction == 1) {
                                System.out.println("Overdue everyday tasks: ");
                                displayOverdueTasks(listOfEverydayTasks, true);
                                System.out.println("Overdue global tasks: ");
                                displayOverdueTasks(listOfGlobalTasks, false);
                                delay(sc);
                            }
                            // Add more time to deadline of overdue tasks
                            else if (overdueAction == 2) {
                                while (true) {
                                    System.out.println("Which type of overdue Tasks would you like to add more time to?");
                                    System.out.println("1. Everyday tasks");
                                    System.out.println("2. Global tasks");
                                    System.out.println("3. Back to previous page");

                                    int overdueTimeAddAction = readNumber(sc, 3);

                                    // Add time to everyday overdue tasks
                                    if (overdueTimeAddAction == 1) {
                                        addOverdueTime(listOfEverydayTasks, sc,true);
                                    }
                                    // Add time to global overdue tasks
                                    else if (overdueTimeAddAction == 2) {
                                        addOverdueTime(listOfGlobalTasks, sc,false);
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

                                    int deleteOverdueAction = readNumber(sc, 3);

                                    // Delete everyday overdue tasks
                                    if (deleteOverdueAction == 1) {
                                        deleteOverdueTask(listOfEverydayTasks,sc,true);
                                    }
                                    // Delete global overdue tasks
                                    else if (deleteOverdueAction == 2) {
                                        deleteOverdueTask(listOfGlobalTasks,sc,false);
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
                saveEverydayTasks(listOfEverydayTasks, EVERYDAY_FILE_NAME);
                saveGlobalTasks(listOfGlobalTasks, GLOBAL_FILE_NAME);
                System.out.println("All data has been saved, Goodbye!");
                System.exit(0);
            }
        }
    }
}