package Booking;

import java.util.ArrayList;
import java.util.Date;


import Database.User;

public class Booking {
    private User user;
    private Date currentdate;
    private int destinationid;

    
    public Booking(User user, Date date, int destinationid) {
        this.user = user;
        this.currentdate = date;
        this.destinationid = destinationid;
            BookingSystem suggestion = new BookingSystem();
            ArrayList<Destination> destinations = suggestion.suggestDestinations(user.getCoordinate()); 
            ArrayList<String> availableDates = suggestion.getTimeSlots(destinationid, date, user);
                         
    }
    

    
}
