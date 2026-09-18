package collabx.services;

import collabx.models.Project;
import collabx.models.Task;
import collabx.models.User;
import collabx.utils.InputValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public void createTask(Scanner scanner, Project project) {
        System.out.println("\n========== CREATE TASK ==========");

        String title = InputValidator.readNonEmpty(scanner, "Title: ");
        String description = InputValidator.readNonEmpty(scanner, "Description: ");
        int userId = InputValidator.readInt(scanner, "Assign to User ID: ");

        if (!project.getTeamMembers().contains(userId)) {
            System.out.println("User is not a member of this project.");
            return;
        }

        String deadline = InputValidator.readNonEmpty(
                scanner, "Deadline (DD-MM-YYYY): ");

        tasks.add(new Task(
                nextId++, project.getId(), title,
                description, userId, deadline));

        System.out.println("Task created successfully.");
    }

    public void displayUserTasks(Scanner scanner, int userId) {
        System.out.println("\n========== MY TASKS ==========");
        boolean found = false;

        for (Task task : tasks) {
            if (task.getAssignedTo() == userId) {
                found = true;
                printTask(task);

                System.out.println("1. Mark IN PROGRESS");
                System.out.println("2. Mark COMPLETED");
                System.out.println("3. Skip");

                int choice = InputValidator.readInt(scanner, "Choice: ");
                if (choice == 1) task.setStatus("IN PROGRESS");
                else if (choice == 2) task.setStatus("COMPLETED");
            }
        }

        if (!found) System.out.println("No tasks assigned to you.");
    }

    public void displayProjectTasks(int projectId, UserService userService) {
        System.out.println("\n========== PROJECT TASKS ==========");
        boolean found = false;

        for (Task task : tasks) {
            if (task.getProjectId() == projectId) {
                found = true;
                printTask(task);
                User user = userService.findById(task.getAssignedTo());
                System.out.println("Assigned To: "
                        + (user == null ? "Unknown" : user.getName()));
            }
        }

        if (!found) System.out.println("No tasks found.");
    }

    private void printTask(Task task) {
        System.out.println("----------------------------------------");
        System.out.println("Task ID: " + task.getId());
        System.out.println("Title: " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Deadline: " + task.getDeadline());
        System.out.println("Status: " + task.getStatus());
    }
}
