package collabx.models;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private final int id;
    private String title;
    private String description;
    private String category;
    private final int creatorId;
    private final int teamSize;
    private String status = "OPEN";
    private final List<String> requiredSkills = new ArrayList<>();
    private final List<Integer> teamMembers = new ArrayList<>();

    public Project(int id, String title, String description, String category,
                   int creatorId, int teamSize) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.creatorId = creatorId;
        this.teamSize = teamSize;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public int getCreatorId() { return creatorId; }
    public int getTeamSize() { return teamSize; }
    public String getStatus() { return status; }
    public List<String> getRequiredSkills() { return requiredSkills; }
    public List<Integer> getTeamMembers() { return teamMembers; }

    public void addRequiredSkill(String skill) {
        if (!skill.isBlank() && !requiredSkills.contains(skill.toLowerCase())) {
            requiredSkills.add(skill.toLowerCase());
        }
    }

    public void addTeamMember(int userId) {
        if (!teamMembers.contains(userId) && teamMembers.size() < teamSize) {
            teamMembers.add(userId);
        }
        if (teamMembers.size() >= teamSize) {
            status = "FULL";
        }
    }
}
