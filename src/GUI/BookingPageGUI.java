package GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Database.Booking;
import Database.BookingData;
import Booking.BookingSystem;
import Booking.Destination;
import Database.User;
public class BookingPageGUI {

    public static VBox BookingTabPage(){
        BorderPane borderPane = new BorderPane();
        Label titleLabel = new Label("Booking Page");
        titleLabel.getStyleClass().add("event_title");
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(10));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(391.0, 247.0);

        //vbox contain lots of hbox
        VBox bigvBox = new VBox();
        VBox.setVgrow(bigvBox, javafx.scene.layout.Priority.ALWAYS); 
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 

        BookingSystem bookSys = new BookingSystem();
        ArrayList <Destination> recommendSystem = bookSys.suggestDestinations(User.getCurrentUser().getCoordinate()) ; 
        ArrayList <String> destinationStrg = new ArrayList<>();
        ArrayList <Double> distances = bookSys.distanceAway(User.getCurrentUser().getCoordinate()); 
        ArrayList <Integer> destinationId = new ArrayList <Integer>();

        for (int i = 0; i < recommendSystem.size(); i++) {
            final int ind = i;
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(10));
            dataBox.setStyle("-fx-background-color: #b5def7; -fx-border-color: white; -fx-border-width: 0 0 2 0;");

            Label number = new Label(String.valueOf(i + 1)); 
            number.getStyleClass().add("destination_label");
            number.setPadding(new Insets(15));

            String destinationName = recommendSystem.get(i).getName();
            destinationStrg.add(destinationName);

            VBox storeDesAndDis = new VBox();
            storeDesAndDis.setPadding(new Insets(10));

            Label destinationsLabel = new Label(destinationName);
            destinationsLabel.getStyleClass().add("destination_label");
            destinationsLabel.setPadding(new Insets(5,10,5,5));

            destinationId.add(bookSys.findDestinationID(destinationName));  

            double distance = distances.get(i); 
            String Distance= String.format("%.4f km", distance);
            Label distanceLabel = new Label(Distance);
            distanceLabel.getStyleClass().add("distance_label");
            distanceLabel.setPadding(new Insets(5,10, 5, 5));

            storeDesAndDis.getChildren().addAll(destinationsLabel,distanceLabel);

            Button bookingBtn = new Button("Book");
            bookingBtn.getStyleClass().add("bookingButton");
            bookingBtn.setOnAction(event ->{ 
                //which children are you booking for ?
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(chooseChildrenBox(destinationId.get(ind), destinationStrg.get(ind), bookSys), 500, 200));
                stage.setTitle("Available Time Slot");
                stage.showAndWait();
                    
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            dataBox.getChildren().addAll(number, storeDesAndDis, spacer, bookingBtn); 
            
            bigvBox.getChildren().add(dataBox); //continue add all the databox into vbox
        }

        scrollPane.setContent(bigvBox);
        borderPane.setCenter(scrollPane);

        Pane leftPane = new Pane();
        leftPane.setPrefSize(20.0, 250.0);
        Pane rightPane = new Pane();
        rightPane.setPrefSize(20.0, 250.0);
        Pane bottomPane = new Pane();
        bottomPane.setPrefSize(400, 20);
        borderPane.setLeft(leftPane);
        borderPane.setRight(rightPane);
        borderPane.setBottom(bottomPane);

        VBox mainvbox = new VBox();
        mainvbox.setStyle("-fx-background-color: lightyellow;");
        mainvbox.getChildren().add(borderPane);

        mainvbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(borderPane, Priority.ALWAYS);
        VBox.setVgrow(mainvbox, Priority.ALWAYS);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
      
