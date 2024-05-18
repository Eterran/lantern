package Database;
import java.util.*;
import java.sql.*;

public class Quiz {
    /*public void createQuiz(Connection connection,String title,String description,String theme,String content,String username){
    
        Login_Register_Gui lg=new Login_Register_Gui();
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
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
    increaseQuizNumber(connection,getNumberOfQuiz(connection,username),username);
    }*/
    
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
      //preparedStatement.executeUpdate();
      int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            increaseQuizNumber(connection, getNumberOfQuiz(connection, username), username);
            updateLatestQuiz(connection,new QuizData(title,description,theme,content));
            return true;
        }
      } catch(SQLException e){
           e.printStackTrace();

      }
      return false;}

    //   catch(SQLException e){
    //   e.printStackTrace();
    //   }
    // increaseQuizNumber(connection,getNumberOfQuiz(connection,username),username);

    
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
    
        public static void deleteQuiz(Connection connection,QuizData quiz,String username){
        Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
        try{
       String query = "DELETE FROM Quiz WHERE (quizTitle=? AND description=? AND theme=? AND content=?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,quiz.quizTitle);
            preparedStatement.setString(2,quiz.description);
            preparedStatement.setString(3,quiz.theme);
            preparedStatement.setString(4,quiz.content);
            preparedStatement.executeUpdate();
        }
         catch(SQLException e){
            e.printStackTrace();
        }
        deleteColumn(connection,quiz.quizTitle);
        decreaseQuizNumber(connection,getNumberOfQuiz(connection,username),username);
    }
        
       public static void deleteColumn(Connection connection,String title) {
          String table ="QuizAttempt";
         String query = "ALTER TABLE " + table + " DROP COLUMN \"" + title + "\"";
           // System.out.println(query);
         try{
             Statement stm = connection.createStatement();
             stm.executeUpdate(query);
             
    } catch (SQLException e) {
        e.printStackTrace();
    }
        }



    
     public static void decreaseQuizNumber(Connection connection,int num,String username){
      int count =num-1;
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
      int count=0;
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
    //it is possible there are several same question name
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
    
    //get all quiz created by a educator
     public static ArrayList<QuizData>getQuizUser(Connection connection,String username){
    ArrayList<QuizData>quiz=new ArrayList<>();
    String title="",description="",content="",theme="";
    Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
     String query = "SELECT quizTitle,description,content,theme FROM Quiz WHERE main_id=?";
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1,id);
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
    //q1 mean quiz with id 1 in Quiz table
   /* public static void updateLatestQuizColumn(Connection connection){
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
    */
    //use to insert the new quiz in quiz attempt table//call it everytime create a quiz 
    //adding one column at the quizAttempt
    public static void updateLatestQuiz(Connection connection, QuizData quiz) {
    try {
        // Properly format the SQL statement with a space before INTEGER
        String columnName = "\"" + quiz.quizTitle + "\"";
        String query2 = "ALTER TABLE QuizAttempt ADD " + columnName + " INTEGER";
        
        // Create a statement
        Statement stm = connection.createStatement();
        
        // Execute the update
        stm.executeUpdate(query2);
        
    } catch (SQLException e) {
        // Print the stack trace if an SQLException occurs
        e.printStackTrace();
    }
}

    
    /*
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
    }*/
    
    //when a student is incresed in database,update it into a QuizAttempt table 
    //when a student press startAttempt, increase one row for students
    public static void updateLatestQuizRow(Connection connection){
         ArrayList<Integer>IDList=new ArrayList<>();
         ArrayList<Integer>checkList=new ArrayList<>();
        String query = "SELECT id FROM user WHERE role=?";
        String query2="SELECT main_id FROM QuizAttempt";
        String query3="INSERT INTO QuizAttempt (main_id) VALUES (?)";
         try{
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1,"student");
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
    
    //use to set a user to all quiz become 0  // 0 mean not attempted,1 attempted//pass a arraylist contain all quiz data
    public static void initializeRow(Connection connection,String username){
    
        Login_Register lg=new Login_Register();
      int id =lg.getID(username, connection);
      ArrayList<QuizData>quiz=getAllQuiz(connection);
      try{
          for(int i=0;i<quiz.size();i++){
              String columnName = "\"" + quiz.get(i).quizTitle+ "\"";
              String query = "UPDATE QuizAttempt SET " + columnName+" = ? WHERE main_id = ?";
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
    
    //pass the quiz parameter in q1,q2,q3 (quiztitle).....format,after click finish button
    //update the 1 into QuizAttempt table
    public static void attemptQuiz(Connection connection,QuizData quiz,String username){
          Login_Register lg=new Login_Register();
          int id =lg.getID(username, connection);
        String columnName = "\"" + quiz.quizTitle+ "\"";  
        String query = "UPDATE QuizAttempt SET " + columnName + " = ? WHERE main_id = ?";
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
    //start attempt button
    public static boolean checkAttempted(Connection connection,String quiztitle,String username){
    
        Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);
        int check=0;
        String columnName = "\"" + quiztitle+ "\"";  
        String query="SELECT "+columnName+" FROM QuizAttempt WHERE main_id=?";
         try{
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1,id);
          ResultSet result=preparedStatement.executeQuery();
          if(result.next()){
          check=result.getInt(quiztitle);
          }
      }
      
      catch(SQLException e){
      e.printStackTrace();
      }
         
         if(check==1)
             return true;
         else 
             return false;
    }
    
    
    
    
    
    
    
}
