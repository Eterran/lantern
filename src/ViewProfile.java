import java.util.List;


public class ViewProfile {
   
     public void displayProfile(User user) {
        // Display profile information excluding password
        System.out.println("Name: " + user.getUsername());
        System.out.println("Role: " + user.getRole());
        System.out.println("Location Coordinate: " + user.getLocationCoordinate());

        // Display role-specific data based on user's role
        switch (user.getRole()) {
            case EDUCATOR:
                int numQuizzesCreated = getNumQuizzesCreated(user);
                int numEventsCreated = getNumEventsCreated(user);
                System.out.println("Number of quizzes created: " + numQuizzesCreated);
                System.out.println("Number of events created: " + numEventsCreated);
                break;
            case PARENT:
                List<Booking> pastBookings = getPastBookings(user);
                System.out.println("Past bookings:");
                for (Booking booking : pastBookings) {
                    System.out.println(booking);
                }
                break;
            case YOUNG_STUDENT:
                int points = user.getCurrentPoints();
                List<User> friends = user.getFriends();
                System.out.println("Points: " + points);
                System.out.println("Friends:");
                for (User friend : friends) {
                    System.out.println(friend.getUsername());
                }
                break;
        }
    }

    // Method to get the number of quizzes created by an educator
    private int getNumQuizzesCreated(User user) {
        
        return 0; 

    // Method to get the number of events created by an educator
    private int getNumEventsCreated(User user) {
        
        return 0; 
    }

    // Method to get past bookings made by a parent
    private List<Booking> getPastBookings(User user) {
        
        return null; 
    }
}
