package GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Database.User;
import Student.GlobalLeaderBoard;
import java.sql.SQLException;
import java.util.ArrayList;
import Database.Login_Register;
import Database.Database;
import Database.Quiz;
import Database.QuizData;
public class QuizPage {
    private static Double pointLabel = User.getCurrentUser().getPoints();//fetch from the user db
    private static VBox displayQuizBox = new VBox();
    private static VBox displayPointsBox = new VBox();
    private static VBox column1 = new VBox();
    private static VBox column2 = new VBox();
    private static ArrayList<String> selectedThemes = new ArrayList<>();

    public static void updatePointsVbox(){
        displayPointsBox.getChildren().clear();
        updatePoints();
        Label pointsLabel = new Label("Points: "+ pointLabel);
        pointsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        displayPointsBox.getChildren().add(pointsLabel);
    }

    public static void updatePoints(){
        pointLabel = User.getCurrentUser().getPoints();
    }

    public static void refreshQuizBasedOnThemes(ArrayList<String> themes) {
        displayQuizBox.getChildren().clear();
        VBox temp = new VBox();
        
        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); 
    
        HBox row = new HBox();
        row.setPadding(new Insets(10));
        VBox column1 = new VBox();
        column1.setPadding(new Insets(10));
        VBox column2 = new VBox();
        column2.setPadding(new Insets(10));
        row.getChildren().addAll(column1, column2);
        scrollPane1.setContent(row);
       
        HBox.setHgrow(column1, Priority.ALWAYS);
        HBox.setHgrow(column2, Priority.ALWAYS);
        column1.setSpacing(20); 
        column2.setSpacing(20);
    
        ArrayList<QuizData> filteredData = new ArrayList<>();
        for (String theme : themes) {
            filteredData.addAll(Quiz.getQuizBasedTheme(Lantern.getConn(), theme));
        }
       
        column1.getChildren().clear();
        column2.getChildren().clear();
       
        for (int i = 0; i < filteredData.size(); i++) {
            QuizData qd = filteredData.get(i);
            String quizTitle = qd.getQuizTitle();
            String quizDescription = qd.getDescription();
            String quizContent = qd.getContent();
            String quizTheme = qd.getTheme();
    
            BorderPane borderPane = BPForEveryQuiz(quizTitle, quizTheme, quizDescription, quizContent);
            if (i % 2 == 0) {
                column1.getChildren().add(borderPane);
            } else {
                column2.getChildren().add(borderPane);
            }
        }
      
