package app.admin;

import app.student.StudentDAO;
import app.student.StudentRecord;
import app.auth.AuthService;
import java.sql.SQLException;

public class AdminService {
    private final StudentDAO studentDAO = new StudentDAO();
    private final AuthService auth = new AuthService();

    public boolean addStudent(StudentRecord s) throws SQLException { return studentDAO.addStudent(s); }
    public boolean removeStudent(int id) throws SQLException { return studentDAO.removeStudent(id); }

    public boolean addTeacher(String username, String password) throws SQLException {
        return auth.createUser(username, password, "TEACHER", null);
    }
    public boolean removeTeacher(String username) throws SQLException {
        return auth.deleteUser(username, "TEACHER");
    }
    public boolean addStudentUser(String username, String password, int studentId) throws SQLException {
        return auth.createUser(username, password, "STUDENT", studentId);
    }
}
