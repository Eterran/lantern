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
    public void register(Event event){
        System.out.println("Select an event: ");
        String choice = sc.nextLine();
        //Check if the event is already registered
        for(Event registeredEvent : registeredEvents){
            if(choice.equalsIgnoreCase(registeredEvent.getEventTitle())){
                System.out.println("You are already registered for this events.");
                return;
            }
        }
        //Check for date clashes with already registered events
        for(Event registeredEvent : registeredEvents){
            if(event.getDate().equals(registeredEvent.getDate()));{
                System.out.println("You are already registered for an event on the same day. Please choose another event ^^");
                return;
            }
        }

        //If no clashes
        Event chosenEvent = null;
        for(Event e : eventList){
            if(e.getEventTitle().equalsIgnoreCase(choice)){
                chosenEvent = e;
                break;
            }
        }
        registeredEvents.add(chosenEvent);
        points+=5;
        System.out.println("You have successfully registered for the event: "+ chosenEvent.getEventTitle());
        System.out.println("You have been awarded 5 marks!");
    }

    private static void accessCreateEventPage(List<Event> eventList){
        System.out.println("Enter event title: ");
        String eventTitle = sc.nextLine();
        System.out.println("Enter event description: ");
        String descrip = sc.nextLine();
        System.out.println("Enter event venue: ");
        String venue = sc.nextLine();
        System.out.println("Enter event date: ");
        String date = sc.nextLine();
        System.out.println("Enter event time: ");
        String time = sc.nextLine();

        Event newEvent = new Event(eventTitle, descrip, venue, date, time);
        eventList.add(newEvent);
        numOfEventCreated++;
        System.out.println("Event added successfully!");
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
