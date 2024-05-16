/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author den51
 */
import java.util.*;
import java.sql.*;

public class User {
    private String email, username, password, role, coordinate;
    private String[] temp;
    private double points;
    private double X,Y;
    private ArrayList<String> parents = new ArrayList<>();
    private ArrayList<String> childrens = new ArrayList<>();
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
        //System.out.println("Current user: " + user.getUsername());
    }
    
    public void userData(int id) {
        String sql = "SELECT email,username ,password,role,coordinate,point FROM user WHERE id = ?";
        try {
            Connection connection = Database.connectionDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                email = resultSet.getString("email");
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                role = resultSet.getString("role");
                coordinate = resultSet.getString("coordinate");
                temp = coordinate.split(",");
                X = Double.parseDouble(temp[0]);
                Y = Double.parseDouble(temp[1]);
                points = resultSet.getDouble("point");
            }
            getParents(connection, id);
            getChildren(connection, id);

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getParents(Connection connection, int id) {
        String sql = "SELECT parent FROM children WHERE main_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String value = resultSet.getString("parent");
                parents.add(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getChildren(Connection connection, int id) {
        String sql = "SELECT children FROM parent WHERE main_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String value = resultSet.getString("children");
                System.out.println(value);
                childrens.add(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCoordinate() {
        return coordinate;
    }
    public Double getX() {
        return X;

    }

    public Double getY() {
        return Y;
    }

    public String getRole() {
        return role;
    }

    public Double getPoints() {
        return points;
    }
    public void setPoints(Double p){
        this.points = p;
    }
    public ArrayList<String> getParents() {
        return parents;
    }

    public ArrayList<String> getChildren() {
        return childrens;
    }

}
