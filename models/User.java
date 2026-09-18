package collabx.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private final String name;
    private final String email;
    private final String password;
    private final String course;
    private final List<String> skills = new ArrayList<>();

    public User(int id, String name, String email, String password, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getCourse() { return course; }
    public List<String> getSkills() { return skills; }

    public void addSkill(String skill) {
        if (!skill.isBlank() && !skills.contains(skill.toLowerCase())) {
            skills.add(skill.toLowerCase());
        }
    }
}
