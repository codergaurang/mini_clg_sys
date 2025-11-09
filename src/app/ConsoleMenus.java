package app;

import app.auth.AuthService;
import app.auth.User;
import app.admin.AdminService;
import app.teacher.TeacherService;
import app.student.StudentService;
import app.student.StudentRecord;
import app.student.StudentDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleMenus {
    private final Scanner in = new Scanner(System.in);
    private final AuthService auth = new AuthService();
    private final AdminService admin = new AdminService();
    private final TeacherService teacher = new TeacherService();
    private final StudentService student = new StudentService();

    public void start() {
        while (true) {
            System.out.println("\n== Login ==");
            System.out.print("Username: ");
            String u = in.nextLine().trim();
            System.out.print("Password: ");
            String p = in.nextLine();
            User user;
            try { user = auth.login(u, p); }
            catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); continue; }
            if (user == null) { System.out.println("Invalid credentials."); continue; }
            switch (user.role) {
                case "ADMIN": adminMenu(); break;
                case "TEACHER": teacherMenu(); break;
                case "STUDENT": studentMenu(user); break;
                default: System.out.println("Unknown role.");
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n-- Admin Menu --");
            System.out.println("1) Add Student");
            System.out.println("2) Remove Student");
            System.out.println("3) Add Teacher");
            System.out.println("4) Remove Teacher");
            System.out.println("5) Create Student Login");
            System.out.println("0) Logout");
            System.out.print("Choice: ");
            String c = in.nextLine().trim();
            try {
                switch (c) {
                    case "1": adminAddStudent(); break;
                    case "2": adminRemoveStudent(); break;
                    case "3": adminAddTeacher(); break;
                    case "4": adminRemoveTeacher(); break;
                    case "5": adminCreateStudentLogin(); break;
                    case "0": return;
                    default: System.out.println("Invalid.");
                }
            } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
        }
    }

    private void adminAddStudent() throws SQLException {
        StudentRecord s = new StudentRecord();
        s.id = readInt("Student ID: ");
        s.name = readStr("Name: ");
        s.clazz = readStr("Class: ");
        s.osMarks = readDecimalNullable("OS Marks (blank skip): ");
        s.dbMarks = readDecimalNullable("DB Marks: ");
        s.javaMarks = readDecimalNullable("Java Marks: ");
        s.osAtd = readIntNullable("OS Attendance: ");
        s.dbAtd = readIntNullable("DB Attendance: ");
        s.javaAtd = readIntNullable("Java Attendance: ");
        s.fee = readBoolNullable("Fee paid? (y/n/blank): ");
        boolean ok = admin.addStudent(s);
        System.out.println(ok ? "Student added." : "Add failed.");
    }

    private void adminRemoveStudent() throws SQLException {
        int id = readInt("Student ID to remove: ");
        boolean ok = admin.removeStudent(id);
        System.out.println(ok ? "Removed." : "Not found.");
    }

    private void adminAddTeacher() throws SQLException {
        String u = readStr("Teacher username: ");
        String p = readStr("Temp password: ");
        boolean ok = admin.addTeacher(u, p);
        System.out.println(ok ? "Teacher added." : "Add failed.");
    }

    private void adminRemoveTeacher() throws SQLException {
        String u = readStr("Teacher username to remove: ");
        boolean ok = admin.removeTeacher(u);
        System.out.println(ok ? "Removed." : "Not found.");
    }

    private void adminCreateStudentLogin() throws SQLException {
        String u = readStr("Student username: ");
        String p = readStr("Temp password: ");
        int sid = readInt("Link to Student ID: ");
        boolean ok = admin.addStudentUser(u, p, sid);
        System.out.println(ok ? "Student login created." : "Failed.");
    }

    private void teacherMenu() {
        while (true) {
            System.out.println("\n-- Teacher Menu --");
            System.out.println("1) Update Marks");
            System.out.println("2) Update Attendance");
            System.out.println("3) View Student");
            System.out.println("0) Logout");
            System.out.print("Choice: ");
            String c = in.nextLine().trim();
            try {
                switch (c) {
                    case "1": tUpdateMarks(); break;
                    case "2": tUpdateAttendance(); break;
                    case "3": tViewStudent(); break;
                    case "0": return;
                    default: System.out.println("Invalid.");
                }
            } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
        }
    }

    private void tUpdateMarks() throws SQLException {
        int id = readInt("Student ID: ");
        BigDecimal os = readDecimalNullable("OS Marks (blank keep): ");
        BigDecimal db = readDecimalNullable("DB Marks (blank keep): ");
        BigDecimal jv = readDecimalNullable("Java Marks (blank keep): ");
        if (os == null && db == null && jv == null) { System.out.println("Nothing to update."); return; }
        StudentRecord cur = new StudentDAO().getStudent(id);
        if (cur == null) { System.out.println("Not found."); return; }
        boolean ok = new TeacherService().updateMarks(
            id,
            os != null ? os : cur.osMarks,
            db != null ? db : cur.dbMarks,
            jv != null ? jv : cur.javaMarks
        );
        System.out.println(ok ? "Marks updated." : "Update failed.");
    }

    private void tUpdateAttendance() throws SQLException {
        int id = readInt("Student ID: ");
        Integer os = readIntNullable("OS Attendance (blank keep): ");
        Integer db = readIntNullable("DB Attendance (blank keep): ");
        Integer jv = readIntNullable("Java Attendance (blank keep): ");
        if (os == null && db == null && jv == null) { System.out.println("Nothing to update."); return; }
        StudentRecord cur = new StudentDAO().getStudent(id);
        if (cur == null) { System.out.println("Not found."); return; }
        boolean ok = new TeacherService().updateAttendance(
            id,
            os != null ? os : cur.osAtd,
            db != null ? db : cur.dbAtd,
            jv != null ? jv : cur.javaAtd
        );
        System.out.println(ok ? "Attendance updated." : "Update failed.");
    }

    private void tViewStudent() throws SQLException {
        int id = readInt("Student ID: ");
        StudentRecord s = new TeacherService().view(id);
        System.out.println(s == null ? "Not found." : s.toString());
    }

    private void studentMenu(User user) {
        while (true) {
            System.out.println("\n-- Student Menu --");
            System.out.println("1) View My Record");
            System.out.println("0) Logout");
            System.out.print("Choice: ");
            String c = in.nextLine().trim();
            try {
                switch (c) {
                    case "1":
                        if (user.studentId == null) { System.out.println("No linked student ID."); break; }
                        StudentRecord s = student.viewOwn(user.studentId);
                        System.out.println(s == null ? "Not found." : s.toString());
                        break;
                    case "0": return;
                    default: System.out.println("Invalid.");
                }
            } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
        }
    }

    // Input helpers
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String t = in.nextLine().trim();
            try { return Integer.parseInt(t); } catch (Exception e) { System.out.println("Enter integer."); }
        }
    }
    private Integer readIntNullable(String prompt) {
        System.out.print(prompt);
        String t = in.nextLine().trim();
        if (t.isEmpty()) return null;
        try { return Integer.parseInt(t); } catch (Exception e) { System.out.println("Invalid, skipping."); return null; }
    }
    private String readStr(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }
    private java.math.BigDecimal readDecimalNullable(String prompt) {
        System.out.print(prompt);
        String t = in.nextLine().trim();
        if (t.isEmpty()) return null;
        try { return new java.math.BigDecimal(t); } catch (Exception e) { System.out.println("Invalid, skipping."); return null; }
    }
    private Boolean readBoolNullable(String prompt) {
        System.out.print(prompt);
        String t = in.nextLine().trim().toLowerCase();
        if (t.isEmpty()) return null;
        if (t.startsWith("y")) return true;
        if (t.startsWith("n")) return false;
        System.out.println("Invalid, skipping.");
        return null;
    }
}
