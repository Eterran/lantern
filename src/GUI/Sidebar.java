package GUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import Database.Database;
import Database.Login_Register;
import Database.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Sidebar {
    private static VBox[] pages = new VBox[10];
    private static Button[] tabs = new Button[10];
    private static Button[] rtabs = new Button[10];
    private static Stack<Integer> sidebarHistory = new Stack<Integer>();
    private static int currentPage = 1;

    private static Connection conn = Lantern.getConn();

    final static AtomicBoolean isSidebarRetracted = new AtomicBoolean(false);
    private static VBox sidebar = new VBox(0);
    private static BorderPane root = new BorderPane();
    private static DoubleProperty availableWidth = new SimpleDoubleProperty();
    private static double sidebarWidth = 200;
    private static Button tab1 = new Button("Profile");
    private static Button tab2 = new Button("Events");
    private static Button tab3 = new Button("Discussion");
    private static Button tab4 = new Button("Global Leaderboard");
    private static Button tab5 = new Button();
    private static Button tab6 = new Button();
    private static Button rtab1 = new Button();
    private static Button rtab2 = new Button();
    private static Button rtab3 = new Button();
    private static Button rtab4 = new Button();
    private static Button rtab5 = new Button();
    private static Button rtab6 = new Button();

    private static TranslateTransition tt = new TranslateTransition(Duration.millis(1000), sidebar);
    private static VBox retractedVBox = new VBox();

    private final static AccessManager accessManager = new AccessManager();

    private static StackPane stackPane = new StackPane();
    private static VBox profileBox = Profile.loadProfileTab();
    private static VBox eventBox = EventPage.viewEventTab();
    private static VBox discussionBox = DiscussionPage.createDiscussionPage();
    private static VBox leaderboardBox = GlobalLeaderboard.globalLeaderBoardTab();
    private static VBox box5 = new VBox(10);
    private static VBox box6 = new VBox(10);
    private static VBox box7 = new VBox(10);

    public static void showHomeScene(Stage stg){
        Lantern.Clear_History();

        initialiseButtons();
        initialseVBoxes();
        
        root.setBackground(new Background(new BackgroundFill(Color.web(color.BACKGROUND.getCode()), new CornerRadii(0), Insets.EMPTY)));
        HBox layout1 = new HBox(0);
        layout1.getChildren().addAll(stackPane);
        layout1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        layout1.setBackground(new Background(new BackgroundFill(Color.web(color.BACKGROUND.getCode()), new CornerRadii(0), Insets.EMPTY)));
        LinearGradient gradient = new LinearGradient(
            0.6,
            0,
            1,
            0,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(color.SIDEBAR.getCode())),
            new Stop(1, Color.web(color.BACKGROUND.getCode()))
        );
        sidebar.setBackground(new Background(new BackgroundFill(gradient, new CornerRadii(0), Insets.EMPTY)));
        sidebar.setMaxWidth(Double.MAX_VALUE);
        Button retractButton = createRetractButton();
        Button retractedRetractButton = createRetractButton();
        
        HBox backAndRetract = new HBox();
        
        retractedVBox.setBackground(new Background(new BackgroundFill(Color.web(color.SIDEBAR.getCode()), new CornerRadii(0), Insets.EMPTY)));
        retractedVBox.setMaxWidth(60);
        retractedVBox.setMinWidth(60);

        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        tt.setOnFinished(e -> {
            layout1.setPrefWidth(availableWidth.get());
            if (isSidebarRetracted.get()) {
                root.setLeft(retractedVBox);
            }
        });
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateAvailableWidth(isSidebarRetracted.get());
        });
        
        HBox searcHBox = createSearchBox();

        HBox.setHgrow(stackPane, Priority.ALWAYS);
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        stackPane.getChildren().addAll(profileBox, eventBox, discussionBox, leaderboardBox, box5, box6, box7);
        
        Button backButton = createBackButton();
        backAndRetract.getChildren().addAll(backButton, retractButton);
        HBox.setHgrow(backButton, Priority.ALWAYS);
        backButton.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(backAndRetract, Lantern.createHorizontalSeparator(8), searcHBox, Lantern.createHorizontalSeparator(6), tab1, Lantern.createHorizontalSeparator(6), tab2, Lantern.createHorizontalSeparator(6), tab3, Lantern.createHorizontalSeparator(6), tab4);
        retractedVBox.getChildren().addAll(retractedRetractButton, rtab1, Lantern.createHorizontalSeparator(6), rtab2, Lantern.createHorizontalSeparator(6), rtab3, Lantern.createHorizontalSeparator(6), rtab4);
        
        accessManager.getAccessibleButtons1(accessManager.getUserRole(User.getCurrentUser())).forEach(buttonSupplier -> {
            sidebar.getChildren().add(Lantern.createHorizontalSeparator(6));
            tab5 = buttonSupplier.get();
            sidebar.getChildren().add(tab5);
        });
        accessManager.getAccessibleButtons2(accessManager.getUserRole(User.getCurrentUser())).forEach(buttonSupplier -> {
            sidebar.getChildren().add(Lantern.createHorizontalSeparator(6));
            tab6 = buttonSupplier.get();
            sidebar.getChildren().add(tab6);
        });
        accessManager.getAccessibleSidebar1(accessManager.getUserRole(User.getCurrentUser())).forEach(
                sidebarSupplier -> 
                    box5.getChildren().add(sidebarSupplier.get()));
        accessManager.getAccessibleSidebar2(accessManager.getUserRole(User.getCurrentUser())).forEach(
                sidebarSupplier -> 
                    box6.getChildren().add(sidebarSupplier.get()));
        accessManager.getAccessibleRetractedButtons1(accessManager.getUserRole(User.getCurrentUser())).forEach(
                buttonSupplier -> {
                    retractedVBox.getChildren().add(Lantern.createHorizontalSeparator(6));
                    rtab5 = buttonSupplier.get();
                    retractedVBox.getChildren().add(rtab5);
                });
        accessManager.getAccessibleRetractedButtons2(accessManager.getUserRole(User.getCurrentUser())).forEach(
                buttonSupplier -> {
                    retractedVBox.getChildren().add(Lantern.createHorizontalSeparator(6));
                    rtab6 = buttonSupplier.get();
                    retractedVBox.getChildren().add(rtab6);
                });
        
        initialiseArrays();

        Platform.runLater(() -> {
            updateAvailableWidth(isSidebarRetracted.get());
            sidebarWidth = sidebar.getWidth();
        });
        
        root.setCenter(layout1);
        root.setLeft(sidebar);
        Scene scene1 = new Scene(root, 1200, 700);
        
        scene1.getStylesheets().add("resources/style.css");
        stg.setScene(scene1);
    }


    // sidebar navigation functions
    private static void setOneVisible(int index){
        for(int i = 1; i <= pages.length; i++){
            if(pages[i] == null)
                break;
            if(i == index){
                pages[i].setVisible(true);
            } else {
                pages[i].setVisible(false);
            }
        }
        currentPage = index;
        if(sidebarHistory.isEmpty()){
            sidebarHistory.push(index);
        }
    }
    private static boolean goBackSidebar(){
        if(!sidebarHistory.isEmpty() && currentPage == sidebarHistory.peek()){
            sidebarHistory.pop();
        }
        if(!sidebarHistory.isEmpty()){
            setOneVisible(sidebarHistory.pop());
            return true;
        }
        return false;
    }
    private static void push_SidebarHistory(int index){
        if(sidebarHistory.isEmpty() || sidebarHistory.peek() != index)
            sidebarHistory.push(index);
    }
    private static void setSelectedButton(int index){
        for(int i = 1; i <= tabs.length; i++){
            if(tabs[i] == null)
                break;
            if(i == index){
                tabs[i].getStyleClass().clear();
                tabs[i].getStyleClass().add("sidebar_button_selected");
                rtabs[i].getStyleClass().clear();;
                rtabs[i].getStyleClass().add("sidebar_button_selected");
            } else {
                tabs[i].getStyleClass().clear();
                tabs[i].getStyleClass().add("sidebar_button");
                rtabs[i].getStyleClass().clear();
                rtabs[i].getStyleClass().add("sidebar_button");
            }
        }
    }
    public static void selectTab(int ind){
        push_SidebarHistory(ind);
        setOneVisible(ind);
        setSelectedButton(ind);
    }
    private static void updateAvailableWidth(boolean isRetracted) {
        double offsetWidth = 60;
        if(isRetracted) {
            availableWidth.set(root.getWidth() - offsetWidth);
        } else {
            availableWidth.set(root.getWidth() - sidebarWidth);
        }
    }


    //create sidebar components
    private static Button createRetractButton(){
        Button retractButton = new Button();
        Image sidebarImage = new Image("resources/assets/sidebar_icon.png");
        ImageView sideBarImageView = new ImageView(sidebarImage);
        sideBarImageView.setFitHeight(30);
        sideBarImageView.setFitWidth(30);
        retractButton.setGraphic(sideBarImageView);
        retractButton.getStyleClass().add("back_button");

        retractButton.setOnAction(e -> {
            boolean isNowRetracted = !isSidebarRetracted.getAndSet(!isSidebarRetracted.get());
            if (isNowRetracted) {
                tt.setToX(-(sidebarWidth- 60));
            } else {
                root.setLeft(sidebar);
                tt.setToX(0);
            }
            tt.playFromStart();
            updateAvailableWidth(isNowRetracted);
        });
        return retractButton;
    }
    private static HBox createSearchBox(){
        HBox searchBox = new HBox();
        searchBox.setPadding(new Insets(0, 0, 2, 0));
        TextField searchField = new TextField();
        searchField.setPromptText("Search Users");
        searchField.getStyleClass().add("search_field");
        searchField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        Image searchImage = new Image("resources/assets/search_icon.png");
        ImageView searchImageView = new ImageView(searchImage);
        searchImageView.setFitHeight(22);
        searchImageView.setFitWidth(22);
        Button searchButton = new Button();
        searchButton.setGraphic(searchImageView);
        searchButton.getStyleClass().add("search_button");
        searchButton.setOnAction(e -> {
            String search = searchField.getText();
            if(search != null && !search.isEmpty()){
                try {
                    if(Database.usernameExists(conn, search)){
                        box7.getChildren().clear();
                        box7.getChildren().add(Profile.loadOthersProfileTab(Login_Register.getUser(search, conn)));
                        pages[7] = box7;
                        setOneVisible(7);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("User not found");
                        alert.setContentText("The user you are trying to find does not exist.");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchBox.getChildren().addAll(searchField, Lantern.createVerticalSeparator(0),  searchButton);
        searchBox.setAlignment(Pos.CENTER);
        return searchBox;
    }
    private static Button createBackButton(){
        ImageView imageView = new ImageView(new Image("resources/assets/back_arrow.png"));
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Button backButton = new Button();
        backButton.setGraphic(imageView);
        backButton.getStyleClass().add("back_button");
        backButton.setOnAction(e -> {
            goBackSidebar();
        });
        return backButton;
    }

    // Initialisation
    private static void initialseVBoxes(){
        profileBox.prefWidthProperty().bind(stackPane.widthProperty());
        eventBox.prefWidthProperty().bind(stackPane.widthProperty());
        discussionBox.prefWidthProperty().bind(stackPane.widthProperty());
        leaderboardBox.prefWidthProperty().bind(stackPane.widthProperty());
        box5.prefWidthProperty().bind(stackPane.widthProperty());
        box6.prefWidthProperty().bind(stackPane.widthProperty());
        box7.prefWidthProperty().bind(stackPane.widthProperty());
        profileBox.prefHeightProperty().bind(stackPane.heightProperty());
        eventBox.prefHeightProperty().bind(stackPane.heightProperty());
        discussionBox.prefHeightProperty().bind(stackPane.heightProperty());
        leaderboardBox.prefHeightProperty().bind(stackPane.heightProperty());
        box5.prefHeightProperty().bind(stackPane.heightProperty());
        box6.prefHeightProperty().bind(stackPane.heightProperty());
        box7.prefHeightProperty().bind(stackPane.heightProperty());
        VBox.setVgrow(profileBox, Priority.ALWAYS);
        VBox.setVgrow(eventBox, Priority.ALWAYS);
        VBox.setVgrow(discussionBox, Priority.ALWAYS);
        VBox.setVgrow(leaderboardBox, Priority.ALWAYS);
        VBox.setVgrow(box5, Priority.ALWAYS);
        VBox.setVgrow(box6, Priority.ALWAYS);
        VBox.setVgrow(box7, Priority.ALWAYS);
        profileBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        eventBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        discussionBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        leaderboardBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box5.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box6.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box7.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
    private static void initialiseButtons(){
        tab1 = new Button();
        tab2 = new Button();
        tab3 = new Button();
        tab4 = new Button();

        HBox hbox = new HBox();
        Text profileText = new Text("Profile");
        profileText.setFill(Color.WHITE);
        hbox.getChildren().addAll(getProfileIcon(), Lantern.createVerticalSeparator(8), profileText);
        tab1.setGraphic(hbox);
        hbox = new HBox();
        Text eventsText = new Text("Events");
        eventsText.setFill(Color.WHITE);
        hbox.getChildren().addAll(getEventIcon(), Lantern.createVerticalSeparator(8), eventsText);
        tab2.setGraphic(hbox);
        hbox = new HBox();
        Text discussionText = new Text("Discussion");
        discussionText.setFill(Color.WHITE);
        hbox.getChildren().addAll(getDiscussionIcon(), Lantern.createVerticalSeparator(8), discussionText);
        tab3.setGraphic(hbox);
        hbox = new HBox();
        Text leaderboardText = new Text("Global Leaderboard");
        leaderboardText.setFill(Color.WHITE);
        hbox.getChildren().addAll(getLeaderboardIcon(), Lantern.createVerticalSeparator(8), leaderboardText);
        tab4.setGraphic(hbox);

        rtab1.setGraphic(getRProfileIcon());
        rtab2.setGraphic(getREventIcon());
        rtab3.setGraphic(getRDiscussionIcon());
        rtab4.setGraphic(getRLeaderboardIcon());

        tab1.getStyleClass().add("sidebar_button");
        tab2.getStyleClass().add("sidebar_button");
        tab3.getStyleClass().add("sidebar_button");
        tab4.getStyleClass().add("sidebar_button");
        rtab1.getStyleClass().add("sidebar_button");
        rtab2.getStyleClass().add("sidebar_button");
        rtab3.getStyleClass().add("sidebar_button");
        rtab4.getStyleClass().add("sidebar_button");
        tab1.setMaxWidth(Double.MAX_VALUE);
        tab2.setMaxWidth(Double.MAX_VALUE);
        tab3.setMaxWidth(Double.MAX_VALUE);
        tab4.setMaxWidth(Double.MAX_VALUE);
        rtab1.setMaxWidth(Double.MAX_VALUE);
        rtab2.setMaxWidth(Double.MAX_VALUE);
        rtab3.setMaxWidth(Double.MAX_VALUE);
        rtab4.setMaxWidth(Double.MAX_VALUE);

        tab1.setOnAction(e -> {
            selectTab(1);
        });
        tab2.setOnAction(e -> {
            selectTab(2);
        });
        tab3.setOnAction(e -> {
            selectTab(3);
        });
        tab4.setOnAction(e -> {
            selectTab(4);
        });
        rtab1.setOnAction(e -> {
            selectTab(1);
        });
        rtab2.setOnAction(e -> {
            selectTab(2);
        });
        rtab3.setOnAction(e -> {
            selectTab(3);
        });
        rtab4.setOnAction(e -> {
            selectTab(4);
        });
    }
    private static void initialiseArrays(){
        pages[1] = profileBox;
        pages[2] = eventBox;
        pages[3] = discussionBox;
        pages[4] = leaderboardBox;
        pages[5] = box5;
        pages[6] = box6;
        pages[7] = box7;
        
        tabs[1] = tab1;
        tabs[2] = tab2;
        tabs[3] = tab3;
        tabs[4] = tab4;
        tabs[5] = tab5;
        tabs[6] = tab6;

        rtabs[1] = rtab1;
        rtabs[2] = rtab2;
        rtabs[3] = rtab3;
        rtabs[4] = rtab4;
        rtabs[5] = rtab5;
        rtabs[6] = rtab6;

        setOneVisible(1);
        setSelectedButton(1);
    }

    // Getters
    public static AccessManager getAccessManager(){
        return accessManager;
    }
    public static ImageView getProfileIcon(){
        ImageView profileIcon = new ImageView(new Image("resources/assets/profile_icon.png"));
        profileIcon.setFitHeight(30);
        profileIcon.setFitWidth(30);
        return profileIcon;
    }
    public static ImageView getEventIcon(){
        ImageView eventIcon = new ImageView(new Image("resources/assets/events_icon.png"));
        eventIcon.setFitHeight(30);
        eventIcon.setFitWidth(30);
        return eventIcon;
    }
    public static ImageView getDiscussionIcon(){
        ImageView discussionIcon = new ImageView(new Image("resources/assets/discussion_icon.png"));
        discussionIcon.setFitHeight(30);
        discussionIcon.setFitWidth(30);
        return discussionIcon;
    }
    public static ImageView getLeaderboardIcon(){
        ImageView leaderboardIcon = new ImageView(new Image("resources/assets/leaderboard_icon.png"));
        leaderboardIcon.setFitHeight(30);
        leaderboardIcon.setFitWidth(30);
        return leaderboardIcon;
    }
    public static ImageView getCreateEventIcon(){
        ImageView createEventIcon = new ImageView(new Image("resources/assets/create_event_icon.png"));
        createEventIcon.setFitHeight(30);
        createEventIcon.setFitWidth(30);
        return createEventIcon;
    }
    public static ImageView getQuizzesIcon(){
        ImageView quizzesIcon = new ImageView(new Image("resources/assets/quizzes_icon.png"));
        quizzesIcon.setFitHeight(30);
        quizzesIcon.setFitWidth(30);
        return quizzesIcon;
    }
    public static ImageView getCreateQuizzesIcon(){
        ImageView createQuizzesIcon = new ImageView(new Image("resources/assets/create_quizzes_icon.png"));
        createQuizzesIcon.setFitHeight(30);
        createQuizzesIcon.setFitWidth(30);
        return createQuizzesIcon;
    }
    public static ImageView getDestinationsIcon(){
        ImageView destinationsIcon = new ImageView(new Image("resources/assets/destinations_icon.png"));
        destinationsIcon.setFitHeight(30);
        destinationsIcon.setFitWidth(30);
        return destinationsIcon;
    }
    public static ImageView getBookingsIcon(){
        ImageView bookingsIcon = new ImageView(new Image("resources/assets/bookings_icon.png"));
        bookingsIcon.setFitHeight(30);
        bookingsIcon.setFitWidth(30);
        return bookingsIcon;
    }
    public static ImageView getFriendlistIcon(){
        ImageView friendlistIcon = new ImageView(new Image("resources/assets/friendlist_icon.png"));
        friendlistIcon.setFitHeight(30);
        friendlistIcon.setFitWidth(30);
        return friendlistIcon;
    }
    public static ImageView getRProfileIcon(){
        ImageView rprofileIcon = new ImageView(new Image("resources/assets/profile_icon.png"));
        rprofileIcon.setFitHeight(30);
        rprofileIcon.setFitWidth(30);
        return rprofileIcon;
    }
    public static ImageView getREventIcon(){
        ImageView reventIcon = new ImageView(new Image("resources/assets/events_icon.png"));
        reventIcon.setFitHeight(30);
        reventIcon.setFitWidth(30);
        return reventIcon;
    }
    public static ImageView getRDiscussionIcon(){
        ImageView rdiscussionIcon = new ImageView(new Image("resources/assets/discussion_icon.png"));
        rdiscussionIcon.setFitHeight(30);
        rdiscussionIcon.setFitWidth(30);
        return rdiscussionIcon;
    }
    public static ImageView getRLeaderboardIcon(){
        ImageView rleaderboardIcon = new ImageView(new Image("resources/assets/leaderboard_icon.png"));
        rleaderboardIcon.setFitHeight(30);
        rleaderboardIcon.setFitWidth(30);
        return rleaderboardIcon;
    }
    public static ImageView getRCreateEventIcon(){
        ImageView rcreateEventIcon = new ImageView(new Image("resources/assets/create_event_icon.png"));
        rcreateEventIcon.setFitHeight(30);
        rcreateEventIcon.setFitWidth(30);
        return rcreateEventIcon;
    }
    public static ImageView getRQuizzesIcon(){
        ImageView rquizzesIcon = new ImageView(new Image("resources/assets/quizzes_icon.png"));
        rquizzesIcon.setFitHeight(30);
        rquizzesIcon.setFitWidth(30);
        return rquizzesIcon;
    }
    public static ImageView getRCreateQuizzesIcon(){
        ImageView rcreateQuizzesIcon = new ImageView(new Image("resources/assets/create_quizzes_icon.png"));
        rcreateQuizzesIcon.setFitHeight(30);
        rcreateQuizzesIcon.setFitWidth(30);
        return rcreateQuizzesIcon;
    }
    public static ImageView getRDestinationsIcon(){
        ImageView rdestinationsIcon = new ImageView(new Image("resources/assets/destinations_icon.png"));
        rdestinationsIcon.setFitHeight(30);
        rdestinationsIcon.setFitWidth(30);
        return rdestinationsIcon;
    }
    public static ImageView getRBookingsIcon(){
        ImageView rbookingsIcon = new ImageView(new Image("resources/assets/bookings_icon.png"));
        rbookingsIcon.setFitHeight(30);
        rbookingsIcon.setFitWidth(30);
        return rbookingsIcon;
    }
    public static ImageView getRFriendlistIcon(){
        ImageView rfriendlistIcon = new ImageView(new Image("resources/assets/friendlist_icon.png"));
        rfriendlistIcon.setFitHeight(30);
        rfriendlistIcon.setFitWidth(30);
        return rfriendlistIcon;
    }
    public static void setBox7(VBox box){
        box7.getChildren().clear();
        box7.getChildren().add(box);
        setOneVisible(7);
    }
    public static void setBox1(VBox box){
        box7.getChildren().clear();
        box7.getChildren().add(box);
        setOneVisible(1);
    }
}
