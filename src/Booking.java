import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.Database;

public class Booking {
    private User user;
    private Database database; 

    
    public Booking(User user) {
        this.user = user;
        this.database = new Database(); 
        try {
            Destination suggestion = new Destination();
            suggestion.suggestDestinations(user.getXCoordinate(), user.getYCoordinate()); 
            displayAvailableTimeSlots(user.getBookingDate()); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAvailableTimeSlots(String selectedDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date selected = dateFormat.parse(selectedDate);

            List<Date> availableDates = getAvailableDates(selected);
            System.out.println("Available Time Slots:");
            for (int i = 0; i < availableDates.size(); i++) {
                System.out.printf("[%d] %s%n", i + 1, dateFormat.format(availableDates.get(i)));
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            e.printStackTrace();
        }
    }

    
    private List<Date> getAvailableDates(Date selectedDate) throws SQLException {
        List<Date> availableDates = new ArrayList<>();
        try (Connection connection = database.connectDatabase();  // Ensure your Database class has a connectDatabase method that returns a Connection object
             PreparedStatement statement = connection.prepareStatement("SELECT event_date FROM events WHERE user_id = ?")) {
            statement.setInt(1, user.getId()); // Assuming User class has getId() method
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Date eventDate = resultSet.getDate("event_date");
                    if (!isSameDay(selectedDate, eventDate) && eventDate.after(selectedDate)) {
                        availableDates.add(eventDate);
                    }
                }
            }
        }
        return availableDates;
    }

    
    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date1).equals(sdf.format(date2));
    }
}
