package GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Database.User;
import Student.GlobalLeaderBoard;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import Database.Login_Register;
import Database.Database;
import Database.Quiz;
import Database.QuizData;
public class QuizPage {
    private static Double label = User.getCurrentUser().getPoints();//fetch from the user db

    public static void updatePoints(){
        label = User.getCurrentUser().getPoints();
    }
    public static VBox quizPageTab(){

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Quiz"); 
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Label pointsLabel = new Label("Points:"+ label);
        pointsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS); 

        HBox hbox= new HBox();
        hbox.getChildren().addAll(titleLabel,spacer1, pointsLabel);
        root.setTop(hbox);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets (10));
        CheckBox checkBox = new CheckBox("Science");
        CheckBox checkBox2 = new CheckBox("Technology");
        CheckBox checkBox3 = new CheckBox("Engineering");
        CheckBox checkBox4 = new CheckBox("Mathematics");
        hbox2.getChildren().addAll(checkBox, checkBox2, checkBox3, checkBox4);
        hbox2.setSpacing(10);
        root.setCenter(hbox2);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ArrayList<QuizData> quizDataList = Quiz.getAllQuiz(Lantern.getConn());
        for (int i = 0; i < quizDataList.size(); i++) { 
            QuizData qd = quizDataList.get(i);
            String quizTitle= qd.getQuizTitle();
            String quizDescription= qd.getDescription();
            String quizContent = qd.getContent();
            String quiztheme = qd.getTheme();

            BorderPane borderPane = BPForEveryQuiz(quizTitle, quiztheme, quizDescription, quizContent);
            gridPane.add(borderPane, i % 5, i / 5);
        }

        root.setBottom(gridPane);
        VBox mainvBox = new VBox();
        mainvBox.getChildren().add(root);
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
            toggleBtn.setOnAction(event->{
                //attempt the quiz
                if(toggleBtn.isSelected()){
                    toggleBtn.setDisable(true);
                  //  Quiz.updateLatestQuizRow(Lantern.getConn());  
                  //  Quiz.initializeRow(Lantern.getConn(), User.getCurrentUser().getUsername());
                    QuizData qd= new QuizData(qtitle, qdescrip, qtheme, qContent);
                    Quiz.attemptQuiz(Lantern.getConn(), qd, User.getCurrentUser().getUsername());
        
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
            glb.insertXpState(Lantern.getConn(),lr.getId());  
            glb.updateXpState(Lantern.getConn(), lr.getId());
            User.getCurrentUser().setPoints(updatedpoint);
            try {
                db.updatePoint(Lantern.getConn(), lr.getId(), updatedpoint);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
                stage.close();
        });
       return borderPane;
    }
 
}
