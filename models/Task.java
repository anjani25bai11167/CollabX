package collabx.models;

public class Task {
    private final int id;
    private final int projectId;
    private final String title;
    private final String description;
    private final int assignedTo;
    private final String deadline;
    private String status = "PENDING";

    public Task(int id, int projectId, String title, String description,
                int assignedTo, String deadline) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public int getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getAssignedTo() { return assignedTo; }
    public String getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
