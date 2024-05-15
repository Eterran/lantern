import Database.Event;
import Database.EventData;
import Database.Database;
import Database.Login_Register;
import Database.QuizData;

import java.util.*;
import java.sql.*;

import static Database.Event.createEvent;
import static Database.Event.getAllEvent;
import static Database.Quiz.*;
import static Database.RegisterEvent.getAllEventRegistered;
import static Database.RegisterEvent.registerEvent;
import static Database.Quiz.attempQuiz;


public class CreateEventAndQuizSystem {

    public List<EventData> viewEventPage(){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        List<EventData> events = getAllEvent(connection);

        return events;
    }
    public List<EventData> displayLatestEvents(){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        //Display live event and closest 3 upcoming events
        List<EventData> eventDataList = Event.getLatestEvent(connection);
        List<EventData> latestEvents = convertToEventObjects(eventDataList);

        return latestEvents;
    }
    private List<EventData> convertToEventObjects(List<EventData> eventDataList){
        List<EventData> events = new ArrayList<>();
        for(EventData eventData : eventDataList){
            EventData event = new EventData(eventData.getEventTitle(), eventData.getDescription(), eventData.getVenue(), eventData.getDate(), eventData.getTime());
            events.add(event);
        }
        return events;
    }
    public int getEventCountForUser(String username){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        int eventCount = Event.getNumberOfEvent(connection, username);
        return eventCount;
    }

    public static void register(String username, EventData event){
        Database database = new Database();
        Connection connection = database.connectionDatabase();
        Login_Register lg=new Login_Register();
        int id =lg.getID(username, connection);

        //Check if the event is already registered
        List<EventData> userEvents = getAllEventRegistered(connection, username);
        for(EventData registeredEvent : userEvents){
            if(event.getEventTitle().equalsIgnoreCase(registeredEvent.getEventTitle())){
                throw new IllegalArgumentException("You are already registered for this events.");
            }
        }
        //Check for date clashes with already registered events
        for(EventData registeredEvent : userEvents){
            if(event.getDate().equals(registeredEvent.getDate()));{
                throw new IllegalArgumentException("You are already registered for an event on the same day. Please choose another event ^^");
            }
        }

        //If no clashes, register the event
        registerEvent(connection, event, username);
    }
    private ArrayList<EventData> accessCreateEventPage(EventData event, String username){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        boolean newEvent = createEvent(connection, event, username);
        if(newEvent) {
            ArrayList<EventData> allEvents = getAllEvent(connection);
            return allEvents;
        }else{
            return null;
        }
    }
    private Object accessCreateQuizPage(String title, String description, String theme, String content, String username){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        QuizData newQuizData = new QuizData(title, description, theme, content);
        if(!checkSameQuiz(connection, newQuizData)){
            boolean newQuiz = createQuiz(connection, title, description, theme, content, username);
            if(newQuiz) {
                ArrayList<QuizData> allQuizzes = getAllQuiz(connection);
                return allQuizzes;
            }else{
                return null;
            }
        }else{
           return null;
        }
    }
    public ArrayList<QuizData> viewQuizzes(String theme){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        ArrayList<QuizData> quizzesList = getQuizBasedTheme(connection, theme);
        return quizzesList;
    }

    public void attemptQuizzes(String quiz,String username){
        Database database = new Database();
        Connection connection = database.connectionDatabase();

        attempQuiz(connection, quiz, username);
    }
}
