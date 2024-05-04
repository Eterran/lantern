package GUI;

import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

import Database.User;
import Student.friend;

public class FriendList {
    public static VBox loadFriendList() {
        VBox friendVBox = new VBox();
        User user = User.getCurrentUser();
        friendVBox.getChildren().add(new Label("Friends"));
        friend friends = new friend(user.getUsername());
        ArrayList<String> friendList = friends.getFriendList();
        for (String friend : friendList) {
            Button profileButton = new Button(friend);
            profileButton.getStyleClass().add("profile_button");
            profileButton.setOnAction(e -> {
                //TODO: Load profile of friend
                //Profile.loadProfileTab(new User(friend));
            });
            friendVBox.getChildren().add(profileButton);
        }
        return friendVBox;
    }
}
