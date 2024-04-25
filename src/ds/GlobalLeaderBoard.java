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
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

    
    public class GlobalLeaderBoard {
        String[]username2 ;
        LocalDate[]xpLastUpdated2;
        double[]XP2;
    
    public void loadGlobal(Connection connection)throws IOException,SQLException{
       
        String username="";
        LocalDate xpLastUpdated=null;
        double XP=0;
        int count=0;
        
        
        for(int i=1;i<Integer.MAX_VALUE;i++){
            try{
            String sql = "SELECT username FROM user WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, i);
            ResultSet result= preparedStatement.executeQuery();
            if(!result.next()){
             break;}
             count++;}
            
            catch(SQLException e){
            e.printStackTrace();}
        }
            
        
        username2 = new String[count];
        xpLastUpdated2 = new LocalDate[count];
        XP2=new double[count];
        
        for(int i=0;i<count;i++){
            try{
            String sql = "SELECT username,point FROM user WHERE id = ? ";
            String sql2 = "SELECT xpLastUpdated FROM XpLastUpdated WHERE main_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement.setInt(1, i+1);
            preparedStatement2.setInt(1,i+1);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet.next()) {
               username = resultSet.getString("username");
               XP=resultSet.getDouble("point");
             }
            
            if (resultSet2.next()) {
            String temp=resultSet2.getString("xpLastUpdated");
            xpLastUpdated=LocalDate.parse(temp);
             }
            else{
             xpLastUpdated=null;
            }
            
            
            }
            catch(SQLException e){
            e.printStackTrace();}
             
            
            username2[i]=username;
            xpLastUpdated2[i]=xpLastUpdated;
            XP2[i]=XP;
        }
        
        for(int i=0;i<count;i++){
            System.out.println(username2[i]+" "+xpLastUpdated2[i]+" "+XP2[i]);
        }
        
           for(int i=1; i<count; i++){
            for(int j=0; j<count-1;j++){
                if(XP2[j+1]>XP2[j]){
                    double hold = XP2[j];
                    XP2[j] = XP2[j+1];
                    XP2[j+1]=hold;
                    String hold2 = username2[j];
                    username2[j] = username2[j+1];
                    username2[j+1]=hold2;
                    LocalDate hold3 = xpLastUpdated2[j];
                    xpLastUpdated2[j] = xpLastUpdated2[j+1];
                    xpLastUpdated2[j+1]=hold3;
                    
                }
            }
        }
        
        
        
        for(int i=1;i<count;i++){
            for(int j=0;j<count-1;j++){
                if(XP2[j]==XP2[j+1]){
                    if(xpLastUpdated2[j].compareTo(xpLastUpdated2[j+1])<0){
                        double hold = XP2[j+1];
                        XP2[j+1] = XP2[j];
                        XP2[j]=hold;
                        String hold2 = username2[j+1];
                        username2[j+1] = username2[j];
                        username2[j]=hold2;
                        LocalDate hold3 = xpLastUpdated2[j+1];
                        xpLastUpdated2[j+1] = xpLastUpdated2[j];
                        xpLastUpdated2[j]=hold3;
                    }
            }
            
        }
        
    
    }
        
        for(int i=0;i<count;i++){
            System.out.println(username2[i]);
            System.out.println(XP2[i]);
            System.out.println();}
    }
    


     public String[]getUsername(){
        return username2;
    }
    
    public LocalDate[]getXpUpdate(){
        return  xpLastUpdated2;
    }
    
    public double[]getXp(){
        return XP2;
    }
    
    public void insertXpState(Connection connection,int id ){
      LocalDate date =LocalDate.now();
      String dat=date.toString();
      String sql = "INSERT INTO XpLastUpdated(main_id, xpLastUpdated) VALUES (?, ?)";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, dat);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
      
      }
    
    public void updateXpState(Connection connection,int id ){
      LocalDate date =LocalDate.now();
      String dat=date.toString();
      String sql = "UPDATE XpLastUpdated SET xpLastUpdated = ? WHERE main_id= ?";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, dat);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
      
      }
}