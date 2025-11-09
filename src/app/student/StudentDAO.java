package app.student;

import app.db.DB;
import java.sql.*;
import java.math.BigDecimal;

public class StudentDAO {
    public boolean addStudent(StudentRecord s) throws SQLException {
        String sql = "INSERT INTO studata(ID, Name, Class, os_marks, db_marks, java_marks, os_atd, db_atd, java_atd, fee) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.id);
            ps.setString(2, s.name);
            ps.setString(3, s.clazz);
            psSetDecimal(ps, 4, s.osMarks);
            psSetDecimal(ps, 5, s.dbMarks);
            psSetDecimal(ps, 6, s.javaMarks);
            psSetInt(ps, 7, s.osAtd);
            psSetInt(ps, 8, s.dbAtd);
            psSetInt(ps, 9, s.javaAtd);
            if (s.fee == null) ps.setNull(10, Types.TINYINT); else ps.setBoolean(10, s.fee);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean removeStudent(int id) throws SQLException {
        String sql = "DELETE FROM studata WHERE ID=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public StudentRecord getStudent(int id) throws SQLException {
        String sql = "SELECT ID, Name, Class, os_marks, db_marks, java_marks, os_atd, db_atd, java_atd, fee FROM studata WHERE ID=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                StudentRecord s = new StudentRecord();
                s.id = rs.getInt("ID");
                s.name = rs.getString("Name");
                s.clazz = rs.getString("Class");
                s.osMarks = rs.getBigDecimal("os_marks");
                s.dbMarks = rs.getBigDecimal("db_marks");
                s.javaMarks = rs.getBigDecimal("java_marks");
                s.osAtd = getNullableInt(rs, "os_atd");
                s.dbAtd = getNullableInt(rs, "db_atd");
                s.javaAtd = getNullableInt(rs, "java_atd");
                boolean feeVal = rs.getBoolean("fee");
                s.fee = rs.wasNull() ? null : feeVal;
                return s;
            }
        }
    }

    public boolean updateMarks(int id, BigDecimal os, BigDecimal db, BigDecimal java) throws SQLException {
        String sql = "UPDATE studata SET os_marks=?, db_marks=?, java_marks=? WHERE ID=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            psSetDecimal(ps, 1, os);
            psSetDecimal(ps, 2, db);
            psSetDecimal(ps, 3, java);
            ps.setInt(4, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateAttendance(int id, Integer osAtd, Integer dbAtd, Integer javaAtd) throws SQLException {
        String sql = "UPDATE studata SET os_atd=?, db_atd=?, java_atd=? WHERE ID=?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            psSetInt(ps, 1, osAtd);
            psSetInt(ps, 2, dbAtd);
            psSetInt(ps, 3, javaAtd);
            ps.setInt(4, id);
            return ps.executeUpdate() == 1;
        }
    }

    private static void psSetDecimal(PreparedStatement ps, int idx, BigDecimal v) throws SQLException {
        if (v == null) ps.setNull(idx, Types.DECIMAL); else ps.setBigDecimal(idx, v);
    }
    private static void psSetInt(PreparedStatement ps, int idx, Integer v) throws SQLException {
        if (v == null) ps.setNull(idx, Types.INTEGER); else ps.setInt(idx, v);
    }
    private static Integer getNullableInt(ResultSet rs, String col) throws SQLException {
        int v = rs.getInt(col);
        return rs.wasNull() ? null : v;
    }
}
