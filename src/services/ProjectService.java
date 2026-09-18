package collabx.services;

import collabx.models.Project;
import collabx.models.User;
import collabx.utils.InputValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectService {
    private final List<Project> projects = new ArrayList<>();
    private int nextId = 1;

    public Project createProject(Scanner scanner, User creator) {
        System.out.println("\n========== CREATE PROJECT ==========");
        String title = InputValidator.readNonEmpty(scanner, "Title: ");
        String description = InputValidator.readNonEmpty(scanner, "Description: ");
        String category = InputValidator.readNonEmpty(scanner, "Category: ");
        int teamSize = InputValidator.readPositiveInt(scanner, "Maximum team size: ");
        String skills = InputValidator.readNonEmpty(
                scanner, "Required skills (comma separated): ");

        Project project = new Project(
                nextId++, title, description, category, creator.getId(), teamSize);

        for (String skill : skills.split(",")) {
            project.addRequiredSkill(skill.trim());
        }

        project.addTeamMember(creator.getId());
        projects.add(project);

        System.out.println("Project created successfully. Project ID: " + project.getId());
        return project;
    }

    public Project addSampleProject(String title, String description,
                                    String category, int creatorId,
                                    int teamSize, String skills) {
        Project project = new Project(
                nextId++, title, description, category, creatorId, teamSize);

        for (String skill : skills.split(",")) {
            project.addRequiredSkill(skill.trim());
        }

        project.addTeamMember(creatorId);
        projects.add(project);
        return project;
    }

    public void displayProjects() {
        System.out.println("\n========== AVAILABLE PROJECTS ==========");

        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        for (Project p : projects) {
            System.out.println("----------------------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Title: " + p.getTitle());
            System.out.println("Category: " + p.getCategory());
            System.out.println("Description: " + p.getDescription());
            System.out.printf("Team: %d/%d%n",
                    p.getTeamMembers().size(), p.getTeamSize());
            System.out.println("Required Skills: "
                    + String.join(", ", p.getRequiredSkills()));
            System.out.println("Status: " + p.getStatus());
        }
    }

    public void searchProjects(Scanner scanner) {
        String keyword = InputValidator.readNonEmpty(
                scanner, "Search by title/category/skill: ").toLowerCase();

        boolean found = false;

        for (Project p : projects) {
            boolean matches = p.getTitle().toLowerCase().contains(keyword)
                    || p.getCategory().toLowerCase().contains(keyword)
                    || p.getDescription().toLowerCase().contains(keyword);

            for (String skill : p.getRequiredSkills()) {
                if (skill.contains(keyword)) matches = true;
            }

            if (matches) {
                found = true;
                System.out.println("\n[" + p.getId() + "] " + p.getTitle());
                System.out.println("Category: " + p.getCategory());
                System.out.println("Skills: "
                        + String.join(", ", p.getRequiredSkills()));
            }
        }

        if (!found) System.out.println("No matching projects found.");
    }

    public void recommendProjects(User user) {
        System.out.println("\n========== RECOMMENDED PROJECTS ==========");
        boolean found = false;

        for (Project p : projects) {
            if (p.getTeamMembers().size() >= p.getTeamSize()
                    || p.getTeamMembers().contains(user.getId())) continue;

            int match = calculateMatch(user, p);
            if (match > 0) {
                found = true;
                System.out.println("----------------------------------------");
                System.out.println("Project: " + p.getTitle());
                System.out.println("Category: " + p.getCategory());
                System.out.println("Skill Match: " + match + "%");
                System.out.println("Required: "
                        + String.join(", ", p.getRequiredSkills()));
            }
        }

        if (!found) System.out.println("No suitable projects found.");
    }

    public int calculateMatch(User user, Project project) {
        if (project.getRequiredSkills().isEmpty()) return 0;

        int matches = 0;
        for (String required : project.getRequiredSkills()) {
            if (user.getSkills().contains(required.toLowerCase())) matches++;
        }
        return matches * 100 / project.getRequiredSkills().size();
    }

    public Project findById(int id) {
        for (Project p : projects) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public List<Project> getProjectsByCreator(int creatorId) {
        List<Project> result = new ArrayList<>();
        for (Project p : projects) {
            if (p.getCreatorId() == creatorId) result.add(p);
        }
        return result;
    }

    public void displayTeam(Project project, UserService userService) {
        System.out.println("\n========== PROJECT TEAM ==========");
        for (int id : project.getTeamMembers()) {
            User user = userService.findById(id);
            if (user != null) {
                System.out.println(user.getId() + " - " + user.getName()
                        + " | " + String.join(", ", user.getSkills()));
            }
        }
    }
}
