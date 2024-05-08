package GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.geometry.Insets;
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
      //  scrollPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        //vbox contain lots of hbox
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS); 
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 
        // headerBox.setStyle("-fx-background-color:#730099;");
             
        // Label rankLabel = new Label("No.");
        // rankLabel.setPadding(new Insets(5));
        // rankLabel.setStyle("-fx-font-weight: bold; -fx-font-size:14px; -fx-font-family: Arial;-fx-text-fill: white");
        // Label usernameLabel = new Label("Destination");
        // usernameLabel.setStyle("-fx-font-weight:bold;-fx-font-size: 14px; -fx-font-family: Arial;-fx-text-fill: white");
        // usernameLabel.setPadding(new Insets(5));
        // Label pointLabel = new Label("");
        // pointLabel.setStyle("-fx-font-weight:bold; -fx-font-size: 14px; -fx-font-family: Arial;-fx-text-fill: white");
        // pointLabel.setPadding(new Insets(5,10,5,5));

        // Region spacer1 = new Region();
        // HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS);
        // Region spacer2 = new Region();
        // HBox.setHgrow(spacer2, javafx.scene.layout.Priority.ALWAYS);
        // headerBox.setPadding(new Insets(5));
        // headerBox.getChildren().addAll(rankLabel,spacer1, usernameLabel, spacer2, pointLabel);

        // vBox.getChildren().add(headerBox);

        int numberOfBoxes = 20;

        // Loop to create the boxes
        for (int i = 0; i < numberOfBoxes; i++) {
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(5)); //padding for the databox
           
            if (i % 2 == 0) {
                dataBox.setStyle("-fx-background-color:#ADEFD1;");
            } else {
                dataBox.setStyle("-fx-background-color: #ffffff;");
            }

            Label number = new Label(String.valueOf(i + 1));  //fetch the value from the db
            number.setPadding(new Insets(5,10,5,5));
            Label destinationData = new Label("Destination " + (i + 1));
            destinationData.setPadding(new Insets(5,10,5,5));
            Button bookingBtn = new Button("Book");
            bookingBtn.setOnAction(event ->{
                Stage stage = new Stage();
                stage.setScene(new Scene(AvailableTimeSlot(), 400, 300));
                stage.setTitle("Available Time Slot");
                stage.show();
            });
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            dataBox.getChildren().addAll(number,  destinationData, spacer, bookingBtn);  //adding every info into hbox for each line
            
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
 

    public static VBox AvailableTimeSlot(){
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

        int numberOfBoxes = 3;

        // Loop to create the boxes
        for (int i = 0; i < numberOfBoxes; i++) {
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(5)); //padding for the databox
           
            // if (i % 2 == 0) {
            //    dataBox.setStyle("-fx-background-color:#909C86;");
                
            // } else {
                dataBox.setStyle("-fx-background-color: #C9DFC9;-fx-border-color:white; -fx-border-width: 1px;");
            
            // }

            Label number2 = new Label(String.valueOf(i + 1));  //fetch the value from the db
            number2.setPadding(new Insets(5,10,5,5));
            Label availableTimeLabel = new Label("Time " + (i + 1));
            availableTimeLabel.setPadding(new Insets(5,10,5,5));
            Button selectBtn = new Button("Select");
            selectBtn.setOnAction(event ->{
               // 
            });
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            dataBox.getChildren().addAll(number2, availableTimeLabel , spacer, selectBtn);  //adding every info into hbox for each line
            
            vBox.getChildren().add(dataBox); //continue add all the databox into vbox
        }

        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);


        VBox mainvbox = new VBox();
        mainvbox.setStyle("-fx-background-color:white;");
        mainvbox.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS); //ensure borderPane grow together with Vbox

        return mainvbox;
    }


//     public static void main(String[] args) {
//         launch(args);
//     }
 }



