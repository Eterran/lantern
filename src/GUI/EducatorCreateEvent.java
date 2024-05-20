package GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.sql.Connection;
import Database.Event;
import Database.Database;
import Database.EventData;
import Database.User;

public class EducatorCreateEvent  {

    public static VBox tabCreateEvent() {
        Label title = new Label("Create Event");
        title.setPadding(new Insets(10, 0, 0, 10));
        title.setTextFill(Color.BLACK);
        title.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        Label eventTitle = new Label("Event Title: ");
        Label eventDescription = new Label("Event Description: ");
        Label eventVenue = new Label("Event Venue:");
        Label eventDate = new Label("Event Date:");
        Label eventTime = new Label("Event Time:");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(eventTitle, eventDescription, eventVenue, eventDate, eventTime);
        vbox.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        TextField eventTitleTF = new TextField();
        TextArea eventDescriptionTA = new TextArea();
        TextField eventVenueTF = new TextField();
        TextField eventDateTF = new TextField();
        ComboBox<String> eventTimeComboBox = new ComboBox<>();
        eventTimeComboBox.getItems().addAll(
            "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00",
            "16:00", "17:00", "18:00", "19:00", "20:00"
        );
        eventTimeComboBox.setEditable(true);
        eventTitleTF.setPromptText("Enter title");
        eventDescriptionTA.setPromptText("Enter description");
        eventVenueTF.setPromptText("Enter venue");
        eventDateTF.setPromptText("yyyy-MM-dd");
        eventTimeComboBox.setPromptText("Select time");

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");
        saveBtn.setStyle("-fx-background-color: #9068be; -fx-text-fill: white");
        cancelBtn.setStyle("-fx-background-color:#9068be; -fx-text-fill: white");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(6);
        gridPane.setHgap(6);

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
        gridPane.add(eventTitle, 0, 0);
        gridPane.add(eventTitleTF, 1, 0);
        gridPane.add(eventDescription, 0, 1);
        gridPane.add(eventDescriptionTA, 1, 1);
        gridPane.add(eventVenue, 0, 2);
        gridPane.add(eventVenueTF, 1, 2);
        gridPane.add(eventDate, 0, 3);
        gridPane.add(eventDateTF, 1, 3);
        gridPane.add(eventTime, 0, 4);
        gridPane.add(eventTimeComboBox, 1, 4);
        gridPane.add(saveBtn, 0, 5);
        gridPane.add(cancelBtn, 0, 6);

        VBox mainvBox = new VBox();
        mainvBox.getChildren().addAll(title, gridPane);
        mainvBox.setStyle("-fx-background-color: #e1e8f0; -fx-font-weight: bold;");
        VBox.setVgrow(mainvBox, Priority.ALWAYS);

        User user = User.getCurrentUser();
        saveBtn.setOnAction(e -> {
            if (eventTitleTF.getText().isEmpty() || eventDescriptionTA.getText().isEmpty() ||
            eventVenueTF.getText().isEmpty()|| eventDateTF.getText().isEmpty() || eventTimeComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields before saving.", ButtonType.OK);
                alert.showAndWait();
            } else if (!EducatorCheckEventCreated.isValidDateFormat(eventDateTF.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid date format. Please enter date in yyyy-MM-dd format.");
                alert.showAndWait();
            } else {
                String ETitle = eventTitleTF.getText();
                String Edescription = eventDescriptionTA.getText();
                String Evenue = eventVenueTF.getText();
                String Edate = eventDateTF.getText();
                String Etime = eventTimeComboBox.getValue();
            
                EventData events = new EventData(ETitle, Edescription, Evenue, Edate, Etime);
            
                boolean savedSuccessfully = Event.createEvent(Lantern.getConn(), events, user.getUsername());
                if (savedSuccessfully) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Event saved successfully!");
                    alert.showAndWait();
                    eventTitleTF.clear();
                    eventDescriptionTA.clear();
                    eventVenueTF.clear();
                    eventDateTF.clear();
                    eventTimeComboBox.getSelectionModel().clearSelection();
                    //EducatorCheckEventCreated.vboxput();
                    //how to straight away update in the ui 
                    EducatorCheckEventCreated.refreshUI();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to save event.");
                    alert.showAndWait();
                }
            }
        });
        
        return mainvBox;
    }
}
