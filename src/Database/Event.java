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
import java.io.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
public class Event {
    
    //date all in format of local date 2024-05-04
    public static boolean createEvent(Connection connection,EventData event,String username){
      Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
     /* LocalDate date=LocalDate.now();
      LocalTime time=LocalTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
       String currentTime = time.format(formatter);
       String currentDate=date.toString();*/
     String sql = "INSERT INTO event(main_id,EventTitle,Description,Venue,Date,Time) VALUES (?,?,?,?,?,?)";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, event.eventTitle);
      preparedStatement.setString(3, event.description);
      preparedStatement.setString(4, event.venue);
      preparedStatement.setString(5, event.date);
      preparedStatement.setString(6, event.time);
      preparedStatement.executeUpdate();
      //trying to check for the saving updates
      int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            increaseEventNumber(connection, getNumberOfEvent(connection, username), username);
            return true;
        }
      }
      
      catch(SQLException e){
        e.printStackTrace();
      }
     // increaseEventNumber(connection,getNumberOfEvent(connection,username),username);
      return false;
    }

    
    public static void increaseEventNumber(Connection connection,int num,String username){
      int count =num+1;
      Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
      
      String sql = "UPDATE event SET EventCount = ? WHERE main_id= ?";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1,count );
      preparedStatement.setInt(2,id);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    }
    
   
    
    public static void deleteEvent(Connection connection,String date){
    
        try{
       String query = "DELETE FROM event WHERE date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,date);
            preparedStatement.executeUpdate();
        }
         catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public static ArrayList<EventData> getAllEvent(Connection connection){
    ArrayList<EventData>data=new ArrayList<>();
    String description="",venue="",time="",eventName="",date="";
        String sql = "SELECT EventTitle,Description,Venue,Date,Time FROM event ";

    try{
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                eventName=result.getString("EventTitle");
            description=result.getString("Description");
            venue=result.getString("Venue");
            time=result.getString("Time");
            date=result.getString("Date");
            data.add(new EventData(eventName,description,venue,date,time));
            }
    }
    catch (SQLException e){
        e.printStackTrace();}
    
    return data;
   
    
    }
    
    public static int getNumberOfEvent(Connection connection,String username){
    Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
      int count=-1;
         String query = "SELECT EventCount FROM event WHERE main_id="+id;
    try{
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(query);
            if(result.next()){
                count=result.getInt("EventCount");}
            
    }
     catch (SQLException e){
        e.printStackTrace();}
    return count;
    }
    
    
    public static void removeOutdatedEvent(Connection connection){
        
        ArrayList<String>date=new ArrayList<>();
        String query = "SELECT Date FROM event";

    try{
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(query);
            while(result.next()){
            date.add(result.getString("Date"));
            }
    }
    catch (SQLException e){
        e.printStackTrace();}
    
    
        LocalDate currentDate=LocalDate.now();
        ArrayList<String>outdatedDate=new ArrayList<>();
        for(int i=0;i<date.size();i++){
            LocalDate tempDate=LocalDate.parse(date.get(i));
        if(tempDate.compareTo(currentDate)<0)
            outdatedDate.add(date.get(i));
        }
        
        for(int i=0;i<outdatedDate.size();i++){
        deleteEvent(connection,outdatedDate.get(i));
        
        }
        
    }
    
    public static ArrayList<EventData> getLatestEvent(Connection connection){
        LocalDate currentDate=LocalDate.now();
        removeOutdatedEvent(connection);
        ArrayList<String>event=new ArrayList<>();
        ArrayList<LocalDate>eventNotSameDate=new ArrayList<>();
        ArrayList<EventData>latestEvent=new ArrayList<>();
        
        
        try{
            String sql = "SELECT Date FROM event";
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(sql);
            while(result.next()){
            event.add(result.getString("Date"));
            }
    }
            
            catch(SQLException e){
            e.printStackTrace();}
        
        
        for(int i=0;i<event.size();i++){
            LocalDate temp=LocalDate.parse(event.get(i));
            //this != change to >=0 if outdated date is needed
        if(temp.compareTo(currentDate)!=0){
            eventNotSameDate.add(temp);
        }
        }
        
         LocalDate[]array =eventNotSameDate.toArray(new LocalDate[eventNotSameDate.size()]);
         for(int i=1; i<array.length; i++){
            for(int j=0; j<array.length-1;j++){
                if(array[j].compareTo(array[j+1])>0){
                    LocalDate hold = array[j];
                    array[j] = array[j+1];
                    array[j+1]=hold;
                 }
            }
        }
         
       String description="",venue="",time="",eventName="",date="";
         
          try{
            String sql = "SELECT EventTitle,Description,Venue,Time FROM event WHERE Date=?";
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, currentDate.toString());
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                 eventName=result.getString("EventTitle");
            description=result.getString("Description");
            venue=result.getString("Venue");
            time=result.getString("Time");
            latestEvent.add(new EventData(eventName,description,venue,currentDate.toString(),time));
            }
    }
            
            catch(SQLException e){
            e.printStackTrace();}
            
         
         int count = 0;
         ArrayList<String> check = new ArrayList<>();
         check.add("a"); 
         try {
            String sql = "SELECT EventTitle,Description,Venue,Time FROM event WHERE Date=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < array.length; i++) {
               String hold = array[i].toString();
               if (check.contains(hold)) {
                   continue;
        }
        preparedStatement.setString(1,hold);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next() && count < 3) {
            eventName=result.getString("EventTitle");
            description=result.getString("Description");
            venue=result.getString("Venue");
            time=result.getString("Time");
             latestEvent.add(new EventData(eventName,description,venue,hold,time));
            count++;
        }
        
        check.add(date);
    }
}     catch (SQLException e) {
         e.printStackTrace();
}

        return latestEvent;
    }
    
    //doing arraylist in case there are multiple same event with different date
    public static ArrayList<String> dateOfEvent(Connection connection,String eventName){
        ArrayList<String>date=new ArrayList<>();
     try{
            String sql = "SELECT Date FROM event WHERE EventTitle = ?";
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eventName);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
            date.add(result.getString("Date"));
            }
    }
            catch(SQLException e){
            e.printStackTrace();}
     return date;
    }
    
    //the same event in same data is not allowed to create
    public static boolean checkSameEvent(Connection connection,EventData event){
    //boolean check=false;
        //ArrayList<String>allEvent=getAllEvent(connection);
        //LocalDate dateRegister=LocalDate.parse(event.date);
        /*for(String hold:allEvent)
            if(hold.equalsIgnoreCase(event.)){
                for(String hold2:dateOfEvent(connection,hold)){
                    if(LocalDate.parse(hold2).compareTo(dateRegister)==0)
                        check=true;
                }
            }
        return check;*/
        String query = "SELECT id FROM event WHERE (EventTitle=? AND Description=? AND Venue=? AND Date=?)";
    try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1,event.eventTitle);
      preparedStatement.setString(2,event.description);
      preparedStatement.setString(3,event.venue);
      preparedStatement.setString(4,event.date);
      ResultSet result=preparedStatement.executeQuery();
      return result.next();
            
    }
     catch (SQLException e){
        e.printStackTrace();}
        return false;
    }
    
    /*//provide the event name and its date to get all detail //
    public static EventData getDatailOfEvent(Connection connection,String eventName,String date){
    String description="",venue="",time="";
        try{
            String sql = "SELECT Description,Venue,Time FROM event WHERE (EventTitle = ? AND Date=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eventName);
            preparedStatement.setString(2, date);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
            description=result.getString("Description");
            venue=result.getString("Venue");
            time=result.getString("Time");
            }
    }
            catch(SQLException e){
            e.printStackTrace();}
        
        EventData ed=new EventData(eventName,description,venue,date,time);
    
        return ed;
    }*/
    
    //get all events create by a user
    public static ArrayList<EventData>getEventOfUser(Connection connection,String username){
    String description="",venue="",time="",eventName="",date="";
        ArrayList<EventData>events=new ArrayList<>();
        Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
    
      
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
       return events;
    }
    
    
 }
