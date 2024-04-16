public class Event {
    protected String eventTitle;
    protected String description;
    protected String venue;
    protected String date;
    protected String time;
    
    public Event(String eventTitle, String description, String venue, String date, String time){
        this.eventTitle=eventTitle;
        this.description=description;
        this.venue=venue;
        this.date=date;
        this.time=time;
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
}
