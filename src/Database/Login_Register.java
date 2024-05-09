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
import Student.GlobalLeaderBoard;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Login_Register {

    private static int id = 0;

    public static boolean login(String name_email, String pw, Connection connection) throws SQLException {
        User user = new User();
        GlobalLeaderBoard glb = new GlobalLeaderBoard();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Database database = new Database();
        // Connection connection=database.connectionDatabase();

        double min = -500.0;
        double max = 500.0;
        Random random = new Random();
        double x_coordinate = min + (random.nextDouble() * (max - min));
        double y_coordinate=min + (random.nextDouble() * (max - min));;
        String coordinate=String.format("%.1f", x_coordinate)+","+String.format("%.1f", y_coordinate);
        database.updateCoordinate(connection, coordinate);
        id = getID(name_email, connection);
        user.userData(id);
        boolean comfirmPw = passwordEncoder.matches(pw, User.getCurrentUser().getPassword());
        if ((name_email.equalsIgnoreCase(User.getCurrentUser().getUsername()) && comfirmPw)
                || (name_email.equalsIgnoreCase(User.getCurrentUser().getEmail()) && comfirmPw)) {
            // glb.updateXpState(connection, id);
            User.setCurrentUser(user);
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public static boolean register(ArrayList<String> parent, ArrayList<String> children, String username, String email,
            String password, String role) {
        Database database = new Database();
        Random rand = new Random();
        Connection connection = database.connectionDatabase();
        GlobalLeaderBoard glb = new GlobalLeaderBoard();
        String encyrptPw = encrypt(password);
        double min = -500.0;
        double max = 500.0;
        Random random = new Random();
        double x_coordinate = min + (random.nextDouble() * (max - min));
        double y_coordinate=min + (random.nextDouble() * (max - min));;
        String coordinate=String.format("%.1f", x_coordinate)+","+String.format("%.1f", y_coordinate);
        /*
         * if(role.equalsIgnoreCase("parent")){
         * String name ="";
         * System.out.println("Enter the children name (1 to stop process): ");
         * name=input.nextLine();
         * do{
         * children.add(name);
         * name=input.nextLine();
         * }while(!name.equalsIgnoreCase("1"));
         * }
         * if(role.equalsIgnoreCase("children")){
         * String name="";
         * System.out.println("Enter the parents name (1 to stop process): ");
         * name=input.nextLine();
         * do{
         * parent.add(name);
         * name=input.nextLine();
         * }while(!name.equalsIgnoreCase("1"));
         * }
         */
        try {
            String insertQuery = "INSERT INTO user (email, username, password, role, coordinate,point) VALUES (?, ?, ?, ?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, encyrptPw);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, coordinate);
            preparedStatement.setDouble(6, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        id = getID(username, connection);
        // glb.insertXpState(connection, id);
        insertParent(connection, id, parent);
        insertChildren(connection, id, children);
        User.setCurrentUser(getUser(username, connection));
        return true;
    }

    public static String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static int getID(String name_email, Connection connection) {
        int Id = 0;
        String query = "SELECT id FROM user WHERE username=? OR email=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name_email);
            preparedStatement.setString(2, name_email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Id = resultSet.getInt("id");
                System.out.println("ID found: " + Id);
            } else {
                System.out.println("No ID found for the given name and email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Id;
    }

    public static void insertParent(Connection connection, int id, ArrayList<String> parent) {
        try {
            PreparedStatement insertRelated = connection
                    .prepareStatement("INSERT INTO children (main_id, parent) VALUES (?, ?)");
            insertRelated.setInt(1, id);
            for (int i = 0; i < parent.size(); i++) {
                String value = parent.get(i);
                insertRelated.setString(2, value);
                insertRelated.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertChildren(Connection connection, int id, ArrayList<String> children) {
        try {
            PreparedStatement insertRelated = connection
                    .prepareStatement("INSERT INTO parent (main_id, children) VALUES (?, ?)");
            insertRelated.setInt(1, id);
            for (int i = 0; i < children.size(); i++) {
                String value = children.get(i);
                insertRelated.setString(2, value);
                insertRelated.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static User getUser(String username, Connection connection) {
        User user = new User();
        int id = getID(username, connection);
        user.userData(id);
        return user;
    }

}
