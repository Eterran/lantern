public class Event {
    protected String eventTitle;
    protected String description;
    protected String venue;
    protected String date;
    protected String time;
    protected int numOfEventCreated;
    
    public Event(String eventTitle, String description, String venue, String date, String time, int numOfEventCreated){
        this.eventTitle=eventTitle;
        this.description=description;
        this.venue=venue;
        this.date=date;
        this.time=time;
        this.numOfEventCreated=numOfEventCreated;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public int getNumOfEventCreated() {
        return numOfEventCreated;
    }
     public static void viewEvent(List<Event> eventList){
        if(eventList.isEmpty()){
            System.out.println("No events available.");
            return;
        }
        //Display live event
        System.out.println("Live Events : "+ eventList.get(0));
        //Display closest 3 upcoming events
        System.out.println("Closest 3 Upcoming Events : ");
        for(int i =0; i<3; i++) {
            System.out.println((i + 1) + "." + eventList.get(i));
        }

    }

    @Override
    public String toString() {
        return "Event{" +
                "eventTitle='" + eventTitle + '\'' +
                ", description='" + description + '\'' +
                ", venue='" + venue + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", numOfEventCreated=" + numOfEventCreated +
                '}';
    }
}