        temp.getChildren().add(scrollPane1);
        displayQuizBox.getChildren().add(temp);
    }
    

    public static VBox quizPageTab(){    
        VBox topvbox = new VBox(); 
        Label titleLabel = new Label("Quiz"); 
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        updatePointsVbox();
       

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS); 
        HBox hbox= new HBox();
        hbox.getChildren().addAll(titleLabel,spacer1, displayPointsBox);
       
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets (10));
        CheckBox checkBox = new CheckBox("Science");
        CheckBox checkBox2 = new CheckBox("Technology");
        CheckBox checkBox3 = new CheckBox("Engineering");
        CheckBox checkBox4 = new CheckBox("Mathematics");
        hbox2.getChildren().addAll(checkBox, checkBox2, checkBox3, checkBox4);
        hbox2.setSpacing(10);

        checkBox.setOnAction(event ->{
            if (checkBox.isSelected()) {
                selectedThemes.add("Science");
            } else {
                selectedThemes.remove("Science");
            }
            refreshQuizBasedOnThemes(selectedThemes);
        });
        
        checkBox2.setOnAction(event ->{
            if (checkBox2.isSelected()) {
                selectedThemes.add("Technology");
            } else {
                selectedThemes.remove("Technology");
            }
            refreshQuizBasedOnThemes(selectedThemes);
        });
        
        checkBox3.setOnAction(event ->{
            if (checkBox3.isSelected()) {
                selectedThemes.add("Engineering");
            } else {
                selectedThemes.remove("Engineering");
            }
            refreshQuizBasedOnThemes(selectedThemes);
        });
        
        checkBox4.setOnAction(event ->{
            if (checkBox4.isSelected()) {
                selectedThemes.add("Mathematics");
            } else {
                selectedThemes.remove("Mathematics");
            }
            refreshQuizBasedOnThemes(selectedThemes);
        });
        
        ArrayList<QuizData> quizDataList = Quiz.getAllQuiz(Lantern.getConn());
        for (int i = 0; i < quizDataList.size(); i++) { 
            QuizData qd = quizDataList.get(i);
            String quizTitle= qd.getQuizTitle();
            String quizDescription= qd.getDescription();
            String quizContent = qd.getContent();
            String quiztheme = qd.getTheme();

            BorderPane borderPane = BPForEveryQuiz(quizTitle, quiztheme, quizDescription, quizContent);
            if(i%2==0){
                column1.getChildren().add(borderPane);
            }else{
                column2.getChildren().add(borderPane);
            }         
        }
        topvbox.getChildren().addAll(hbox,hbox2);

        VBox mainvBox = new VBox();
        mainvBox.getChildren().addAll(topvbox, displayQuizBox);
        return mainvBox;
    }


    public static BorderPane BPForEveryQuiz(String qtitle, String qtheme, String qdescrip, String qContent) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(180);
        borderPane.setPrefHeight(120);
        borderPane.setStyle("-fx-background-color: #226c94;");
        borderPane.setPadding(new Insets(10));

        Label label1 = new Label(qtitle);
        label1.setTextFill(Color.WHITE); 
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label label2 = new Label(qtheme);
        label2.setTextFill(Color.WHITE); 
        label2.setFont(Font.font("Arial", 10));
        Label label3 = new Label(qdescrip);
        label3.setTextFill(Color.WHITE); 
        label3.setFont(Font.font("Arial", 10));
        ToggleButton toggleBtn = new ToggleButton("Start Attempt");
        toggleBtn.setAlignment(Pos.BOTTOM_RIGHT);
   
        if(Quiz.checkAttempted(Lantern.getConn(),qtitle,User.getCurrentUser().getUsername())){
            toggleBtn.setDisable(true);
        }else{
            toggleBtn.setDisable(false);
            toggleBtn.setOnAction(event->{
                if(toggleBtn.isSelected()){
                    toggleBtn.setDisable(true);
                    QuizData qd= new QuizData(qtitle, qdescrip, qtheme, qContent);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(showQuiz(qContent, stage, qd), 400, 200));
                    stage.setTitle("Quiz Description");
                    stage.show();

                }
            });
            
        }
 
        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        VBox storelabel23 = new VBox();
        storelabel23.getChildren().addAll(label2, label3);
        BorderPane.setAlignment(storelabel23, Pos.CENTER);
        BorderPane.setAlignment(toggleBtn, Pos.BOTTOM_RIGHT);

        borderPane.setTop(label1);
        borderPane.setLeft(storelabel23);
        borderPane.setBottom(toggleBtn);

        return borderPane;
    }

    //show quiz description and finish attempt button
    public static BorderPane showQuiz(String quizContent,Stage stage, QuizData qd){
        BorderPane borderPane= new BorderPane();
        
        Label quizC = new Label (quizContent);
        Button finishAttemptBtn = new Button("Finish Attempt");
       
        borderPane.setTop(quizC);
        borderPane.setBottom(finishAttemptBtn);
        BorderPane.setAlignment(finishAttemptBtn,Pos.BOTTOM_RIGHT);

        BorderPane.setMargin(quizC, new Insets(10)); 
        BorderPane.setMargin(finishAttemptBtn, new Insets(0, 10, 10, 0)); 

        Login_Register lr = new Login_Register();
        GlobalLeaderBoard glb= new GlobalLeaderBoard();
        Database db = new Database();
       
        finishAttemptBtn.setOnAction(e ->{
            Quiz.attemptQuiz(Lantern.getConn(), qd, User.getCurrentUser().getUsername());  
            double updatedpoint = User.getCurrentUser().getPoints() + 2;  
            glb.updateXpState(Lantern.getConn(), lr.getId()); 
            User.getCurrentUser().setPoints(updatedpoint);
            
            try {
                db.updatePoint(Lantern.getConn(), lr.getId(), updatedpoint);
                updatePointsVbox();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
                stage.close();
        });
       return borderPane;
    }
 
}
