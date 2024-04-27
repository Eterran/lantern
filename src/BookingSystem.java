public class BookingSystem {
    private ArrayList<Destination> destinations;
    private String[] data;

    public BookingSystem() {
        this.destinations = readDestinationsFromFile();
    }

    private List<Destination> readDestinationsFromFile() {
        List<Destination> destinations = new ArrayList<>();
        try (Scanner s = new Scanner(new FileInputStream("Eterran/lantern/Booking.txt"))) {
            while (s.hasNextLine()) {
                size++;
                s.nextLine();
            }

            data = new String[size];
            s = new Scanner(new FileInputStream("Eterran/lantern/Booking.txt"));

            int i = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                data[i] = line;
                i++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                destinations.add(data[i]);
            } else {
                String[] xy = data[i].split(",");
                X.add(Double.parseDouble(xy[0].trim()));
                Y.add(Double.parseDouble(xy[1].trim()));
            }
        }
        return destinations;
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void suggestDestinations(double userX, double userY) {
        ArrayList<Double> distances = new ArrayList<>();

        for (int i = 0; i < destinations.size(); i++) {
            double distance = calculateDistance(userX, userY, X.get(i), Y.get(i));
            distances.add(distance);
        }

        for (int i = 0; i < destinations.size() - 1; i++) {
            for (int j = i + 1; j < destinations.size(); j++) {
                if (distances.get(i) > distances.get(j)) {
                    double tempDistance = distances.get(i);
                    distances.set(i, distances.get(j));
                    distances.set(j, tempDistance);

                    String tempDestination = destinations.get(i);
                    destinations.set(i, destinations.get(j));
                    destinations.set(j, tempDestination);
                }
            }
        }
    }

    public List<String> getTimeSlots(int destinationId, Date currentDate, User user) {
        List<String> timeSlots = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        
        List<Date> availableDates = getAvailableDates(currentDate, userId);
        for (int i = 0; i < availableDates.size(); i++) {
            timeSlots.add(String.format("[%d] %s", i + 1, dateFormat.format(availableDates.get(i))));
        }
        timeSlots.add("Enter a time slot:");
        return timeSlots;
    }

    private List<Date> getAvailableDates(User user) {
        List<Date> availableDates = new ArrayList<>();

        try {        
              if ("parent".equals(user.getRole())) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lantern", "root", "root");
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM bookings WHERE user_id = " + user.getId() + " AND date >= CURDATE()";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
             availableDates.add(date);
            }
        }
             
            } catch (SQLException e) {
        }

        return availableDates;
    }

    private List<Date> getBookedDatesForDestination(int destinationId) {
    List<Date> bookedDates = new ArrayList<>();
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
private boolean isDateBooked(Date specificDate, List<Date> bookedDates) {
    for (Date bookedDate : bookedDates) {
        if (specificDate.equals(bookedDate)) {
            return true; // Date is booked
        }
    }
    return false; // Date is not booked
}

}