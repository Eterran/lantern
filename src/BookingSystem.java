import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Database.BookingDate;
import Database.User;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingSystem {
    private ArrayList<Destination> destinations;
    int size = 0;

    public BookingSystem() {
        this.destinations = readDestinationsFromFile();
    }

    private ArrayList<Destination> readDestinationsFromFile() {
    ArrayList<Destination> destinations = new ArrayList<>();
    try (Scanner s = new Scanner(new FileInputStream("Eterran/lantern/Booking.txt"))) {
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String name = line;
            line = s.nextLine();
            String[] parts = line.split(",");
            double x = Double.parseDouble(parts[1].trim());
            double y = Double.parseDouble(parts[2].trim());
            Destination destination = new Destination(name, x, y);
            destinations.add(destination);
        }
    } catch (FileNotFoundException ex) {
        System.out.println("File not found");
    }
    return destinations;
}


    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public ArrayList<Destination> suggestDestinations(double userX, double userY) {
        ArrayList<Double> distances = new ArrayList<>();
    
        for (int i = 0; i < destinations.size(); i++) {
            double x = destinations.get(i).getX();
            double y = destinations.get(i).getY();
            double distance = calculateDistance(userX, userY, x, y);
            distances.add(distance);
        }
    
        for (int i = 0; i < destinations.size() - 1; i++) {
            for (int j = i + 1; j < destinations.size(); j++) {
                if (distances.get(i) > distances.get(j)) {
                    double tempDistance = distances.get(i);
                    distances.set(i, distances.get(j));
                    distances.set(j, tempDistance);
    
                    Destination tempDestination = destinations.get(i);
                    destinations.set(i, destinations.get(j));
                    destinations.set(j, tempDestination);
                }
            }
        }
        
        return destinations;
    }
    
    

    public ArrayList<String> getTimeSlots(int destinationId, Date currentDate, User user) {
        ArrayList<String> timeSlots = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        
        ArrayList<Date> availableDates = getAvailableDates(user);
        if(availableDates.isEmpty()){
            return null;
        }
        for (Date availableDate : availableDates) {
            if (availableDate.compareTo(currentDate) < 0) {
                continue; // Skip dates that are in the past
            }
            ArrayList<Date> bookedDates = getBookedDatesForDestination(destinationId);
            if (isDateBooked(availableDate, bookedDates)) {
                continue; // Skip dates that are already booked
            }
            timeSlots.add(dateFormat.format(availableDate));
        }

        if (timeSlots.isEmpty()) {
            return null; // No available dates
        }
        for (int i = 0; i < availableDates.size(); i++) {
            timeSlots.add(String.format("[%d] %s", i + 1, dateFormat.format(availableDates.get(i))));
        }
        
        return timeSlots;
    }

    private ArrayList<Date> getAvailableDates(User user) {
    ArrayList<Date> availableDates = new ArrayList<>();

    try {
        if ("parent".equals(user.getRole())) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lantern", "username", "password");
            Statement statement = connection.createStatement();
            String username = user.getUsername(); 

            // Construct the SQL query to retrieve bookings for the user
            String query = "SELECT * FROM bookings WHERE user_date = '" + username + "' AND date >= CURDATE()";

            // Execute the query
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate through the result set
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");

                // Convert date to string in format "yyyy-MM-dd"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(date);

                // Check if the date exists
                if (!new BookingDate().checkExistingDate(connection, username, dateString)) {
                    availableDates.add(date);
                }
            }
            
            resultSet.close();
            statement.close();
            connection.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return availableDates;
}


    private ArrayList<Date> getBookedDatesForDestination(int destinationId) {
        ArrayList<Date> bookedDates = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lantern", "username", "password")) {
        String query = "SELECT date FROM bookings WHERE destination_id = ?"; // Query to retrieve booked dates
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, destinationId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Date date = resultSet.getDate("date");
            if(isDateBooked(date, bookedDates)){
                continue;
            }
            else{
                bookedDates.add(date);
            }
        }
    } catch (SQLException e) {
        // Handle exceptions
    }
    return bookedDates;
}
private boolean isDateBooked(Date specificDate, ArrayList<Date> bookedDates) {
    for (Date bookedDate : bookedDates) {
        if (specificDate.equals(bookedDate)) {
            return true; // Date is booked
        }
    }
    return false; // Date is not booked
}

}