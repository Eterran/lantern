package GUI;

import java.sql.Connection;
import java.util.ArrayList;

import Database.Event;
import Database.EventData;
import Database.Quiz;
import Database.QuizData;
import Database.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class listOutEventCreated{

    // @Override
    // public void start(Stage primaryStage) {

    //     // Set up the scene
    //     Scene scene = new Scene(educatorEditList(), 600, 400);
    //     primaryStage.setScene(scene);
    //     primaryStage.setTitle("Product Table");
    //     primaryStage.show();

    // }

    public static VBox educatorEditEventList(){
        BorderPane bp  = new BorderPane();
        Label title = new Label("Edit Event");
        title.setPadding(new Insets(10, 0, 10, 0));
        title.setStyle("-fx-font-size: 20px");
        VBox topvbox = new VBox();
        topvbox.setAlignment(javafx.geometry.Pos.CENTER);
        topvbox.setPadding(new Insets(0, 0, 10, 0));

         TableView<EditEvent> tableView = new TableView<EditEvent>();

         TableColumn<EditEvent, String> titleCol = new TableColumn<EditEvent, String>("Event Title");
          titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
          titleCol.setPrefWidth(200);
          titleCol.setResizable(false); 
          titleCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
  
          TableColumn<EditEvent, String> descriptionCol = new TableColumn<EditEvent, String>("Description");
          descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
          descriptionCol.setPrefWidth(200);
          descriptionCol.setResizable(false); 
          descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
  
          TableColumn<EditEvent, String> venueCol = new TableColumn<EditEvent, String>("Venue");
          venueCol.setCellValueFactory(cellData -> cellData.getValue().venueProperty());
          venueCol.setPrefWidth(100);
          venueCol.setResizable(false); 
          venueCol.setCellFactory(TextFieldTableCell.forTableColumn()); 

          TableColumn<EditEvent, String> dateCol = new TableColumn<EditEvent, String>("Date");
          dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
          dateCol.setPrefWidth(100);
          dateCol.setResizable(false);
          dateCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
  
          TableColumn<EditEvent, String> timeCol = new TableColumn<EditEvent, String>("Time");
          timeCol.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
          timeCol.setPrefWidth(100);
          timeCol.setResizable(false);
          timeCol.setCellFactory(TextFieldTableCell.forTableColumn()); 

          Button updateButton = new Button("Update");
        
          topvbox.getChildren().addAll(title, updateButton);
          tableView.getColumns().addAll(titleCol, descriptionCol,venueCol, dateCol, timeCol);
         tableView.setColumnResizePolicy(TableView. UNCONSTRAINED_RESIZE_POLICY);


        ArrayList<EventData> eventList = Event.getEventOfUser(Lantern.getConn(), User.getCurrentUser().getUsername());
        ObservableList<EditEvent> data = FXCollections.observableArrayList();
        for (EventData event : eventList) {
            EditEvent editEvent = new EditEvent(
                    event.getEventTitle(),
                    event.getDescription(),
                    event.getVenue(),
                    event.getDate(),
                    event.getTime()
            );
            data.add(editEvent);
        };
    
    //     public static void updateLatestQuiz(Connection connection, QuizData quiz) {

    //    updateButton.setOnAction(event ->{
    //         Quiz.updateLatestQuiz(Lanter.getConn(), )
    //    });
          tableView.setItems(data);
          tableView.setEditable(true);
  
         bp.setTop(topvbox);
         bp.setCenter(tableView);
         VBox mainvbox = new VBox();
         mainvbox.getChildren().add(bp);
         VBox.setVgrow(bp, Priority.ALWAYS);
         return mainvbox;
    }



    // public static void main(String[] args) {
    //     launch(args);
    // }
}

class EditEvent {
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty venue;
    private final StringProperty date;
    private final StringProperty time;

    public EditEvent(String title, String description, String venue, String date, String time) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.venue = new SimpleStringProperty(venue);
        this.date= new SimpleStringProperty(date);
        this.time= new SimpleStringProperty(time);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getVenue() {
        return venue.get();
    }

    public StringProperty venueProperty() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue.set(venue);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }



}
