package collabx.services;

import collabx.models.Application;
import collabx.models.Project;
import collabx.models.User;
import collabx.utils.InputValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationService {
    private final List<Application> applications = new ArrayList<>();
    private int nextId = 1;

    public void applyToProject(Scanner scanner, User user,
                               ProjectService projectService) {
        projectService.displayProjects();

        int projectId = InputValidator.readInt(scanner, "Project ID: ");
        Project project = projectService.findById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getTeamMembers().contains(user.getId())) {
            System.out.println("You are already a member of this project.");
            return;
        }

        if (project.getTeamMembers().size() >= project.getTeamSize()) {
            System.out.println("The team is already full.");
            return;
        }

        for (Application a : applications) {
            if (a.getProjectId() == projectId
                    && a.getUserId() == user.getId()
                    && a.getStatus().equals("PENDING")) {
                System.out.println("You have already applied.");
                return;
            }
        }

        String message = InputValidator.readNonEmpty(
                scanner, "Application message: ");

        applications.add(new Application(
                nextId++, projectId, user.getId(), message));

        System.out.println("Application submitted successfully.");
    }

    public void displayUserApplications(int userId,
                                        ProjectService projectService) {
        System.out.println("\n========== MY APPLICATIONS ==========");
        boolean found = false;

        for (Application a : applications) {
            if (a.getUserId() == userId) {
                found = true;
                Project p = projectService.findById(a.getProjectId());
                System.out.println("\nApplication ID: " + a.getId());
                System.out.println("Project: " + (p == null ? "Unknown" : p.getTitle()));
                System.out.println("Message: " + a.getMessage());
                System.out.println("Status: " + a.getStatus());
            }
        }

        if (!found) System.out.println("No applications found.");
    }

    public void displayProjectApplications(Project project,
                                           UserService userService) {
        System.out.println("\n========== PROJECT APPLICATIONS ==========");
        boolean found = false;

        for (Application a : applications) {
            if (a.getProjectId() == project.getId()) {
                found = true;
                User applicant = userService.findById(a.getUserId());

                System.out.println("\nApplication ID: " + a.getId());
                System.out.println("Applicant: "
                        + (applicant == null ? "Unknown" : applicant.getName()));

                if (applicant != null) {
                    System.out.println("Skills: "
                            + String.join(", ", applicant.getSkills()));
                }

                System.out.println("Status: " + a.getStatus());
            }
        }

        if (!found) System.out.println("No applications found.");
    }

    public void acceptApplication(Scanner scanner, Project project,
                                   UserService userService) {
        displayProjectApplications(project, userService);

        int applicationId = InputValidator.readInt(
                scanner, "Application ID to accept: ");

        for (Application a : applications) {
            if (a.getId() == applicationId
                    && a.getProjectId() == project.getId()) {

                if (!a.getStatus().equals("PENDING")) {
                    System.out.println("This application is already processed.");
                    return;
                }

                if (project.getTeamMembers().size() >= project.getTeamSize()) {
                    System.out.println("Team is full.");
                    return;
                }

                project.addTeamMember(a.getUserId());
                a.setStatus("ACCEPTED");

                User user = userService.findById(a.getUserId());
                System.out.println((user == null ? "Applicant" : user.getName())
                        + " has been added to the team.");
                return;
            }
        }

        System.out.println("Application not found.");
    }
}
