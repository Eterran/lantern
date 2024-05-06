package GUI;

import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import Database.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Profile {
    public static VBox loadProfileTab(User user){
        if(user == null){
            user = User.getCurrentUser();
        }
        try {
            PrintWriter writer = new PrintWriter("profile.txt");
            writer.println(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.getProperty("user.dir"));;
        VBox profileTab = new VBox(10);
        Label profileLabel = new Label("My Profile");
        profileLabel.getStyleClass().add("title");
        HBox profileLabelBox = new HBox();
        profileLabelBox.getChildren().add(profileLabel);
        profileLabelBox.setPadding(new Insets(12, 0, 12, 12));

        profileTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        profileTab.getChildren().add(profileLabelBox);
        profileTab.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Insets padding = new Insets(8, 10, 8, 15);
        VBox usernameBox = Lantern.createInfoBox("USERNAME: ", user.getUsername(), padding);
        VBox roleBox = Lantern.createInfoBox("ROLE: ", user.getRole(), padding);
        VBox emailBox = Lantern.createInfoBox("EMAIL: ", user.getEmail(), padding);
        VBox locationCoordinateBox = Lantern.createInfoBox("LOCATION: ", "("+user.getXCoordinate()+", "+ user.getYCoordinate()+")", padding);

        VBox profileContents = new VBox();
        profileContents.getChildren().addAll(usernameBox, roleBox, emailBox, locationCoordinateBox);
        profileContents.getStyleClass().add("content_box_background");
        profileContents.maxWidthProperty().bind(profileTab.widthProperty().multiply(0.9));
        profileContents.setPadding(new Insets(30, 10, 30, 10));
        profileTab.getChildren().addAll(profileContents);
        profileTab.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        if(AccessManager.hasAccess("Student", AccessManager.ContentType.STUDENT)){
            Label studentLabel = new Label("Students");
            studentLabel.getStyleClass().add("title");
            profileTab.getChildren().add(studentLabel);
            VBox pointBox = Lantern.createInfoBox("Points: ", user.getPoints(), padding);
            profileTab.getChildren().addAll(pointBox);

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
