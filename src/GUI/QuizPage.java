package GUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class QuizPage {

 
    public static VBox quizPageTab(){

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Quiz"); //if not at the center, then just adjust this bah
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        String label = "120"; //fetch from the db 
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

        for (int i = 0; i < 5; i++) {
            //String labelText = (calling method to fetch the question theme from db);  fetchingmethod(int index)
            //String labelText2 = (calling method to fetch the theme description from db);  fetchingmethod(int index)
            String labelText1 = "Quiz Title";
            String labelText2 = "Quiz theme";
            BorderPane borderPane = BPForEveryQuiz(labelText1, labelText2, "Start Attempt","#226c94");
            gridPane.add(borderPane, i % 4, i / 4);
        }

        root.setBottom(gridPane);
        VBox mainvBox = new VBox();
        mainvBox.getChildren().add(root);
        return mainvBox;

    }


    public static BorderPane BPForEveryQuiz(String labelText1, String labelText2, String buttonText, String backgroundColor) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(180);
        borderPane.setPrefHeight(100);
        borderPane.setStyle("-fx-background-color: " + backgroundColor + ";");
        borderPane.setPadding(new Insets(10));

        Label label1 = new Label(labelText1);
        label1.setTextFill(Color.WHITE); 
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(labelText2);
        label2.setTextFill(Color.WHITE); 
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Button button = new Button(buttonText);
        button.setAlignment(Pos.BOTTOM_RIGHT);

        button.setOnAction(event->{
            Stage stage = new Stage();
            stage.setScene(new Scene(showQuiz(), 400, 200));
            stage.setTitle("Quiz Description");
            stage.show();
        });
        

        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        BorderPane.setAlignment(label2, Pos.CENTER);
        BorderPane.setAlignment(button, Pos.BOTTOM_RIGHT);

        borderPane.setTop(label1);
        borderPane.setLeft(label2);
        borderPane.setBottom(button);

        return borderPane;
    }

    //show quiz description and finish attempt button
    public static BorderPane showQuiz(){
        BorderPane borderPane= new BorderPane();
        
        Label quizDescription = new Label("Fetch the description from db");
        Button finishAttemptBtn = new Button("Finish Attempt");
       
        borderPane.setTop(quizDescription);
        borderPane.setBottom(finishAttemptBtn);
        BorderPane.setAlignment(finishAttemptBtn,Pos.BOTTOM_RIGHT);

       BorderPane.setMargin(quizDescription, new Insets(10)); 
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
