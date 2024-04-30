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
import java.time.*;
public class BookingDate {
    
    
    
    public void insertDate(Connection connection,String date,String username){
         Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
     String sql = "INSERT INTO BookingDate(main_id, bookingDate) VALUES (?, ?)";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, date);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    }
    
    public void updateDate(Connection connection,String date,String username){
    Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
     String sql = "UPDATE BookingDate SET bookingDate=? WHERE main_id=?";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, date);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    
    }
    //false mean no same date
    public boolean checkExistingDate(Connection connection,String username,String Date){
        ArrayList<String>datesList=new ArrayList<>();
        Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
    String query = "SELECT bookingDate FROM BookingDate WHERE main_id=?";
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
     ResultSet result= preparedStatement.executeQuery();
     while(result.next()){
     datesList.add(result.getString("bookingDate"));
     }
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    
    boolean check=false;
    for(String date: datesList)
        if(date.equals(Date))
            check=true;
    
    return check;
    
    
    }
    
    //datesList contain all the bookingdate made by user
    public ArrayList<String> viewDate(Connection connection,String username){
     ArrayList<String>datesList=new ArrayList<>();
        Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
    String query = "SELECT bookingDate FROM BookingDate WHERE main_id=?";
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
     ResultSet result= preparedStatement.executeQuery();
     while(result.next()){
     datesList.add(result.getString("bookingDate"));
     }
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    return datesList;
    }

    public ArrayList<String>allDateBooked(Connection connection){
    
     ArrayList<String>datesList=new ArrayList<>();
       
    String query = "SELECT bookingDate FROM BookingDate ";
    try{
      Statement stm = connection.createStatement();
     ResultSet result= stm.executeQuery(query);
     while(result.next()){
     datesList.add(result.getString("bookingDate"));
     }
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    return datesList;}
}
