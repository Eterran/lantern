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
import Database.User;
import Student.GlobalLeaderBoard;
import javafx.geometry.Insets;

public class GlobalLeaderboard {
    private static VBox refreshGLBtab =  new VBox();

    // public static void refreshUIGLB() {
    //     refreshGLBtab.getChildren().clear();
    //     VBox temp = new VBox();
    //     VBox.setVgrow(temp, javafx.scene.layout.Priority.ALWAYS);
    //     GlobalLeaderBoard glb = new GlobalLeaderBoard();
    //     Login_Register user = new Login_Register();
    //     glb.updateXpState(Lantern.getConn(), user.getId());
    //     try {
    //         glb.loadGlobal(Lantern.getConn());
    //     } catch (IOException e) {
    //         e.printStackTrace(); 
    //         return;
    //     }catch(SQLException e){
    //         e.printStackTrace();
    //         return;
    //     }
    //     String [] username = glb.getUsername();
    //     double[] points = glb.getXp();
    //     for (int i = 0; i <20; i++) {
    //         HBox dataBox = new HBox();
    //         dataBox.setPadding(new Insets(5)); 
    //         if(i>=username.length){
    //              dataBox.setPrefSize(300, 80);
    //              if (i % 2 == 0) {
    //                 dataBox.setStyle("-fx-background-color: #FACBA6;");
    //             } else {
    //                 dataBox.setStyle("-fx-background-color: #ffffff;");
    //             }    
    //             temp.getChildren().add(dataBox);

    //         }else{
    //             if (i % 2 == 0) {
    //                 dataBox.setStyle("-fx-background-color: #FACBA6;");
    //             } else {
    //                 dataBox.setStyle("-fx-background-color: #ffffff;");
    //             }       
    //             Label rankData = new Label(String.valueOf(i + 1));  
    //             rankData.setPadding(new Insets(5,10,5,5));
    //             Label usernameData = new Label(username[i]);
    //             usernameData.setPadding(new Insets(5,10,5,5));
    //             Label pointData = new Label(String.valueOf(points[i]));
    //             pointData.setPadding(new Insets(5,10,5,5));
    //             Region spacer3 = new Region();
    //             HBox.setHgrow(spacer3, javafx.scene.layout.Priority.ALWAYS);
    //             Region spacer4 = new Region();
    //             HBox.setHgrow(spacer4, javafx.scene.layout.Priority.ALWAYS);
    //             dataBox.getChildren().addAll(rankData, spacer3, usernameData, spacer4, pointData);  //adding every info into hbox for each line

    //             temp.getChildren().add(dataBox); //continue add all the databox into vbox

    //         }
    //     }

      
    //    // temp.getChildren().add(scrollPane);
    //     refreshGLBtab.getChildren().add(temp);
    // }

