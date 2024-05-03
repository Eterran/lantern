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
    private static int numOfEventCreated = 0;
    private static int numOfQuizCreated = 0;
    private  int points;
    public static void main(String[] args) {
        

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
        numOfQuizCreated++;
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
