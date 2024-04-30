package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

        saveBtn.setOnAction(e -> {
            if (quizTitleTF.getText().isEmpty() || quizDescriptionTA.getText().isEmpty() ||
                    !(scCheckbox.isSelected() || techCheckbox.isSelected() || engCheckbox.isSelected() || mathCheckbox.isSelected()) ||
                    quizContentTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields before saving.", ButtonType.OK);
                alert.showAndWait();
            } else {
                // here to save the quiz
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Quiz saved successfully.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        //Creating gridpane
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400,200);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(6);
        gridPane.setHgap(6);

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
        

        return mainvBox;
    }

   
}

