/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Student;

/**
 *
 * @author den51
 */
import java.sql.*;
import java.util.*;

import Database.Database;
import Database.Login_Register;

public class friend {
    ArrayList<String> Friend_List = new ArrayList<String>();
    public friend(String username){
        Connection connection = Database.connectionDatabase();
        this.Friend_List = showFriend(connection, username);
    }
    public ArrayList<String> getFriendList(){
        return this.Friend_List;
    }
    public static boolean checkExistingFriend(Connection connection, String Friendname, String username) {
        Login_Register lg = new Login_Register();
        boolean check = false;
        int id = lg.getID(username, connection);
        try {

            String query = "SELECT id FROM friends WHERE (pending = ? OR complete=?)AND main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Friendname);
            preparedStatement.setString(2, Friendname);
            preparedStatement.setInt(3, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next())
                check = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return check;
    }

    public static void friendRequest(Connection connection, String friendName, String username) {
        Login_Register lg = new Login_Register();
        int id = lg.getID(username, connection);
        try {
            String query = "INSERT INTO friends(pending,main_id) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, friendName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getFriendId(Connection connection, String friendName, String username) {

        Login_Register lg = new Login_Register();
        int id = lg.getID(username, connection);
        int userId = 0;
        try {
            String query = "SELECT id FROM friends WHERE pending = ? AND main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, friendName);
            preparedStatement.setInt(2, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                userId = result.getInt("id");
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    public void acceptFriend(Connection connection, String friendName, String username) {
        int id = getFriendId(connection, friendName, username);
        Login_Register lr = new Login_Register();
        int main_id = lr.getID(username, connection);
        try {
            String query = "UPDATE friends SET complete=?,pending=? WHERE id=?";
            // String query2="UPDATE friends SET pending = ? WHERE id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, friendName);
            preparedStatement.setString(2, null);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            /*
             * PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
             * preparedStatement2.setString(1,null);
             * preparedStatement2.setInt(2,id);
             * preparedStatement2.executeUpdate();
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

     public void declineFriend(Connection connection,String friendName,String username){
        int id=getFriendId(connection,friendName,username);
    try{
       String query = "DELETE FROM friends WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }
         catch(SQLException e){
            e.printStackTrace();
        }
    
    }

    public ArrayList<String> showPending(Connection connection, String username) {
        Login_Register lr = new Login_Register();
        int main_id = lr.getID(username, connection);
        ArrayList<String> list = new ArrayList<>();
        try {
            String query = "SELECT pending FROM friends WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, main_id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String hold = result.getString("pending");
                if (hold == null)
                    continue;
                list.add(hold);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    public ArrayList<String> showFriend(Connection connection, String username) {
        Login_Register lr = new Login_Register();
        int main_id = lr.getID(username, connection);
        ArrayList<String> list = new ArrayList<>();
        try {
            String query = "SELECT complete FROM friends WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, main_id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String hold = result.getString("complete");
                if (hold == null || list.contains(hold))
                    continue;
                list.add(hold);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
