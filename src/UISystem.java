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
        System.out.print("Name: " + task.getName() + ", Description: " + task.getDescription() + ", Priority: " + task.getPriority() + " (1 - low, 2 - medium, 3 - high), Deadline time: ");
        displaySpecificTaskDeadline(task, scale);
    }

    public void display(List<Task> list, TaskScale scale) {
        if (list.isEmpty()) {
            System.out.println("No tasks to display");
        } else {
            for (Task t : list) {
                displaySpecificTask(t, scale);
                System.out.println();
            }
        }
    }

    public void statistics(List<Task> list, TaskScale scale) {
        int overdueCounter = 0, lowPriorityCounter = 0, midPriorityCounter = 0, highPriorityCounter = 0, counterOfActiveTasks = 0;
        Task nearest = null, farthest = null;
        long totalMinutes = 0;
        LocalDateTime now = LocalDateTime.now();

        // counter block
        for (Task t : list) {
            // Nearest deadline
            if (t.getDeadline().isAfter(now)) {
                if (nearest == null || t.getDeadline().isBefore(nearest.getDeadline())) {
                    nearest = t;
                }
            }
            // Farthest deadline
            if (t.getDeadline().isAfter(now)) {
                if (farthest == null || t.getDeadline().isAfter(farthest.getDeadline())) {
                    farthest = t;
                }
            }
            // Average deadline counter
            Duration duration = Duration.between(now, t.getDeadline());
            if (!duration.isNegative()) {
                totalMinutes += duration.toMinutes();
                counterOfActiveTasks++;
            }
            // Overdue and priority counter
            if (t.getDeadline().isBefore(LocalDateTime.now())) {
                overdueCounter++;
            } if (t.getPriority().getValue() == 1) {
                lowPriorityCounter++;
            } else if (t.getPriority().getValue() == 2) {
                midPriorityCounter++;
            } else {
                highPriorityCounter++;
            }
        }
        System.out.println("Statistic: ");
        System.out.println("Total number of " + scale + " tasks: " + list.size());
        System.out.println("---");
        System.out.println("Overdue tasks: " + overdueCounter);
        System.out.println("Active tasks: " + (list.size() - overdueCounter));
        System.out.println("---");
        System.out.println("Low priority tasks: " + lowPriorityCounter);
        System.out.println("Middle priority tasks: " + midPriorityCounter);
        System.out.println("High priority tasks: " + highPriorityCounter);
        System.out.println("---");

        if (nearest == null) {
            System.out.println("Nearest deadline: no active tasks");
        } else {
            System.out.print("Nearest deadline: " + nearest.getName() + " ");
            System.out.println(nearest.getDeadline().format(f));
        }
        if (farthest == null) {
            System.out.println("Farthest deadline: no active tasks");
        } else {
            System.out.print("Farthest deadline: " + farthest.getName() + " ");
            System.out.println(farthest.getDeadline().format(f));
        }
        System.out.println("---");
        if (counterOfActiveTasks == 0) {
            System.out.println("Average time to deadline: no active tasks");
        } else {
            if (scale == TaskScale.EVERYDAY) {
                long averageMinutes = totalMinutes / counterOfActiveTasks, totalAverageHours = averageMinutes / 60, totalAverageMinutes = totalMinutes % 60;
                System.out.println("Average time to deadline: " + totalAverageHours + " hours, " + totalAverageMinutes + " minutes");
            } else {
                long averageMinutes = totalMinutes / counterOfActiveTasks, totalAverageDays = averageMinutes / (60 * 24), totalAverageHours = (averageMinutes / 60) % 24, totalAverageMinutes = totalMinutes % 60;
                System.out.println("Average time to deadline: " + totalAverageDays + " days, " + totalAverageHours + " hours, " + totalAverageMinutes + " minutes");
            }
        }
        try {
            System.out.println("Percent of overdue tasks: " + ((100*overdueCounter) / list.size() + "%"));
        } catch (Exception e) {
            System.out.println("Percent of overdue tasks: no overdue tasks or empty task list");
        }
    }

}
