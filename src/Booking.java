
public class Booking {
    private int bookingId;
    private String bookingDate;
    private String eventName;
    private String venue;
    private String eventDate;
    private User user; // The user who made the booking

    // Constructor
    public Booking(int bookingId, String bookingDate, String eventName, String venue, String eventDate, User user) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.user = user;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingDate=" + bookingDate +
                ", eventName='" + eventName + '\'' +
                ", venue='" + venue + '\'' +
                ", eventDate=" + eventDate +
                ", user=" + user.getUsername() + // Print username for user who made the booking
                '}';
    }
}
