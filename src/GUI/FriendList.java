package GUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Database.Login_Register;
import Database.User;
import Student.friend;

public class FriendList {
    private static Connection conn = Lantern.getConn();
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
            if (Login_Register.getUser(friendUsername, conn) != null) {
                friendVBox.getChildren().clear();
                friendVBox.getChildren().add(Profile.loadOthersProfileTab(Login_Register.getUser(friendUsername, conn)));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User not found");
                alert.setContentText("The user you are trying to find does not exist.");
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
                Profile.loadOthersProfileTab(Login_Register.getUser(friend, conn));
            });
            friendVBox.getChildren().add(profileButton);
        }

        friendVBox.getChildren().add(addFriendHBox);
        return friendVBox;
    }
    public ArrayList<String> findCommonFriends(String username1, String username2){
        friend friends1 = new friend(username1);
        friend friends2 = new friend(username2);
        
        ArrayList<String> friendList1 = friends1.getFriendList();
        ArrayList<String> friendList2 = friends2.getFriendList();
        
        Set<String> set1 = new HashSet<>(friendList1);
        Set<String> set2 = new HashSet<>(friendList2);
        
        set1.retainAll(set2);
        
        return new ArrayList<>(set1);
    }
}
