package GUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import Database.BookingData;
import Database.Login_Register;
import Database.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Database.Booking;
public class viewBookingTab {
    private static ArrayList<BookingData> bookingMade = Booking.viewBooking(Lantern.getConn(),User.getCurrentUser().getUsername());
    private static VBox bookingCheckBox = new VBox(); 

    public static void refreshbookingUI() {
        
        bookingCheckBox.getChildren().clear();
        bookingMade = Booking.viewBooking(Lantern.getConn(),User.getCurrentUser().getUsername()); //does not get the latest info
        for(BookingData bd : bookingMade){
            System.out.println(bd.getDestination());
        }
        VBox column = new VBox(); 
        column.setPadding(new Insets(10));
        HBox.setHgrow(column, Priority.ALWAYS);
        column.setStyle("-fx-background-color: lightyellow");
        column.setSpacing(20); // Set spacing between items

        for(BookingData data: bookingMade){
            String labelText1 = data.getDestination();
            String labelText2 = data.getDate();
            BorderPane bp =BPForAllBooking(labelText1, labelText2);

            column.getChildren().addAll(bp);
        }
        
        ScrollPane scrollPane1 = new ScrollPane(column);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //Never show horizontal scrollbar
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Alaways show vertical scrollbar as needed
      
        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        BorderPane borderPane2 = new BorderPane();
        borderPane2.setCenter(scrollPane1);
        bookingCheckBox.getChildren().add(scrollPane1);
    }

    public static VBox vboxput() {
        //title
        VBox vbox1 = new VBox();
        vbox1.setStyle("-fx-background-color: lightyellow");
        Label label1 = new Label("BOOKING MADE");
        label1.getStyleClass().add("booking_title");
        //label1.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        label1.setPadding(new Insets(10));
        vbox1.getChildren().add(label1);

        refreshbookingUI() ;
        VBox mainvbox = new VBox();
        mainvbox.getChildren().addAll(vbox1, bookingCheckBox);
        VBox.setVgrow(mainvbox, Priority.ALWAYS);
        if(bookingMade.isEmpty()){
            return showNoBooking();
        }else{
            return mainvbox;
        }
        
    }

    public static VBox showNoBooking(){      
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: grey; -fx-padding: 20px; -fx-background-radius: 10;");

        Label text = new Label("Oops, no booking has been made.");
        text.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("okbutton");
        okButton.setOnAction(e -> (vbox.getParent()).setVisible(false));

        vbox.getChildren().addAll(text, okButton);
        
        return vbox;
    }
    public static BorderPane BPForAllBooking(String labelText1, String labelText2) {
        System.out.println("Label " + labelText1);
        System.out.println("Date "+ labelText2);
        BorderPane borderPane3 = new BorderPane();
        borderPane3.setStyle("-fx-background-color: #b5def7; fx-border-width: 1px; -fx-border-color: black; -fx-border-radius: 5px; ");

        borderPane3.setPadding(new Insets(15));

        Label label1 = new Label(labelText1);
        label1.setTextFill(Color.WHITE);
        label1.getStyleClass().add("destination_label");
       // label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(labelText2);
        label2.getStyleClass().add("distance_label");
     //   label2.setTextFill(Color.WHITE);
        MenuButton menubutton = new MenuButton();
        menubutton.setText("Settings");
        MenuItem deleteItem = new MenuItem("Delete");
        menubutton.getItems().add(deleteItem);
        menubutton.setAlignment(Pos.TOP_RIGHT);
        menubutton.getStyleClass().add("bookingButton2");
        deleteItem.setOnAction(event ->{
            deleteBooking (Lantern.getConn(),User.getCurrentUser().getUsername(), labelText2);
            if (borderPane3.getParent() instanceof VBox) {
                VBox parentVBox = (VBox) borderPane3.getParent();
                parentVBox.getChildren().remove(borderPane3);
            } 
        });

        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        BorderPane.setAlignment(menubutton, Pos.BOTTOM_LEFT);
        BorderPane.setAlignment(label2, Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        //HBox topBox = new HBox(label1, spacer, menubutton);
        HBox topBox = new HBox(label1, spacer, menubutton);
        topBox.setPrefHeight(40);
        VBox bottomBox = new VBox(menubutton);
        VBox middleBox = new VBox(label2);

        borderPane3.setTop(topBox);
        borderPane3.setCenter(middleBox);
        borderPane3.setBottom( bottomBox);

        return borderPane3;
    }



    public static void deleteBooking (Connection conn,String username, String date){ //success
        Login_Register lg = new Login_Register();
        int id = lg.getID(username, conn);
        try{
            String query = "DELETE FROM BookingDate WHERE bookingDate= ? AND main_id =?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, date);
            statement.setInt(2, id);
            // statement.executeUpdate();

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                Booking.decreaseCount(conn, Booking.getCount(conn, username), username); //checking
                System.out.println("Quiz deleted successfully.");
            } else {
                System.out.println("No quiz found matching the criteria.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

}