        ArrayList<Integer> childrenId =  getChildren(Lantern.getConn(), User.getCurrentUser().getUsername());
        if(childrenId.isEmpty()){
            return showNoChild();
        }else{
            return mainvbox;
        }  
    }
    public static VBox showNoChild(){      
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: grey; -fx-padding: 20px; -fx-background-radius: 10;");

        Label text = new Label("Add a child before you make a booking");
        text.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("okbutton");
        okButton.setOnAction(e -> (vbox.getParent()).setVisible(false));

        vbox.getChildren().addAll(text, okButton);
        
        return vbox;
    }


    public static VBox chooseChildrenBox(int destinationId, String destinationName, BookingSystem bookSys){
        VBox vbox = new VBox();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(20));
        Label label1 = new Label("Which children are you booking for? \nChild Id:");
        label1.setStyle("-fx-font-size:20px;");
        ArrayList<Integer> childrenId = getChildren(Lantern.getConn(), User.getCurrentUser().getUsername());

        ToggleGroup toggleGroup = new ToggleGroup();
        HBox storeradioButton = new HBox();
        storeradioButton.setPadding(new Insets(10));
        for (Integer childId : childrenId) {
            RadioButton radioButton = new RadioButton(String.valueOf(childId));
            radioButton.setToggleGroup(toggleGroup);
            storeradioButton.getChildren().add(radioButton);
            
        }

        String[] selectedChildId = {null};

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                selectedChildId[0] = selectedRadioButton.getText(); 
            }
        });
       
        Button saveBtn = new Button("Save");

        saveBtn.setOnAction(event -> {
            if (selectedChildId[0] != null) {
                try {
                    Integer childId = Integer.parseInt(selectedChildId[0]);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(AvailableTimeSlot(destinationId, destinationName, bookSys, childId), 300, 300));
                    stage.setTitle("Available Time Slot");
                    stage.showAndWait();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please choose any one of the children before.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        bp.setTop(label1);
        bp.setBottom(saveBtn);
        bp.setCenter(storeradioButton);
        BorderPane.setAlignment(saveBtn,Pos.BOTTOM_RIGHT);

        bp.prefHeightProperty().bind(vbox.heightProperty());
        bp.prefWidthProperty().bind(vbox.widthProperty());

        vbox.getChildren().add(bp);
        vbox.setStyle("-fx-background-color: lightpink");
        vbox.setFillWidth(true);
        return vbox;
    }


    public static VBox AvailableTimeSlot(int destinationId, String destinationName, BookingSystem bookSys, Integer childId){
        
        BorderPane borderPane = new BorderPane();
        Label titleLabel = new Label("Available Time Slot");
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.TOP_LEFT);
        titleLabel.setPadding(new Insets(10));
        titleLabel.setFont(new Font(15));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-family:'sans-serif';");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(391.0, 247.0);
        scrollPane.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        VBox vBox = new VBox();
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS); 
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 

        //ArrayList<Integer> childrenId =  getChildren(Lantern.getConn(), User.getCurrentUser().getUsername());
        ArrayList<Integer> eventIds = geteventId(Lantern.getConn(), childId); //get the eventIds for the child

        ArrayList<String> finalDate = new ArrayList<>();

        if(eventIds.isEmpty()){
            ArrayList<Date> convertDateToString = bookSys.getAvailableDates(User.getCurrentUser(),destinationId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            for (Date date : convertDateToString) {
                String dateString = dateFormat.format(date);
                finalDate.add(dateString);
            }
        }else{
            ArrayList <Date> availableDate = bookSys.getAvailableDates(User.getCurrentUser(),destinationId); //all 7 days excluding booking made
            ArrayList <Date> registeredEventDate = getDateForEventRegistered(Lantern.getConn(), eventIds); //child event date
            finalDate = getFinalAvailableDates(availableDate, registeredEventDate) ;
        }
        
        for (int i = 0; i <finalDate.size() ; i++) {
            
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(5));      
            dataBox.setStyle("-fx-background-color: #C9DFC9;-fx-border-color:white; -fx-border-width: 1px;");
            Label num = new Label(String.valueOf(i + 1)); 
            num.setPadding(new Insets(5,10,5,5));
            Label availableTimeLabel = new Label(finalDate.get(i)); 
            availableTimeLabel.setPadding(new Insets(5,10,5,5));
            BookingData bd = new BookingData (destinationName, finalDate.get(i));

            Button selectBtn = new Button("Select"); 
           // selectBtn.getStyleClass().add("selectButton");
            selectBtn.setStyle("-fx-font-size: 12px; -fx-border-radius: 3px; -fx-background-radius: 5px;-fx-border-width: 0;   -fx-background-color: #ffffff;");
                selectBtn.setOnAction(event ->{
                    Booking.bookingTour(Lantern.getConn(), bd,User.getCurrentUser().getUsername(), childId); //make booking
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Your booking has been confirmed.");
                    alert.showAndWait();

                    Stage stage = (Stage) selectBtn.getScene().getWindow();
                    stage.close();
                });

            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            dataBox.getChildren().addAll(num, availableTimeLabel , spacer, selectBtn);  
            vBox.getChildren().add(dataBox);               
        }
        
        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);
        borderPane.setStyle("-fx-background-color:white;");
        VBox mainvbox = new VBox();
        mainvbox.setStyle("-fx-background-color:white;");
        mainvbox.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS); 

        return mainvbox;
    }

    public static ArrayList<Integer> getChildren(Connection conn, String username) {
        ArrayList<Integer> childrenList = new ArrayList<>();
        String query = "SELECT main_id FROM children WHERE parent = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int mainId = resultSet.getInt("main_id");
                childrenList.add(mainId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }     
        return childrenList;
    }
       
    // public static ArrayList<Integer> geteventId(Connection conn, Integer childId) {
    //     ArrayList<Integer> eventIds = new ArrayList<>();
    //     String queryEventRegistered = "SELECT event_id FROM EventRegistered WHERE main_id = ?";
    //     String queryEventExists = "SELECT 1 FROM event WHERE id = ?";
    
    //     try (PreparedStatement statementEventRegistered = conn.prepareStatement(queryEventRegistered);
    //          PreparedStatement statementEventExists = conn.prepareStatement(queryEventExists)) {
            
    //         for (Integer childId : childrenList) {
    //             statementEventRegistered.setInt(1, childId);
    //             ResultSet resultSetRegistered = statementEventRegistered.executeQuery();
    //             boolean found = false;
                
    //             while (resultSetRegistered.next()) {
    //                 int eventId = resultSetRegistered.getInt("event_id");                
    //                 statementEventExists.setInt(1, eventId);
    //                 ResultSet resultSetExists = statementEventExists.executeQuery();
                    
    //                 if (resultSetExists.next()) {  
    //                     eventIds.add(eventId);
    //                 }                
    //                 found = true;
    //             }
                
    //             if (!found) {
    //                 System.out.println("No events found for child with ID: " + childId);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    
    //     return eventIds;
    // }
    public static ArrayList<Integer> geteventId(Connection conn, Integer childId) {
        ArrayList<Integer> eventIds = new ArrayList<>();
        String queryEventRegistered = "SELECT event_id FROM EventRegistered WHERE main_id = ?";
        String queryEventExists = "SELECT 1 FROM event WHERE id = ?";
    
        try (PreparedStatement statementEventRegistered = conn.prepareStatement(queryEventRegistered);
             PreparedStatement statementEventExists = conn.prepareStatement(queryEventExists)) {
    
            statementEventRegistered.setInt(1, childId);
            ResultSet resultSetRegistered = statementEventRegistered.executeQuery();
            boolean found = false;
    
            while (resultSetRegistered.next()) {
                int eventId = resultSetRegistered.getInt("event_id");
                statementEventExists.setInt(1, eventId);
                ResultSet resultSetExists = statementEventExists.executeQuery();
    
                if (resultSetExists.next()) {
                    eventIds.add(eventId);
                }
                found = true;
            }
    
            if (!found) {
                System.out.println("No events found for child with ID: " + childId);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return eventIds;
    }
    
    

    public static ArrayList<Date> getDateForEventRegistered(Connection conn, ArrayList<Integer> eventIds) {
    ArrayList<Date> registeredEventDates = new ArrayList<>();
    String query = "SELECT Date FROM event WHERE id = ?";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
        for (Integer eventId : eventIds) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String eventDateString = resultSet.getString("Date");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date eventDate = dateFormat.parse(eventDateString);
                    registeredEventDates.add(eventDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }

    return registeredEventDates.isEmpty() ? null : registeredEventDates;
}



    public static ArrayList<String> getFinalAvailableDates(ArrayList<Date> availableTimeSlots, ArrayList<Date> registeredEventList) {
        ArrayList<String> finalAvailableDate = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> registeredEvent = new ArrayList<>();

        for (Date registered : registeredEventList) {
            String dateString = dateFormat.format(registered);
            if (!registeredEvent.contains(dateString)) {
                registeredEvent.add(dateString);
            }
        }
    
        for (Date available : availableTimeSlots) {
            String availableDateString = dateFormat.format(available);
            if (!registeredEvent.contains(availableDateString)) {
                finalAvailableDate.add(availableDateString);
            }
        }
    
        return finalAvailableDate.isEmpty() ? null : finalAvailableDate;
    }

 }



