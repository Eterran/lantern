/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author den51
 */
public class QuizData {
    
    protected String quizTitle;
    protected String description;
    protected String theme;
    protected String content;
    //protected int numOfQuizCreated;
   // private boolean completed;
    
    
    public QuizData(String quizTitle,String description,String theme,String content){
        this.quizTitle= quizTitle;
        this.description= description;
        this.theme= theme;
        this.content= content;
        //this.completed = false;
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

  
    @Override
     public String toString() {
        return "Quiz Title : "+quizTitle+"\nDescription:  "+description+"\nTheme: "+theme+"\nContent: "+content;
    }
}
