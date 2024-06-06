package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AboutDatabase {
    private static Connection conn = Lantern.getConn();
    public static void addOrUpdateAbout(String username, String about) {
        String selectSql = "SELECT COUNT(*) FROM About WHERE username = ?";
        String updateSql = "UPDATE About SET about = ? WHERE username = ?";
        String insertSql = "INSERT INTO About (username, about) VALUES (?, ?)";
        try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
            selectPstmt.setString(1, username);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                    updatePstmt.setString(1, about);
                    updatePstmt.setString(2, username);
                    updatePstmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                    insertPstmt.setString(1, username);
                    insertPstmt.setString(2, about);
                    insertPstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getAbout(String username) throws SQLException {
        String sql = "SELECT about FROM About WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("about");
            } else {
                return "";
            }
        }
    }
}
