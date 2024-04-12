import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    static List<Quiz> quizzes = new ArrayList<>();
    static List<Event> eventList = new ArrayList<>();
    List<Event> registeredEvents = new ArrayList<>();
    List<Quiz> completedQuizzes = new ArrayList<>();
    static List<Educators> educatorsList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private  int points;
    public static void main(String[] args) {
        Educators educator = new Educators();
        educatorsList.add(educator);
        accessCreateEventPage(eventList);
        accessCreateQuizPage(quizzes);

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
        System.out.println("Event added successfully!");
    }
    private static void accessCreateQuizPage(List<Quiz> quizzes){
        System.out.println("Enter quiz title: ");
        String quizTitle = sc.nextLine();
        System.out.println("Enter quiz description: ");
        String description = sc.nextLine();
        System.out.println("Enter quiz theme (Science/ Technology/ Engineering/ Mathematics): ");
        String theme = sc.nextLine();
        System.out.println("Enter quiz content: ");
        String content = sc.nextLine();

        Quiz newQuiz = new Quiz(quizTitle, description, theme, content);
        quizzes.add(newQuiz);
        System.out.println("Quiz added successfully!");
    }
    public void viewQuizzes(List<Quiz> quizzes){
        System.out.println("Please select the theme to filter(comma to separated):");
        String[] filters = sc.nextLine().split(",");
        boolean filtered = false;
        for(Quiz quiz: quizzes){
            for(String filter: filters){
                if(quiz.getTheme().equalsIgnoreCase(filter)){
                    System.out.println(quiz.quizTitle);
                    filtered = true;
                    break;
                }
            }
        }
        if(!filtered){
            System.out.println("No quizzes found for the selected themes.");
        }
    }

    public void attemptQuiz(Quiz quiz){
        System.out.println("Attempting Quiz: "+ quiz.quizTitle);
        completedQuizzes.add(quiz);
        points += 2;
        System.out.println("Quiz completed! You have been awarded 2 marks.");
    }
}