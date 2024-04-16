public class Quiz {
    protected String quizTitle;
    protected String description;
    protected String theme;
    protected String content;
    private boolean completed;
    
    public Quiz(String quizTitle,String description,String theme,String content){
        this.quizTitle= quizTitle;
        this.description= description;
        this.theme= theme;
        this.content= content;
        this.completed = false;
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

    @Override
    public String toString() {
        return "Quiz{" +
                "quizTitle='" + quizTitle + '\'' +
                ", description='" + description + '\'' +
                ", theme='" + theme + '\'' +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                '}';
    }
}
