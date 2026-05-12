import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Statistics {
    private int overdueCounter , lowPriorityCounter , midPriorityCounter , highPriorityCounter , counterOfActiveTasks, percentOfOverdueTasks;
    private Task nearest, farthest ;
    private long totalMinutes, totalAverageDays, totalAverageHours, totalAverageMinutes;
    private final LocalDateTime now = LocalDateTime.now();


    public void calculateStatistics(List<Task> list) {
        for (Task t : list) {
            // Nearest deadline
            if (t.getDeadline().isAfter(now)) {
                if (nearest == null || t.getDeadline().isBefore(nearest.getDeadline())) {
                    nearest = t;
                }
                // Farthest deadline
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
            if (t.getDeadline().isBefore(now)) {
                overdueCounter++;
            } if (t.getPriority() == Priority.LOW) {
                lowPriorityCounter++;
            } else if (t.getPriority() == Priority.MEDIUM) {
                midPriorityCounter++;
            } else {
                highPriorityCounter++;
            }
        }
    }
    public void calculateTimeForStatistics(TaskScale scale) {
        long averageMinutes;
        if (scale == TaskScale.EVERYDAY) {
            averageMinutes = totalMinutes / counterOfActiveTasks;
            totalAverageHours = averageMinutes / 60;
            totalAverageMinutes = averageMinutes % 60;
        } else {
            averageMinutes = totalMinutes / counterOfActiveTasks;
            totalAverageDays = averageMinutes / (60 * 24);
            totalAverageHours = (averageMinutes / 60) % 24;
            totalAverageMinutes = averageMinutes % 60;
        }
    }
    public void calculatePercentOfOverdueTasks(List<Task> list) {
        percentOfOverdueTasks = (100*overdueCounter) / list.size();
    }
    public int getOverdueCounter() {
        return overdueCounter;
    }
    public int getLowPriorityCounter() {
        return lowPriorityCounter;
    }
    public int getMidPriorityCounter() {
        return midPriorityCounter;
    }
    public int getHighPriorityCounter() {
        return highPriorityCounter;
    }
    public int getCounterOfActiveTasks() {
        return counterOfActiveTasks;
    }
    public Task getNearest() {
        return nearest;
    }
    public Task getFarthest() {
        return farthest;
    }
    public long getTotalAverageHours() {
        return totalAverageHours;
    }
    public long getTotalAverageMinutes() {
        return totalAverageMinutes;
    }
    public long getTotalAverageDays() {
        return totalAverageDays;
    }
    public int getPercentOfOverdueTasks() {
        return percentOfOverdueTasks;
    }
}
