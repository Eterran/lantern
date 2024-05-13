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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Sidebar {
    private static VBox[] pages = new VBox[10];
    private static Stack<Integer> sidebarHistory = new Stack<Integer>();
    private static int currentPage = 1;

    private final static AccessManager accessManager = new AccessManager();

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

    private static VBox retractedVBox = new VBox();
    private static ImageView profileIcon = new ImageView(new Image("resources/assets/profile_icon.png"));

    private static VBox profileBox = Profile.loadProfileTab();
    private static VBox eventBox = EventPage.viewEventTab();
    private static VBox discussionBox = new VBox();
    private static VBox leaderboardBox = GlobalLeaderboard.globalLeaderBoardTab();
    private static VBox box5 = new VBox(10);
    private static VBox box6 = new VBox(10);
    private static VBox box7 = new VBox(10);



    public static void showHomeScene(Stage stg){
        Lantern.Clear_History();

        root.setBackground(new Background(new BackgroundFill(Color.web(color.BACKGROUND.getCode()), new CornerRadii(0), Insets.EMPTY)));
        Image image = new Image("resources/assets/back_arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Button backButton = new Button();
        backButton.setGraphic(imageView);
        backButton.getStyleClass().add("back_button");
        HBox layout1 = new HBox(0);
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
        Button retractButton = new Button();
        Button fakeRetractButton = new Button();
        Image sidebarImage = new Image("resources/assets/sidebar_icon.png");
        ImageView sideBarImageView = new ImageView(sidebarImage);
        sideBarImageView.setFitHeight(30);
        sideBarImageView.setFitWidth(30);
        retractButton.setGraphic(sideBarImageView);
        retractButton.getStyleClass().add("back_button");
        
        ImageView fakeSideBarImageView = new ImageView(sidebarImage);
        fakeSideBarImageView.setFitHeight(30);
        fakeSideBarImageView.setFitWidth(30);
        fakeRetractButton.setGraphic(fakeSideBarImageView);
        fakeRetractButton.getStyleClass().add("back_button");
        
        HBox backAndRetract = new HBox();
        
        retractedVBox.setBackground(new Background(new BackgroundFill(Color.web(color.SIDEBAR.getCode()), new CornerRadii(0), Insets.EMPTY)));
        retractedVBox.setMaxWidth(60);
        retractedVBox.setMinWidth(60);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), sidebar);
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateAvailableWidth(isSidebarRetracted.get());
        });
        
        retractButton.setOnAction(e -> {
            boolean isNowRetracted = !isSidebarRetracted.getAndSet(!isSidebarRetracted.get());
            if (isNowRetracted) {
                retractedVBox.getChildren().add(retractButton);
                backAndRetract.getChildren().add(fakeRetractButton);
                tt.setToX(-(sidebarWidth- 60));
            } else {
                backAndRetract.getChildren().remove(fakeRetractButton);
                backAndRetract.getChildren().add(retractButton);
                root.setLeft(sidebar);
                tt.setToX(0);
            }
            tt.playFromStart();
            updateAvailableWidth(isNowRetracted);
        });

        HBox searcHBox = createSearchBox();
        
        StackPane stackPane = new StackPane();
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        stackPane.getChildren().addAll(profileBox, eventBox, discussionBox, leaderboardBox, box5, box6, box7);

        profileBox.prefWidthProperty().bind(stackPane.widthProperty());
        eventBox.prefWidthProperty().bind(stackPane.widthProperty());
        discussionBox.prefWidthProperty().bind(stackPane.widthProperty());
        leaderboardBox.prefWidthProperty().bind(stackPane.widthProperty());
        box5.prefWidthProperty().bind(stackPane.widthProperty());
        box6.prefWidthProperty().bind(stackPane.widthProperty());
        box7.prefWidthProperty().bind(stackPane.widthProperty());
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

        backButton.setOnAction(e -> {
            goBackSidebar();
        });
        tab1.setOnAction(e -> {
            push_SidebarHistory(1);
            setOneVisible(1);
        });
        tab2.setOnAction(e -> {
            push_SidebarHistory(2);
            setOneVisible(2);
        });
        tab3.setOnAction(e -> {
            push_SidebarHistory(3);
            setOneVisible(3);
        });
        tab4.setOnAction(e -> {
            push_SidebarHistory(4);
            setOneVisible(4);
        });
        
        layout1.getChildren().addAll(stackPane);
        tab1.setMaxWidth(Double.MAX_VALUE);
        tab2.setMaxWidth(Double.MAX_VALUE);
        tab3.setMaxWidth(Double.MAX_VALUE);
        tab4.setMaxWidth(Double.MAX_VALUE);
        
        backAndRetract.getChildren().addAll(backButton, retractButton);
        HBox.setHgrow(backButton, Priority.ALWAYS);
        backButton.setMaxWidth(Double.MAX_VALUE);
        sidebar.getChildren().addAll(backAndRetract, searcHBox, Lantern.createHorizontalSeparator(6), tab1, Lantern.createHorizontalSeparator(6), tab2, Lantern.createHorizontalSeparator(6), tab3, Lantern.createHorizontalSeparator(6), tab4);
        accessManager.getAccessibleButtons(accessManager.getUserRole(User.getCurrentUser())).forEach(buttonSupplier -> {
            sidebar.getChildren().add(Lantern.createHorizontalSeparator(6));
            sidebar.getChildren().add(buttonSupplier.get());
        });
        accessManager.getAccessibleSidebar1(accessManager.getUserRole(User.getCurrentUser())).forEach(
                    sidebarSupplier -> box5.getChildren().add(sidebarSupplier.get()));
        accessManager.getAccessibleSidebar2(accessManager.getUserRole(User.getCurrentUser())).forEach(
                    sidebarSupplier -> box6.getChildren().add(sidebarSupplier.get()));
        pages[1] = profileBox;
        pages[2] = eventBox;
        pages[3] = discussionBox;
        pages[4] = leaderboardBox;
        pages[5] = box5;
        pages[6] = box6;
        pages[7] = box7;
        setOneVisible(1);
        Platform.runLater(() -> {
            updateAvailableWidth(isSidebarRetracted.get());
            sidebarWidth = sidebar.getWidth();
        });
        tt.setOnFinished(e -> {
            layout1.setPrefWidth(availableWidth.get());
            if (isSidebarRetracted.get()) {
                backAndRetract.getChildren().remove(retractButton);
                root.setLeft(retractedVBox);
            }
        });
        root.setCenter(layout1);
        root.setLeft(sidebar);
        Scene scene1 = new Scene(root, 1200, 700);
        tab1.getStyleClass().add("sidebar_button");
        tab2.getStyleClass().add("sidebar_button");
        tab3.getStyleClass().add("sidebar_button");
        tab4.getStyleClass().add("sidebar_button");
        scene1.getStylesheets().add("resources/style.css");
        stg.setScene(scene1);
    }
    public static void setOneVisible(int index){
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
    public static void push_SidebarHistory(int index){
        if(sidebarHistory.isEmpty() || sidebarHistory.peek() != index)
            sidebarHistory.push(index);
    }
    public static AccessManager getAccessManager(){
        return accessManager;
    }

    private static void updateAvailableWidth(boolean isRetracted) {
        double offsetWidth = 60;
        if(isRetracted) {
            availableWidth.set(root.getWidth() - offsetWidth);
        } else {
            availableWidth.set(root.getWidth() - sidebarWidth);
        }
    }
    private static HBox createSearchBox(){
        HBox searchBox = new HBox();
        searchBox.setPadding(new Insets(0, 0, 0, 0));
        TextField searchField = new TextField();
        searchField.setPromptText("Search Users");
        searchField.getStyleClass().add("search_field");
        searchField.setMaxSize(sidebarWidth, Double.MAX_VALUE);
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
        searchBox.getChildren().addAll(searchField, Lantern.createVerticalSeparator(2),  searchButton);
        return searchBox;
    }
}
