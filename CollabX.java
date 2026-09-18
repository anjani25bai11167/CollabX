import java.util.*;

/*
 * ============================================================
 * COLLABX - PROJECT COLLABORATION PLATFORM
 * ============================================================
 * A Java console-based project collaboration platform.
 *
 * Main Features:
 * 1. User Registration & Profiles
 * 2. Project Creation & Management
 * 3. Project Search
 * 4. Skill Matching
 * 5. Project Applications
 * 6. Team Formation
 * 7. Task Management
 * ============================================================
 */

public class CollabX {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Project> projects = new ArrayList<>();
    static ArrayList<Application> applications = new ArrayList<>();
    static ArrayList<Task> tasks = new ArrayList<>();

    static int userIdCounter = 1;
    static int projectIdCounter = 1;
    static int applicationIdCounter = 1;
    static int taskIdCounter = 1;

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        addSampleData();

        while (true) {

            System.out.println("\n======================================");
            System.out.println("          COLLABX PLATFORM");
            System.out.println("       Find. Build. Collaborate.");
            System.out.println("======================================");

            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Projects");
            System.out.println("4. Search Projects");
            System.out.println("5. Exit");

            int choice = getInt("Enter choice: ");

            switch (choice) {

                case 1:
                    registerUser();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    viewProjects();
                    break;

                case 4:
                    searchProjects();
                    break;

                case 5:
                    System.out.println("Thank you for using CollabX!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // USER REGISTRATION
    // =========================================================

    static void registerUser() {

        System.out.println("\n========== USER REGISTRATION ==========");

        String name = getString("Enter name: ");
        String email = getString("Enter email: ");
        String password = getString("Enter password: ");
        String course = getString("Enter course: ");

        User user = new User(
                userIdCounter++,
                name,
                email,
                password,
                course
        );

        System.out.println("\nEnter your skills.");
        System.out.println("Example: Java, Python, SQL");

        String skillInput = getString("Skills: ");

        String[] skillArray = skillInput.split(",");

        for (String skill : skillArray) {
            user.skills.add(skill.trim().toLowerCase());
        }

        users.add(user);

        System.out.println("\nRegistration successful!");
        System.out.println("Your User ID: " + user.id);
    }

    // =========================================================
    // LOGIN
    // =========================================================

    static void login() {

        System.out.println("\n============== LOGIN ==============");

        String email = getString("Email: ");
        String password = getString("Password: ");

        User loggedInUser = null;

        for (User user : users) {

            if (user.email.equalsIgnoreCase(email)
                    && user.password.equals(password)) {

                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser == null) {

            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println("\nWelcome, " + loggedInUser.name + "!");

        userDashboard(loggedInUser);
    }

    // =========================================================
    // USER DASHBOARD
    // =========================================================

    static void userDashboard(User user) {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("             USER DASHBOARD");
            System.out.println("======================================");

            System.out.println("1. View Profile");
            System.out.println("2. Create Project");
            System.out.println("3. View All Projects");
            System.out.println("4. Search Projects");
            System.out.println("5. Get Project Recommendations");
            System.out.println("6. Apply to Project");
            System.out.println("7. View My Applications");
            System.out.println("8. Manage My Projects");
            System.out.println("9. View My Tasks");
            System.out.println("10. Logout");

            int choice = getInt("Enter choice: ");

            switch (choice) {

                case 1:
                    viewProfile(user);
                    break;

                case 2:
                    createProject(user);
                    break;

                case 3:
                    viewProjects();
                    break;

                case 4:
                    searchProjects();
                    break;

                case 5:
                    recommendProjects(user);
                    break;

                case 6:
                    applyToProject(user);
                    break;

                case 7:
                    viewApplications(user);
                    break;

                case 8:
                    manageProjects(user);
                    break;

                case 9:
                    viewTasks(user);
                    break;

                case 10:
                    System.out.println("Logged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // PROFILE
    // =========================================================

    static void viewProfile(User user) {

        System.out.println("\n========== MY PROFILE ==========");

        System.out.println("User ID: " + user.id);
        System.out.println("Name: " + user.name);
        System.out.println("Email: " + user.email);
        System.out.println("Course: " + user.course);

        System.out.println("Skills:");

        for (String skill : user.skills) {
            System.out.println("- " + skill);
        }
    }

    // =========================================================
    // CREATE PROJECT
    // =========================================================

    static void createProject(User creator) {

        System.out.println("\n========== CREATE PROJECT ==========");

        String title = getString("Project title: ");
        String description = getString("Description: ");
        String category = getString("Category: ");

        int teamSize = getInt("Maximum team size: ");

        String skillInput =
                getString("Required skills (comma separated): ");

        Project project = new Project(
                projectIdCounter++,
                title,
                description,
                category,
                creator.id,
                teamSize
        );

        String[] skillArray = skillInput.split(",");

        for (String skill : skillArray) {
            project.requiredSkills.add(
                    skill.trim().toLowerCase()
            );
        }

        // Creator automatically becomes first team member
        project.teamMembers.add(creator.id);

        projects.add(project);

        System.out.println("\nProject created successfully!");
        System.out.println("Project ID: " + project.id);
    }

    // =========================================================
    // VIEW PROJECTS
    // =========================================================

    static void viewProjects() {

        System.out.println("\n========== AVAILABLE PROJECTS ==========");

        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        for (Project p : projects) {

            System.out.println("\n--------------------------------------");

            System.out.println("Project ID: " + p.id);
            System.out.println("Title: " + p.title);
            System.out.println("Category: " + p.category);

            System.out.println("Description: " + p.description);

            System.out.println(
                    "Team: "
                    + p.teamMembers.size()
                    + "/"
                    + p.teamSize
            );

            System.out.println(
                    "Required Skills: "
                    + String.join(", ", p.requiredSkills)
            );

            System.out.println(
                    "Status: " + p.status
            );
        }
    }

    // =========================================================
    // SEARCH PROJECTS
    // =========================================================

    static void searchProjects() {

        System.out.println("\n========== SEARCH PROJECTS ==========");

        String keyword =
                getString("Enter skill/category/title: ")
                        .toLowerCase();

        boolean found = false;

        for (Project p : projects) {

            boolean matches =
                    p.title.toLowerCase().contains(keyword)
                    || p.category.toLowerCase().contains(keyword)
                    || p.description.toLowerCase().contains(keyword);

            for (String skill : p.requiredSkills) {

                if (skill.contains(keyword)) {
                    matches = true;
                }
            }

            if (matches) {

                found = true;

                System.out.println("\nProject ID: " + p.id);
                System.out.println("Title: " + p.title);
                System.out.println("Category: " + p.category);
                System.out.println(
                        "Skills: "
                        + String.join(", ", p.requiredSkills)
                );
            }
        }

        if (!found) {
            System.out.println("No matching projects found.");
        }
    }

    // =========================================================
    // PROJECT RECOMMENDATION
    // =========================================================

    static void recommendProjects(User user) {

        System.out.println(
                "\n========== PROJECT RECOMMENDATIONS =========="
        );

        boolean found = false;

        for (Project project : projects) {

            if (project.teamMembers.size()
                    >= project.teamSize) {
                continue;
            }

            int match = calculateMatch(user, project);

            if (match > 0) {

                found = true;

                System.out.println("\n--------------------------------------");

                System.out.println(
                        "Project: " + project.title
                );

                System.out.println(
                        "Skill Match: " + match + "%"
                );

                System.out.println(
                        "Category: " + project.category
                );

                System.out.println(
                        "Required Skills: "
                        + String.join(
                                ", ",
                                project.requiredSkills
                        )
                );
            }
        }

        if (!found) {
            System.out.println(
                    "No suitable projects found."
            );
        }
    }

    // =========================================================
    // MATCHING ALGORITHM
    // =========================================================

    static int calculateMatch(User user, Project project) {

        if (project.requiredSkills.isEmpty()) {
            return 0;
        }

        int matchingSkills = 0;

        for (String requiredSkill :
                project.requiredSkills) {

            if (user.skills.contains(
                    requiredSkill.toLowerCase())) {

                matchingSkills++;
            }
        }

        return (matchingSkills * 100)
                / project.requiredSkills.size();
    }

    // =========================================================
    // APPLY TO PROJECT
    // =========================================================

    static void applyToProject(User user) {

        viewProjects();

        int projectId =
                getInt("\nEnter Project ID: ");

        Project project = findProject(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.teamMembers.contains(user.id)) {

            System.out.println(
                    "You are already part of this project."
            );

            return;
        }

        if (project.teamMembers.size()
                >= project.teamSize) {

            System.out.println(
                    "This project has reached its team limit."
            );

            return;
        }

        // Check duplicate application
        for (Application a : applications) {

            if (a.projectId == projectId
                    && a.userId == user.id
                    && a.status.equals("PENDING")) {

                System.out.println(
                        "You have already applied."
                );

                return;
            }
        }

        String message =
                getString("Application message: ");

        Application application =
                new Application(
                        applicationIdCounter++,
                        projectId,
                        user.id,
                        message
                );

        applications.add(application);

        System.out.println(
                "Application submitted successfully!"
        );
    }

    // =========================================================
    // VIEW APPLICATIONS
    // =========================================================

    static void viewApplications(User user) {

        System.out.println(
                "\n========== MY APPLICATIONS =========="
        );

        boolean found = false;

        for (Application a : applications) {

            if (a.userId == user.id) {

                found = true;

                Project p = findProject(a.projectId);

                System.out.println("\nApplication ID: "
                        + a.id);

                System.out.println(
                        "Project: " + p.title
                );

                System.out.println(
                        "Message: " + a.message
                );

                System.out.println(
                        "Status: " + a.status
                );
            }
        }

        if (!found) {
            System.out.println(
                    "You have no applications."
            );
        }
    }

    // =========================================================
    // MANAGE PROJECTS
    // =========================================================

    static void manageProjects(User user) {

        ArrayList<Project> myProjects =
                new ArrayList<>();

        for (Project p : projects) {

            if (p.creatorId == user.id) {
                myProjects.add(p);
            }
        }

        if (myProjects.isEmpty()) {

            System.out.println(
                    "You haven't created any projects."
            );

            return;
        }

        System.out.println(
                "\n========== MY PROJECTS =========="
        );

        for (Project p : myProjects) {

            System.out.println(
                    p.id + ". " + p.title
            );
        }

        int projectId =
                getInt("Enter Project ID: ");

        Project project = findProject(projectId);

        if (project == null
                || project.creatorId != user.id) {

            System.out.println(
                    "Invalid project."
            );

            return;
        }

        while (true) {

            System.out.println(
                    "\nProject: " + project.title
            );

            System.out.println("1. View Applications");
            System.out.println("2. Accept Application");
            System.out.println("3. Create Task");
            System.out.println("4. View Team");
            System.out.println("5. Back");

            int choice =
                    getInt("Choice: ");

            switch (choice) {

                case 1:
                    viewProjectApplications(project);
                    break;

                case 2:
                    acceptApplication(project);
                    break;

                case 3:
                    createTask(project);
                    break;

                case 4:
                    viewTeam(project);
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // =========================================================
    // VIEW PROJECT APPLICATIONS
    // =========================================================

    static void viewProjectApplications(
            Project project) {

        System.out.println(
                "\n========== APPLICATIONS =========="
        );

        boolean found = false;

        for (Application a : applications) {

            if (a.projectId == project.id) {

                found = true;

                User applicant =
                        findUser(a.userId);

                System.out.println(
                        "\nApplication ID: "
                        + a.id
                );

                System.out.println(
                        "Applicant: "
                        + applicant.name
                );

                System.out.println(
                        "Skills: "
                        + String.join(
                                ", ",
                                applicant.skills
                        )
                );

                System.out.println(
                        "Match: "
                        + calculateMatch(
                                applicant,
                                project
                        )
                        + "%"
                );

                System.out.println(
                        "Status: "
                        + a.status
                );
            }
        }

        if (!found) {
            System.out.println(
                    "No applications found."
            );
        }
    }

    // =========================================================
    // ACCEPT APPLICATION
    // =========================================================

    static void acceptApplication(
            Project project) {

        viewProjectApplications(project);

        int applicationId =
                getInt(
                        "\nEnter Application ID: "
                );

        Application application = null;

        for (Application a : applications) {

            if (a.id == applicationId
                    && a.projectId == project.id) {

                application = a;
                break;
            }
        }

        if (application == null) {

            System.out.println(
                    "Application not found."
            );

            return;
        }

        if (project.teamMembers.size()
                >= project.teamSize) {

            System.out.println(
                    "Team is already full."
            );

            return;
        }

        User applicant =
                findUser(application.userId);

        project.teamMembers.add(applicant.id);

        application.status = "ACCEPTED";

        System.out.println(
                applicant.name
                + " has been added to the team."
        );
    }

    // =========================================================
    // VIEW TEAM
    // =========================================================

    static void viewTeam(Project project) {

        System.out.println(
                "\n========== PROJECT TEAM =========="
        );

        for (int userId : project.teamMembers) {

            User user = findUser(userId);

            System.out.println(
                    "• "
                    + user.name
                    + " | "
                    + String.join(
                            ", ",
                            user.skills
                    )
            );
        }
    }

    // =========================================================
    // CREATE TASK
    // =========================================================

    static void createTask(Project project) {

        viewTeam(project);

        String title =
                getString("Task title: ");

        String description =
                getString("Task description: ");

        int assignedUser =
                getInt(
                        "Enter User ID to assign task: "
                );

        User user =
                findUser(assignedUser);

        if (user == null
                || !project.teamMembers
                .contains(assignedUser)) {

            System.out.println(
                    "Invalid team member."
            );

            return;
        }

        String deadline =
                getString(
                        "Deadline (DD-MM-YYYY): "
                );

        Task task =
                new Task(
                        taskIdCounter++,
                        project.id,
                        title,
                        description,
                        assignedUser,
                        deadline
                );

        tasks.add(task);

        System.out.println(
                "Task created successfully!"
        );
    }

    // =========================================================
    // VIEW TASKS
    // =========================================================

    static void viewTasks(User user) {

        System.out.println(
                "\n========== MY TASKS =========="
        );

        boolean found = false;

        for (Task task : tasks) {

            if (task.assignedTo == user.id) {

                found = true;

                System.out.println(
                        "\nTask ID: "
                        + task.id
                );

                System.out.println(
                        "Title: "
                        + task.title
                );

                System.out.println(
                        "Description: "
                        + task.description
                );

                System.out.println(
                        "Deadline: "
                        + task.deadline
                );

                System.out.println(
                        "Status: "
                        + task.status
                );

                System.out.println(
                        "1. Mark as IN PROGRESS"
                );

                System.out.println(
                        "2. Mark as COMPLETED"
                );

                System.out.println(
                        "3. Skip"
                );

                int choice =
                        getInt("Choice: ");

                if (choice == 1) {
                    task.status = "IN PROGRESS";
                }

                else if (choice == 2) {
                    task.status = "COMPLETED";
                }
            }
        }

        if (!found) {

            System.out.println(
                    "You have no assigned tasks."
            );
        }
    }

    // =========================================================
    // FIND USER
    // =========================================================

    static User findUser(int id) {

        for (User user : users) {

            if (user.id == id) {
                return user;
            }
        }

        return null;
    }

    // =========================================================
    // FIND PROJECT
    // =========================================================

    static Project findProject(int id) {

        for (Project project : projects) {

            if (project.id == id) {
                return project;
            }
        }

        return null;
    }

    // =========================================================
    // INPUT HELPERS
    // =========================================================

    static String getString(String message) {

        System.out.print(message);

        return sc.nextLine();
    }

    static int getInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        sc.nextLine()
                );

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    // =========================================================
    // SAMPLE DATA
    // =========================================================

    static void addSampleData() {

        User u1 = new User(
                userIdCounter++,
                "Rahul",
                "rahul@gmail.com",
                "1234",
                "Computer Science"
        );

        u1.skills.add("java");
        u1.skills.add("sql");
        u1.skills.add("python");

        User u2 = new User(
                userIdCounter++,
                "Aarav",
                "aarav@gmail.com",
                "1234",
                "Computer Science"
        );

        u2.skills.add("react");
        u2.skills.add("javascript");
        u2.skills.add("html");

        User u3 = new User(
                userIdCounter++,
                "Neha",
                "neha@gmail.com",
                "1234",
                "Information Technology"
        );

        u3.skills.add("python");
        u3.skills.add("machine learning");
        u3.skills.add("sql");

        users.add(u1);
        users.add(u2);
        users.add(u3);

        Project p1 = new Project(
                projectIdCounter++,
                "Smart Traffic Prediction",
                "An ML system that predicts traffic congestion.",
                "Artificial Intelligence",
                u1.id,
                4
        );

        p1.requiredSkills.add("python");
        p1.requiredSkills.add("machine learning");
        p1.requiredSkills.add("sql");

        p1.teamMembers.add(u1.id);

        Project p2 = new Project(
                projectIdCounter++,
                "Campus Marketplace",
                "Platform for students to buy and sell used products.",
                "Web Development",
                u2.id,
                4
        );

        p2.requiredSkills.add("react");
        p2.requiredSkills.add("javascript");
        p2.requiredSkills.add("sql");

        p2.teamMembers.add(u2.id);

        projects.add(p1);
        projects.add(p2);
    }
}


// =============================================================
// USER CLASS
// =============================================================

class User {

    int id;
    String name;
    String email;
    String password;
    String course;

    ArrayList<String> skills =
            new ArrayList<>();

    User(
            int id,
            String name,
            String email,
            String password,
            String course) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
    }
}


// =============================================================
// PROJECT CLASS
// =============================================================

class Project {

    int id;

    String title;
    String description;
    String category;

    int creatorId;
    int teamSize;

    String status = "OPEN";

    ArrayList<String> requiredSkills =
            new ArrayList<>();

    ArrayList<Integer> teamMembers =
            new ArrayList<>();

    Project(
            int id,
            String title,
            String description,
            String category,
            int creatorId,
            int teamSize) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.creatorId = creatorId;
        this.teamSize = teamSize;
    }
}


// =============================================================
// APPLICATION CLASS
// =============================================================

class Application {

    int id;
    int projectId;
    int userId;

    String message;
    String status = "PENDING";

    Application(
            int id,
            int projectId,
            int userId,
            String message) {

        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.message = message;
    }
}


// =============================================================
// TASK CLASS
// =============================================================

class Task {

    int id;
    int projectId;

    String title;
    String description;

    int assignedTo;

    String deadline;
    String status = "PENDING";

    Task(
            int id,
            int projectId,
            String title,
            String description,
            int assignedTo,
            String deadline) {

        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.deadline = deadline;
    }
}
