package GUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Database.Login_Register;
import Database.User;
import Student.friend;

public class FriendList {
    private static Connection conn = Lantern.getConn();
    private static User user = User.getCurrentUser();
    private static friend friends = new friend(user.getUsername());
    private static ArrayList<String> friendList = friends.getFriendList();

    public static VBox loadFriendList() {
        StackPane stackpane = new StackPane();
        ImageView searchFriendImageView = new ImageView(new Image("resources/assets/search_friends_icon.png"));
        searchFriendImageView.setFitWidth(40);
        searchFriendImageView.setFitHeight(40);
        VBox friendVBox = new VBox();
        friendVBox.getChildren().add(stackpane);
        
        Label label = new Label("Friends");
        label.getStyleClass().add("title");
        label.setPadding(new Insets(12, 0, 12, 12));
        VBox friendListBox = new VBox();
        
        TextField searchFriendTF = new TextField();
        searchFriendTF.getStyleClass().add("text_field");
        Button searchFriendButton = new Button();
        searchFriendButton.setGraphic(searchFriendImageView);
        searchFriendButton.getStyleClass().add("add_friend_button");
        searchFriendButton.setOnAction(e -> {
            String friendUsername = searchFriendTF.getText();
            friendListBox.getChildren().clear();
            friendListBox.getChildren().add(label);
        
            for (String friend : friendList) {
                if (friend.toLowerCase().contains(friendUsername.toLowerCase())) {
                    Button profileButton = new Button(friend);
                    profileButton.getStyleClass().add("profile_button");
                    profileButton.setOnAction(ev -> {
                        Sidebar.setBox7(Profile.loadOthersProfileTab(Login_Register.getUser(friend, conn)));
                    });
                    friendListBox.getChildren().add(profileButton);
                }
            }
        });
        HBox searchFriendHBox = new HBox();
        Button showFriendRequestsButton = new Button();
        ImageView showFriendRequestsImageView = new ImageView(new Image("resources/assets/add_friends_icon.png"));
        showFriendRequestsImageView.setFitWidth(40);
        showFriendRequestsImageView.setFitHeight(40);
        showFriendRequestsButton.setGraphic(showFriendRequestsImageView);
        showFriendRequestsButton.getStyleClass().add("add_friend_button");
        
        showFriendRequestsButton.setOnAction(e -> {
            VBox friendRequestsVBox = createFriendRequestsVBox();
            VBox overlay = new VBox();
            Button closeButton = new Button();
            ImageView closeImageView = new ImageView(new Image("resources/assets/close_icon.png"));
            closeImageView.setFitWidth(40);
            closeImageView.setFitHeight(40);
            closeButton.getStyleClass().add("close_button");
            closeButton.setGraphic(closeImageView);

            closeButton.setOnAction(event -> stackpane.getChildren().remove(overlay));

            HBox closeButtonBox = new HBox();
            closeButtonBox.setAlignment(Pos.TOP_RIGHT);
            closeButtonBox.getChildren().add(closeButton);

            overlay.getChildren().addAll(closeButtonBox, friendRequestsVBox);
            overlay.getStyleClass().add("overlay");
            overlay.minHeightProperty().bind(stackpane.heightProperty().multiply(0.85));
            overlay.minWidthProperty().bind(stackpane.widthProperty().multiply(0.75));
            overlay.maxHeightProperty().bind(stackpane.heightProperty().multiply(0.85));
            overlay.maxWidthProperty().bind(stackpane.widthProperty().multiply(0.75));

            stackpane.getChildren().add(overlay);
        });

        searchFriendHBox.getChildren().addAll(searchFriendTF, searchFriendButton, showFriendRequestsButton);
        VBox baseVBox = new VBox();
        baseVBox.getChildren().addAll(label, searchFriendHBox);

        for (String friend : friendList) {
            Button profileButton = new Button(friend);
            profileButton.getStyleClass().add("profile_button");
            profileButton.setOnAction(e -> {
                Sidebar.setBox7(Profile.loadOthersProfileTab(Login_Register.getUser(friend, conn)));
            });
            friendListBox.getChildren().add(profileButton);
        }
        if(friendList.isEmpty()){
            VBox noFriendsVBox = new VBox();
            noFriendsVBox.setPadding(new Insets(30, 0, 30, 0));
            noFriendsVBox.setAlignment(Pos.CENTER);
            Label noFriendsLabel = new Label("It's quite lonely here...");
            noFriendsLabel.getStyleClass().add("text_greyed_out");
            ImageView img = new ImageView(new Image("resources/assets/black_lantern.png"));
            img.setFitHeight(180);
            img.setFitWidth(180);
            img.setOpacity(0.5);
            noFriendsVBox.getChildren().addAll(noFriendsLabel, img);
            friendListBox.getChildren().add(noFriendsVBox);
        }
        
        baseVBox.getChildren().add(friendListBox);
        stackpane.getChildren().add(baseVBox);
        stackpane.prefHeightProperty().bind(friendVBox.heightProperty().multiply(1));
        stackpane.prefWidthProperty().bind(friendVBox.widthProperty().multiply(1));
        friendVBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(friendVBox, javafx.scene.layout.Priority.ALWAYS);
        return friendVBox;
    }
    public static ArrayList<String> findCommonFriends(String username1, String username2){
        friend friends1 = new friend(username1);
        friend friends2 = new friend(username2);
        
        ArrayList<String> friendList1 = friends1.getFriendList();
        ArrayList<String> friendList2 = friends2.getFriendList();
        
        Set<String> set1 = new HashSet<>(friendList1);
        Set<String> set2 = new HashSet<>(friendList2);
        
        set1.retainAll(set2);
        
        return new ArrayList<>(set1);
    }
    private static VBox createFriendRequestsVBox(){
        friend fren = new friend(User.getCurrentUser().getUsername());
        ArrayList<String> friendRequests = fren.showPending(conn, User.getCurrentUser().getUsername());
        VBox friendRequestsVBox = new VBox();
        Label friendRequestsLabel = new Label("Friend Requests");
        friendRequestsLabel.getStyleClass().add("title");
        friendRequestsLabel.setPadding(new Insets(12, 0, 12, 12));
        friendRequestsVBox.getChildren().add(friendRequestsLabel);
        for (String friendRequest : friendRequests) {
            HBox friendRequestHBox = new HBox();
            Label friendRequestLabel = new Label(friendRequest);
            friendRequestLabel.getStyleClass().add("friend_request_label");
            Button acceptButton = new Button();
            acceptButton.getStyleClass().add("accept_button");
            acceptButton.setOnAction(e -> {
                fren.acceptFriend(conn, friendRequest, User.getCurrentUser().getUsername());
                friendRequestsVBox.getChildren().remove(friendRequestHBox);
            });
            Button declineButton = new Button();
            declineButton.getStyleClass().add("decline_button");
            declineButton.setOnAction(e -> {
                fren.declineFriend(conn, friendRequest, User.getCurrentUser().getUsername());
                friendRequestsVBox.getChildren().remove(friendRequestHBox);
            });
            friendRequestHBox.getChildren().addAll(friendRequestLabel, acceptButton, declineButton);
            friendRequestsVBox.getChildren().add(friendRequestHBox);
        }
        if(friendRequests.isEmpty()){
            Label noFriendRequestsLabel = new Label("No friend requests");
            noFriendRequestsLabel.getStyleClass().add("friend_request_label");
            friendRequestsVBox.getChildren().add(noFriendRequestsLabel);
        }
        return friendRequestsVBox;
    }
}
