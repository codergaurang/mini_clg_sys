package app.auth;

import app.db.DB;
import java.sql.*;

public class AuthService {
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT id, username, role, student_id FROM users WHERE username = ? AND password = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                int id = rs.getInt("id");
                String role = rs.getString("role");
                int sid = rs.getInt("student_id");
                Integer studentId = rs.wasNull() ? null : sid;
                return new User(id, username, role, studentId);
            }
        }
    }

    public boolean createUser(String username, String rawPassword, String role, Integer studentId) throws SQLException {
        String sql = "INSERT INTO users(username, password, role, student_id) VALUES(?,?,?,?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, rawPassword);
            ps.setString(3, role);
            if (studentId == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, studentId);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteUser(String username, String role) throws SQLException {
        String sql = "DELETE FROM users WHERE username=? AND role=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, role);
            return ps.executeUpdate() == 1;
        }
    }
}
