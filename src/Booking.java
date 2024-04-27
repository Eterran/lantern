import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Database.Database;


public class Booking {
    private User user;

    try{
        Database database=new Database();
        Connection connection=database.connectionDatabase();
    }catch(SQLExeption e){
        e.printStackTrace;
    }

    public Booking(User user, Double x1, Double y1){
        Destination suggestion = new Destination();

        suggestDestinations(x1, y1);

        displayAvailableTimeSlots(user.getBookingDate);
    }

    public void displayAvailableTimeSlots(String selectedDate) {
        try {
            Date selected = new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate);

           // System.out.println("Available Time Slots:");
            List<Date> availableDates = getAvailableDates(selected);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < availableDates.size(); i++) {
                System.out.printf("[%d] %s%n", i + 1, dateFormat.format(availableDates.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Date> getAvailableDates(Date selectedDate) {
        List<Date> availableDates = new ArrayList<>();
        try (Connection connection = database.connectDatabase();
             PreparedStatement statement = connection.prepareStatement("SELECT event_date FROM events")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Date eventDate = resultSet.getDate("event_date");
                    if (!isSameDay(selectedDate, eventDate) && !eventDate.before(selectedDate)) {
                        availableDates.add(eventDate);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableDates;
    }

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date1).equals(dateFormat.format(date2));
    }

}
