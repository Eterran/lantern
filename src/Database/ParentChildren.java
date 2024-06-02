package Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author den51
 */
public class ParentChildren {
    
    //This is used for parent to add children
    
    
    public static boolean checkExistingChildren(Connection connection ,String childrenName,String parentName ){
        Login_Register lg=new Login_Register();
        boolean check=false;
        int id =lg.getID(parentName, connection);
     try{
         
      String query = "SELECT id FROM parent WHERE (request = ? OR children=?)AND main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, childrenName);
            preparedStatement.setString(2, childrenName);
            preparedStatement.setInt(3, id);
            ResultSet result=preparedStatement.executeQuery();
            if(result.next())
                check=true;
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
     
     return check;
     }

    //check parent of a child cnnr be higher than two
    public boolean checkParentNumber(Connection connection ,String childrenName){
        Login_Register lg=new Login_Register();
        int count=0;
        int id =lg.getID(childrenName, connection);
     try{
            String query = "SELECT id FROM children WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet result=preparedStatement.executeQuery();
            while(result.next()){
            count++;
            }
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
      return count >= 2;
     
     }
    
    public boolean checkExistingParent(Connection connection ,String childrenName,String parentName ){
        Login_Register lg=new Login_Register();
        boolean check=false;
        int id =lg.getID(childrenName, connection);
     try{
         
      String query = "SELECT id FROM children WHERE (request = ? OR parent=?)AND main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, parentName);
            preparedStatement.setString(2, parentName);
            preparedStatement.setInt(3, id);
            ResultSet result=preparedStatement.executeQuery();
            if(result.next())
                check=true;
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
     
     return check;
     }
    
    //make a request
    public static void request(Connection connection,String childrenName,String parentName){
        Login_Register lg=new Login_Register();
        int childrenId =lg.getID(childrenName, connection);
        int parentId=lg.getID(parentName, connection);
     try{
       String query = "INSERT INTO children(request,main_id) VALUES (?,?)";
       String query2 = "INSERT INTO parent (request,main_id) VALUES (?,?)";
       
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, parentName);
            preparedStatement.setInt(2, childrenId);
            preparedStatement.executeUpdate();
            
             PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
             preparedStatement2.setString(1,childrenName);
            preparedStatement2.setInt(2, parentId);
            preparedStatement2.executeUpdate();
            
        }
         catch(SQLException e){
            e.printStackTrace();
        }
    }
    
   
    
    
     
    //Accept request
    public static void acceptRequest(Connection connection,String childrenName,String parentName){
       Login_Register lg=new Login_Register();
        int childrenId =lg.getID(childrenName, connection);
        int parentId=lg.getID(parentName, connection);
    try{
       String query = "UPDATE children SET parent=?,request=? WHERE main_id=? AND request=?";
       String query2="UPDATE parent SET children=?,request=? WHERE main_id=? AND request=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,parentName);
            preparedStatement.setString(2,null);
            preparedStatement.setInt(3,childrenId);
            preparedStatement.setString(4,parentName);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setString(1,childrenName);
            preparedStatement2.setString(2,null);
            preparedStatement2.setInt(3,parentId);
            preparedStatement2.setString(4,childrenName);
            preparedStatement2.executeUpdate();
           
        }
         catch(SQLException e){
            e.printStackTrace();
        }
    
    }
    
     public static void declineRequest(Connection connection,String childrenName,String parentName){
         Login_Register lg=new Login_Register();
        int childrenId =lg.getID(childrenName, connection);
        int parentId=lg.getID(parentName, connection);
    try{
       String query = "DELETE FROM parent WHERE main_id = ? AND request = ?";
       String query2 = "DELETE FROM children WHERE main_id = ? AND request = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,parentId);
            preparedStatement.setString(2,childrenName);
            preparedStatement.executeUpdate();
            
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setInt(1,childrenId);
            preparedStatement2.setString(2,parentName);
            preparedStatement2.executeUpdate();
        }
         catch(SQLException e){
            e.printStackTrace();
        }
    
    }
    
     //show who had make request to become a certain child's parent
    public static ArrayList<String> showRequestParent(Connection connection,String childrenName){
        Login_Register lr=new Login_Register();
        int main_id=lr.getID(childrenName, connection);
        ArrayList <String>list=new ArrayList<>();
         try{
       String query = "SELECT request FROM children WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, main_id);
            ResultSet result=preparedStatement.executeQuery();
            while(result.next()){
                String hold=result.getString("request");
                if(hold==null)
                    continue;
               list.add(hold);
            }
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
    
    
         return list;
         
    }
    
    public static ArrayList<String> showParent(Connection connection,String childrenName){
        Login_Register lr=new Login_Register();
        int main_id=lr.getID(childrenName, connection);
        ArrayList <String>list=new ArrayList<>();
         try{
       String query = "SELECT parent FROM children WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, main_id);
            ResultSet result=preparedStatement.executeQuery();
            while(result.next()){
              String hold=result.getString("parent");
                if(hold==null||list.contains(hold))
                    continue;
               list.add(hold);}
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
    
    
         return list;
     }
    
    //show this parent who had make request to become him/her child
    public ArrayList<String> showReuqestChildren(Connection connection,String parentName){
        Login_Register lr=new Login_Register();
        int main_id=lr.getID(parentName, connection);
        ArrayList <String>list=new ArrayList<>();
         try{
       String query = "SELECT request FROM parent WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, main_id);
            ResultSet result=preparedStatement.executeQuery();
            while(result.next()){
                String hold=result.getString("request");
                if(hold==null)
                    continue;
               list.add(hold);
            }
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
    
    
         return list;
         
    }
    
    public ArrayList<String> showChildren(Connection connection,String parentName){
        Login_Register lr=new Login_Register();
        int main_id=lr.getID(parentName, connection);
        ArrayList <String>list=new ArrayList<>();
         try{
       String query = "SELECT children FROM parent WHERE main_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, main_id);
            ResultSet result=preparedStatement.executeQuery();
            while(result.next()){
              String hold=result.getString("children");
                if(hold==null||list.contains(hold))
                    continue;
               list.add(hold);}
        }
     
        catch(SQLException e){
            e.printStackTrace();
        }
    
    
         return list;
     }
}
