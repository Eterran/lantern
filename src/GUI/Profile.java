package GUI;

import Database.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Profile {
    public static VBox loadProfileTab(User user){
        if(user == null){
            user = User.getCurrentUser();
        }
        VBox profileTab = new VBox(10);
        Label profileLabel = new Label("Profile");
        profileLabel.getStyleClass().add("title");
        profileTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        profileTab.getChildren().add(profileLabel);
        profileTab.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Insets padding = new Insets(4, 10, 4, 15);
        HBox usernameBox = Lantern.createInfoBox("Username: ", user.getUsername(), padding);
        HBox roleBox = Lantern.createInfoBox("Role: ", user.getRole(), padding);
        HBox emailBox = Lantern.createInfoBox("Email: ", user.getEmail(), padding);
        HBox locationCoordinateBox = Lantern.createInfoBox("Location: ", "("+user.getXCoordinate()+", "+ user.getYCoordinate()+")", padding);
        profileTab.getChildren().addAll(usernameBox, roleBox, emailBox, locationCoordinateBox);

        if(AccessManager.hasAccess("Student", AccessManager.ContentType.STUDENT)){
            Label studentLabel = new Label("Students");
            studentLabel.getStyleClass().add("title");
            profileTab.getChildren().add(studentLabel);
            HBox pointBox = Lantern.createInfoBox("Points: ", user.getPoints(), padding);
            //TODO FRIENDS
            VBox friendBox = new VBox(10);
            friendBox.getChildren().add(new Label("Friends"));
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
