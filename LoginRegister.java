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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class LoginRegister {
    
    public int choose(){
        Scanner input =new Scanner(System.in);
        System.out.println("1. Login ");
        System.out.println("2. Register ");
        System.out.println("Please enter the number to choose the operation: ");
        int choice=input.nextInt();
        //input.close();
        return choice;
    }
    
    public boolean login(){
        User user=new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Database database=new Database();
        Connection connection=database.connectionDatabase();
        int id =0;
        Scanner input=new Scanner(System.in);
        Random rand=new Random();
        boolean check=false;
        String username="";
        String email="";
         do{  
             
        System.out.println("Choose method to login (1 is username,2 is email): ");
        int number =input.nextInt();
        if(number==1){
            System.out.println("Enter username: ");
            input.nextLine();
            username=input.nextLine();
            id=getID_Username(username,connection);
        }
        else if(number ==2){
            System.out.println("Enter email");
            input.nextLine();
        email=input.nextLine();
        id=getID_Email(email,connection);}
            
        System.out.println("Enter the pasword: ");
        String password=input.nextLine();
        user.userData(id);
        boolean comfirmPw=passwordEncoder.matches(password,user.password);
        if ((username.equalsIgnoreCase(user.username)&&comfirmPw)||(email.equalsIgnoreCase(user.email)&&comfirmPw)){
            System.out.println("You have success in login");
            check=true;
        return true ;
        }
        else {
            System.out.println("You are fail to login");
         }
        }while(!check);
         
         return false;
         }
        
        
        
        
     
    
    public void register(){
        ArrayList <String>parent=new ArrayList<>();
        ArrayList <String >children=new ArrayList<>();
        Database database=new Database();
        Random rand=new Random();
        Connection connection=database.connectionDatabase();
        Scanner input=new Scanner(System.in);
        System.out.println("Please enter the username: ");
        String username=input.nextLine();
        System.out.println("Please enter the email");
        String email=input.nextLine();
        System.out.println("Please enter the password: ");
        String password=input.nextLine();
        String encyrptPw=encrypt(password);
        System.out.println("Please enter the role: ");
       String role=input.nextLine();
        int x_coordinate=rand.nextInt(1001)-500;
        int y_coordinate=rand.nextInt(1001)-500;
        String coordinate="("+x_coordinate+","+y_coordinate+ ")";
        System.out.println("This is the coordinate : "+coordinate);
        if(role.equalsIgnoreCase("parent")){
            String name ="";
            System.out.println("Enter the children name (1 to stop process): ");
            name=input.nextLine();
             do{
                 children.add(name);
                 name=input.nextLine();
             }while(!name.equalsIgnoreCase("1"));
        }
        if(role.equalsIgnoreCase("children")){
            String name="";
            System.out.println("Enter the parents name (1 to stop process): ");
             name=input.nextLine();
             do{
                 parent.add(name);
                 name=input.nextLine();
             }while(!name.equalsIgnoreCase("1"));
        }
        try{
       String insertQuery = "INSERT INTO user (email, username, password, role, coordinate,point) VALUES (?, ?, ?, ?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, encyrptPw);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5,coordinate );
            preparedStatement.setDouble(6,0);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        int id =getID_Username(username,connection);
        insertParent(connection,id,parent);
        insertChildren(connection,id,children);
        
    }
    
    
    public static String encrypt(String password){
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(password);
    }
    
    public int getID_Username(String name, Connection connection){
        int id=0 ;
    String sql = "SELECT id FROM user WHERE username = ? ";
    try{
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, name);
    ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        id = resultSet.getInt("id");
        System.out.println("ID found: " + id);
    } else {
        System.out.println("No ID found for the given name and email.");
    }
    }
    catch (SQLException e){
        e.printStackTrace();}
    return id;
    }
    
     public int getID_Email(String email, Connection connection){
        int id=0 ;
    String sql = "SELECT id FROM user WHERE email = ? ";
    try{
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, email);
    ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        id = resultSet.getInt("id");
        System.out.println("ID found: " + id);
    } else {
        System.out.println("No ID found for the given name and email.");
    }
    }
    catch (SQLException e){
        e.printStackTrace();}
    return id;
    }
    
    
    
    public static void insertParent(Connection connection,int id,ArrayList<String>parent){
     try{
         PreparedStatement insertRelated = connection.prepareStatement("INSERT INTO children (main_id, parent) VALUES (?, ?)");
         insertRelated.setInt(1, id);
             for (int i=0;i<parent.size();i++) {
                String value=parent.get(i);
                insertRelated.setString(2, value);
                insertRelated.executeUpdate();
            }
    }
    catch(SQLException e){
        e.printStackTrace();}
     
}
    
     public static void insertChildren(Connection connection,int id,ArrayList<String>children){
     try{
         PreparedStatement insertRelated = connection.prepareStatement("INSERT INTO parent (main_id, children) VALUES (?, ?)");
         insertRelated.setInt(1, id);
             for (int i=0;i<children.size();i++) {
                String value=children.get(i);
                insertRelated.setString(2, value);
                insertRelated.executeUpdate();
            }
    }
    catch(SQLException e){
        e.printStackTrace();}
     
}


}
