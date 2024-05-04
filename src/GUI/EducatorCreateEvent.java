package GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EducatorCreateEvent {

    // public static void main(String[] args) {
    //     launch(args);
    // }

    // @Override
    // public void start(Stage primaryStage) throws Exception {
        
    //     Scene scene = new Scene(tabCreateEvent(), 650, 400);
    //     primaryStage.setResizable(false);
    //     primaryStage.setTitle("Create Event");
    //     primaryStage.setScene(scene);
    //     primaryStage.show();
    
    // }
    
    public static VBox tabCreateEvent(){
        Label title = new Label("Create Event") ;
        title.setPadding(new Insets(10, 0, 0, 10));
        title.setTextFill(Color.BLACK);
        title.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        Label eventTitle = new Label("Event Title: ") ;
        Label eventDescription = new Label("Event Description: ") ;
        Label eventVenue = new Label("Event Venue:");
        Label eventDate = new Label("Event Date:");
        Label eventTime = new Label("Event Time:");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(eventTitle, eventDescription, eventVenue, eventDate, eventTime);
        vbox.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        

        TextField eventTitleTF= new TextField();
        TextArea eventDescriptionTA = new TextArea();
        TextField eventVenueTF= new TextField();
        TextField eventDateTF = new TextField();
        TextField eventTimeTF = new TextField();

        eventTitleTF.setPromptText("Enter text");
        eventDescriptionTA.setPromptText("Enter description");
        eventVenueTF.setPromptText("Enter venue");
        eventDateTF.setPromptText("xx/xx/xxxx");
        eventTimeTF.setPromptText("Enter time");

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");
        saveBtn.setStyle("-fx-background-color: #9068be; -fx-text-fill: white");
        cancelBtn.setStyle("-fx-background-color:#9068be; -fx-text-fill: white");

        // cancelBtn.setOnAction(e -> {
        
        // }
        //Creating gridpane
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400,200);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(6);
        gridPane.setHgap(6);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(eventTitle,0,0);
        gridPane.add(eventTitleTF,1,0);
        gridPane.add(eventDescription,0,1);
        gridPane.add(eventDescriptionTA,1,1);
        gridPane.add(eventVenue,0,2);
        gridPane.add(eventVenueTF,1,2);
        gridPane.add(eventDate,0,3);
        gridPane.add(eventDateTF,1,3);
        gridPane.add(eventTime,0,4);
        gridPane.add(eventTimeTF,1,4);
        gridPane.add(saveBtn,0,5);
        gridPane.add(cancelBtn, 0,6);


        
        VBox mainvBox = new VBox();
        mainvBox.getChildren().addAll(title,gridPane);
        mainvBox.setStyle("-fx-background-color: #e1e8f0; -fx-font-weight: bold;");
        

        return mainvBox;
    }

   
}