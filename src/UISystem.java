import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UISystem {
    private final InputHandler inputHandler = new InputHandler();
    private final DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void delay() {
        System.out.println("Print 1 to continue: ");
        inputHandler.readNumber(1);
    }
    public boolean sure() {
        System.out.println("Are you sure you want to do this action?");
        System.out.println("1. Yes, I sure");
        System.out.println("2. Dont do this action!");
        int choice = inputHandler.readNumber(2);
        return choice == 1;
    }
    public void displaySpecificTaskDeadline(Task task, TaskScale scale) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, task.getDeadline());
        boolean passed = duration.isNegative();
        duration = duration.abs();

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        if (scale == TaskScale.EVERYDAY) {
            if (passed) {
                System.out.print("passed " + (days * 24 + hours) + " hours " + minutes + " minutes ago");
            } else {
                System.out.print((days * 24 + hours) + " hours " + minutes + " minutes left");
            }
        } else {
            if (passed) {
                System.out.print("passed " + days + " days " + hours + " hours " + minutes + " minutes ago");
            } else {
                System.out.print(days + " days " + hours + " hours " + minutes + " minutes left");
            }
        }
    }
    public void displaySpecificTask(Task task, TaskScale scale) {
        System.out.print("Name: " + task.getName() + ", Description: " + task.getDescription() + ", Priority: " + task.getPriority() + ", Deadline time: ");
        displaySpecificTaskDeadline(task, scale);
    }

    public void display(List<Task> list, TaskScale scale) {
        if (list.isEmpty()) {
            System.out.println("No tasks to display");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.print((i+1) + ") ");
                displaySpecificTask(list.get(i), scale);
                System.out.println();
            }
        }
    }

    public void statistics(List<Task> list, TaskScale scale) {
        // counter block
        Statistics statistics = new Statistics();
        statistics.calculateStatistics(list);

        System.out.println("Statistic: ");

        displayTotalNumberOfAnyTasks(scale, list.size());

        System.out.println("---");

        displayNumberOfEachTypeOfTasks(statistics, list);

        System.out.println("---");

        displayStatisticsPriorityOfTheTasks(statistics.getLowPriorityCounter(), statistics.getMidPriorityCounter(), statistics.getHighPriorityCounter());

        System.out.println("---");

        displayNearestDeadline(statistics.getNearest());
        displayFarthestDeadline(statistics.getFarthest());

        System.out.println("---");

        displayAverageTimeToDeadline(statistics, scale);
        displayPercentOfOverdueTasks(list, statistics);

    }
    public void displayTotalNumberOfAnyTasks(TaskScale scale, int amountOfTasks) {
        System.out.println("Total number of " + scale + " tasks: " + amountOfTasks);
    }

    public void displayNumberOfEachTypeOfTasks(Statistics statistics, List<Task> list) {
        System.out.println("Overdue tasks: " + statistics.getOverdueCounter());
        System.out.println("Active tasks: " + (list.size() - statistics.getOverdueCounter()));
    }

    public void displayStatisticsPriorityOfTheTasks(int lowPriorityCounter, int mediumPriorityCounter, int highPriorityCounter) {
        System.out.println("Low priority tasks: " + lowPriorityCounter);
        System.out.println("Middle priority tasks: " + mediumPriorityCounter);
        System.out.println("High priority tasks: " + highPriorityCounter);
    }

    public void displayNearestDeadline(Task nearest) {
        if (nearest == null) {
            System.out.println("Nearest deadline: no active tasks");
        } else {
            System.out.print("Nearest deadline: " + nearest.getName() + " ");
            System.out.println(nearest.getDeadline().format(f));
        }
    }

    public void displayFarthestDeadline(Task farthest) {
        if (farthest == null) {
            System.out.println("Farthest deadline: no active tasks");
        } else {
            System.out.print("Farthest deadline: " + farthest.getName() + " ");
            System.out.println(farthest.getDeadline().format(f));
        }
    }

    public void displayAverageTimeToDeadline(Statistics statistics, TaskScale scale) {
        if (statistics.getCounterOfActiveTasks() == 0) {
            System.out.println("Average time to deadline: no active tasks");
        } else {
            statistics.calculateTimeForStatistics(scale);
            if (scale == TaskScale.EVERYDAY) {
                System.out.println("Average time to deadline: " + statistics.getTotalAverageHours() + " hours, " + statistics.getTotalAverageMinutes() + " minutes");
            } else {
                System.out.println("Average time to deadline: " + statistics.getTotalAverageDays() + " days, " + statistics.getTotalAverageHours() + " hours, " + statistics.getTotalAverageMinutes() + " minutes");
            }
        }
    }

    public void displayPercentOfOverdueTasks(List<Task> list, Statistics statistics) {
        if (list.isEmpty()) {
            System.out.println("Percent of overdue tasks: empty task list");
        } else {
            statistics.calculatePercentOfOverdueTasks(list);
            System.out.println("Percent of overdue tasks: " + (statistics.getPercentOfOverdueTasks() + "%"));
        }
    }

}
