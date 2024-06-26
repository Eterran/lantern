package GUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private static ArrayList<String> friendList = friend.showFriend(conn, User.getCurrentUser().getUsername());
    private static VBox friendListBox = new VBox();
    private static ScrollPane friendScrollPane = new ScrollPane();

    public static void refreshFriendList(){
        friendList = friend.showFriend(conn, User.getCurrentUser().getUsername());
        friendListBox.getChildren().clear();
        VBox temp = new VBox();
        for (String friend : friendList) {
            Button profileButton = new Button(friend);
            profileButton.getStyleClass().add("profile_button");
            profileButton.setOnAction(e -> {
                Sidebar.setothersProfile(Profile.loadProfileTab(Login_Register.getUser(friend, conn)));
            });
            temp.getChildren().addAll(Lantern.createHorizontalSeparator(new Insets(3, 14, 3, 14)), profileButton);
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
            temp.getChildren().add(noFriendsVBox);
        }
        friendListBox.getChildren().add(temp);
        friendScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        friendScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        friendScrollPane.setFitToWidth(true);
        friendScrollPane.setFitToHeight(true);
        //friendScrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        friendScrollPane.setContent(friendListBox);
        VBox.setVgrow(friendScrollPane, javafx.scene.layout.Priority.ALWAYS);
        Sidebar.selectTab(6);
    }

    public static VBox loadFriendList() {
        friendListBox.getChildren().clear();
        StackPane stackpane = new StackPane();
        VBox friendVBox = new VBox();
        friendVBox.getChildren().add(stackpane);
        
        Label label = new Label("Friends");
        label.getStyleClass().add("title");

        TextField searchFriendTF = new TextField();
        searchFriendTF.getStyleClass().add("text_field");
        searchFriendTF.setPromptText("Search friend");
        searchFriendTF.setPrefHeight(40);

        StackPane searchFriendTFstackPane = new StackPane();
        searchFriendTFstackPane.getChildren().addAll(searchFriendTF);

        StackPane.setAlignment(searchFriendTF, Pos.CENTER);

        searchFriendTF.textProperty().addListener((observable, oldValue, newValue) -> {
            String friendUsername = searchFriendTF.getText();
            friendListBox.getChildren().clear();
            for (String friend : friendList) {
                if (friend.toLowerCase().contains(friendUsername.toLowerCase())) {
                    Button profileButton = new Button(friend);
                    profileButton.getStyleClass().add("profile_button");
                    profileButton.setOnAction(ev -> {
                        Sidebar.setothersProfile(Profile.loadProfileTab(Login_Register.getUser(friend, conn)));
                    });
                    friendListBox.getChildren().addAll(Lantern.createHorizontalSeparator(new Insets(3, 14, 3, 14)), profileButton);
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
            VBox backgroundBlocker = new VBox();
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

            backgroundBlocker.getStyleClass().add("background_transparent");
            backgroundBlocker.minHeightProperty().bind(stackpane.heightProperty());
            backgroundBlocker.minWidthProperty().bind(stackpane.widthProperty());
            backgroundBlocker.maxHeightProperty().bind(stackpane.heightProperty());
            backgroundBlocker.maxWidthProperty().bind(stackpane.widthProperty());
            backgroundBlocker.setOnMouseClicked(event -> {
                stackpane.getChildren().remove(overlay);
                stackpane.getChildren().remove(backgroundBlocker);
            });

            stackpane.getChildren().add(backgroundBlocker);
            stackpane.getChildren().add(overlay);

            Lantern.playTransition(overlay, 1.0);
        });

        searchFriendHBox.getChildren().addAll(searchFriendTF, Lantern.createSpacer(), showFriendRequestsButton);
        searchFriendHBox.setPadding(new Insets(0, 12, 12, 10));
        VBox baseVBox = new VBox();
        baseVBox.getChildren().addAll(label, searchFriendHBox);

        refreshFriendList();

        friendScrollPane.getStyleClass().add("friendlist-scroll-pane");
        VBox.setVgrow(friendScrollPane, javafx.scene.layout.Priority.ALWAYS);
        baseVBox.getChildren().add(friendScrollPane);
        baseVBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        stackpane.getChildren().add(baseVBox);
        stackpane.prefHeightProperty().bind(friendVBox.heightProperty().multiply(1));
        stackpane.prefWidthProperty().bind(friendVBox.widthProperty().multiply(1));
        stackpane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        friendVBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        baseVBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(friendVBox, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(stackpane, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(baseVBox, javafx.scene.layout.Priority.ALWAYS);

        return friendVBox;
    }

    public static ArrayList<String> findCommonFriends(String username1, String username2){
        ArrayList<String> friendList1 = friend.showFriend(conn, username1);
        ArrayList<String> friendList2 = friend.showFriend(conn, username2);
        
        Set<String> set1 = new HashSet<>(friendList1);
        Set<String> set2 = new HashSet<>(friendList2);
        
        set1.retainAll(set2);
        
        return new ArrayList<>(set1);
    }

    private static VBox createFriendRequestsVBox(){
        ArrayList<String> friendRequests = friend.showPendingReceived(conn, User.getCurrentUser().getUsername());
        VBox friendRequestsVBox = new VBox();
        Label friendRequestsLabel = new Label("Friend Requests");
        friendRequestsLabel.getStyleClass().add("title");
        friendRequestsLabel.setPadding(new Insets(12, 0, 12, 12));
        friendRequestsVBox.getChildren().addAll(friendRequestsLabel, Lantern.createHorizontalSeparator(2));
        
        for (String friendRequest : friendRequests) {
            HBox friendRequestHBox = new HBox();
            Label friendRequestLabel = new Label(friendRequest);
            friendRequestLabel.getStyleClass().add("text_content_black");
            Button acceptButton = new Button();
            acceptButton.setPrefSize(30, 30);
            acceptButton.getStyleClass().add("accept_button");
            acceptButton.setOnAction(e -> {
                friend.acceptFriend(conn, friendRequest, User.getCurrentUser().getUsername());
                friendRequestsVBox.getChildren().remove(friendRequestHBox);
                refreshFriendList();
                Sidebar.setBox7(FriendGraph.createFriendGraph());
            });
            Button declineButton = new Button();
            declineButton.setPrefSize(30, 30);
            declineButton.getStyleClass().add("decline_button");
            declineButton.setOnAction(e -> {
                friend.declineFriend(conn, friendRequest, User.getCurrentUser().getUsername());
                friendRequestsVBox.getChildren().remove(friendRequestHBox);
            });
            friendRequestHBox.getChildren().addAll(friendRequestLabel, Lantern.createSpacer(), acceptButton, declineButton);
            friendRequestsVBox.getChildren().add(friendRequestHBox);
        }
        if(friendRequests.isEmpty()){
            Label noFriendRequestsLabel = new Label("No friend requests");
            noFriendRequestsLabel.getStyleClass().add("text_black");
            noFriendRequestsLabel.setPadding(new Insets(12, 0, 12, 12));
            friendRequestsVBox.getChildren().add(noFriendRequestsLabel);
        }
        return friendRequestsVBox;
    }
}
