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

public class Booking {
  // checkExistingBooking (false mean booking is not made yet)
  // if true (already exist for certain day...remove it in the availale time slot
  // )
  // else false --> checkDate (whether got clash with another booking ) --> false,
  // --> bookingTour (make booking)

  // viewBooking (view all the list made by user )

  // make booking
  public static void bookingTour(Connection connection, BookingData booking, String username) {
    Login_Register lg = new Login_Register();
    int id = lg.getID(username, connection);
    String sql = "INSERT INTO BookingDate(main_id, bookingDate,destination) VALUES (?,?,?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, booking.date);
      preparedStatement.setString(3, booking.destination);
      preparedStatement.executeUpdate();
    }

    catch (SQLException e) {
      e.printStackTrace();
    }

    increaseCount(connection, getCount(connection, username), username);
  }

  /*
   * public void updateDate(Connection connection,String date,String username){
   * Login_Register lg=new Login_Register();
   * int id =lg.getID(username, connection);
   * String sql = "UPDATE Booking SET bookingDate=? WHERE main_id=?";
   * try{
   * PreparedStatement preparedStatement = connection.prepareStatement(sql);
   * preparedStatement.setString(1, date);
   * preparedStatement.setInt(2, id);
   * preparedStatement.executeUpdate();
   * }
   * 
   * catch(SQLException e){
   * e.printStackTrace();
   * }
   * 
   * }
   */
  // false mean booking is not make yet
  //applied 
  //check if there's any existing booking from the db prevent duplication on the same booking date, destination based on main_id
  public static boolean checkExistingBooking(Connection connection, String username, BookingData booking) {
    ArrayList<String> datesList = new ArrayList<>();
    ArrayList<String> destination = new ArrayList<>();
    Login_Register lg = new Login_Register();
    int id = lg.getID(username, connection);
    String query = "SELECT bookingDate,destination FROM BookingDate WHERE main_id=?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        datesList.add(result.getString("bookingDate"));
        destination.add(result.getString("destination"));
      }
    }

    catch (SQLException e) {
      e.printStackTrace();
    }
    boolean check = false;
    for (int i = 0; i < datesList.size(); i++) {
      if ((datesList.get(i).equalsIgnoreCase(booking.date))
          && (destination.get(i).equalsIgnoreCase(booking.destination)))
        check = true;
    }

    return check;

  }

  // get all the booking date made by the user (main_id), check with the date from the eventdb
  // fetch the date from event db
  // apply in eventpage gui (for register event)
  public static boolean checkDate(Connection connection, String username, String date) {
    ArrayList<String> datesList = new ArrayList<>();
    int id = Login_Register.getID(username, connection);
    String query = "SELECT bookingDate FROM BookingDate WHERE main_id=?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        datesList.add(result.getString("bookingDate"));
      }
    }

    catch (SQLException e) {
      e.printStackTrace();
    }

    boolean check = false;
    for (String hold : datesList) {
      if (date.equals(hold))
        check = true;
    }
    return check;
  }

  // view all the booking data (in BookingDate table based on the username)
  public static ArrayList<BookingData> viewBooking(Connection connection, String username) {
    ArrayList<BookingData> bookingList = new ArrayList<>();
    Login_Register lg = new Login_Register();
    int id = lg.getID(username, connection);
    String query = "SELECT bookingDate,destination FROM BookingDate WHERE main_id=?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        bookingList.add(new BookingData(result.getString("destination"), result.getString("bookingDate")));
      }
    }

    catch (SQLException e) {
      e.printStackTrace();
    }
    return bookingList;
  }

  /*
   * public ArrayList<String>allDateBooked(Connection connection){
   * 
   * ArrayList<String>datesList=new ArrayList<>();
   * 
   * String query = "SELECT bookingDate FROM Booking ";
   * try{
   * Statement stm = connection.createStatement();
   * ResultSet result= stm.executeQuery(query);
   * while(result.next()){
   * datesList.add(result.getString("bookingDate"));
   * }
   * }
   * 
   * catch(SQLException e){
   * e.printStackTrace();
   * }
   * return datesList;}
   */

  public static void increaseCount(Connection connection, int num, String parentName) {
    int count = num + 1;
    Login_Register lg = new Login_Register();
    int id = lg.getID(parentName, connection);

    String sql = "UPDATE BookingDate SET count = ? WHERE main_id= ?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, count);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
    }

    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static int getCount(Connection connection, String username) {
    int count = 0;
    Login_Register lg = new Login_Register();
    int id = lg.getID(username, connection);
    String query = "SELECT count FROM BookingDate WHERE main_id=?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet result = preparedStatement.executeQuery();
      if (result.next()) {
        count = result.getInt("count");
      }
    }

    catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

}
