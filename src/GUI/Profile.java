package GUI;

import Database.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Profile {
    public static void showHomeScene(Stage stg) {
        Lantern.Clear_History();
        BorderPane root = new BorderPane();
        Button backButton = new Button("Back");
        TabPane tabPane = new TabPane();
        Tab tabHome = new Tab("Home");
        Tab tabProfile = new Tab("Profile");
        Tab tab3 = new Tab("3");
        Tab tab4 = new Tab("4");
        Tab tab5 = new Tab("5");
        tabHome.setClosable(false);
        tabProfile.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);

        tabHome.setContent(Lantern.loadHomeTab());
        tabProfile.setContent(loadProfileTab());
        
        //TODO: Add content to tabs
        Label content3 = new Label("Content 3");
        content3.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        tab3.setContent(content3);
        Label content4 = new Label("Content 4");
        content4.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        tab4.setContent(content4);
        Label content5 = new Label("Content 5");
        content5.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        tab5.setContent(content5);

        tabHome.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tabProfile.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tab3.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tab4.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tab5.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tabPane.getTabs().addAll(tabHome, tabProfile, tab3, tab4, tab5);
        backButton.setOnAction(e -> {
            Lantern.goBackTab(tabPane);
            Lantern.goBackTab(tabPane);
        });
        Lantern.Push_TabHistory(tabHome);

        tabPane.setStyle(
                "-fx-tab-min-width:100px; -fx-tab-max-width:100px; -fx-tab-min-height:30px; -fx-tab-max-height:30px; -fx-font-size:14px; -fx-font-weight:bold;");

        tabHome.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
        tabProfile.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
        tab3.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
        tab4.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
        tab5.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");

        tabHome.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!Lantern.History_isEmpty()  || Lantern.Peek_TabHistory() != tabPane.getSelectionModel().getSelectedItem())) {
                    Lantern.Push_TabHistory(tabPane.getSelectionModel().getSelectedItem());
                }
                tabHome.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tabHome.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tabProfile.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!Lantern.History_isEmpty()  || Lantern.Peek_TabHistory() != tabPane.getSelectionModel().getSelectedItem())) {
                    Lantern.Push_TabHistory(tabPane.getSelectionModel().getSelectedItem());
                }
                tabProfile.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tabProfile.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tab3.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!Lantern.History_isEmpty()  || Lantern.Peek_TabHistory() != tabPane.getSelectionModel().getSelectedItem())) {
                    Lantern.Push_TabHistory(tabPane.getSelectionModel().getSelectedItem());
                }
                tab3.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tab3.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tab4.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!Lantern.History_isEmpty()  || Lantern.Peek_TabHistory() != tabPane.getSelectionModel().getSelectedItem())) {
                    Lantern.Push_TabHistory(tabPane.getSelectionModel().getSelectedItem());
                }
                tab4.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tab4.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tab5.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!Lantern.History_isEmpty() || Lantern.Peek_TabHistory() != tabPane.getSelectionModel().getSelectedItem())) {
                    Lantern.Push_TabHistory(tabPane.getSelectionModel().getSelectedItem());
                }
                tab5.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tab5.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(tabPane);

        root.setCenter(layout1);
        root.setTop(backButton);

        Scene scene1 = new Scene(root, 1000, 650);

        stg.setScene(scene1);
    }
    public static VBox loadProfileTab(){
        VBox profileTab = new VBox(10);
        Label profileLabel = new Label("Profile");
        profileLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        profileTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        profileTab.getChildren().add(profileLabel);
        profileTab.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Insets padding = new Insets(10, 10, 10, 10);
        HBox usernameBox = Lantern.createInfoBox("Username: ", User.getCurrentUser().getUsername(), padding);
        HBox roleBox = Lantern.createInfoBox("Role: ", User.getCurrentUser().getRole(), padding);
        HBox emailBox = Lantern.createInfoBox("Email: ", User.getCurrentUser().getEmail(), padding);
        HBox locationCoordinateBox = Lantern.createInfoBox("Location: ", "("+User.getCurrentUser().getXCoordinate()+", "+ User.getCurrentUser().getYCoordinate()+")", padding);
        profileTab.getChildren().addAll(usernameBox, roleBox, emailBox, locationCoordinateBox);

        if(AccessManager.hasAccess("Student", AccessManager.ContentType.STUDENT)){
            Label studentLabel = new Label("Students");
            profileTab.getChildren().add(studentLabel);
            HBox pointBox = Lantern.createInfoBox("Points", User.getCurrentUser().getPoints(), 10);
            //TODO FRIENDS
            VBox friendBox = new VBox(10);
            
            profileTab.getChildren().addAll(pointBox, friendBox);
        } else if(AccessManager.hasAccess("Educator", AccessManager.ContentType.EDUCATOR)){
            Label educatorLabel = new Label("Educators");
            profileTab.getChildren().add(educatorLabel);
            HBox quizBox = new HBox(10);
            HBox eventBox = new HBox(10);
            Text quizCreated = new Text("Quizzes Created: ");
            Text eventCreated = new Text("Events Created: ");
            //TODO access database 
            Text quizCount = new Text("5");
            Text eventCount = new Text("3");
            quizBox.getChildren().addAll(quizCreated, quizCount);
            eventBox.getChildren().addAll(eventCreated, eventCount);
            profileTab.getChildren().addAll(quizBox, eventBox);
        } else if(AccessManager.hasAccess("Parent", AccessManager.ContentType.PARENT)){
            Label parentLabel = new Label("Parents");
            profileTab.getChildren().add(parentLabel);
            HBox bookingBox = new HBox(10);
            Text bookingMade = new Text("Bookings Made: ");
            //TODO access database 
            Text bookingCount = new Text("5");
            bookingBox.getChildren().addAll(bookingMade, bookingCount);
            profileTab.getChildren().add(bookingBox);
        } else {
            profileTab.getChildren().add(new Label("No Profile Information"));
        }
        return profileTab;
    }
}
