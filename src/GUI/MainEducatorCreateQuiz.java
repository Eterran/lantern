package GUI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.Login_Register;
import Database.QuizData;
import Student.GlobalLeaderBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Database.User;
import Database.Quiz;
public class MainEducatorCreateQuiz{

    public static VBox QuizButton() {
        HBox hbox = new HBox(20);
        Button Addbutton = new Button("Add");
        Button Editbutton = new Button("Edit");
        Button Deletebutton = new Button("Delete");
        Addbutton.setPrefSize(100, 50);
        Editbutton.setPrefSize(100, 50);
        Deletebutton.setPrefSize(100, 50);

        hbox.getChildren().addAll(Addbutton, Editbutton, Deletebutton);

        VBox mainVBox = new VBox();
        mainVBox.getChildren().add(hbox);
        VBox.setVgrow(hbox, javafx.scene.layout.Priority.ALWAYS);

        Addbutton.setOnAction(e -> {
            VBox content = EducatorCreateQuiz.tabCreateQuiz();
            Stage stage = new Stage();
            stage.setScene(new Scene(content, 500, 300));
            stage.setTitle("Create Quiz");
            stage.show();

        });
        Editbutton.setOnAction(e ->{
            VBox listOutQBox = listOutQuizCreated.educatorEditQuizList();
            Stage stage = new Stage();
            stage.setScene(new Scene(listOutQBox, 600, 500));
            stage.setTitle("Edit quiz");
            stage.show();

            //list out 

            //put in edit function once the user choose to edit

        });
        Deletebutton.setOnAction(e->{
            VBox listOutEBox = listOutEventCreated.educatorEditEventList();
            Stage stage = new Stage();
            stage.setScene(new Scene(listOutEBox, 700, 400));
            stage.setTitle("Edit quiz");
            stage.show();

            
        });
    
        
        return mainVBox;
        
    }

    // public static VBox listOutQuizcreated(){
    //     BorderPane borderPane = new BorderPane();
    //     Label titleLabel = new Label("Edit Quiz");
    //     borderPane.setTop(titleLabel);
    //     titleLabel.setAlignment(Pos.CENTER);
    //     BorderPane.setAlignment(titleLabel, Pos.CENTER);
    //     titleLabel.setFont(new Font(23));
    //     titleLabel.setStyle("-fx-font-weight: bold;");
     
    //     //creating ScrollPane 
    //     ScrollPane scrollPane = new ScrollPane();
    //     scrollPane.setFitToWidth(true);
    //     scrollPane.setPrefSize(391.0, 247.0);

    //     //vbox contain lots of hbox
    //     VBox vBox = new VBox();
    //     VBox.setVgrow(vBox, Priority.ALWAYS); 
    //     User user = new User();
    //     Quiz.getQuizUser(Lantern.getConn(),user.getUsername()); //arraylist

    //     for (int i = 0; i < 20; i++) {
    //         VBox dataBox = new VBox();
    //         dataBox.setPadding(new Insets(5));
    //         Button showAllQuiz = new Button("1" + "     "+ "quiztitle");

    //         if (i % 2 == 0) {
    //             dataBox.setStyle("-fx-background-color: #ADEFD1;");
    //             showAllQuiz.setStyle("-fx-background-color: #ADEFD1;");

    //         } else {
    //             dataBox.setStyle("-fx-background-color: #ffffff;");
    //             showAllQuiz.setStyle("-fx-background-color: #ffffff;");
    //         }
            
    //         showAllQuiz.setPadding(new Insets(5, 10, 5, 5));
    //         showAllQuiz.setMaxWidth(Double.MAX_VALUE);
            
    //         dataBox.getChildren().add(showAllQuiz); 
    //         VBox.setVgrow(dataBox, Priority.ALWAYS);
            
    //         vBox.getChildren().add(dataBox);
    //     }
        
    //     scrollPane.setContent(vBox);
    //     borderPane.setCenter(scrollPane);
        
    //     // Pane leftPane = new Pane();
    //     // leftPane.setPrefSize(50.0, 250.0);
    //     // Pane rightPane = new Pane();
    //     // rightPane.setPrefSize(50.0, 250.0);
    //     Pane bottomPane = new Pane();
    //     bottomPane.setPrefSize(400, 20);
    //     // borderPane.setLeft(leftPane);
    //     // borderPane.setRight(rightPane);
    //     borderPane.setBottom(bottomPane);

    //     VBox mainvbox = new VBox();
    //     // mainvbox.setStyle("-fx-background-color:white;");
    //     mainvbox.getChildren().add(borderPane);
    //     VBox.setVgrow(borderPane, Priority.ALWAYS); //ensure borderPane grow together with Vbox
    //     mainvbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    //     return mainvbox;

    //}

}

