package GUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.User;
import Student.friend;
import Database.Database;
import Database.ParentChildren;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

public class Profile {
    private final static AccessManager accessManager = Sidebar.getAccessManager();
    private final static Connection conn = Lantern.getConn();
    public static void refreshProfile(){
        Sidebar.setBox1(loadProfileTab());
    }
    public static VBox loadProfileTab(){
        try {
            if(!Database.usernameExists(Database.connectionDatabase(), User.getCurrentUser().getUsername())){
                VBox errorBox = new VBox();
                Text errorText = new Text("User does not exist");
                errorText.getStyleClass().add("title");
                errorBox.getChildren().add(errorText);
                return errorBox;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        VBox profileRootBox = new VBox();
        StackPane stackpane = new StackPane();
        VBox profileTab = new VBox(10);
        Label profileLabel = new Label("My Profile");
        profileLabel.getStyleClass().add("title");
        HBox profileLabelBox = new HBox();
        profileLabelBox.setPadding(new Insets(12, 0, 12, 12));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        profileLabelBox.getChildren().addAll(profileLabel, spacer);

        if(User.getCurrentUser().getRole().toLowerCase().equals("student")){
            Button pendingRequestsButton = new Button();
            pendingRequestsButton.getStyleClass().add("add_friend_button");
            ImageView pendingRequestsImageView = new ImageView(new Image("resources/assets/three_dots_icon.png"));
            pendingRequestsImageView.setFitWidth(40);
            pendingRequestsImageView.setFitHeight(40);
            pendingRequestsButton.setGraphic(pendingRequestsImageView);
            pendingRequestsButton.setOnAction(e -> {
                VBox pendingRequestsVBox = createPendingRequestsVBox();
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

                overlay.getChildren().addAll(closeButtonBox, pendingRequestsVBox);
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
            });
            profileLabelBox.getChildren().add(pendingRequestsButton);
        }
        
        profileTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        profileTab.getChildren().add(profileLabelBox);
        profileTab.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Insets padding = new Insets(8, 10, 8, 15);
        VBox usernameBox = Lantern.createInfoVBox("USERNAME: ", User.getCurrentUser().getUsername(), padding);
        VBox roleBox = Lantern.createInfoVBox("ROLE: ", User.getCurrentUser().getRole(), padding);
        VBox emailBox = Lantern.createInfoVBox("EMAIL: ", User.getCurrentUser().getEmail(), padding);
        VBox locationCoordinateBox = Lantern.createInfoVBox("LOCATION: ", "("+User.getCurrentUser().getX()+", "+ User.getCurrentUser().getY()+")", padding);

        VBox profileContents = new VBox();
        profileContents.getChildren().addAll(usernameBox, roleBox, emailBox, locationCoordinateBox);
        profileContents.getStyleClass().add("content_box_background");
        profileContents.maxWidthProperty().bind(profileTab.widthProperty().multiply(0.9));
        profileContents.setPadding(new Insets(30, 10, 30, 10));

        VBox aboutVBox = new VBox();
        String about[] = {"Tell us about yourself!"};
        try {
            about[0] = AboutDatabase.getAbout(User.getCurrentUser().getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Button editAboutButton = new Button("Edit");
        HBox aboutHBox = new HBox();
        VBox aboutContents = Lantern.createInfoVBox("ABOUT: ", about[0], padding);
        aboutHBox.getChildren().addAll(aboutContents, Lantern.createSpacer(), editAboutButton);
        editAboutButton.getStyleClass().add("edit_button");
        editAboutButton.setOnAction(e -> {
            VBox overlay = new VBox();
            VBox editAboutVBox = new VBox();
            Text editAboutLabel = new Text("Edit About");
            editAboutLabel.getStyleClass().add("title");
            editAboutVBox.getChildren().add(editAboutLabel);
            editAboutVBox.getChildren().add(Lantern.createHorizontalSeparator(2));
            TextArea aboutTextArea = new TextArea();
            aboutTextArea.getStyleClass().add("text_area");
            aboutTextArea.setText(about[0]);
            aboutTextArea.setWrapText(true);
            aboutTextArea.setPadding(new Insets(10, 10, 10, 10));
            Button saveButton = new Button("Save");
            saveButton.getStyleClass().add("save_button");
            saveButton.setOnAction(event -> {
                AboutDatabase.addOrUpdateAbout(User.getCurrentUser().getUsername(), aboutTextArea.getText());
                about[0] = aboutTextArea.getText();
                aboutContents.getChildren().clear();
                aboutContents.getChildren().add(Lantern.createInfoVBox("ABOUT: ", about[0], padding));
                stackpane.getChildren().remove(editAboutVBox);
            });
            Button cancelButton = new Button("Cancel");
            cancelButton.getStyleClass().add("cancel_button");
            cancelButton.setOnAction(event -> stackpane.getChildren().remove(overlay));
            HBox buttonBox = new HBox();
            buttonBox.getChildren().addAll(saveButton, cancelButton);
            buttonBox.setSpacing(10);
            buttonBox.setPadding(new Insets(10, 10, 10, 10));
            buttonBox.setAlignment(Pos.CENTER);
            editAboutVBox.getChildren().add(aboutTextArea);
            editAboutVBox.getChildren().add(buttonBox);
            editAboutVBox.setAlignment(Pos.CENTER);
            editAboutVBox.setPadding(new Insets(30, 10, 30, 10));
            editAboutVBox.getStyleClass().add("content_box_background");
            
            overlay.getChildren().add(editAboutVBox);

            overlay.getStyleClass().add("background_transparent");
            overlay.minHeightProperty().bind(stackpane.heightProperty().multiply(0.85));
            overlay.minWidthProperty().bind(stackpane.widthProperty().multiply(0.75));
            overlay.maxHeightProperty().bind(stackpane.heightProperty().multiply(0.85));
            overlay.maxWidthProperty().bind(stackpane.widthProperty().multiply(0.75));

            VBox backgroundBlocker = new VBox();
            backgroundBlocker.getStyleClass().add("background_transparent");
            backgroundBlocker.minHeightProperty().bind(stackpane.heightProperty());
            backgroundBlocker.minWidthProperty().bind(stackpane.widthProperty());
            backgroundBlocker.maxHeightProperty().bind(stackpane.heightProperty());
            backgroundBlocker.maxWidthProperty().bind(stackpane.widthProperty());
            backgroundBlocker.setOnMouseClicked(event -> {
                stackpane.getChildren().remove(overlay);
                stackpane.getChildren().remove(backgroundBlocker);
            });
            stackpane.getStyleClass().add("background_transparent");
            stackpane.getChildren().add(backgroundBlocker);
            stackpane.getChildren().add(overlay);
        });
        
        aboutVBox.getChildren().addAll(aboutHBox);
        aboutVBox.getStyleClass().add("content_box_background");
        aboutVBox.maxWidthProperty().bind(profileTab.widthProperty().multiply(0.9));
        aboutVBox.setPadding(new Insets(10, 10, 30, 10));

        accessManager.getAccessiblePrivateProfile(accessManager.getUserRole(User.getCurrentUser())).forEach(
                    vBoxSupplier -> profileContents.getChildren().add(vBoxSupplier.get()));
        accessManager.getAccessiblePublicProfile(accessManager.getUserRole(User.getCurrentUser())).forEach(
                    vBoxSupplier -> profileContents.getChildren().add(vBoxSupplier.get()));

        profileTab.getChildren().addAll(profileContents, aboutVBox);
        profileTab.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        stackpane.getChildren().add(profileTab);
        profileRootBox.getChildren().add(stackpane);
        profileRootBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        return profileRootBox;
    }

    public static VBox loadOthersProfileTab(User user){
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
        VBox profileTab = new VBox(10);
        Label profileLabel = new Label(user.getUsername() + "'s Profile");
        profileLabel.getStyleClass().add("title");
        HBox profileLabelBox = new HBox();
        profileLabelBox.getChildren().add(profileLabel);
        profileLabelBox.setPadding(new Insets(12, 0, 12, 12));

        profileTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        profileTab.getChildren().add(profileLabelBox);
        profileTab.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Insets padding = new Insets(8, 10, 8, 15);
        VBox usernameBox = Lantern.createInfoVBox("USERNAME: ", user.getUsername(), padding);
        VBox roleBox = Lantern.createInfoVBox("ROLE: ", Lantern.capitalizeFirstLetter(user.getRole()), padding);

        VBox profileContents = new VBox();
        
        profileContents.getStyleClass().add("content_box_background");
        profileContents.maxWidthProperty().bind(profileTab.widthProperty().multiply(0.9));
        profileContents.setPadding(new Insets(30, 10, 30, 10));

        profileTab.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        profileContents.getChildren().addAll(usernameBox, roleBox);
        accessManager.getAccessiblePublicProfile(accessManager.getUserRole(user)).forEach(
                            vBoxSupplier -> profileContents.getChildren().add(vBoxSupplier.get()));
                            
        boolean isBothStudents = User.getCurrentUser().getRole().toLowerCase().equals("student") && user.getRole().toLowerCase().equals("student");
        boolean isParentStudent = User.getCurrentUser().getRole().toLowerCase().equals("parent") && user.getRole().toLowerCase().equals("student") ;

        if(isBothStudents){
            VBox commonFriendsBox = Lantern.createInfoVBox("NUMBER OF FRIENDS IN COMMON: ", FriendList.findCommonFriends(User.getCurrentUser().getUsername(), user.getUsername()).size(), padding);
            profileContents.getChildren().add(commonFriendsBox);
        }
        if(isBothStudents && !friend.checkExistingFriend(conn, user.getUsername(), User.getCurrentUser().getUsername())){
            Button addFriendButton = new Button("Send Friend Request");
            addFriendButton.getStyleClass().add("add_friend_button");
            addFriendButton.setOnAction(e -> {
                friend.friendRequest(conn, user.getUsername(), User.getCurrentUser().getUsername());
            });
            profileContents.getChildren().add(addFriendButton);
        }
        if(isParentStudent && !ParentChildren.checkExistingChildren(conn, user.getUsername(), User.getCurrentUser().getUsername())){
            Button parentButton = new Button("Request to be Parent");
            parentButton.getStyleClass().add("add_friend_button");
            parentButton.setOnAction(e -> {
                ParentChildren.request(conn, user.getUsername(), User.getCurrentUser().getUsername());
            });
            profileContents.getChildren().add(parentButton);
        }
        
        profileTab.getChildren().addAll(profileContents);
        return profileTab;
    }
    private static VBox createPendingRequestsVBox(){
        VBox pendingRequestsVBox = new VBox();      
        ArrayList<String> pendingRequests = ParentChildren.showRequestParent(conn, User.getCurrentUser().getUsername());
        Label pendingRequestsLabel = new Label("Pending Requests");
        pendingRequestsLabel.getStyleClass().add("title");
        pendingRequestsLabel.setPadding(new Insets(12, 0, 12, 12));
        pendingRequestsVBox.getChildren().addAll(pendingRequestsLabel, Lantern.createHorizontalSeparator(2));
        for (String pendingRequest : pendingRequests) {
            HBox pendingRequestHBox = new HBox();
            Label pendingRequestLabel = new Label(pendingRequest);
            pendingRequestLabel.getStyleClass().add("text_content_black");
            Button acceptButton = new Button();
            acceptButton.setPrefSize(30, 30);
            acceptButton.getStyleClass().add("accept_button");
            acceptButton.setOnAction(e -> {
                ParentChildren.acceptRequest(conn, User.getCurrentUser().getUsername(), pendingRequest);
                pendingRequestsVBox.getChildren().remove(pendingRequestHBox);
            });
            Button declineButton = new Button();
            declineButton.setPrefSize(30, 30);
            declineButton.getStyleClass().add("decline_button");
            declineButton.setOnAction(e -> {
                ParentChildren.declineRequest(conn, User.getCurrentUser().getUsername(), pendingRequest);
                pendingRequestsVBox.getChildren().remove(pendingRequestHBox);
                refreshProfile();
            });
            pendingRequestHBox.getChildren().addAll(pendingRequestLabel, Lantern.createSpacer(), acceptButton, declineButton);
            pendingRequestsVBox.getChildren().add(pendingRequestHBox);
        }
        if(pendingRequests.isEmpty()){
            Label noFriendRequestsLabel = new Label("No pending requests");
            noFriendRequestsLabel.getStyleClass().add("text_black");
            noFriendRequestsLabel.setPadding(new Insets(12, 0, 12, 12));
            pendingRequestsVBox.getChildren().add(noFriendRequestsLabel);
        }
        return pendingRequestsVBox;
    }

}
