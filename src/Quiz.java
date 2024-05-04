public class Quiz {
    protected String quizTitle;
    protected String description;
    protected String theme;
    protected String content;
    protected int numOfQuizCreated;
    private boolean completed;
    
    public Quiz(String quizTitle,String description,String theme,String content, int numOfQuizCreated){
        this.quizTitle= quizTitle;
        this.description= description;
        this.theme= theme;
        this.content= content;
        this.completed = false;
        this.numOfQuizCreated = numOfQuizCreated;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public int getNumOfQuizCreate() {
        return numOfQuizCreated;
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

    @Override
    public String toString() {
        return "Quiz{" +
                "quizTitle='" + quizTitle + '\'' +
                ", description='" + description + '\'' +
                ", theme='" + theme + '\'' +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                ", numOfQuizCreate=" + numOfQuizCreated +
                '}';
    }
}
