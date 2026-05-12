import java.time.LocalDateTime;

public class Task {

    private String name;
    private String description;
    private Priority priority;
    private LocalDateTime deadline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority == 1) {
            this.priority = Priority.LOW;
        }
        else if (priority == 2) {
            this.priority = Priority.MEDIUM;
        }
        else if (priority == 3) {
            this.priority = Priority.HIGH;
        }
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
