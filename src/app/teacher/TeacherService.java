package app.teacher;

import app.student.StudentDAO;
import app.student.StudentRecord;
import java.math.BigDecimal;
import java.sql.SQLException;

public class TeacherService {
    private final StudentDAO studentDAO = new StudentDAO();
    public boolean updateMarks(int id, BigDecimal os, BigDecimal db, BigDecimal java) throws SQLException {
        return studentDAO.updateMarks(id, os, db, java);
    }
    public boolean updateAttendance(int id, Integer osAtd, Integer dbAtd, Integer javaAtd) throws SQLException {
        return studentDAO.updateAttendance(id, osAtd, dbAtd, javaAtd);
    }
    public StudentRecord view(int id) throws SQLException { return studentDAO.getStudent(id); }
}
