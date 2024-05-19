package GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Database.Booking;
import Database.BookingData;
import Booking.BookingSystem;
import Booking.Destination;
import Database.User;
public class BookingPageGUI {

    // @Override
    // public void start(Stage primaryStage) {
    //     Scene scene = new Scene(BookingTabPage(), 600, 400);
    //     primaryStage.setScene(scene);
    //     primaryStage.setTitle("Booking Page");
    //     primaryStage.show();
    // }

    public static VBox BookingTabPage(){
        BorderPane borderPane = new BorderPane();
        Label titleLabel = new Label("Booking Page");
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(10));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        titleLabel.setFont(new Font(23));
        titleLabel.setStyle("-fx-font-weight: bold;");
     
        //creating ScrollPane 
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(391.0, 247.0);

        //vbox contain lots of hbox
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS); 
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 

      
        //display all the destination list (based on user location ---> recommendation system)
        //BookingSystem....destinationName, disntance from the user
      //  User user = new User();
        BookingSystem bookSys = new BookingSystem();
        ArrayList <Destination> recommendSystem = bookSys.suggestDestinations(User.getCurrentUser().getCoordinate()) ; //return Arraylist with destination and distance
        System.out.print(User.getCurrentUser().getCoordinate());
        ArrayList <Double> distances = bookSys.distanceAway(User.getCurrentUser().getCoordinate()); 
        ArrayList <Integer> destinationId = new ArrayList <Integer>();

        // Loop to create the boxes
        for (int i = 0; i < recommendSystem.size(); i++) {
            final int ind = i;
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(5)); //padding for the databox
           
            if (i % 2 == 0) {
                dataBox.setStyle("-fx-background-color:#ADEFD1;");
            } else {
                dataBox.setStyle("-fx-background-color: #ffffff;");
            }

            Label number = new Label(String.valueOf(i + 1));  //fetch the value from the db
            number.setPadding(new Insets(5,10,5,5));
            String destinationName = recommendSystem.get(i).toString();
            Label destinations = new Label(destinationName);
            destinations.setPadding(new Insets(5,10,5,5));
            destinationId.add(bookSys.findDestinationID(destinationName));  
            Label distance = new Label(distances.get(i).toString() + " km");
            distance.setPadding(new Insets(5,10, 5, 5));
            Button bookingBtn = new Button("Book");
            //go recommendSystem get the string find the string in the destinationName to get the id

            bookingBtn.setOnAction(event ->{  
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(AvailableTimeSlot(destinationId.get(ind), recommendSystem,bookSys), 400, 300));
                stage.setTitle("Available Time Slot");
                stage.showAndWait();
            });
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            dataBox.getChildren().addAll(number,  destinations, distance, spacer, bookingBtn);  //adding every info into hbox for each line
            
            vBox.getChildren().add(dataBox); //continue add all the databox into vbox
        }

        scrollPane.setContent(vBox);
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
        VBox.setVgrow(borderPane, Priority.ALWAYS); //ensure borderPane grow together with Vbox

        return mainvbox;
    }



    //select part
    public static VBox AvailableTimeSlot(int destinationId, ArrayList<Destination> recommendSystem, BookingSystem bookSys){
        
        BorderPane borderPane = new BorderPane();
        Label titleLabel = new Label("Available Time Slot");
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.TOP_LEFT);
        titleLabel.setPadding(new Insets(10));
        titleLabel.setFont(new Font(20));
        titleLabel.setStyle("-fx-font-weight: bold;");

        //creating ScrollPane 
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(391.0, 247.0);
        scrollPane.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        //vbox contain lots of hbox
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS); 
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 

        //method to be used
        //getTimeSlot  && checkDate
        Date currentDate = new Date();

        // get the date from the event table by referring to eventRegistered
        //go the eventRegistered, select event_id based on main_id
        //based on event_id in eventRegistered, go to get the eventTitle (not in db based on the code)
        //based on the eventTitle, select the Date in event table in database

        //public ArrayList<String> getTimeSlots(destinationId.get(i), currentDate, User.getCurrentUser()) 
        //public static boolean checkDate(Lantern.getConn(),User.getCurrentUser().getUsername(),String date)

        // Loop to create the boxes  //destination.size???
        for (int i = 0; i < bookSys.getAvailableDates(User.getCurrentUser(),destinationId).size(); i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(bookSys.getAvailableDates(User.getCurrentUser(),destinationId).get(i)); //convert Date to String

            //get the date
            boolean check = Booking.checkDate(Lantern.getConn(),User.getCurrentUser().getUsername(),dateString);  //should be the date tht you registered for the event
            if(check){ //mean occupied by others event

            }else{ //if still available then show datebox with date 
                HBox dataBox = new HBox();
                dataBox.setPadding(new Insets(5)); //padding for the databox      
                dataBox.setStyle("-fx-background-color: #C9DFC9;-fx-border-color:white; -fx-border-width: 1px;");
                Label number2 = new Label(String.valueOf(i + 1));  //fetch the value from the db
                number2.setPadding(new Insets(5,10,5,5));
                Label availableTimeLabel = new Label(dateString); 
                availableTimeLabel.setPadding(new Insets(5,10,5,5));
                BookingData bd = new BookingData (recommendSystem.get(i).toString(), dateString);
                

                Button selectBtn = new Button("Select");  //saved
                selectBtn.setOnAction(event ->{
                    Booking.bookingTour(Lantern.getConn(), bd,User.getCurrentUser().getUsername());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Your booking has been confirmed.");
                    alert.showAndWait();

                    Stage stage = (Stage) selectBtn.getScene().getWindow();
                    stage.close();
                });

                Region spacer = new Region();
                HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
    
                dataBox.getChildren().addAll(number2, availableTimeLabel , spacer, selectBtn);  //adding every info into hbox for each line
                
                vBox.getChildren().add(dataBox); 
                
            }
        }
        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);

        VBox mainvbox = new VBox();
        mainvbox.setStyle("-fx-background-color:white;");
        mainvbox.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS); 

        return mainvbox;
    }

 }



