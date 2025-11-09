package app.auth;

public class User {
    public int id;
    public String username;
    public String role;
    public Integer studentId;

    public User(int id, String username, String role, Integer studentId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.studentId = studentId;
    }
}
