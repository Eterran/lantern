package GUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Database.Event;
import Database.EventData;
import Database.Login_Register;
import Database.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EducatorCheckEventCreated {
    
    private static ArrayList<EventData> eventCreated = Event.getEventOfUser(Lantern.getConn(), User.getCurrentUser().getUsername());
    private static VBox eventCheckBox = new VBox(); 
    
    public static void refreshUI() {
        eventCreated = Event.getEventOfUser(Lantern.getConn(), User.getCurrentUser().getUsername());
        eventCheckBox.getChildren().clear();
        VBox temp = new VBox();

        HBox row =  new HBox(); 
        VBox column1 = new VBox(); 
        column1.setPadding(new Insets(10));
        row.getChildren().addAll(column1);
        HBox.setHgrow(column1, Priority.ALWAYS);
        
        column1.setStyle("-fx-background-color: lightyellow");
        column1.setSpacing(20); // Set spacing between items
        column1.getChildren().add(AddBorderPane());

        for(EventData data: eventCreated){
            String labelText1 = data.getEventTitle();
            String labelText2 = data.getDescription();
            String labelText3 = data.getVenue();
            String labelText4 = data.getDate();
            String labelText5 = data.getTime();
            BorderPane borderPane = BPForAllEvents(labelText1, labelText2, labelText3, labelText4, labelText5);

            column1.getChildren().addAll(borderPane);
        }
        ScrollPane scrollPane1 = new ScrollPane(row);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //Never show horizontal scrollbar
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Alaways show vertical scrollbar as needed
      
        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        BorderPane borderPane2 = new BorderPane();
        borderPane2.setCenter(scrollPane1);

        temp.getChildren().add(borderPane2);
        eventCheckBox.getChildren().add(temp);
    }


    public static VBox vboxput() {
        //title
        VBox vbox1 = new VBox();
        vbox1.setStyle("-fx-background-color: lightyellow");
        Label label1 = new Label("Event Created");
        label1.getStyleClass().add("title");
        // label1.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        label1.setPadding(new Insets(10));
        vbox1.getChildren().add(label1);

        refreshUI();
        VBox mainvbox = new VBox();
        mainvbox.getChildren().addAll(vbox1, eventCheckBox);
        VBox.setVgrow(mainvbox, Priority.ALWAYS);
        return mainvbox;
    }


    public static BorderPane BPForAllEvents(String labelText1, String labelText2, String labelText3, String labelText4, String labelText5) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:#C7F1FA; -fx-border-color:#475558; -fx-border-width: 2px; -fx-border-radius: 10px;");
        borderPane.setPadding(new Insets(15));

        Label label1 = new Label(labelText1);
        // label1.setTextFill(Color.WHITE);
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(labelText2);
        // label2.setTextFill(Color.WHITE);
        Label label3 = new Label(labelText3);
        // label3.setTextFill(Color.WHITE);
        Label label4 = new Label(labelText4);
        // label4.setTextFill(Color.WHITE);
        Label label5 = new Label(labelText5);
        // label5.setTextFill(Color.WHITE);
        MenuButton menubutton = new MenuButton();
        menubutton.setText("Settings");
        menubutton.setStyle("-fx-background-color:white");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        menubutton.getItems().addAll(editItem, deleteItem);
        menubutton.setAlignment(Pos.TOP_RIGHT);

        editItem.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(showEditPage(labelText1,labelText2,labelText3,labelText4,labelText5), 400, 300));
            stage.setTitle("Edit Event");
            stage.showAndWait();      
  
        });

        deleteItem.setOnAction(event ->{
            Event.deleteEvent(Lantern.getConn(),labelText4);  
            if (borderPane.getParent() instanceof VBox) {
                VBox parentVBox = (VBox) borderPane.getParent();
                parentVBox.getChildren().remove(borderPane);
            } 
        });
        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        BorderPane.setAlignment(label2, Pos.TOP_LEFT);
        BorderPane.setAlignment(label3, Pos.CENTER_LEFT);
        BorderPane.setAlignment(label4, Pos.CENTER_LEFT);
        BorderPane.setAlignment(label5, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(menubutton, Pos.TOP_RIGHT);

        Region spacer = new Region();
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox topBox = new HBox(label1, spacer1, menubutton);
        topBox.setPrefHeight(40);
        VBox middleBox = new VBox(label2, label3, label4);
        HBox bottomBox = new HBox(label5, spacer);

        borderPane.setTop(topBox);
        borderPane.setCenter(middleBox);
        borderPane.setBottom(bottomBox);

        return borderPane;
    }

    public static BorderPane AddBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:#475558");
        borderPane.setPadding(new Insets(15));
       
        Button addButton = new Button("Add Event");    
        addButton.setStyle("-fx-background-color:white");
        borderPane.setCenter(addButton);

        addButton.setOnAction(event -> {
            VBox content = EducatorCreateEvent.tabCreateEvent();
            Stage stage = new Stage();
            stage.setScene(new Scene(content, 500, 300));
            stage.setTitle("Create Quiz");
            stage.show();
        });

        BorderPane.setAlignment(addButton, Pos.CENTER);

        return borderPane;
    }

    public static VBox showEditPage(String title, String description, String venue, String date, String time) {
        Label Etitle = new Label("Edit Event");
        Etitle.setPadding(new Insets(10, 0, 0, 10));
        Etitle.setTextFill(Color.BLACK);
        Etitle.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        Label eventTitle = new Label("Event Title: ");
        Label eventDescription = new Label("Event Description: ");
        Label eventVenue = new Label("Event Venue:");
        Label eventDate = new Label("Event Date:");
        Label eventTime = new Label("Event Time:");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(eventTitle, eventDescription, eventVenue, eventDate, eventTime);
        vbox.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        //fetch all the details from db
        TextField eventTitleTF = new TextField(title);
        TextArea eventDescriptionTA = new TextArea(description);
        TextField eventVenueTF = new TextField(venue);
        TextField eventDateTF = new TextField(date);
        ComboBox<String> eventTimeComboBox = new ComboBox<>();
        eventTimeComboBox.getItems().addAll(
            "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00",
            "16:00", "17:00", "18:00", "19:00", "20:00"
        );
        eventTimeComboBox.setValue(time);
        eventTimeComboBox.setEditable(true);
        eventTitleTF.setPromptText("Enter title");
        eventDescriptionTA.setPromptText("Enter description");
        eventVenueTF.setPromptText("Enter venue");
        eventDateTF.setPromptText("yyyy-MM-dd");
        eventTimeComboBox.setPromptText("Select time");

        Button saveBtn = new Button("SAVE");
        Button cancelBtn = new Button("CANCEL"); //back button fucntion
        saveBtn.setStyle("-fx-background-color: #475558; -fx-text-fill: white");
        cancelBtn.setStyle("-fx-background-color:#475558; -fx-text-fill: white");

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
        mainvBox.getChildren().addAll(Etitle, gridPane);
        mainvBox.setStyle("-fx-background-color: #CFE1E5; -fx-font-weight: bold;");
        VBox.setVgrow(mainvBox, Priority.ALWAYS);

        saveBtn.setOnAction(e -> {
            
            String ETitle = eventTitleTF.getText();
            String Edescription = eventDescriptionTA.getText();
            String Evenue = eventVenueTF.getText();
            String Edate = eventDateTF.getText();
            String Etime = eventTimeComboBox.getValue();

            if (!isValidDateFormat(Edate)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid date format. Please enter date in yyyy-MM-dd format.");
                alert.showAndWait();
            } else {
        
                EventData newevents = new EventData(ETitle, Edescription, Evenue, Edate, Etime);
                EventData oldevents = new EventData(title, description, venue, date, time);
                boolean updateEvent = updateEvent(Lantern.getConn(), oldevents, newevents, User.getCurrentUser().getUsername());

                if (updateEvent) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Event updated successfully!");
                    alert.show();
                    refreshUI();
                    Stage stage = (Stage) saveBtn.getScene().getWindow();
                    stage.close();
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


    // In your Event class or wherever you handle database operations
    public static boolean updateEvent(Connection conn, EventData oldevent, EventData newevent, String username) {
         Login_Register lg=new Login_Register();
         int id =lg.getID(username, conn);
        try {
            String query = "UPDATE event SET EventTitle = ?, Description = ?, Venue = ?, Date = ?, Time = ? WHERE main_id = ? AND EventTitle = ? AND Description = ? AND Venue = ? AND Date = ? AND Time = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newevent.getEventTitle());
            statement.setString(2, newevent.getDescription());
            statement.setString(3, newevent.getVenue());
            statement.setString(4, newevent.getDate());
            statement.setString(5, newevent.getTime());
            statement.setInt(6,id);

            statement.setString(7, oldevent.getEventTitle());
            statement.setString(8, oldevent.getDescription());
            statement.setString(9, oldevent.getVenue());
            statement.setString(10, oldevent.getDate());
            statement.setString(11, oldevent.getTime());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // If rowsUpdated > 0, return true indicating success
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false indicating failure
        }
    }

    public static boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}


