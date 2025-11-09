package app.auth;

import app.db.DB;
import java.sql.*;

public class SeedAdmin {
    public static void main(String[] args) throws Exception {
        String username = "admin";
        String raw = "admin123";
        String sql = "INSERT INTO users(username, password, role, student_id) VALUES(?,?, 'ADMIN', NULL)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, raw);
            ps.executeUpdate();
            System.out.println("Admin seeded: " + username + " / " + raw);
        }
    }
}
