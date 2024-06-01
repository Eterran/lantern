package GUI;

import java.sql.*;

public class DiscussionDatabase {
    private static Connection conn = Lantern.getConn();

    public static void insertPost(String title, String description, String author, String datetime) {
        String sql = "INSERT INTO Posts (title, description, author, datetime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, author);
            pstmt.setString(4, datetime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getPosts() throws SQLException {
        String sql = "SELECT * FROM Posts ORDER BY id DESC";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    public static void insertComment(int postId, String comment, String author, String datetime) {
        String sql = "INSERT INTO Comments (post_id, comment, author, datetime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setString(2, comment);
            pstmt.setString(3, author);
            pstmt.setString(4, datetime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getComments(int postId) throws SQLException {
        String sql = "SELECT * FROM Comments WHERE post_id = ? ORDER BY datetime ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, postId);
        return pstmt.executeQuery();
    }
}
