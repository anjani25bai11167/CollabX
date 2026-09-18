# CollabX — Project Collaboration Platform

> **Find. Build. Collaborate.**

CollabX is a Java-based project collaboration platform designed for students who want to create projects, discover teammates, find projects matching their skills, manage applications, form teams, and track project tasks.

## Problem Statement

Students frequently have project ideas but struggle to find teammates with the right technical skills. At the same time, students who want to participate in projects do not have a centralized way to discover suitable opportunities.

CollabX provides a structured platform that connects students with projects based on their skills and interests.

## Objectives

- Allow students to create and manage profiles.
- Allow users to create and discover projects.
- Match students with projects using their skills.
- Allow students to apply to projects.
- Allow project creators to accept applicants and form teams.
- Provide task assignment and progress tracking.
- Demonstrate Java OOP, collections, modular programming, validation, and basic algorithmic logic.

## Main Functional Modules

### 1. User Management
- Registration
- Login
- Profile display
- Skill management

### 2. Project Management
- Create projects
- View projects
- Search projects
- Required-skill definition
- Team-size management

### 3. Skill Matching & Recommendations
The system compares a user's skills with the required skills of a project.

**Formula:**

`Match Percentage = (Matching Skills / Required Skills) × 100`

### 4. Application & Team Management
- Apply to projects
- View applications
- Accept applications
- Form project teams

### 5. Task Management
- Create tasks
- Assign tasks to team members
- Set deadlines
- Update task status
- View project tasks

## Technology Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- ArrayList
- Scanner
- Git / GitHub

## Project Structure

```text
CollabX/
│
├── src/
│   ├── Main.java
│   │
│   ├── models/
│   │   ├── User.java
│   │   ├── Project.java
│   │   ├── Application.java
│   │   └── Task.java
│   │
│   ├── services/
│   │   ├── UserService.java
│   │   ├── ProjectService.java
│   │   ├── ApplicationService.java
│   │   └── TaskService.java
│   │
│   └── utils/
│       └── InputValidator.java
│
└── README.md
```

## Requirements

Install:

- JDK 17 or later
- Any Java IDE such as IntelliJ IDEA, Eclipse, NetBeans, or VS Code

## How to Run

### Using terminal

From the `src` parent directory:

```bash
javac -d out src/Main.java src/models/*.java src/services/*.java src/utils/*.java
```

Run:

```bash
java -cp out collabx.Main
```

> If your IDE is used, mark `src` as the source folder and run `Main.java`.

## Sample Login Accounts

The application starts with sample data.

| Name | Email | Password |
|---|---|---|
| Rahul | rahul@gmail.com | 1234 |
| Aarav | aarav@gmail.com | 1234 |
| Neha | neha@gmail.com | 1234 |

## Example Projects

### Smart Traffic Prediction
Required skills:
- Python
- Machine Learning
- SQL

### Campus Marketplace
Required skills:
- React
- JavaScript
- SQL

## Example Workflow

```text
Register
   ↓
Create Profile
   ↓
Add Skills
   ↓
Explore Projects
   ↓
Skill Matching
   ↓
Apply to Project
   ↓
Project Creator Reviews Application
   ↓
Application Accepted
   ↓
Team Formation
   ↓
Tasks Assigned
   ↓
Tasks Completed
   ↓
Project Completed
```

## Object-Oriented Concepts Demonstrated

### Encapsulation
Model classes keep their fields private and expose controlled getters/setters.

### Classes and Objects
The project uses separate classes for:

- User
- Project
- Application
- Task

### Abstraction
Business operations are separated into service classes.

### Modular Design
The application separates:

- Models
- Services
- Utilities
- Main application logic

## Error Handling

The application validates:

- Empty input
- Invalid numeric input
- Duplicate email registration
- Invalid project IDs
- Invalid team members
- Full project teams
- Duplicate applications

## Future Enhancements

The current version is a console-based prototype. Future versions can include:

- MySQL database using JDBC
- Java Swing or JavaFX GUI
- Web interface
- Secure password hashing
- Chat/messaging
- Email notifications
- GitHub API integration
- Advanced recommendation algorithm
- Project ratings and reviews
- Admin dashboard
- File/document sharing

## Academic Design Artifacts

For the VITyarthi project report, the following can be prepared from this implementation:

- Problem Statement
- Objectives
- Functional Requirements
- Non-functional Requirements
- System Architecture Diagram
- Workflow Diagram
- Use Case Diagram
- Class Diagram
- Sequence Diagram
- ER Diagram for the future database version
- Testing Approach
- Screenshots
- Future Enhancements

## Non-Functional Requirements

### Performance
The application should respond quickly for normal student/project data volumes.

### Usability
The menu-driven interface should provide clear choices and understandable messages.

### Reliability
Invalid inputs should not terminate the application unexpectedly.

### Maintainability
The application is divided into model, service, and utility packages to make future modifications easier.

## Author

**CollabX — Student Project**

Developed as a Java academic project.
