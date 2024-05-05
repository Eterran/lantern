package GUI;

import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

import Database.Database;
import Database.Login_Register;
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
                Profile.loadProfileTab(Login_Register.getUser(friend, Database.connectionDatabase()));
            });
            friendVBox.getChildren().add(profileButton);
        }
        return friendVBox;
    }
}
