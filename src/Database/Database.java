/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author den51
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import GUI.Lantern;

import java.sql.*;


/*public class Database {
    public static void main(String[] args) {
        String databasePath = "jdbc:sqlite:.\\database.db";

        try {
            // Establish connection to the SQLite database
            Connection connection = DriverManager.getConnection(databasePath);

            // Check if connection is successful
            if (connection != null) {
                System.out.println("Connected to the SQLite database.");
                // Perform database operations here
            } else {
                System.out.println("Failed to connect to the SQLite database.");
            }
            
            String insertQuery = "INSERT INTO user (email) VALUES ('haha')";
    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
    //preparedStatement.setString(1, "haha");
    preparedStatement.executeUpdate();
    System.out.println("Data inserted successfully.");

    String selectQuery = "SELECT * FROM user";
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(selectQuery);
while (resultSet.next()) {
    String email = resultSet.getString("email");
    System.out.println("Email: " + email);
}

            // Close connection
            connection.close();
            
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        
        
    }
}*/
public class Database {
    private static Connection conn = null;
    public static Connection connectionDatabase(){
        String databasePath = "jdbc:sqlite:..\\database.db";
        if(conn == null)
            try {
                conn = DriverManager.getConnection(databasePath);
                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return conn;
    }
  
    public void deleteUser(Connection conn, int id) throws SQLException {
        String SQL_DELETE = "DELETE FROM user WHERE username = ?";
        PreparedStatement prepstate = conn.prepareStatement(SQL_DELETE);
        prepstate.setInt(1, id);
        int row = prepstate.executeUpdate();
        if (row > 0) {
            System.out.println("User deleted successfully: ");
        } else {
            System.out.println("Failed to delete user: " );
        }
    }
    
    public void updatePassword(Connection conn, String username, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows>0)
                System.out.println("Successful");
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    
     public static boolean usernameExists(Connection conn, String username) throws SQLException {
        String SQL_SELECT = "SELECT username FROM user WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
     
      public String getPassword(Connection conn, String username) throws SQLException {
        String SQL_SELECT = "SELECT password FROM user WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("password");
        } else {
            return null;
        }
    }
      
      
      //do for update point method
      public void updatePoint(Connection conn,int id,double point)throws SQLException{
       String SQL_SELECT = "UPDATE user SET point = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
        preparedStatement.setDouble(1, point);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      
      }
      
       public void updateCoordinate(Connection conn,String coordinate)throws SQLException{
       String SQL_SELECT = "UPDATE user SET coordinate = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
        preparedStatement.setString(1, coordinate);
        preparedStatement.executeUpdate();
      
      }
      
      public void resetDatabase(Connection conn)throws SQLException{
        Statement statement = conn.createStatement();
        statement.executeUpdate("VACUUM");
        System.out.println("Database vacuumed successfully.");
    }
} 

      
      
      
      





