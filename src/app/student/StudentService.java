package app.student;

import java.sql.SQLException;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();
    public StudentRecord viewOwn(int studentId) throws SQLException { return studentDAO.getStudent(studentId); }
}
