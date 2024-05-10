package GUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import Database.Database;
import Database.Login_Register;
import Database.User;
import Student.friend;

public class FriendList {
    public static VBox loadFriendList() {
        Image addFriendImage = new Image("resources/assets/add_friends_icon.png");
        ImageView addFriendImageView = new ImageView(addFriendImage);
        addFriendImageView.setFitWidth(40);
        addFriendImageView.setFitHeight(40);
        VBox friendVBox = new VBox();
        User user = User.getCurrentUser();
        Label label = new Label("Friends");
        label.getStyleClass().add("title");
        friendVBox.getChildren().add(label);
        TextField addFriendTF = new TextField();
        addFriendTF.getStyleClass().add("text_field");
        Button addFriendButton = new Button();
        addFriendButton.setGraphic(addFriendImageView);
        addFriendButton.getStyleClass().add("add_friend_button");
        addFriendButton.setOnAction(e -> {
            String friendUsername = addFriendTF.getText();
            if (Login_Register.getUser(friendUsername, Database.connectionDatabase()) != null) {
                //friend friends = new friend(user.getUsername());
                //TODO addFriend
                //friends.addFriend(friendUsername);
                //friendVBox.getChildren().add(new Button(friendUsername));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User not found");
                alert.setContentText("The user you are trying to add as a friend does not exist.");
            }
        });
        HBox addFriendHBox = new HBox();
        addFriendHBox.getChildren().addAll(addFriendTF, addFriendButton);

        friend friends = new friend(user.getUsername());
        ArrayList<String> friendList = friends.getFriendList();
        for (String friend : friendList) {
            Button profileButton = new Button(friend);
            profileButton.getStyleClass().add("profile_button");
            profileButton.setOnAction(e -> {
                Profile.loadProfileTab(Login_Register.getUser(friend, Database.connectionDatabase()));
            });
            friendVBox.getChildren().add(profileButton);
        }

        friendVBox.getChildren().add(addFriendHBox);
        return friendVBox;
    }
}
