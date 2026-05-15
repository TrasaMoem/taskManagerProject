import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Statistics {
    private int overdueCounter , lowPriorityCounter , midPriorityCounter , highPriorityCounter , counterOfActiveTasks, percentOfOverdueTasks;
    private Task nearest, farthest ;
    private long totalMinutes, totalAverageDays, totalAverageHours, totalAverageMinutes;

    public void calculateStatistics(List<Task> list) {
        LocalDateTime now = LocalDateTime.now();
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
        if(!(counterOfActiveTasks == 0)) {
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
    }
    public void calculatePercentOfOverdueTasks(List<Task> list) {
        if (!list.isEmpty()) {
            percentOfOverdueTasks = (100*overdueCounter) / list.size();
        }
    }
    public void reset() {
        overdueCounter = 0;
        lowPriorityCounter = 0;
        midPriorityCounter = 0;
        highPriorityCounter = 0;
        counterOfActiveTasks = 0;
        percentOfOverdueTasks = 0;
        nearest = null;
        farthest = null;
        totalMinutes = 0;
        totalAverageDays = 0;
        totalAverageHours = 0;
        totalAverageMinutes = 0;
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
