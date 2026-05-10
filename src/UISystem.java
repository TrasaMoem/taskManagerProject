import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UISystem {
    private final LocalDateTime now = LocalDateTime.now();
    private final InputHandler inputHandler = new InputHandler();

    public void delay() {
        System.out.println("Print 1 to continue: ");
        while(true) {
            int delaying = inputHandler.readNumber(1);

            if (delaying == 1) {
                break;
            }
        }
    }
    public void displaySpecificTaskDeadline(Task task, TaskScale scale) {
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

    public void displayOverdueTasks(ArrayList<Task> list, TaskScale scale) {
        int overdueCounter = 0;
        for (Task task : list) {
            if (task.getDeadline().isBefore(now)) {
                overdueCounter++;
                System.out.print(overdueCounter + ") " + task.getName());
                displaySpecificTaskDeadline(task, scale);
            }
        }
        if (overdueCounter == 0) {
            System.out.println("No overdue tasks");
        }
    }
    public void display(ArrayList<Task> list, TaskScale scale) {
        if (list.isEmpty()) {
            System.out.println("No tasks to display");
        } else {
            for (int i = 0; i < list.size(); i++) {
                Task t = list.get(i);

                System.out.print((i + 1) + ") Name: " + t.getName() + ", Description: " + t.getDescription() + ", Priority: " + t.getPriority() + " (1 - low, 2 - medium, 3 - high), Deadline time: ");

                displaySpecificTaskDeadline(t, scale);
            }
        }
    }

    public void statistics(ArrayList<Task> list, String everydayOrGlobalOrGeneral) {
        int overdueCounter = 0, lowPriorityCounter = 0, midPriorityCounter = 0, highPriorityCounter = 0, moreThanHighPriorityCounter = 0, counterOfActiveTasks = 0;
        Task nearest = null, farthest = null;
        long totalMinutes = 0;

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
            } if (t.getPriority() == 1) {
                lowPriorityCounter++;
            } else if (t.getPriority() == 2) {
                midPriorityCounter++;
            } else if (t.getPriority() == 3) {
                highPriorityCounter++;
            } else {
                moreThanHighPriorityCounter++;
            }
        }
        System.out.println("Statistic: ");
        System.out.println("Total number of " + everydayOrGlobalOrGeneral + " tasks: " + list.size());
        System.out.println("---");
        System.out.println("Overdue tasks: " + overdueCounter);
        System.out.println("Active tasks: " + (list.size() - overdueCounter));
        System.out.println("---");
        System.out.println("Low priority tasks: " + lowPriorityCounter);
        System.out.println("Middle priority tasks: " + midPriorityCounter);
        System.out.println("High priority tasks: " + highPriorityCounter);
        System.out.println("More than high priority tasks: " + moreThanHighPriorityCounter);
        System.out.println("---");
        if (nearest == null) {
            System.out.println("Nearest deadline: no active tasks");
        } else {
            System.out.print("Nearest deadline: " + nearest.getName() + " ");
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            System.out.println(nearest.getDeadline().format(f));
        }
        if (nearest == null) {
            System.out.println("Farthest deadline: no active tasks");
        } else {
            assert farthest != null;
            System.out.print("Farthest deadline: " + farthest.getName() + " ");
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            System.out.println(farthest.getDeadline().format(f));
        }
        System.out.println("---");
        if (counterOfActiveTasks == 0) {
            System.out.println("Average time to deadline: no active tasks");
        } else {
            if (everydayOrGlobalOrGeneral.equalsIgnoreCase("everyday")) {
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
