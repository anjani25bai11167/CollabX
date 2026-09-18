package collabx.models;

public class Application {
    private final int id;
    private final int projectId;
    private final int userId;
    private final String message;
    private String status = "PENDING";

    public Application(int id, int projectId, int userId, String message) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.message = message;
    }

    public int getId() { return id; }
    public int getProjectId() { return projectId; }
    public int getUserId() { return userId; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