    public static void refreshUIGLB() {
        refreshGLBtab.getChildren().clear();

        VBox temp = new VBox();
        VBox.setVgrow(temp, Priority.ALWAYS);

        GlobalLeaderBoard glb = new GlobalLeaderBoard();
        Login_Register user = new Login_Register();
        glb.updateXpState(Lantern.getConn(), user.getId());
        try {
            glb.loadGlobal(Lantern.getConn());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return;
        }

        String[] username = glb.getUsername();
        double[] points = glb.getXp();

        int rowCount = Math.max(20, username.length); // Ensure minimum of 20 rows

        for (int i = 0; i < rowCount; i++) {
            HBox dataBox = new HBox();
            dataBox.setPadding(new Insets(5));
            if (i % 2 == 0) {
                dataBox.setStyle("-fx-background-color: #FACBA6;");
            } else {
                dataBox.setStyle("-fx-background-color: #ffffff;");
            }

            if (i < username.length) {
                if (username[i].equals(User.getCurrentUser().getUsername())) {
                    Label ownrankData = new Label(String.valueOf(i + 1));
                    ownrankData.setPadding(new Insets(5, 10, 5, 5));
                    ownrankData.setStyle("-fx-font-weight: bold;");

                    Label ownUsername = new Label(username[i]);
                    ownUsername.setPadding(new Insets(5, 10, 5, 5));
                    ownUsername.setStyle("-fx-font-weight: bold;");

                    Label ownpointData = new Label(String.valueOf(points[i]));
                    ownpointData.setPadding(new Insets(5, 10, 5, 5));
                    ownpointData.setStyle("-fx-font-weight: bold;");

                    Region spacer5 = new Region();
                    HBox.setHgrow(spacer5, Priority.ALWAYS);

                    Region spacer6 = new Region();
                    HBox.setHgrow(spacer6, Priority.ALWAYS);

                    dataBox.getChildren().addAll(ownrankData, spacer5, ownUsername, spacer6, ownpointData);
                } else {
                    Label rankData = new Label(String.valueOf(i + 1));
                    rankData.setPadding(new Insets(5, 10, 5, 5));

                    Label usernameData = new Label(username[i]);
                    usernameData.setPadding(new Insets(5, 10, 5, 5));

                    Label pointData = new Label(String.valueOf(points[i]));
                    pointData.setPadding(new Insets(5, 10, 5, 5));

                    Region spacer3 = new Region();
                    HBox.setHgrow(spacer3, Priority.ALWAYS);

                    Region spacer4 = new Region();
                    HBox.setHgrow(spacer4, Priority.ALWAYS);

                    dataBox.getChildren().addAll(rankData, spacer3, usernameData, spacer4, pointData);
                }
            } else { // If username.length is smaller than 20
                // Add empty rows
                Label rankData = new Label("");
                rankData.setPadding(new Insets(5, 10, 5, 5));

                Label usernameData = new Label("");
                usernameData.setPadding(new Insets(5, 10, 5, 5));

                Label pointData = new Label("");
                pointData.setPadding(new Insets(5, 10, 5, 5));

                Region spacer3 = new Region();
                HBox.setHgrow(spacer3, Priority.ALWAYS);

                Region spacer4 = new Region();
                HBox.setHgrow(spacer4, Priority.ALWAYS);

                dataBox.getChildren().addAll(rankData, spacer3, usernameData, spacer4, pointData);
            }
            temp.getChildren().add(dataBox);
        }

        refreshGLBtab.getChildren().add(temp);
    }

    public static VBox globalLeaderBoardTab() {

        BorderPane borderPane = new BorderPane();

        //title
        Label titleLabel = new Label("Global Leaderboard");
        titleLabel.getStyleClass().add("title");
        titleLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
       // titleLabel.setFont(new Font(23));
      //  titleLabel.setStyle("-fx-font-weight: bold;");

         //creating ScrollPane 
        Pane content = new Pane();
        content.setStyle("-fx-background-color:lightyellow");
       // content.setPrefSize(391,247);
        content.setPrefWidth(391);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        //scrollPane.setPrefSize(391.0, 247.0);
        

        //header to store rank, username, point label
        HBox headerBox = new HBox();
        HBox.setHgrow(headerBox, javafx.scene.layout.Priority.ALWAYS); 
        headerBox.setStyle("-fx-background-color:#FF8423;");
        
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


        borderPane.setTop(titleLabel);
        //vbox contain lots of hbox
        VBox vBox = new VBox();
        VBox.setVgrow(vBox, javafx.scene.layout.Priority.ALWAYS); 
        refreshUIGLB();

        vBox.getChildren().addAll(headerBox, refreshGLBtab);

        vBox.prefWidthProperty().bind(content.widthProperty());   //bind VBox to the pane
        vBox.prefHeightProperty().bind(content.heightProperty());

        content.getChildren().add(vBox);
        scrollPane.setContent(content);
        
        borderPane.setCenter(scrollPane);

        Pane leftPane = new Pane();
        leftPane.setPrefSize(50.0, 250.0);
        Pane rightPane = new Pane();
        rightPane.setPrefSize(50.0, 250.0);
        // Pane bottomPane = new Pane();
        // bottomPane.setPrefSize(400, 20);
        borderPane.setLeft(leftPane);
        borderPane.setRight(rightPane);
        // borderPane.setBottom(bottomPane);

        VBox mainvbox = new VBox();
        mainvbox.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS); //ensure borderPane grow together with Vbox
        mainvbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return mainvbox;
    }
 

}


