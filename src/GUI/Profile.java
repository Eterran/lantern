package GUI;

import java.sql.Connection;
import java.sql.SQLException;

import Database.User;
import Student.friend;
import Database.Database;
import Student.friend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Profile {
    private final static AccessManager accessManager = Sidebar.getAccessManager();
    private final static Connection conn = Database.connectionDatabase();

    public static VBox loadProfileTab(User user){
        try {
            if(!Database.usernameExists(Database.connectionDatabase(), user.getUsername())){
                VBox errorBox = new VBox();
                Text errorText = new Text("User does not exist");
                errorText.getStyleClass().add("title");
                errorBox.getChildren().add(errorText);
                return errorBox;
            }
        } catch (SQLException e) {
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
        VBox usernameBox = Lantern.createInfoVBox("USERNAME: ", user.getUsername(), padding);
        VBox roleBox = Lantern.createInfoVBox("ROLE: ", user.getRole(), padding);
        VBox emailBox = Lantern.createInfoVBox("EMAIL: ", user.getEmail(), padding);
        VBox locationCoordinateBox = Lantern.createInfoVBox("LOCATION: ", "("+user.getXCoordinate()+", "+ user.getYCoordinate()+")", padding);

        VBox profileContents = new VBox();
        profileContents.getChildren().addAll(usernameBox, roleBox, emailBox, locationCoordinateBox);
        profileContents.getStyleClass().add("content_box_background");
        profileContents.maxWidthProperty().bind(profileTab.widthProperty().multiply(0.9));
        profileContents.setPadding(new Insets(30, 10, 30, 10));

        accessManager.getAccessibleVBoxes(accessManager.getUserRole(User.getCurrentUser())).forEach(
                    vBoxSupplier -> profileContents.getChildren().add(vBoxSupplier.get()));

        profileTab.getChildren().addAll(profileContents);
        profileTab.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        if(!user.getUsername().equals(User.getCurrentUser().getUsername())){
            Button addFriendButton = new Button("Add Friend");
            addFriendButton.getStyleClass().add("add_friend_button");
            addFriendButton.setOnAction(e -> {
                friend.friendRequest(conn, user.getUsername(), User.getCurrentUser().getUsername());
            });
        }

        return profileTab;
    }
}
