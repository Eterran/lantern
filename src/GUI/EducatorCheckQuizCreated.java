package GUI;
import java.util.ArrayList;
import Database.Quiz;
import Database.QuizData;
import Database.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EducatorCheckQuizCreated {
    private static ArrayList<QuizData> quizCreated = Quiz.getQuizUser(Lantern.getConn(), User.getCurrentUser().getUsername());
    private static VBox checkQuizBox = new VBox();

    public static void refreshQuizChanges(){
        checkQuizBox.getChildren().clear();
        quizCreated = Quiz.getQuizUser(Lantern.getConn(), User.getCurrentUser().getUsername());
        VBox temp = new VBox();

        HBox row =  new HBox(); 
        VBox column1 = new VBox(); 
        column1.setPadding(new Insets(10));
        row.getChildren().addAll(column1);
        HBox.setHgrow(column1, Priority.ALWAYS);

        column1.setStyle("-fx-background-color: lightyellow");
        column1.setSpacing(20); 
        
        column1.getChildren().add(AddBorderPane());
        for (QuizData quiz: quizCreated) {
            String labelText1 = quiz.getQuizTitle();
            String labelText2 = quiz.getTheme();
            String labelText3 = quiz.getDescription();
            String labelText4 = quiz.getContent();
                   
            BorderPane borderPane = BPForAllQuiz(labelText1, labelText2, labelText3, labelText4);
            column1.getChildren().addAll(borderPane);  
        }

        //creating scrollpane
        ScrollPane scrollPane1 = new ScrollPane(row);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //Never show horizontal scrollbar
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Alaways show vertical scrollbar as needed

        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane1);

        temp.getChildren().add(borderPane);
        checkQuizBox.getChildren().add(temp);

    }

    public static VBox vboxput() {
        //title
        VBox vbox1 = new VBox();
        vbox1.setStyle("-fx-background-color: lightyellow");
        Label label1 = new Label("Quiz Created");
        label1.getStyleClass().add("title");
        label1.setPadding(new Insets(10));
        vbox1.getChildren().add(label1);

        refreshQuizChanges();
        VBox mainvbox = new VBox();
        mainvbox.getChildren().addAll(vbox1, checkQuizBox);
        VBox.setVgrow(mainvbox, Priority.ALWAYS);
       return mainvbox;
    }


    public static BorderPane BPForAllQuiz(String labelText1, String labelText2, String labelText3, String labelText4) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:#E4FFDC; -fx-border-color:#27561E; -fx-border-width: 2px; -fx-border-radius: 10px;");
        borderPane.setPadding(new Insets(15));

        Label label1 = new Label(labelText1);
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(labelText2);
        Label label3 = new Label(labelText3);
        Label label4 = new Label(labelText4);
        MenuButton menubutton = new MenuButton();
        menubutton.setText("Settings");
        menubutton.setStyle("-fx-background-color: white");
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setStyle("-fx-background-color:white");
        menubutton.getItems().addAll(deleteItem);
        menubutton.setAlignment(Pos.TOP_RIGHT);

        deleteItem.setOnAction(event ->{
            QuizData deleteQuiz = new QuizData(labelText1, labelText2, labelText3, labelText4); 
            Quiz.deleteQuiz2(Lantern.getConn(),deleteQuiz,User.getCurrentUser().getUsername());
       
            //are you sure you want to delete? 

            if (borderPane.getParent() instanceof VBox) {
                VBox parentVBox = (VBox) borderPane.getParent();
                parentVBox.getChildren().remove(borderPane);
            } 
        });
        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        BorderPane.setAlignment(label2, Pos.TOP_LEFT);
        BorderPane.setAlignment(label3, Pos.CENTER_LEFT);
        BorderPane.setAlignment(label4, Pos.CENTER_LEFT);
        BorderPane.setAlignment(menubutton, Pos.TOP_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox topBox = new HBox(label1, spacer, menubutton);
        topBox.setPrefHeight(40);
        VBox middleBox = new VBox(label2, label3);
        HBox bottomBox = new HBox(label4);

        borderPane.setTop(topBox);
        borderPane.setCenter(middleBox);
        borderPane.setBottom(bottomBox);

        return borderPane;
    }

    public static BorderPane AddBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:#27561E ;");
        borderPane.setPadding(new Insets(15));
       
        Button addButton = new Button("Add Quiz");  
        addButton.setStyle("-fx-background-color: white;");
        borderPane.setCenter(addButton);

        addButton.setOnAction(event -> {
            VBox content = EducatorCreateQuiz.tabCreateQuiz();
            Stage stage = new Stage();
            stage.setScene(new Scene(content, 500, 300));
            stage.setTitle("Create Quiz");
            stage.show();
        });

        BorderPane.setAlignment(addButton, Pos.CENTER);

        return borderPane;
    }

}



