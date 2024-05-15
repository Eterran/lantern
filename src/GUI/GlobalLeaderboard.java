package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.SQLException;

import Database.Login_Register;
import Student.GlobalLeaderBoard;
import javafx.geometry.Insets;

public class GlobalLeaderboard {

    // @Override
    // public void start(Stage primaryStage) {

    //     Scene scene = new Scene(globalLeaderBoardTab());
    //     primaryStage.setScene(scene);
    //     primaryStage.setTitle("Global Leaderboard");
    //     primaryStage.show();
    // }

    // public static void main(String[] args) {
    //     launch(args);
    // }


    public static VBox globalLeaderBoardTab(){
        //
        BorderPane borderPane = new BorderPane();
        Label titleLabel = new Label("Global Leaderboard");
        borderPane.setTop(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);
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
        headerBox.setStyle("-fx-background-color:#004b3a;");
        //730099
        
        Label rankLabel = new Label("Rank");
        rankLabel.setPadding(new Insets(5));
        rankLabel.setStyle("-fx-font-weight: bold; -fx-font-size:14px; -fx-font-family: Arial;-fx-text-fill: white");
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-weight:bold;-fx-font-size: 14px; -fx-font-family: Arial;-fx-text-fill: white");
        usernameLabel.setPadding(new Insets(5));
        Label pointLabel = new Label("Point");
        pointLabel.setStyle("-fx-font-weight:bold; -fx-font-size: 14px; -fx-font-family: Arial;-fx-text-fill: white");
        pointLabel.setPadding(new Insets(5,10,5,5));

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, javafx.scene.layout.Priority.ALWAYS);
        headerBox.setPadding(new Insets(5));
        headerBox.getChildren().addAll(rankLabel,spacer1, usernameLabel, spacer2, pointLabel);

        vBox.getChildren().add(headerBox);

        // int numberOfBoxes = 4; //based on total number of students (totalrowsinquizAttempt)
        GlobalLeaderBoard glb = new GlobalLeaderBoard();
        Login_Register user = new Login_Register();
        glb.insertXpState(Lantern.getConn(), user.getId());
        glb.updateXpState(Lantern.getConn(), user.getId());
        try {
                glb.loadGlobal(Lantern.getConn());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        String [] username = glb.getUsername();
        System.out.println(username.length);
        double[] points = glb.getXp();
        for (int i = 0; i < username.length; i++) {
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(5)); 
            if (i % 2 == 0) {
                dataBox.setStyle("-fx-background-color: #ADEFD1;");
            } else {
                dataBox.setStyle("-fx-background-color: #ffffff;");
            }
           
            
            Label rankData = new Label(String.valueOf(i + 1));  //fetch the value from the db
            rankData.setPadding(new Insets(5,10,5,5));
            Label usernameData = new Label(username[i]);
            usernameData.setPadding(new Insets(5,10,5,5));
            Label pointData = new Label(String.valueOf(points[i]));
            pointData.setPadding(new Insets(5,10,5,5));
            Region spacer3 = new Region();
            HBox.setHgrow(spacer3, javafx.scene.layout.Priority.ALWAYS);
            Region spacer4 = new Region();
            HBox.setHgrow(spacer4, javafx.scene.layout.Priority.ALWAYS);

            dataBox.getChildren().addAll(rankData, spacer3, usernameData, spacer4, pointData);  //adding every info into hbox for each line
            
            vBox.getChildren().add(dataBox); //continue add all the databox into vbox
        }

        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);

        Pane leftPane = new Pane();
        leftPane.setPrefSize(50.0, 250.0);
        Pane rightPane = new Pane();
        rightPane.setPrefSize(50.0, 250.0);
        Pane bottomPane = new Pane();
        bottomPane.setPrefSize(400, 20);
        borderPane.setLeft(leftPane);
        borderPane.setRight(rightPane);
        borderPane.setBottom(bottomPane);

        VBox mainvbox = new VBox();
        // mainvbox.setStyle("-fx-background-color:white;");
        mainvbox.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS); //ensure borderPane grow together with Vbox
        mainvbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return mainvbox;
    }
 

    
}


