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
        System.out.println("Refresh UI is being executed");
        System.out.println("booking made " + bookingMade.size());
        bookingCheckBox.getChildren().clear();
        VBox temp1 = new VBox();

        VBox column = new VBox(); 
        column.setPadding(new Insets(10));
        HBox.setHgrow(column, Priority.ALWAYS);
        column.setStyle("-fx-background-color: lightyellow");
        column.setSpacing(20); // Set spacing between items

        for(BookingData data: bookingMade){
            String labelText1 = data.getDestination();
            System.out.println("Data fetch" +labelText1);
            String labelText2 = data.getDate();
            System.out.println(labelText2);
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

        temp1.getChildren().add(scrollPane1);
        bookingCheckBox.getChildren().add(temp1);
        System.out.println("Display updated UI");
    }


    public static VBox vboxput() {
        //title
        VBox vbox1 = new VBox();
        vbox1.setStyle("-fx-background-color: lightyellow");
        Label label1 = new Label("BOOKINGS MADE");
        label1.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        label1.setPadding(new Insets(10));
        vbox1.getChildren().add(label1);

        refreshbookingUI() ;
        VBox mainvbox = new VBox();
        mainvbox.getChildren().addAll(vbox1, bookingCheckBox);
        VBox.setVgrow(mainvbox, Priority.ALWAYS);
        return mainvbox;
    }


    public static BorderPane BPForAllBooking(String labelText1, String labelText2) {
        System.out.println("Label " + labelText1);
        System.out.println("Date "+ labelText2);
        BorderPane borderPane3 = new BorderPane();
        borderPane3.setStyle("-fx-background-color: #226c94");
        borderPane3.setPadding(new Insets(15));

        Label label1 = new Label(labelText1);
        label1.setTextFill(Color.WHITE);
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(labelText2);
        label2.setTextFill(Color.WHITE);
        MenuButton menubutton = new MenuButton();
        menubutton.setText("Settings");
        MenuItem deleteItem = new MenuItem("Delete");
        menubutton.getItems().add(deleteItem);
        menubutton.setAlignment(Pos.TOP_RIGHT);

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



    public static void deleteBooking (Connection conn,String username, String date){
        Login_Register lg = new Login_Register();
        int id = lg.getID(username, conn);
        try{
            String query = "DELETE FROM BookingDate WHERE bookingDate= ? AND main_id =?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, date);
            statement.setInt(2, id);
            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        Booking.decreaseCount(conn, Booking.getCount(conn, username), username);

    }

}


