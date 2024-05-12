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

public class Quiz {
    public static boolean createQuiz(Connection connection,String title,String description,String theme,String content,String username){
    
        Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
         String sql = "INSERT INTO Quiz(main_id,quizTitle,description,theme,content) VALUES (?,?,?,?,?)";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, title);
      preparedStatement.setString(3, description);
      preparedStatement.setString(4, theme);
      preparedStatement.setString(5, content);
      preparedStatement.executeUpdate();
      int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            increaseQuizNumber(connection, getNumberOfQuiz(connection, username), username);
            return true;
        }
      } catch(SQLException e){
           e.printStackTrace();

      }
      return false;

    //   catch(SQLException e){
    //   e.printStackTrace();
    //   }
    // increaseQuizNumber(connection,getNumberOfQuiz(connection,username),username);

    }
    
    public static void increaseQuizNumber(Connection connection,int num,String username){
      int count =num+1;
      Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
      String sql = "UPDATE Quiz SET numQuizCreated = ? WHERE main_id= ?";
      try{
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1,count);
      preparedStatement.setInt(2,id);
      preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    }
    
    public static int getNumberOfQuiz(Connection connection,String username){
    Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
      int count=-1;
         String query = "SELECT numQuizCreated FROM Quiz WHERE main_id=?";
    try{
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1,id);
              ResultSet result=preparedStatement.executeQuery();
            if(result.next()){
                count=result.getInt("numQuizCreated");}
            
    }
     catch (SQLException e){
        e.printStackTrace();}
    return count;
    }
    
    public static ArrayList<QuizData>getQuizBasedTheme(Connection connection,String theme){
        ArrayList<QuizData>data=new ArrayList<>();
    String title="",description="",content="";
        String query = "SELECT quizTitle,description,content FROM Quiz WHERE theme=?";
    try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1,theme);
      ResultSet result=preparedStatement.executeQuery();
      
      while(result.next()){
      title=result.getString("quizTitle");
      description=result.getString("description");
      content=result.getString("content");
      data.add(new QuizData(title,description,theme,content));
      }
            
    }
     catch (SQLException e){
        e.printStackTrace();}
        return data;
    }
    
    public static boolean checkSameQuiz(Connection connection,QuizData quiz){
    
    String query = "SELECT id FROM Quiz WHERE (quizTitle=? AND description=? AND theme=? AND content=? )";
    try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1,quiz.quizTitle);
      preparedStatement.setString(2,quiz.description);
      preparedStatement.setString(3,quiz.theme);
      preparedStatement.setString(4,quiz.content);
      ResultSet result=preparedStatement.executeQuery();
      return result.next();
            
    }
     catch (SQLException e){
        e.printStackTrace();}
    
    return false;
    }
    
    public static ArrayList<QuizData>getAllQuiz(Connection connection){
    ArrayList<QuizData>quiz=new ArrayList<>();
    String title="",description="",content="",theme="";
     String query = "SELECT quizTitle,description,content,theme FROM Quiz";
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet result=preparedStatement.executeQuery();
      
      while(result.next()){
      title=result.getString("quizTitle");
      description=result.getString("description");
      content=result.getString("content");
      theme=result.getString("theme");
      quiz.add(new QuizData(title,description,theme,content));
      }
            
    }
     catch (SQLException e){
        e.printStackTrace();}
    
    return quiz;
    }
    
    //use to update column of TABLE QuizAttempt when the new quiz is created
    public void updateLatestQuizColumn(Connection connection){
        int count=0;
        int columnCount = getColumnNumber(connection);
        String query = "SELECT id FROM Quiz";
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet result=preparedStatement.executeQuery();
      
      while(result.next()){
          count++;
      }
      if(columnCount<count)
            for(int i=columnCount+1;i<=count;i++){
         Statement statement = connection.createStatement();
             String columnName="q"+i ;
            String query2 = "ALTER TABLE QuizAttempt ADD " + columnName + " INTEGER";
            statement.executeUpdate(query2);
            }
            
    }
     catch (SQLException e){
        e.printStackTrace();}
    }
    
    public static int getColumnNumber(Connection connection){
        int columnCount = 0;
        try{
        
         DatabaseMetaData metaData = connection.getMetaData();
            String tableName = "QuizAttempt";
            ResultSet rs = metaData.getColumns(null, null, tableName,"q%");
            
            while (rs.next()) {
                columnCount++;
            }
        }
        catch(SQLException e){
        e.printStackTrace();
        }
    return columnCount;
    }
    
    //when a student is incresed in database,update it into a QuizAttempt table 
    public void updateLatestQuizRow(Connection connection){
         ArrayList<Integer>IDList=new ArrayList<>();
         ArrayList<Integer>checkList=new ArrayList<>();
        String query = "SELECT id FROM user WHERE role=?";
        String query2="SELECT main_id FROM QuizAttempt";
        String query3="INSERT INTO QuizAttempt (main_id) VALUES (?)";
         try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1,"Student");
      ResultSet result=preparedStatement.executeQuery();
      while(result.next()){
          IDList.add(result.getInt("id"));
      }
      int count=0;
      Statement stm=connection.createStatement();
      result=stm.executeQuery(query2);
      while(result.next()){
          checkList.add(result.getInt("main_id"));
      count++;
      }
      if(count<IDList.size()){
      IDList.removeAll(checkList);
      for(int hold:IDList){
      preparedStatement = connection.prepareStatement(query3);
      preparedStatement.setInt(1,hold);
      preparedStatement.executeUpdate();
      }
     }
       }
      
      catch (SQLException e){
        e.printStackTrace();
      }
    }
    
    //use to set a user to all quiz become 0  // 0 mean not attempted,1 attempted
    public static void initializeRow(Connection connection,String username){
    
        Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
      
      try{
          for(int i=1;i<=getColumnNumber(connection);i++){
              String columnName = "q" + i;
              String query = "UPDATE QuizAttempt SET " + columnName + " = ? WHERE main_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1,0);
      preparedStatement.setInt(2,id);
      preparedStatement.executeUpdate();
      }
      
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
      
    }
    
    //pass the quiz parameter in q1,q2,q3.....format
    public static void attempQuiz(Connection connection,String quiz,String username){
          Login_Register lg=new Login_Register();
          int id =lg.getID(username, connection);
    
        String query = "UPDATE QuizAttempt SET " + quiz + " = ? WHERE main_id = ?";
      try{
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1,1);
          preparedStatement.setInt(2,id);
          preparedStatement.executeUpdate();
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
 }
    
    
}
