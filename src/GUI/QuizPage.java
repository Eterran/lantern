package GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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

import java.util.ArrayList;

import Database.Quiz;
import Database.QuizData;
public class QuizPage {

 
    public static VBox quizPageTab(){

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Quiz"); //if not at the center, then just adjust this bah
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        User user = User.getCurrentUser(); 
        Double label = user.getPoints();; //fetch from the user db
        Label pointsLabel = new Label("Points:"+ label);
        pointsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        
        //ComboBox<String> comboBox = new ComboBox<>();
        //comboBox.getItems().addAll("Science", "Technology", "Engineering", "Mathematics"); 
        // Label filterLabel = new Label("Filter");
        // filterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS); // add a space between each label in a hbx

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
        
        int totalNumOfQuiz = Quiz.getTotalNumberOfQuizzes(Lantern.getConn());
        for (int i = 0; i < totalNumOfQuiz; i++) { 
            QuizData qd = quizDataList.get(i);
            String quizTitle= qd.getQuizTitle();
            String quizDescription= qd.getDescription();
            String quizContent = qd.getContent();
            String quiztheme = qd.getTheme();

            BorderPane borderPane = BPForEveryQuiz(quizTitle, quiztheme, quizDescription, quizContent, "Start Attempt");
            gridPane.add(borderPane, i % 4, i / 4);
        }

        root.setBottom(gridPane);
        VBox mainvBox = new VBox();
        mainvBox.getChildren().add(root);
        return mainvBox;

    }


    public static BorderPane BPForEveryQuiz(String qtitle, String qtheme, String qdescrip, String qContent, String buttonText) {
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
        Button button = new Button(buttonText);
        button.setAlignment(Pos.BOTTOM_RIGHT);

        button.setOnAction(event->{
            Stage stage = new Stage();
            stage.setScene(new Scene(showQuiz(qContent), 400, 200));
            stage.setTitle("Quiz Description");
            stage.show();
        });
        

        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        VBox storelabel23 = new VBox();
        storelabel23.getChildren().addAll(label2, label3);
        BorderPane.setAlignment(storelabel23, Pos.CENTER);
        BorderPane.setAlignment(button, Pos.BOTTOM_RIGHT);

        borderPane.setTop(label1);
        borderPane.setLeft(storelabel23);
        borderPane.setBottom(button);

        return borderPane;
    }

    //show quiz description and finish attempt button
    public static BorderPane showQuiz(String quizContent){
        BorderPane borderPane= new BorderPane();
        
        Label quizC = new Label (quizContent);
        Button finishAttemptBtn = new Button("Finish Attempt");
       
        borderPane.setTop(quizC);
        borderPane.setBottom(finishAttemptBtn);
        BorderPane.setAlignment(finishAttemptBtn,Pos.BOTTOM_RIGHT);

       BorderPane.setMargin(quizC, new Insets(10)); 
       BorderPane.setMargin(finishAttemptBtn, new Insets(0, 10, 10, 0)); 


        finishAttemptBtn.setOnAction(e ->{
//          #calculate the timer
//          #back to the quizpage (history)
        });

       return borderPane;
    }
    
   
   // @Override
    // public void start(Stage primaryStage) {
    //     Scene scene = new Scene(quizPageTab(), 800, 600);
    //     scene.setFill(Color.valueOf("#e1e8f0"));
    //     primaryStage.setResizable(false);
    //     primaryStage.setScene(scene);
    //     primaryStage.setTitle("Quiz Page");
    //     primaryStage.show();
    // }
   
    // public static void main(String[] args) {
    //     launch(args);
    // }
}
