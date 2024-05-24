package GUI;
import java.util.ArrayList;
import Database.Database;
import Database.Event;
import Database.EventData;
import Database.Login_Register;
import Database.RegisterEvent;
import Database.User;
import Student.GlobalLeaderBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EventPage {
    private static VBox displayPointsBox = new VBox();
    private static Double pointLabel = User.getCurrentUser().getPoints();//fetch from the user db

    public static void updatePointsVbox(){
        displayPointsBox.getChildren().clear();
        updatePoints();
        Label pointsLabel = new Label("Points: "+ pointLabel);
        pointsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        displayPointsBox.getChildren().add(pointsLabel);
    }

    public static void updatePoints(){
        pointLabel = User.getCurrentUser().getPoints();
    }


    public static VBox viewEventTab() {
        VBox vbox1 = new VBox();
        vbox1.setStyle("-fx-background-color:lightyellow");
        vbox1.setPrefSize(600, 80); 
        Label label1 = new Label("Live Event");
        label1.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        vbox1.setPadding(new Insets(10));

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS); 
        HBox tophbox= new HBox();
        updatePointsVbox();
        tophbox.getChildren().addAll(label1 ,spacer1, displayPointsBox);
        vbox1.getChildren().add(tophbox);

        HBox content1 = new HBox();
        content1.setStyle("-fx-background-color: lightblue");
        content1.setSpacing(20);

       
        ArrayList<EventData> liveEventList = Event.getLiveEvents(Lantern.getConn());
        for (int i = 0; i <liveEventList.size(); i++) {
            BorderPane borderPane = BPForAllEvents(liveEventList.get(i));
            content1.getChildren().addAll(borderPane);
        }

        ScrollPane scrollPane1 = new ScrollPane(content1);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); 
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        BorderPane bp = new BorderPane();
        VBox vbox2 = new VBox();
        vbox2.setStyle("-fx-background-color: lightblue");
        vbox2.setPrefSize(600, 200);
        vbox2.getChildren().add(scrollPane1);
        bp.setCenter(vbox2);
        Pane topPane = new Pane();
        topPane.setPrefSize(400,30);
        Pane leftPane = new Pane();
        leftPane.setPrefSize(20.0, 250.0);
        Pane rightPane = new Pane();
        rightPane.setPrefSize(20.0, 250.0);
        Pane bottomPane = new Pane();
        bottomPane.setPrefSize(400, 0);
        bp.setTop(topPane);
        bp.setLeft(leftPane);
        bp.setRight(rightPane);
        bp.setBottom(bottomPane);
        topPane.setStyle("-fx-background-color: lightblue;");
        leftPane.setStyle("-fx-background-color: lightblue;");
        rightPane.setStyle("-fx-background-color: lightblue;");
        bottomPane.setStyle("-fx-background-color: lightblue;");

        //Closest 3 upcoming events section

        //title for upcoming events
        VBox vbox3 = new VBox();
        vbox3.setStyle("-fx-background-color: lightyellow");
        vbox3.setPrefSize(600, 80);
        Label label3 = new Label("Closest 3 Upcoming Event");
        label3.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        label3.setPadding(new Insets(10));
        vbox3.getChildren().add(label3);

        //content in vbox3
        HBox content2 = new HBox();
        content2.setStyle("-fx-background-color: lightblue");
        content2.setSpacing(20); // Set spacing between items
      
        ArrayList<EventData> closestUpcoming = Event.getLatestEvent(Lantern.getConn());
        for (int i = 0; i <closestUpcoming.size(); i++) {
            BorderPane borderPane2 = BPForAllEvents(closestUpcoming.get(i));

            content2.getChildren().addAll(borderPane2);
        }

        //creating scrollpane2
        ScrollPane scrollPane2 = new ScrollPane(content2);
        scrollPane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show horizontal scrollbar
        scrollPane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //vbox4 in bp2
        BorderPane bp2 = new BorderPane();
        VBox vbox4 = new VBox();
        vbox4.setStyle("-fx-background-color: lightblue");
        vbox4.setPrefSize(600, 200);
        vbox4.getChildren().add(scrollPane2);
        bp2.setCenter(vbox4);
        Pane topPane2 = new Pane();
        topPane2.setPrefSize(400,30);
        Pane leftPane2 = new Pane();
        leftPane2.setPrefSize(20.0, 250.0);
        Pane rightPane2 = new Pane();
        rightPane2.setPrefSize(20.0, 250.0);
        Pane bottomPane2 = new Pane();
        bottomPane2.setPrefSize(400, 0);
        bp2.setTop(topPane2);
        bp2.setLeft(leftPane2);
        bp2.setRight(rightPane2);
        bp2.setBottom(bottomPane2);
        topPane2.setStyle("-fx-background-color: lightblue;");
        leftPane2.setStyle("-fx-background-color: lightblue;");
        rightPane2.setStyle("-fx-background-color: lightblue;");
        bottomPane2.setStyle("-fx-background-color: lightblue;");

        VBox mainhbox = new VBox();
        mainhbox.getChildren().addAll(vbox1, bp, vbox3, bp2);
        VBox.setVgrow(mainhbox, Priority.ALWAYS);
        return mainhbox;
    }

    
    public static BorderPane BPForAllEvents(EventData thisevent) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(300);
        borderPane.setPrefHeight(180);
        borderPane.setStyle("-fx-background-color: #226c94;");
        borderPane.setPadding(new Insets(15));
  
        Label label1 = new Label(thisevent.getEventTitle());
        label1.setTextFill(Color.WHITE); 
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(thisevent.getDescription());
        label2.setTextFill(Color.WHITE); 
        Label label3 = new Label(thisevent.getVenue());
        label3.setTextFill(Color.WHITE); 
        Label label4 = new Label(thisevent.getDate());
        label4.setTextFill(Color.WHITE); 
        Label label5 = new Label(thisevent.getTime());
        label5.setTextFill(Color.WHITE); 

        label1.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        ToggleButton toggleButton= new ToggleButton("Register");
        toggleButton.setAlignment(Pos.BOTTOM_RIGHT);

        Login_Register lr = new Login_Register();
        GlobalLeaderBoard glb= new GlobalLeaderBoard();
        Database db = new Database();

        if(RegisterEvent.checkClashDate(Lantern.getConn(), User.getCurrentUser().getUsername(),thisevent) || RegisterEvent.checkEventRegistered (Lantern.getConn(),User.getCurrentUser().getUsername(),thisevent)){
                toggleButton.setDisable(true);
        }else{        
            toggleButton.setDisable(false);
            toggleButton.setOnAction(event->{
                RegisterEvent.registerEvent(Lantern.getConn(), thisevent,User.getCurrentUser().getUsername()) ;
                toggleButton.setDisable(true);
               
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Events registered successfully!");
                alert.showAndWait();

                User.getCurrentUser().setPoints(pointLabel +5);
                glb.updateXpState(Lantern.getConn(), lr.getId()); 
                updatePointsVbox();

              
             });

       }
       
        
        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        BorderPane.setAlignment(label2, Pos.TOP_LEFT);
        BorderPane.setAlignment(label3, Pos.CENTER_LEFT);
        BorderPane.setAlignment(label4, Pos.CENTER_LEFT);
        BorderPane.setAlignment(label5, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(toggleButton, Pos.BOTTOM_RIGHT);

        Region spacer= new Region();
        VBox topBox = new VBox(label1);
        topBox.setPrefHeight(40);
        VBox middleBox = new VBox(label2,label3, label4); 
        HBox bottomBox = new HBox(label5, spacer, toggleButton);
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        // Set nodes to the BorderPane
        borderPane.setTop(topBox);
        borderPane.setCenter(middleBox);
        borderPane.setBottom(bottomBox);

        return borderPane;
    }

}
