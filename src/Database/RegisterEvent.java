/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.ds;

/**
 *
 * @author den51
 */
import java.util.*;
import java.sql.*;
public class RegisterEvent {
    
    public static void registerEvent(Connection connection,EventData event,String username) throws IllegalArgumentException{
        Login_Register_Gui lg=new Login_Register_Gui();
      int id =lg.getID(username, connection);
      int event_id=-1;
      String sql2="SELECT id FROM event WHERE (EventTitle=? AND Description=? AND Venue=? AND Date=?)";
      String sql = "INSERT INTO EventRegistered(main_id,event_id) VALUES (?,?)";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
      preparedStatement2.setString(1,event.eventTitle);
      preparedStatement2.setString(2,event.description);
      preparedStatement2.setString(3,event.venue);
      preparedStatement2.setString(4,event.date);
      ResultSet result=preparedStatement2.executeQuery();
      if(result.next())
          event_id=result.getInt("id");
      if(event_id==-1)
          throw new IllegalArgumentException("No such event exist");
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, event_id);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    }
    
    public static ArrayList<EventData>getAllEventRegistered(Connection connection,String username){
        ArrayList<Integer>data=new ArrayList<>();
        String description="",venue="",time="",eventName="",date="";
        ArrayList<EventData>events=new ArrayList<>();
        Login_Register_Gui lg=new Login_Register_Gui();
      int id =lg.getID(username, connection);
      String query2="SELECT EventTitle,Description,Venue,Date,Time FROM event WHERE id=? ";
        String query = "SELECT event_id FROM EventRegistered WHERE main_id=?";

    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet result=preparedStatement.executeQuery();
      while(result.next()){
      data.add(result.getInt("event_id"));
      }
      PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
      for(int hold:data){
      preparedStatement2.setInt(1,hold);
      result=preparedStatement2.executeQuery();
      while(result.next()){
           eventName=result.getString("EventTitle");
            description=result.getString("Description");
            venue=result.getString("Venue");
            time=result.getString("Time");
            date=result.getString("Date");
            events.add(new EventData(eventName,description,venue,date,time));
      }
      }
      
    }
    catch (SQLException e){
        e.printStackTrace();}
    
    return events;
    }
   /* //in case several different date but same event
    public ArrayList<String>getEventDate(Connection connection,String event,String username){
         ArrayList<String>data=new ArrayList<>();
         Login_Register_Gui lg=new Login_Register_Gui();
      int id =lg.getID(username, connection);
         String query = "SELECT eventDate FROM EventRegistered WHERE (eventRegistered=? AND main_id=?)";

    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, event);
      preparedStatement.setInt(2, id);
      ResultSet result=preparedStatement.executeQuery();
      while(result.next()){
      data.add(result.getString("eventDate"));
      }
    }
    catch (SQLException e){
        e.printStackTrace();}
    return data;
    }*/
    
   /* public boolean checkRegisterEvent(Connection connection,EventData event){
    
         try{
            String sql = "SELECT EventTitle,Description,Venue,Date,Time FROM event WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                eventName=result.getString("EventTitle");
            description=result.getString("Description");
            venue=result.getString("Venue");
            time=result.getString("Time");
            date=result.getString("Date");
            events.add(new EventData(eventName,description,venue,date,time));
            }
    }
            catch(SQLException e){
            e.printStackTrace();}
    
    }*/
}
