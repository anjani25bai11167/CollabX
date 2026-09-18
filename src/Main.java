package collabx;

import collabx.models.*;
import collabx.services.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final ProjectService projectService = new ProjectService();
    private static final ApplicationService applicationService = new ApplicationService();
    private static final TaskService taskService = new TaskService();

    public static void main(String[] args) {
        loadSampleData();

        while (true) {
            System.out.println("\n========================================");
            System.out.println("              COLLABX");
            System.out.println("       Find. Build. Collaborate.");
            System.out.println("========================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Projects");
            System.out.println("4. Search Projects");
            System.out.println("5. Exit");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> projectService.displayProjects();
                case 4 -> projectService.searchProjects(scanner);
                case 5 -> {
                    System.out.println("Thank you for using CollabX!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void register() {
        System.out.println("\n========== REGISTRATION ==========");
        String name = InputValidator.readNonEmpty(scanner, "Name: ");
        String email = InputValidator.readNonEmpty(scanner, "Email: ");
        String password = InputValidator.readNonEmpty(scanner, "Password: ");
        String course = InputValidator.readNonEmpty(scanner, "Course: ");
        String skills = InputValidator.readNonEmpty(
                scanner, "Skills (comma separated): ");

        try {
            User user = userService.register(name, email, password, course, skills);
            System.out.println("Registration successful. User ID: " + user.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.println("\n============== LOGIN ==============");
        String email = InputValidator.readNonEmpty(scanner, "Email: ");
        String password = InputValidator.readNonEmpty(scanner, "Password: ");

        User user = userService.login(email, password);

        if (user == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println("\nWelcome, " + user.getName() + "!");
        dashboard(user);
    }

    private static void dashboard(User user) {
        while (true) {
            System.out.println("\n========== USER DASHBOARD ==========");
            System.out.println("1. View Profile");
            System.out.println("2. Create Project");
            System.out.println("3. View Projects");
            System.out.println("4. Search Projects");
            System.out.println("5. Recommended Projects");
            System.out.println("6. Apply to Project");
            System.out.println("7. My Applications");
            System.out.println("8. Manage My Projects");
            System.out.println("9. My Tasks");
            System.out.println("10. Logout");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");

            switch (choice) {
                case 1 -> userService.displayProfile(user);
                case 2 -> projectService.createProject(scanner, user);
                case 3 -> projectService.displayProjects();
                case 4 -> projectService.searchProjects(scanner);
                case 5 -> projectService.recommendProjects(user);
                case 6 -> applicationService.applyToProject(scanner, user, projectService);
                case 7 -> applicationService.displayUserApplications(user.getId(), projectService);
                case 8 -> manageProjects(user);
                case 9 -> taskService.displayUserTasks(scanner, user.getId());
                case 10 -> {
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageProjects(User user) {
        var owned = projectService.getProjectsByCreator(user.getId());

        if (owned.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        }

        System.out.println("\n========== MY PROJECTS ==========");
        for (Project p : owned) {
            System.out.printf("%d. %s (%d/%d members)%n",
                    p.getId(), p.getTitle(),
                    p.getTeamMembers().size(), p.getTeamSize());
        }

        int id = InputValidator.readInt(scanner, "Project ID: ");
        Project project = projectService.findById(id);

        if (project == null || project.getCreatorId() != user.getId()) {
            System.out.println("Invalid project.");
            return;
        }

        while (true) {
            System.out.println("\nProject: " + project.getTitle());
            System.out.println("1. View Applications");
            System.out.println("2. Accept Application");
            System.out.println("3. Create Task");
            System.out.println("4. View Team");
            System.out.println("5. View Tasks");
            System.out.println("6. Back");

            int choice = InputValidator.readInt(scanner, "Choice: ");

            switch (choice) {
                case 1 -> applicationService.displayProjectApplications(project, userService);
                case 2 -> applicationService.acceptApplication(scanner, project, userService);
                case 3 -> taskService.createTask(scanner, project);
                case 4 -> projectService.displayTeam(project, userService);
                case 5 -> taskService.displayProjectTasks(project.getId(), userService);
                case 6 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void loadSampleData() {
        User rahul = userService.registerSample(
                "Rahul", "rahul@gmail.com", "1234",
                "Computer Science", "java, sql, python");

        User aarav = userService.registerSample(
                "Aarav", "aarav@gmail.com", "1234",
                "Computer Science", "react, javascript, html");

        userService.registerSample(
                "Neha", "neha@gmail.com", "1234",
                "Information Technology", "python, machine learning, sql");

        projectService.addSampleProject(
                "Smart Traffic Prediction",
                "An ML system that predicts traffic congestion.",
                "Artificial Intelligence",
                rahul.getId(), 4,
                "python, machine learning, sql");

        projectService.addSampleProject(
                "Campus Marketplace",
                "A platform for students to buy and sell used products.",
                "Web Development",
                aarav.getId(), 4,
                "react, javascript, sql");
    }
}
