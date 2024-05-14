package GUI;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import Database.Database;
import Database.Event;
import Database.EventData;
import Database.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import Database.Database;
import Database.Quiz;
import Database.QuizData;
import Database.User;
public class EducatorCreateQuiz {

    // public static void main(String[] args) {
    //     launch(args);
    // }

    // @Override
    // public void start(Stage primaryStage) throws Exception {
        
    //     Scene scene = new Scene(tabCreateQuiz(), 650, 400);
    //     primaryStage.setResizable(false);
    //     primaryStage.setTitle("Create Quiz");
    //     primaryStage.setScene(scene);
    //     primaryStage.show();
    
    // }
    private static List<String> QthemeList = new LinkedList<>();
    public static VBox tabCreateQuiz(){
        Label title = new Label("Create Quiz") ;
        title.setPadding(new Insets(10, 0, 0, 10));
        title.setTextFill(Color.BLACK);
        title.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        Label quizTitle = new Label("Quiz Title: ") ;
        Label quizDescription = new Label("Quiz Description: ") ;
        Label quizTheme= new Label("Quiz Theme:");
        Label quizContent = new Label("Quiz Content:");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(quizTitle, quizDescription, quizTheme, quizContent);
        vbox.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        TextField quizTitleTF= new TextField();
        TextArea quizDescriptionTA = new TextArea();
        CheckBox scCheckbox = new CheckBox("Science");
        CheckBox techCheckbox = new CheckBox("Technology");
        CheckBox engCheckbox = new CheckBox("Engineering");
        CheckBox mathCheckbox = new CheckBox("Mathematics");
        TextArea quizContentTF = new TextArea();
       

        quizTitleTF.setPromptText("Enter title");
        quizDescriptionTA.setPromptText("Enter description");
        quizContentTF.setPromptText("Enter content");
     

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");
        saveBtn.setStyle("-fx-background-color: #9068be; -fx-text-fill: white");
        cancelBtn.setStyle("-fx-background-color:#9068be; -fx-text-fill: white");

        // StringBuilder selectedSubjects = new StringBuilder();
        // scCheckbox.setOnAction(e -> {
        //      if (scCheckbox.isSelected()) {
        //          selectedSubjects.append("Science ");
        //      }
        //  });
        // techCheckbox.setOnAction(e -> {
        //     if (techCheckbox.isSelected()) {
        //         selectedSubjects.append("Technology ");
        //     }
        // }); 
        // engCheckbox.setOnAction(e -> {
        //     if (engCheckbox.isSelected()) {
        //         selectedSubjects.append("Engineering ");
        //     }
        // });
        // mathCheckbox.setOnAction(e -> {
        //     if (mathCheckbox.isSelected()) {
        //         selectedSubjects.append("Mathematics ");
        //     }
        // });

        
        scCheckbox.setOnAction(e -> {
            if (scCheckbox.isSelected()) {
                QthemeList.add("Science");
            } else {
                QthemeList.remove("Science");
            }
        });

        techCheckbox.setOnAction(e -> {
            if (techCheckbox.isSelected()) {
                QthemeList.add("Technology");
            } else {
                QthemeList.remove("Technology");
            }
        });

        engCheckbox.setOnAction(e -> {
            if (engCheckbox.isSelected()) {
                QthemeList.add("Engineering");
            } else {
                QthemeList.remove("Engineering");
            }
        });

        mathCheckbox.setOnAction(e -> {
            if (mathCheckbox.isSelected()) {
                QthemeList.add("Mathematics");
            } else {
                QthemeList.remove("Mathematics");
            }
        });

       User user = User.getCurrentUser();

        saveBtn.setOnAction(e -> {
            if (quizTitleTF.getText().isEmpty() || quizDescriptionTA.getText().isEmpty() ||
                    !(scCheckbox.isSelected() || techCheckbox.isSelected() || engCheckbox.isSelected() || mathCheckbox.isSelected()) ||
                    quizContentTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields before saving.", ButtonType.OK);
                alert.showAndWait();
            } else {
                String QTitle = quizTitleTF.getText();
                String Qdescription = quizDescriptionTA.getText();
                String Qtheme = String.join(", ", QthemeList); //solve chekbox
                String Qcontent = quizContentTF.getText();
        
                boolean savedSuccessfully = Quiz.createQuiz(Lantern.getConn(), QTitle, Qdescription, Qtheme, Qcontent, user.getUsername());
                if (savedSuccessfully) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Quiz saved successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to save quiz.");
                    alert.showAndWait();
                }
            }  
        });


        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400,200);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(6);
        gridPane.setHgap(6);

        // Set ColumnConstraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        gridPane.getColumnConstraints().addAll(column1, column2);

        // Set RowConstraints
        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(quizTitle,0,0);
        gridPane.add(quizTitleTF,1,0);
        gridPane.add(quizDescription,0,1);
        gridPane.add(quizDescriptionTA,1,1);
        gridPane.add(quizTheme,0,2);
        VBox themeCheckbox = new VBox();
        themeCheckbox.getChildren().addAll(scCheckbox, techCheckbox, engCheckbox, mathCheckbox);
        gridPane.add(themeCheckbox,1,2);
        gridPane.add(quizContent,0,3);
        gridPane.add(quizContentTF,1,3);
       
        gridPane.add(saveBtn,0,5);
        gridPane.add(cancelBtn, 0,6);

        VBox mainvBox = new VBox();
        mainvBox.getChildren().addAll(title,gridPane);
        mainvBox.setStyle("-fx-background-color: #e1e8f0; -fx-font-weight: bold;");
        VBox.setVgrow(mainvBox, Priority.ALWAYS);
        

        return mainvBox;
    }

   
}

