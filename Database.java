/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.ds;

/**
 *
 * @author den51
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/*public class Database {
    public static void main(String[] args) {
        String databasePath = "jdbc:sqlite:C:\\sqlite\\sqlite3\\database.db";

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

  public Connection connectionDatabase(){
       String databasePath = "jdbc:sqlite:C:\\sqlite\\sqlite3\\database.db";
        Connection connection=null;
        try {
           connection = DriverManager.getConnection(databasePath);

  }
        catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
        
        
        return connection;
        

}
  
  public void insert(){}
}




