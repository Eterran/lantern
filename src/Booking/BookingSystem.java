package Booking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Database.Booking;
import Database.User;
import GUI.Lantern;
import Database.Database;
import Database.Login_Register;
import Database.BookingData;

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
        // try (Scanner s = new Scanner(new FileInputStream("lantern-1/Booking.txt"))) {
        try (Scanner s = new Scanner(new FileInputStream("../Booking.txt"))) {

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String name = line;
                String coordinate = s.nextLine();
                Destination destination = new Destination(name, coordinate);
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

    // return the destination arraylist based on the distance
    public ArrayList<Destination> suggestDestinations(String coordinate) {
       
        ArrayList<Double> distances = new ArrayList<>();
        String data[] = coordinate.split(",");
        double userX = Double.parseDouble(data[0]);
        double userY = Double.parseDouble(data[1]);

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

    // label for distance away
    public ArrayList<Double> distanceAway(String coordinate) {
    
        ArrayList<Double> distances = new ArrayList<>();
        String data[] = coordinate.split(",");
        double userX = Double.parseDouble(data[0]);
        double userY = Double.parseDouble(data[1]);

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

                }
            }
        }

        return distances;
    }

    //available time slot (including c)
    public ArrayList<String> getTimeSlots(int destinationId, Date currentDate, User user) {
        ArrayList<String> timeSlots = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Date> availableDates = getAvailableDates(user, destinationId); //7 days
        if (availableDates.isEmpty()) {
            return null;
        }
        for (Date availableDate : availableDates) {
            if (availableDate.compareTo(currentDate) < 0) {
                continue; // Skip dates that are in the past
            }
            ArrayList<Date> bookedDates = getBookedDatesForDestination(destinationId); //check for existing booking (prevent duplication)
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

    public String destinationName(int destinationId) {
        String destinationName[] = { "Petrosaincs Science Discovery Centre", "Tech Dome Penang",
                "Agro Technology Park in MARDI", "National Science Centre", "Marine Aquarium and Musuem",
                "Pusat Sains & Kreativiti Terengganu", "Biomedical Museum", "Telegraph Museum",
                "Penang Science Cluster" };
        String temp = "";
        for (int i = 0; i < destinationName.length; i++) {
            if (destinationId == i) {
                temp = destinationName[i];
                break;
            }
        }
        return temp;
    }

    public int findDestinationID(String destination){
        String destinationName[] = { "Petrosaincs Science Discovery Centre", "Tech Dome Penang",
                "Agro Technology Park in MARDI", "National Science Centre", "Marine Aquarium and Musuem",
                "Pusat Sains & Kreativiti Terengganu", "Biomedical Museum", "Telegraph Museum",
                "Penang Science Cluster" };
        for(int i=0; i<9; i++){
            if(destination.equals(destinationName[i])){
                return i;
            }
        }
        return -1;
    }

    // get the 7 days from the current dates 
    public ArrayList<Date> getAvailableDates(User user, int destinationId) {
        ArrayList<Date> availableDates = new ArrayList<>();
        String destinationName = destinationName(destinationId);

        try {
                Connection connection = Lantern.getConn();
                Statement statement = connection.createStatement();
                String username = user.getUsername();
                Login_Register lg=new Login_Register();
                int id =lg.getID(username, connection);

                // Get current date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                for (int i = 0; i < 7; i++) {
                    // Add days to current date
                    java.util.Date currentDate = calendar.getTime();
                    String currentDateString = sdf.format(currentDate);

                    // Check if there is an existing booking for the current date
                    String query = "SELECT * FROM BookingDate WHERE main_id = " + id + " AND bookingDate = '"+ currentDateString + "'"; //id is not String no need single quote??
                    ResultSet resultSet = statement.executeQuery(query);

                    if (!resultSet.next()) {
                        // If there is no booking for the current date, add it to the available dates
                        if (!new Booking().checkExistingBooking(connection, username, new BookingData(destinationName, currentDateString))) {
                            availableDates.add(new Date(currentDate.getTime()));
                        }
                    }

                    resultSet.close();

                    // Move to the next day
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }

                statement.close();
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableDates;
    }

    // applied
    private ArrayList<Date> getBookedDatesForDestination(int destinationId) {
        ArrayList<Date> bookedDates = new ArrayList<>();
        try (Connection connection = Lantern.getConn()) {
            String query = "SELECT date FROM bookings WHERE destination_id = ?"; // Query to retrieve booked dates
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, destinationId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                if (isDateBooked(date, bookedDates)) {
                    continue;
                } else {
                    bookedDates.add(date);
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
        }
        return bookedDates;
    }

    // Applied
    private boolean isDateBooked(Date specificDate, ArrayList<Date> bookedDates) {
        for (Date bookedDate : bookedDates) {
            if (specificDate.equals(bookedDate)) {
                return true; // Date is booked
            }
        }
        return false; // Date is not booked
    }

}