package app.student;

import java.math.BigDecimal;

public class StudentRecord {
    public int id;
    public String name;
    public String clazz;
    public BigDecimal osMarks, dbMarks, javaMarks;
    public Integer osAtd, dbAtd, javaAtd;
    public Boolean fee;

    @Override
    public String toString() {
        return "ID=" + id + ", Name=" + name + ", Class=" + clazz +
               ", Marks[OS=" + osMarks + ", DB=" + dbMarks + ", Java=" + javaMarks + "]" +
               ", Attendance[OS=" + osAtd + ", DB=" + dbAtd + ", Java=" + javaAtd + "]" +
               ", FeePaid=" + fee;
    }
}
