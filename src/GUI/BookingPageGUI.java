package GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(10));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        titleLabel.setFont(new Font(23));
        titleLabel.setStyle("-fx-font-weight: bold;");
     
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
            dataBox.setPadding(new Insets(5));
           
            if (i % 2 == 0) {
                dataBox.setStyle("-fx-background-color:#ADEFD1;");
            } else {
                dataBox.setStyle("-fx-background-color: #ffffff;");
            }

            Label number = new Label(String.valueOf(i + 1)); 
            number.setPadding(new Insets(5,10,5,5));

            String destinationName = recommendSystem.get(i).getName();
            destinationStrg.add(destinationName);

            Label destinationsLabel = new Label(destinationName);
            destinationsLabel.setPadding(new Insets(5,10,5,5));

            destinationId.add(bookSys.findDestinationID(destinationName));  

            Label distanceLabel = new Label(distances.get(i).toString() + " km");
            distanceLabel.setPadding(new Insets(5,10, 5, 5));

            Button bookingBtn = new Button("Book");

            bookingBtn.setOnAction(event ->{  
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(AvailableTimeSlot(destinationId.get(ind), destinationStrg.get(ind), bookSys), 500, 400));
                stage.setTitle("Available Time Slot");
                stage.showAndWait();
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            dataBox.getChildren().addAll(number,  destinationsLabel, distanceLabel, spacer, bookingBtn); 
            
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
        mainvbox.setStyle("-fx-background-color: white;");
        mainvbox.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS);
      
        ArrayList<Integer> childrenId =  getChildren(Lantern.getConn(), User.getCurrentUser().getUsername());
        if(childrenId.isEmpty()){
            return showNoChild();
        }else{
            return mainvbox;
        }  
    }

    public static VBox showNoChild(){

        VBox vbox = new VBox();
        Label text = new Label("Add a child before you make a booking");
        vbox.getChildren().add(text);
        return vbox;
    }

    public static VBox AvailableTimeSlot(int destinationId, String destinationName, BookingSystem bookSys){
        
        BorderPane borderPane = new BorderPane();
        Label titleLabel = new Label("Available Time Slot");
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.TOP_LEFT);
        titleLabel.setPadding(new Insets(10));
        titleLabel.setFont(new Font(15));
        titleLabel.setStyle("-fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(391.0, 247.0);
        scrollPane.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        VBox vBox = new VBox();
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS); 
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 

        ArrayList<Integer> childrenId =  getChildren(Lantern.getConn(), User.getCurrentUser().getUsername());
        for(Integer childrenid : childrenId){
            System.out.print("Children id: " + childrenid +" ");
        }
        
        ArrayList<Integer> eventIds = geteventId(Lantern.getConn(), childrenId);
        ArrayList<String> finalDate = new ArrayList<>();

        // for(Integer eventid: eventIds){
        //     System.out.print("Event id" + eventid + " ");
        //
        if(eventIds.isEmpty()){
            ArrayList<Date> convertDateToString = bookSys.getAvailableDates(User.getCurrentUser(),destinationId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            for (Date date : convertDateToString) {
                String dateString = dateFormat.format(date);
                finalDate.add(dateString);
            }
        }else{
            ArrayList <Date> availableDate = bookSys.getAvailableDates(User.getCurrentUser(),destinationId);
            ArrayList <Date> registeredEventDate = getDateForEventRegistered(Lantern.getConn(), eventIds);
            finalDate = getFinalAvailableDates(availableDate, registeredEventDate) ;
            // for(String finaldate: finalDate){
            //     System.out.print("Final Date: " +finaldate + " ");
                
            // }
        }
        // for(Date ad : availableDate){
        //     System.out.print("Available date: "+ ad + " ");
        // }
        // for(Date re: registeredEventDate){
        //     System.out.print("Registered date: "+ re + " ");
        // }
        
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
                selectBtn.setOnAction(event ->{
                    Booking.bookingTour(Lantern.getConn(), bd,User.getCurrentUser().getUsername()); //make booking
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
    

    
    public static ArrayList<Integer> geteventId(Connection conn, ArrayList<Integer> childrenList) {
        ArrayList<Integer> eventIds = new ArrayList<>();
        String query = "SELECT event_id FROM EventRegistered WHERE main_id = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (Integer childId : childrenList) {
                statement.setInt(1, childId);
                ResultSet resultSet = statement.executeQuery();
                boolean found = false;
                while (resultSet.next()) {
                    int eventId = resultSet.getInt("event_id");
                    eventIds.add(eventId);
                    found = true;
                }
                if (!found) {
                    System.out.println("No events found for child with ID: " + childId);
                }
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
                System.out.println("Retrieved Date String: " + eventDateString);

                // Parse the date string into Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date eventDate = dateFormat.parse(eventDateString);
                    registeredEventDates.add(eventDate);
                } catch (ParseException e) {
                    // Handle parsing exception
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



