/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author den51
 */
import java.util.*;
import java.sql.*;
public class User {
    public String email,username,password,role,coordinate;
    public double points;
    public ArrayList<String>parents=new ArrayList<>();
    public ArrayList<String>childrens=new ArrayList<>();

    
    public void userData(int id){
    String sql = "SELECT email,username ,password,role,coordinate,point FROM user WHERE id = ?";
    try{
     Database database=new Database();
     Connection connection=database.connectionDatabase();
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setInt(1, id);
     ResultSet resultSet = preparedStatement.executeQuery();
     while (resultSet.next()) {
        email = resultSet.getString("email");
        username=resultSet.getString("username");
        password=resultSet.getString("password");
        role=resultSet.getString("role");
        coordinate=resultSet.getString("coordinate");
        points=resultSet.getDouble("point");
     }
     getParents(connection,id);
     getChildrens(connection,id);
     
     
    }
    
    catch(SQLException e){
    e.printStackTrace();
    }
    }
    
    public void getParents(Connection connection,int id){
    String sql = "SELECT parent FROM children WHERE main_id = ?";
    try{
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setInt(1, id);
     ResultSet resultSet = preparedStatement.executeQuery();
     while (resultSet.next()) {
        String value = resultSet.getString("parent");
        parents.add(value);
    }
    }
    catch(SQLException e){
        e.printStackTrace();}
    }
    
     public void getChildrens(Connection connection,int id){
    String sql = "SELECT children FROM parent WHERE main_id = ?";
    try{
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setInt(1, id);
     ResultSet resultSet = preparedStatement.executeQuery();
       
     while (resultSet.next()) {
        String value = resultSet.getString("children");
         System.out.println(value);
        childrens.add(value);
    }
    }
    catch(SQLException e){
        e.printStackTrace();}
    }
     
     public String getEmail(){
     return email;}
     
     public String getUsername(){
     return username;}
     
     public String getPassword(){
     return password;}
     
     public String getCoordinate(){
     return coordinate;}
     
     public String getRole(){
     return role;}
     
     public Double getPoints(){
     return points;}
     
     public ArrayList<String> getParents(){
     return parents;
     }
     
     public ArrayList<String> getChildrens(){
     return childrens;
     }
    
}
