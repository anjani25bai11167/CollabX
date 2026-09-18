package collabx.services;

import collabx.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    public User register(String name, String email, String password,
                         String course, String skills) {
        if (findByEmail(email) != null) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        User user = new User(nextId++, name, email, password, course);
        addSkills(user, skills);
        users.add(user);
        return user;
    }

    public User registerSample(String name, String email, String password,
                               String course, String skills) {
        User existing = findByEmail(email);
        if (existing != null) return existing;
        return register(name, email, password, course, skills);
    }

    private void addSkills(User user, String skills) {
        for (String skill : skills.split(",")) {
            user.addSkill(skill.trim());
        }
    }

    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) return user;
        }
        return null;
    }

    public void displayProfile(User user) {
        System.out.println("\n========== PROFILE ==========");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Course: " + user.getCourse());
        System.out.println("Skills: " + String.join(", ", user.getSkills()));
    }
}
